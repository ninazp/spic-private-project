/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.analysisearnings.web;

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
import com.jeeplus.modules.analysisearnings.entity.AnalysisEarnings;
import com.jeeplus.modules.analysisearnings.service.AnalysisEarningsService;

/**
 * 敏感分析（单因素）Controller
 * @author zp
 * @version 2017-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/analysisearnings/analysisEarnings")
public class AnalysisEarningsController extends BaseController {

	@Autowired
	private AnalysisEarningsService analysisEarningsService;
	
	@ModelAttribute
	public AnalysisEarnings get(@RequestParam(required=false) String id) {
		AnalysisEarnings entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = analysisEarningsService.get(id);
		}
		if (entity == null){
			entity = new AnalysisEarnings();
		}
		return entity;
	}
	
	/**
	 * 敏感分析（单因素）列表页面
	 */
	@RequiresPermissions("analysisearnings:analysisEarnings:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/analysisearnings/analysisEarningsList";
	}
	
		/**
	 * 敏感分析（单因素）列表数据
	 */
	@ResponseBody
	@RequiresPermissions("analysisearnings:analysisEarnings:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(AnalysisEarnings analysisEarnings, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AnalysisEarnings> page = analysisEarningsService.findPage(new Page<AnalysisEarnings>(request, response), analysisEarnings); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑敏感分析（单因素）表单页面
	 */
	@RequiresPermissions(value={"analysisearnings:analysisEarnings:view","analysisearnings:analysisEarnings:add","analysisearnings:analysisEarnings:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AnalysisEarnings analysisEarnings, Model model) {
		model.addAttribute("analysisEarnings", analysisEarnings);
		return "modules/analysisearnings/analysisEarningsForm";
	}

	/**
	 * 保存敏感分析（单因素）
	 */
	@ResponseBody
	@RequiresPermissions(value={"analysisearnings:analysisEarnings:add","analysisearnings:analysisEarnings:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(AnalysisEarnings analysisEarnings, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, analysisEarnings)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		//新增或编辑表单保存
		analysisEarningsService.save(analysisEarnings);//保存
		j.setSuccess(true);
		j.setMsg("保存敏感分析（单因素）成功");
		return j;
	}
	
	/**
	 * 删除敏感分析（单因素）
	 */
	@ResponseBody
	@RequiresPermissions("analysisearnings:analysisEarnings:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(AnalysisEarnings analysisEarnings, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		analysisEarningsService.delete(analysisEarnings);
		j.setMsg("删除敏感分析（单因素）成功");
		return j;
	}
	
	/**
	 * 批量删除敏感分析（单因素）
	 */
	@ResponseBody
	@RequiresPermissions("analysisearnings:analysisEarnings:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			analysisEarningsService.delete(analysisEarningsService.get(id));
		}
		j.setMsg("删除敏感分析（单因素）成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("analysisearnings:analysisEarnings:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(AnalysisEarnings analysisEarnings, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "敏感分析（单因素）"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AnalysisEarnings> page = analysisEarningsService.findPage(new Page<AnalysisEarnings>(request, response, -1), analysisEarnings);
    		new ExportExcel("敏感分析（单因素）", AnalysisEarnings.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出敏感分析（单因素）记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public AnalysisEarnings detail(String id) {
		return analysisEarningsService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("analysisearnings:analysisEarnings:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AnalysisEarnings> list = ei.getDataList(AnalysisEarnings.class);
			for (AnalysisEarnings analysisEarnings : list){
				try{
					analysisEarningsService.save(analysisEarnings);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条敏感分析（单因素）记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条敏感分析（单因素）记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入敏感分析（单因素）失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/analysisearnings/analysisEarnings/?repage";
    }
	
	/**
	 * 下载导入敏感分析（单因素）数据模板
	 */
	@RequiresPermissions("analysisearnings:analysisEarnings:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "敏感分析（单因素）数据导入模板.xlsx";
    		List<AnalysisEarnings> list = Lists.newArrayList(); 
    		new ExportExcel("敏感分析（单因素）数据", AnalysisEarnings.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/analysisearnings/analysisEarnings/?repage";
    }
	
	/**
	 */
	@ResponseBody
	@RequiresPermissions(value={"analysisearnings:analysisEarnings:add","analysisearnings:analysisEarnings:edit"},logical=Logical.OR)
	@RequestMapping(value = "calculation")
	public AjaxJson calculation(String json, AnalysisEarnings analysisEarnings, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
//		if (!beanValidator(model, analysisEarnings)){
//			j.setSuccess(false);
//			j.setMsg("非法参数！");
//			return j;
//		}
//		//新增或编辑表单保存
//		analysisEarningsService.save(analysisEarnings);//保存
//		j.setSuccess(true);
//		j.setMsg("保存敏感分析（单因素）成功");
		return j;
	}
	

}