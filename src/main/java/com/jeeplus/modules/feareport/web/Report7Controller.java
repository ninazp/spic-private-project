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
import com.jeeplus.modules.feareport.entity.Report7;
import com.jeeplus.modules.feareport.service.Report7Service;

/**
 * 利润和利润分配表Controller
 * @author zp
 * @version 2017-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/report7")
public class Report7Controller extends BaseController {

	@Autowired
	private Report7Service report7Service;
	
	@ModelAttribute
	public Report7 get(@RequestParam(required=false) String id) {
		Report7 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = report7Service.get(id);
		}
		if (entity == null){
			entity = new Report7();
		}
		return entity;
	}
	
	/**
	 * 利润和利润分配表列表页面
	 */
	@RequiresPermissions("feareport:report7:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/report7List";
	}
	
		/**
	 * 利润和利润分配表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report7:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report7 report7, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report7> page = report7Service.findPage(new Page<Report7>(request, response), report7); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑利润和利润分配表表单页面
	 */
	@RequiresPermissions(value={"feareport:report7:view","feareport:report7:add","feareport:report7:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Report7 report7, Model model) {
		model.addAttribute("report7", report7);
		return "modules/feareport/report7Form";
	}

	/**
	 * 保存利润和利润分配表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report7:add","feareport:report7:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report7 report7, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, report7)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		report7Service.save(report7);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存利润和利润分配表成功");
		return j;
	}
	
	/**
	 * 删除利润和利润分配表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report7:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report7 report7, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		report7Service.delete(report7);
		j.setMsg("删除利润和利润分配表成功");
		return j;
	}
	
	/**
	 * 批量删除利润和利润分配表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report7:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			report7Service.delete(report7Service.get(id));
		}
		j.setMsg("删除利润和利润分配表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report7:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Report7 report7, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "利润和利润分配表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report7> page = report7Service.findPage(new Page<Report7>(request, response, -1), report7);
    		new ExportExcel("利润和利润分配表", Report7.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出利润和利润分配表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:report7:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report7> list = ei.getDataList(Report7.class);
			for (Report7 report7 : list){
				try{
					report7Service.save(report7);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条利润和利润分配表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条利润和利润分配表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入利润和利润分配表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report7/?repage";
    }
	
	/**
	 * 下载导入利润和利润分配表数据模板
	 */
	@RequiresPermissions("feareport:report7:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "利润和利润分配表数据导入模板.xlsx";
    		List<Report7> list = Lists.newArrayList(); 
    		new ExportExcel("利润和利润分配表数据", Report7.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report7/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report7:add","feareport:report7:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();

		List<List<Double>> datas = report7Service.getReportDatas(ids);
		
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
	@RequiresPermissions(value={"feareport:report7:add","feareport:report7:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		FeaProjectB project = report7Service.getDefaultProject();
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