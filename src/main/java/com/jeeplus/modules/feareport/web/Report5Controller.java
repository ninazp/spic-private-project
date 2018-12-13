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
import com.jeeplus.modules.feareport.entity.Report5;
import com.jeeplus.modules.feareport.service.Report5Service;

/**
 * 投资计划与资金筹措表Controller
 * @author zp
 * @version 2017-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/report5")
public class Report5Controller extends BaseController {

	@Autowired
	private Report5Service report5Service;
	
	@ModelAttribute
	public Report5 get(@RequestParam(required=false) String id) {
		Report5 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = report5Service.get(id);
		}
		if (entity == null){
			entity = new Report5();
		}
		return entity;
	}
	
	/**
	 * 投资计划与资金筹措表列表页面
	 */
	@RequiresPermissions("feareport:report5:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/report5List";
	}
	
		/**
	 * 投资计划与资金筹措表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report5:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report5 report5, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report5> page = report5Service.findPage(new Page<Report5>(request, response), report5); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑投资计划与资金筹措表表单页面
	 */
	@RequiresPermissions(value={"feareport:report5:view","feareport:report5:add","feareport:report5:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Report5 report5, Model model) {
		model.addAttribute("report5", report5);
		return "modules/feareport/report5Form";
	}

	/**
	 * 保存投资计划与资金筹措表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report5:add","feareport:report5:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report5 report5, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, report5)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		report5Service.save(report5);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存投资计划与资金筹措表成功");
		return j;
	}
	
	/**
	 * 删除投资计划与资金筹措表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report5:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report5 report5, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		report5Service.delete(report5);
		j.setMsg("删除投资计划与资金筹措表成功");
		return j;
	}
	
	/**
	 * 批量删除投资计划与资金筹措表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report5:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			report5Service.delete(report5Service.get(id));
		}
		j.setMsg("删除投资计划与资金筹措表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report5:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Report5 report5, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "投资计划与资金筹措表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report5> page = report5Service.findPage(new Page<Report5>(request, response, -1), report5);
    		new ExportExcel("投资计划与资金筹措表", Report5.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出投资计划与资金筹措表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:report5:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report5> list = ei.getDataList(Report5.class);
			for (Report5 report5 : list){
				try{
					report5Service.save(report5);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条投资计划与资金筹措表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条投资计划与资金筹措表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入投资计划与资金筹措表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report5/?repage";
    }
	
	/**
	 * 下载导入投资计划与资金筹措表数据模板
	 */
	@RequiresPermissions("feareport:report5:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "投资计划与资金筹措表数据导入模板.xlsx";
    		List<Report5> list = Lists.newArrayList(); 
    		new ExportExcel("投资计划与资金筹措表数据", Report5.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report5/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report5:add","feareport:report5:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();
		List<List<Double>> datas = report5Service.getReportDatas(ids);
		
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
	 * 获取财务指标汇总表报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report5:add","feareport:report5:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas2")
	public AjaxJson getReportDatas2(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();
		List<List<Double>> datas = report5Service.getReportDatas2(ids);
		
		List<List<Double>> newdatas = new ArrayList<List<Double>>();
		if(null!=datas) {
			for(Double d : datas.get(0)) {
				List<Double> line = new ArrayList<Double>();
				line.add(d);
				newdatas.add(line);
			}
			datas = newdatas;
		}
		
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
	@RequiresPermissions(value={"feareport:report5:add","feareport:report5:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		List<FeaProjectB> project = new ArrayList<FeaProjectB>();
		
		project = report5Service.getProjectDatas();
		// 倒叙排序去第一条作为默认值返回
		ids = project.get(0).getId();
		projectName = project.get(0).getProjectName();
		
		j.setMsg("");
		j.setProjectId(ids);
		j.setProjectName(projectName);
		j.setSuccess(true);
		
		return j;
	}

}