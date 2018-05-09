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
import com.jeeplus.modules.feareport.entity.ReportOne;
import com.jeeplus.modules.feareport.service.ReportOneService;

/**
 * 报表Controller
 * @author zp
 * @version 2017-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/reportOne")
public class ReportOneController extends BaseController {

	@Autowired
	private ReportOneService reportOneService;
	
	@ModelAttribute
	public ReportOne get(@RequestParam(required=false) String id) {
		ReportOne entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportOneService.get(id);
		}
		if (entity == null){
			entity = new ReportOne();
		}
		return entity;
	}
	
	/**
	 * 报表列表页面
	 */
	@RequiresPermissions("feareport:reportOne:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/reportOneList";
	}
	
		/**
	 * 报表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:reportOne:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ReportOne reportOne, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportOne> page = reportOneService.findPage(new Page<ReportOne>(request, response), reportOne); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑报表表单页面
	 */
	@RequiresPermissions(value={"feareport:reportOne:view","feareport:reportOne:add","feareport:reportOne:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ReportOne reportOne, Model model) {
		model.addAttribute("reportOne", reportOne);
		return "modules/feareport/reportOneForm";
	}

	/**
	 * 保存报表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:reportOne:add","feareport:reportOne:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ReportOne reportOne, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, reportOne)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		reportOneService.save(reportOne);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存报表成功");
		return j;
	}
	
	/**
	 * 删除报表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:reportOne:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ReportOne reportOne, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		reportOneService.delete(reportOne);
		j.setMsg("删除报表成功");
		return j;
	}
	
	/**
	 * 批量删除报表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:reportOne:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			reportOneService.delete(reportOneService.get(id));
		}
		j.setMsg("删除报表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:reportOne:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ReportOne reportOne, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ReportOne> page = reportOneService.findPage(new Page<ReportOne>(request, response, -1), reportOne);
    		new ExportExcel("报表", ReportOne.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出报表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:reportOne:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ReportOne> list = ei.getDataList(ReportOne.class);
			for (ReportOne reportOne : list){
				try{
					reportOneService.save(reportOne);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条报表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条报表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入报表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/reportOne/?repage";
    }
	
	/**
	 * 下载导入报表数据模板
	 */
	@RequiresPermissions("feareport:reportOne:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "报表数据导入模板.xlsx";
    		List<ReportOne> list = Lists.newArrayList(); 
    		new ExportExcel("报表数据", ReportOne.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/reportOne/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:reportOne:add","feareport:reportOne:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();

		List<List<Double>> datas = reportOneService.getReportDatas(ids);
		
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
	@RequiresPermissions(value={"feareport:reportOne:add","feareport:reportOne:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		FeaProjectB project = reportOneService.getDefaultProject();
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