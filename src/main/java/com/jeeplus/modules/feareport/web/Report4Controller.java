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
import com.jeeplus.modules.feareport.entity.Report4;
import com.jeeplus.modules.feareport.service.Report4Service;

/**
 * 财务计划现金流量表Controller
 * @author zp
 * @version 2017-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/report4")
public class Report4Controller extends BaseController {

	@Autowired
	private Report4Service report4Service;
	
	@ModelAttribute
	public Report4 get(@RequestParam(required=false) String id) {
		Report4 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = report4Service.get(id);
		}
		if (entity == null){
			entity = new Report4();
		}
		return entity;
	}
	
	/**
	 * 财务计划现金流量表列表页面
	 */
	@RequiresPermissions("feareport:report4:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/report4List";
	}
	
		/**
	 * 财务计划现金流量表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report4:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report4 report4, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report4> page = report4Service.findPage(new Page<Report4>(request, response), report4); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑财务计划现金流量表表单页面
	 */
	@RequiresPermissions(value={"feareport:report4:view","feareport:report4:add","feareport:report4:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Report4 report4, Model model) {
		model.addAttribute("report4", report4);
		return "modules/feareport/report4Form";
	}

	/**
	 * 保存财务计划现金流量表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report4:add","feareport:report4:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report4 report4, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, report4)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		report4Service.save(report4);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存财务计划现金流量表成功");
		return j;
	}
	
	/**
	 * 删除财务计划现金流量表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report4:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report4 report4, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		report4Service.delete(report4);
		j.setMsg("删除财务计划现金流量表成功");
		return j;
	}
	
	/**
	 * 批量删除财务计划现金流量表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report4:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			report4Service.delete(report4Service.get(id));
		}
		j.setMsg("删除财务计划现金流量表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report4:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Report4 report4, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "财务计划现金流量表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report4> page = report4Service.findPage(new Page<Report4>(request, response, -1), report4);
    		new ExportExcel("财务计划现金流量表", Report4.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出财务计划现金流量表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:report4:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report4> list = ei.getDataList(Report4.class);
			for (Report4 report4 : list){
				try{
					report4Service.save(report4);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条财务计划现金流量表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条财务计划现金流量表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入财务计划现金流量表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report4/?repage";
    }
	
	/**
	 * 下载导入财务计划现金流量表数据模板
	 */
	@RequiresPermissions("feareport:report4:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "财务计划现金流量表数据导入模板.xlsx";
    		List<Report4> list = Lists.newArrayList(); 
    		new ExportExcel("财务计划现金流量表数据", Report4.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report4/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report4:add","feareport:report4:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, RedirectAttributes redirectAttributes) throws Exception{
		
		List<List<Double>> datas = report4Service.getReportDatas(ids);
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("datas", datas);
		//j.setBody(body);
		j.setMsg(datas.toString());
		j.setSuccess(true);
		return j;
	}

}