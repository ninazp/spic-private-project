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
import com.jeeplus.modules.feareport.entity.Report6;
import com.jeeplus.modules.feareport.service.Report6Service;

/**
 * 借款还本付息计划表Controller
 * @author zp
 * @version 2017-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/report6")
public class Report6Controller extends BaseController {

	@Autowired
	private Report6Service report6Service;
	
	@ModelAttribute
	public Report6 get(@RequestParam(required=false) String id) {
		Report6 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = report6Service.get(id);
		}
		if (entity == null){
			entity = new Report6();
		}
		return entity;
	}
	
	/**
	 * 借款还本付息计划表列表页面
	 */
	@RequiresPermissions("feareport:report6:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/report6List";
	}
	
		/**
	 * 借款还本付息计划表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report6:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report6 report6, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report6> page = report6Service.findPage(new Page<Report6>(request, response), report6); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑借款还本付息计划表表单页面
	 */
	@RequiresPermissions(value={"feareport:report6:view","feareport:report6:add","feareport:report6:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Report6 report6, Model model) {
		model.addAttribute("report6", report6);
		return "modules/feareport/report6Form";
	}

	/**
	 * 保存借款还本付息计划表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report6:add","feareport:report6:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report6 report6, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, report6)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		report6Service.save(report6);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存借款还本付息计划表成功");
		return j;
	}
	
	/**
	 * 删除借款还本付息计划表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report6:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report6 report6, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		report6Service.delete(report6);
		j.setMsg("删除借款还本付息计划表成功");
		return j;
	}
	
	/**
	 * 批量删除借款还本付息计划表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report6:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			report6Service.delete(report6Service.get(id));
		}
		j.setMsg("删除借款还本付息计划表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report6:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Report6 report6, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "借款还本付息计划表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report6> page = report6Service.findPage(new Page<Report6>(request, response, -1), report6);
    		new ExportExcel("借款还本付息计划表", Report6.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出借款还本付息计划表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:report6:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report6> list = ei.getDataList(Report6.class);
			for (Report6 report6 : list){
				try{
					report6Service.save(report6);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条借款还本付息计划表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条借款还本付息计划表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入借款还本付息计划表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report6/?repage";
    }
	
	/**
	 * 下载导入借款还本付息计划表数据模板
	 */
	@RequiresPermissions("feareport:report6:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "借款还本付息计划表数据导入模板.xlsx";
    		List<Report6> list = Lists.newArrayList(); 
    		new ExportExcel("借款还本付息计划表数据", Report6.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report6/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report6:add","feareport:report6:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();

		List<List<Double>> datas = report6Service.getReportDatas(ids);
		
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
	@RequiresPermissions(value={"feareport:report6:add","feareport:report6:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		
		List<FeaProjectB> projectlist = report6Service.getProjectDatas();
		FeaProjectB project = projectlist.get(0);
		
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