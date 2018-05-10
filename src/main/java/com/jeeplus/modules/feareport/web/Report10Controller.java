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
import com.jeeplus.modules.feareport.entity.Report10;
import com.jeeplus.modules.feareport.service.Report10Service;

/**
 * EVA测算表Controller
 * @author zp
 * @version 2017-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/report10")
public class Report10Controller extends BaseController {

	@Autowired
	private Report10Service report10Service;
	
	@ModelAttribute
	public Report10 get(@RequestParam(required=false) String id) {
		Report10 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = report10Service.get(id);
		}
		if (entity == null){
			entity = new Report10();
		}
		return entity;
	}
	
	/**
	 * EVA测算表列表页面
	 */
	@RequiresPermissions("feareport:report10:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/report10List";
	}
	
		/**
	 * EVA测算表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report10:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report10 report10, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report10> page = report10Service.findPage(new Page<Report10>(request, response), report10); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑EVA测算表表单页面
	 */
	@RequiresPermissions(value={"feareport:report10:view","feareport:report10:add","feareport:report10:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Report10 report10, Model model) {
		model.addAttribute("report10", report10);
		return "modules/feareport/report10Form";
	}

	/**
	 * 保存EVA测算表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report10:add","feareport:report10:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report10 report10, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, report10)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		report10Service.save(report10);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存EVA测算表成功");
		return j;
	}
	
	/**
	 * 删除EVA测算表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report10:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report10 report10, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		report10Service.delete(report10);
		j.setMsg("删除EVA测算表成功");
		return j;
	}
	
	/**
	 * 批量删除EVA测算表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report10:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			report10Service.delete(report10Service.get(id));
		}
		j.setMsg("删除EVA测算表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report10:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Report10 report10, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "EVA测算表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report10> page = report10Service.findPage(new Page<Report10>(request, response, -1), report10);
    		new ExportExcel("EVA测算表", Report10.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出EVA测算表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:report10:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report10> list = ei.getDataList(Report10.class);
			for (Report10 report10 : list){
				try{
					report10Service.save(report10);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条EVA测算表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条EVA测算表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入EVA测算表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report10/?repage";
    }
	
	/**
	 * 下载导入EVA测算表数据模板
	 */
	@RequiresPermissions("feareport:report10:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "EVA测算表数据导入模板.xlsx";
    		List<Report10> list = Lists.newArrayList(); 
    		new ExportExcel("EVA测算表数据", Report10.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report10/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report10:add","feareport:report10:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();

		List<List<Double>> datas = report10Service.getReportDatas(ids);
		
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
	@RequiresPermissions(value={"feareport:report10:add","feareport:report10:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		List<FeaProjectB> projectlist = report10Service.getProjectDatas();
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