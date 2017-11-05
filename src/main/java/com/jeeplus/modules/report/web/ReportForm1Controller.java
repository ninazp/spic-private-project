/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.report.web;

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
import com.jeeplus.modules.report.entity.ReportForm1;
import com.jeeplus.modules.report.service.ReportForm1Service;

/**
 * 报表Controller
 * @author zhaopeng
 * @version 2017-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/report/reportForm1")
public class ReportForm1Controller extends BaseController {

	@Autowired
	private ReportForm1Service reportForm1Service;
	
	@ModelAttribute
	public ReportForm1 get(@RequestParam(required=false) String id) {
		ReportForm1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportForm1Service.get(id);
		}
		if (entity == null){
			entity = new ReportForm1();
		}
		return entity;
	}
	
	/**
	 * 报表列表页面
	 */
	@RequiresPermissions("report:reportForm1:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/report/reportForm1List";
	}
	
		/**
	 * 报表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("report:reportForm1:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ReportForm1 reportForm1, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportForm1> page = reportForm1Service.findPage(new Page<ReportForm1>(request, response), reportForm1); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑报表表单页面
	 */
	@RequiresPermissions(value={"report:reportForm1:view","report:reportForm1:add","report:reportForm1:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ReportForm1 reportForm1, Model model) {
		model.addAttribute("reportForm1", reportForm1);
		if(StringUtils.isBlank(reportForm1.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/report/reportForm1Form";
	}

	/**
	 * 保存报表
	 */
	@RequiresPermissions(value={"report:reportForm1:add","report:reportForm1:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ReportForm1 reportForm1, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, reportForm1)){
			return form(reportForm1, model);
		}
		//新增或编辑表单保存
		reportForm1Service.save(reportForm1);//保存
		addMessage(redirectAttributes, "保存报表成功");
		return "redirect:"+Global.getAdminPath()+"/report/reportForm1/?repage";
	}
	
	/**
	 * 删除报表
	 */
	@ResponseBody
	@RequiresPermissions("report:reportForm1:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ReportForm1 reportForm1, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		reportForm1Service.delete(reportForm1);
		j.setMsg("删除报表成功");
		return j;
	}
	
	/**
	 * 批量删除报表
	 */
	@ResponseBody
	@RequiresPermissions("report:reportForm1:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			reportForm1Service.delete(reportForm1Service.get(id));
		}
		j.setMsg("删除报表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("report:reportForm1:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ReportForm1 reportForm1, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ReportForm1> page = reportForm1Service.findPage(new Page<ReportForm1>(request, response, -1), reportForm1);
    		new ExportExcel("报表", ReportForm1.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("report:reportForm1:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ReportForm1> list = ei.getDataList(ReportForm1.class);
			for (ReportForm1 reportForm1 : list){
				try{
					reportForm1Service.save(reportForm1);
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
		return "redirect:"+Global.getAdminPath()+"/report/reportForm1/?repage";
    }
	
	/**
	 * 下载导入报表数据模板
	 */
	@RequiresPermissions("report:reportForm1:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "报表数据导入模板.xlsx";
    		List<ReportForm1> list = Lists.newArrayList(); 
    		new ExportExcel("报表数据", ReportForm1.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/report/reportForm1/?repage";
    }

}