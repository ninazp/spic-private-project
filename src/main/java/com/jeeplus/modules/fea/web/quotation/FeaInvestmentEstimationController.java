/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.quotation;

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
import com.jeeplus.modules.fea.entity.quotation.FeaInvestmentEstimation;
import com.jeeplus.modules.fea.service.quotation.FeaInvestmentEstimationService;

/**
 * 换热站设备购置费及安装工程投资估算Controller
 * @author zp
 * @version 2018-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/quotation/feaInvestmentEstimation")
public class FeaInvestmentEstimationController extends BaseController {

	@Autowired
	private FeaInvestmentEstimationService feaInvestmentEstimationService;
	
	@ModelAttribute
	public FeaInvestmentEstimation get(@RequestParam(required=false) String id) {
		FeaInvestmentEstimation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feaInvestmentEstimationService.get(id);
		}
		if (entity == null){
			entity = new FeaInvestmentEstimation();
		}
		return entity;
	}
	
	/**
	 * 换热站设备购置费及安装工程投资估算列表页面
	 */
	@RequiresPermissions("fea:quotation:feaInvestmentEstimation:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/quotation/feaInvestmentEstimationList";
	}
	
		/**
	 * 换热站设备购置费及安装工程投资估算列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaInvestmentEstimation:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FeaInvestmentEstimation feaInvestmentEstimation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FeaInvestmentEstimation> page = feaInvestmentEstimationService.findPage(new Page<FeaInvestmentEstimation>(request, response), feaInvestmentEstimation); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑换热站设备购置费及安装工程投资估算表单页面
	 */
	@RequiresPermissions(value={"fea:quotation:feaInvestmentEstimation:view","fea:quotation:feaInvestmentEstimation:add","fea:quotation:feaInvestmentEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FeaInvestmentEstimation feaInvestmentEstimation, Model model) {
		model.addAttribute("feaInvestmentEstimation", feaInvestmentEstimation);
		return "modules/fea/quotation/feaInvestmentEstimationForm";
	}

	/**
	 * 保存换热站设备购置费及安装工程投资估算
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:quotation:feaInvestmentEstimation:add","fea:quotation:feaInvestmentEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FeaInvestmentEstimation feaInvestmentEstimation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, feaInvestmentEstimation)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		feaInvestmentEstimationService.save(feaInvestmentEstimation);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存换热站设备购置费及安装工程投资估算成功");
		return j;
	}
	
	/**
	 * 删除换热站设备购置费及安装工程投资估算
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaInvestmentEstimation:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FeaInvestmentEstimation feaInvestmentEstimation, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		feaInvestmentEstimationService.delete(feaInvestmentEstimation);
		j.setMsg("删除换热站设备购置费及安装工程投资估算成功");
		return j;
	}
	
	/**
	 * 批量删除换热站设备购置费及安装工程投资估算
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaInvestmentEstimation:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			feaInvestmentEstimationService.delete(feaInvestmentEstimationService.get(id));
		}
		j.setMsg("删除换热站设备购置费及安装工程投资估算成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaInvestmentEstimation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FeaInvestmentEstimation feaInvestmentEstimation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "换热站设备购置费及安装工程投资估算"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FeaInvestmentEstimation> page = feaInvestmentEstimationService.findPage(new Page<FeaInvestmentEstimation>(request, response, -1), feaInvestmentEstimation);
    		new ExportExcel("换热站设备购置费及安装工程投资估算", FeaInvestmentEstimation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出换热站设备购置费及安装工程投资估算记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:quotation:feaInvestmentEstimation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FeaInvestmentEstimation> list = ei.getDataList(FeaInvestmentEstimation.class);
			for (FeaInvestmentEstimation feaInvestmentEstimation : list){
				try{
					feaInvestmentEstimationService.save(feaInvestmentEstimation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条换热站设备购置费及安装工程投资估算记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条换热站设备购置费及安装工程投资估算记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入换热站设备购置费及安装工程投资估算失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/quotation/feaInvestmentEstimation/?repage";
    }
	
	/**
	 * 下载导入换热站设备购置费及安装工程投资估算数据模板
	 */
	@RequiresPermissions("fea:quotation:feaInvestmentEstimation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "换热站设备购置费及安装工程投资估算数据导入模板.xlsx";
    		List<FeaInvestmentEstimation> list = Lists.newArrayList(); 
    		new ExportExcel("换热站设备购置费及安装工程投资估算数据", FeaInvestmentEstimation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/quotation/feaInvestmentEstimation/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:quotation:feaInvestmentEstimation:add","fea:quotation:feaInvestmentEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();

		//List<List<String>> datas = feaInvestmentEstimationService.getReportDatas(ids);
		List<List<String>> datas = new ArrayList<List<String>>();
		List<String> li = new ArrayList<String>();
		li.add("1");
		li.add("2");
		li.add("3");
		li.add("4");
		li.add("5");
		li.add("6");
		
		datas.add(li);
		
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
	@RequiresPermissions(value={"fea:quotation:feaInvestmentEstimation:add","fea:quotation:feaInvestmentEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		List<FeaProjectB> project = new ArrayList<FeaProjectB>();
		
		project = feaInvestmentEstimationService.getProjectDatas();
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