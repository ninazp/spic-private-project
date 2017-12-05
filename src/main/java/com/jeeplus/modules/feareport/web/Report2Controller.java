/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.feareport.entity.Report2;
import com.jeeplus.modules.feareport.service.Report2Service;

/**
 * 项目投资现金流量表Controller
 * @author zp
 * @version 2017-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/report2")
public class Report2Controller extends BaseController {

	@Autowired
	private Report2Service report2Service;
	
	@ModelAttribute
	public Report2 get(@RequestParam(required=false) String id) {
		Report2 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = report2Service.get(id);
		}
		if (entity == null){
			entity = new Report2();
		}
		return entity;
	}
	
	/**
	 * 项目投资现金流量表列表页面
	 */
	@RequiresPermissions("feareport:report2:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/report2List";
	}
	
		/**
	 * 项目投资现金流量表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report2:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report2 report2, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report2> page = report2Service.findPage(new Page<Report2>(request, response), report2); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑项目投资现金流量表表单页面
	 */
	@RequiresPermissions(value={"feareport:report2:view","feareport:report2:add","feareport:report2:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Report2 report2, Model model) {
		model.addAttribute("report2", report2);
		return "modules/feareport/report2Form";
	}

	/**
	 * 保存项目投资现金流量表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report2:add","feareport:report2:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report2 report2, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, report2)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		report2Service.save(report2);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存项目投资现金流量表成功");
		return j;
	}
	
	/**
	 * 删除项目投资现金流量表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report2:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report2 report2, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		report2Service.delete(report2);
		j.setMsg("删除项目投资现金流量表成功");
		return j;
	}
	
	/**
	 * 批量删除项目投资现金流量表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report2:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			report2Service.delete(report2Service.get(id));
		}
		j.setMsg("删除项目投资现金流量表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report2:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Report2 report2, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "项目投资现金流量表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report2> page = report2Service.findPage(new Page<Report2>(request, response, -1), report2);
    		new ExportExcel("项目投资现金流量表", Report2.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出项目投资现金流量表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:report2:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report2> list = ei.getDataList(Report2.class);
			for (Report2 report2 : list){
				try{
					report2Service.save(report2);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条项目投资现金流量表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条项目投资现金流量表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目投资现金流量表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report2/?repage";
    }
	
	/**
	 * 下载导入项目投资现金流量表数据模板
	 */
	@RequiresPermissions("feareport:report2:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目投资现金流量表数据导入模板.xlsx";
    		List<Report2> list = Lists.newArrayList(); 
    		new ExportExcel("项目投资现金流量表数据", Report2.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report2/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report2:add","feareport:report2:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, RedirectAttributes redirectAttributes) throws Exception{
		
		List<List<Double>> datas = report2Service.getReportDatas(ids);
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("datas", datas);
		//j.setBody(body);
		j.setMsg(datas.toString());
		j.setSuccess(true);
		return j;
	}

}