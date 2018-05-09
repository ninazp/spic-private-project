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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.feareport.entity.Report3;
import com.jeeplus.modules.feareport.service.Report3Service;

/**
 * 项目资本金现金流量表Controller
 * @author zp
 * @version 2017-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/report3")
public class Report3Controller extends BaseController {

	@Autowired
	private Report3Service report3Service;
	
	@ModelAttribute
	public Report3 get(@RequestParam(required=false) String id) {
		Report3 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = report3Service.get(id);
		}
		if (entity == null){
			entity = new Report3();
		}
		return entity;
	}
	
	/**
	 * 项目资本金现金流量表列表页面
	 */
	@RequiresPermissions("feareport:report3:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/report3List";
	}
	
		/**
	 * 项目资本金现金流量表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report3:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Report3 report3, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Report3> page = report3Service.findPage(new Page<Report3>(request, response), report3); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑项目资本金现金流量表表单页面
	 */
	@RequiresPermissions(value={"feareport:report3:view","feareport:report3:add","feareport:report3:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Report3 report3, Model model) {
		model.addAttribute("report3", report3);
		return "modules/feareport/report3Form";
	}

	/**
	 * 保存项目资本金现金流量表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report3:add","feareport:report3:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Report3 report3, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, report3)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		report3Service.save(report3);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存项目资本金现金流量表成功");
		return j;
	}
	
	/**
	 * 删除项目资本金现金流量表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report3:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Report3 report3, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		report3Service.delete(report3);
		j.setMsg("删除项目资本金现金流量表成功");
		return j;
	}
	
	/**
	 * 批量删除项目资本金现金流量表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report3:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			report3Service.delete(report3Service.get(id));
		}
		j.setMsg("删除项目资本金现金流量表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:report3:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Report3 report3, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "项目资本金现金流量表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Report3> page = report3Service.findPage(new Page<Report3>(request, response, -1), report3);
    		new ExportExcel("项目资本金现金流量表", Report3.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出项目资本金现金流量表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:report3:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Report3> list = ei.getDataList(Report3.class);
			for (Report3 report3 : list){
				try{
					report3Service.save(report3);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条项目资本金现金流量表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条项目资本金现金流量表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目资本金现金流量表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report3/?repage";
    }
	
	/**
	 * 下载导入项目资本金现金流量表数据模板
	 */
	@RequiresPermissions("feareport:report3:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目资本金现金流量表数据导入模板.xlsx";
    		List<Report3> list = Lists.newArrayList(); 
    		new ExportExcel("项目资本金现金流量表数据", Report3.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/report3/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report3:add","feareport:report3:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();

		List<List<Double>> datas = report3Service.getReportDatas(ids);
		
		List<List<Double>> datas2 = report3Service.getReportDatas2(datas);
		
		if(null == datas || datas.size()<1){
			j.setMsg("没有查询到报表信息");
			j.setSuccess(false);
			return j;
		}
		j.setMsg(datas.toString());
		j.setMsg2(datas2.toString());
		j.setProjectId(ids);
		j.setSuccess(true);
		
		return j;
	}
	
	/**
	 * 获取项目数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:report3:add","feareport:report3:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		FeaProjectB project = report3Service.getDefaultProject();
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