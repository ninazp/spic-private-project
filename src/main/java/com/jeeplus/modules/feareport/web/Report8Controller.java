/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.web;

import java.util.ArrayList;
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
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.feareport.entity.Report8;
import com.jeeplus.modules.feareport.service.Report8Service;

/**
 * 资金来源与运用表Controller
 * @author zp
 * @version 2017-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/report8")
public class Report8Controller extends BaseController {

	@Autowired
	private Report8Service report8Service;
	
	@ModelAttribute
	public Report8 get(@RequestParam(required=false) String id) {
		Report8 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = report8Service.get(id);
		}
		if (entity == null){
			entity = new Report8();
		}
		return entity;
	}
	
	/**
	 * 资金来源与运用表列表页面
	 */
	@RequiresPermissions("feareport:report8:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/report8List";
	}
	
		/**
	 * 资金来源与运用表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report8:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report8 report8, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report8> page = report8Service.findPage(new Page<Report8>(request, response), report8); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资金来源与运用表表单页面
	 */
	@RequiresPermissions(value={"feareport:report8:view","feareport:report8:add","feareport:report8:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Report8 report8, Model model) {
		model.addAttribute("report8", report8);
		return "modules/feareport/report8Form";
	}

	/**
	 * 保存资金来源与运用表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report8:add","feareport:report8:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report8 report8, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, report8)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		report8Service.save(report8);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存资金来源与运用表成功");
		return j;
	}
	
	/**
	 * 删除资金来源与运用表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report8:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report8 report8, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		report8Service.delete(report8);
		j.setMsg("删除资金来源与运用表成功");
		return j;
	}
	
	/**
	 * 批量删除资金来源与运用表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report8:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			report8Service.delete(report8Service.get(id));
		}
		j.setMsg("删除资金来源与运用表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report8:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Report8 report8, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资金来源与运用表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report8> page = report8Service.findPage(new Page<Report8>(request, response, -1), report8);
    		new ExportExcel("资金来源与运用表", Report8.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资金来源与运用表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:report8:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report8> list = ei.getDataList(Report8.class);
			for (Report8 report8 : list){
				try{
					report8Service.save(report8);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资金来源与运用表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资金来源与运用表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资金来源与运用表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report8/?repage";
    }
	
	/**
	 * 下载导入资金来源与运用表数据模板
	 */
	@RequiresPermissions("feareport:report8:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资金来源与运用表数据导入模板.xlsx";
    		List<Report8> list = Lists.newArrayList(); 
    		new ExportExcel("资金来源与运用表数据", Report8.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report8/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report8:add","feareport:report8:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();

		List<List<Double>> datas = report8Service.getReportDatas(ids);
		
		if(null == datas || datas.size()<1){
			j.setMsg("没有查询到报表信息");
			j.setSuccess(false);
			return j;
		}
		j.setMsg(datas.toString());
		j.setProjectId(ids);
		j.setSuccess(true);
		
		return j;
		
	}
	
	/**
	 * 获取项目数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report8:add","feareport:report8:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		FeaProjectB project = report8Service.getDefaultProject();
		// 倒叙排序去第一条作为默认值返回
		ids = project.getId();
		projectName = project.getProjectName();
		
		j.setMsg("");
		j.setProjectId(ids);
		j.setProjectName(projectName);
		j.setSuccess(true);
		
		return j;
	}

}