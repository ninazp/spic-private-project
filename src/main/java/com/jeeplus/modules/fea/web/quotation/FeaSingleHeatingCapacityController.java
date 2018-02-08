/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.quotation;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
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
import com.jeeplus.modules.fea.designcal.PubDesignCal;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.quotation.FeaSingleHeatingCapacity;
import com.jeeplus.modules.fea.service.quotation.FeaSingleHeatingCapacityService;

/**
 * 单井供热能力Controller
 * @author zp
 * @version 2018-02-08
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/quotation/feaSingleHeatingCapacity")
public class FeaSingleHeatingCapacityController extends BaseController {

	@Autowired
	private FeaSingleHeatingCapacityService feaSingleHeatingCapacityService;
	
	@ModelAttribute
	public FeaSingleHeatingCapacity get(@RequestParam(required=false) String id) {
		FeaSingleHeatingCapacity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feaSingleHeatingCapacityService.get(id);
		}
		if (entity == null){
			entity = new FeaSingleHeatingCapacity();
		}
		return entity;
	}
	
	/**
	 * 单井供热能力列表页面
	 */
	@RequiresPermissions("fea:quotation:feaSingleHeatingCapacity:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/quotation/feaSingleHeatingCapacityList";
	}
	
		/**
	 * 单井供热能力列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaSingleHeatingCapacity:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FeaSingleHeatingCapacity feaSingleHeatingCapacity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FeaSingleHeatingCapacity> page = feaSingleHeatingCapacityService.findPage(new Page<FeaSingleHeatingCapacity>(request, response), feaSingleHeatingCapacity); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑单井供热能力表单页面
	 */
	@RequiresPermissions(value={"fea:quotation:feaSingleHeatingCapacity:view","fea:quotation:feaSingleHeatingCapacity:add","fea:quotation:feaSingleHeatingCapacity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FeaSingleHeatingCapacity feaSingleHeatingCapacity, Model model) {
		model.addAttribute("feaSingleHeatingCapacity", feaSingleHeatingCapacity);
		return "modules/fea/quotation/feaSingleHeatingCapacityForm";
	}

	/**
	 * 保存单井供热能力
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:quotation:feaSingleHeatingCapacity:add","fea:quotation:feaSingleHeatingCapacity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FeaSingleHeatingCapacity feaSingleHeatingCapacity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, feaSingleHeatingCapacity)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		feaSingleHeatingCapacityService.save(feaSingleHeatingCapacity);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存单井供热能力成功");
		return j;
	}
	
	/**
	 * 删除单井供热能力
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaSingleHeatingCapacity:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FeaSingleHeatingCapacity feaSingleHeatingCapacity, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		feaSingleHeatingCapacityService.delete(feaSingleHeatingCapacity);
		j.setMsg("删除单井供热能力成功");
		return j;
	}
	
	/**
	 * 批量删除单井供热能力
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaSingleHeatingCapacity:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			feaSingleHeatingCapacityService.delete(feaSingleHeatingCapacityService.get(id));
		}
		j.setMsg("删除单井供热能力成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaSingleHeatingCapacity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FeaSingleHeatingCapacity feaSingleHeatingCapacity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "单井供热能力"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FeaSingleHeatingCapacity> page = feaSingleHeatingCapacityService.findPage(new Page<FeaSingleHeatingCapacity>(request, response, -1), feaSingleHeatingCapacity);
    		new ExportExcel("单井供热能力", FeaSingleHeatingCapacity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出单井供热能力记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:quotation:feaSingleHeatingCapacity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FeaSingleHeatingCapacity> list = ei.getDataList(FeaSingleHeatingCapacity.class);
			for (FeaSingleHeatingCapacity feaSingleHeatingCapacity : list){
				try{
					feaSingleHeatingCapacityService.save(feaSingleHeatingCapacity);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条单井供热能力记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条单井供热能力记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入单井供热能力失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/quotation/feaSingleHeatingCapacity/?repage";
    }
	
	/**
	 * 下载导入单井供热能力数据模板
	 */
	@RequiresPermissions("fea:quotation:feaSingleHeatingCapacity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "单井供热能力数据导入模板.xlsx";
    		List<FeaSingleHeatingCapacity> list = Lists.newArrayList(); 
    		new ExportExcel("单井供热能力数据", FeaSingleHeatingCapacity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/quotation/feaSingleHeatingCapacity/?repage";
    }
	
	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:quotation:feaSingleHeatingCapacity:add","fea:quotation:feaSingleHeatingCapacity:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();
		Map<String,Object> reportmap = new HashMap<String, Object>();

		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		PubDesignCal pubDesignCal = (PubDesignCal) wac.getBean("pubDesignCal");
		
		if(null!=pubDesignCal){
			reportmap = pubDesignCal.calprocess(ids);
		}
		
		List<List<String>> datas = null != reportmap ? (List<List<String>>)reportmap.get("单井供热") : null;
		List<List<String>> reDatas = new ArrayList<List<String>>();
		
		for(List<String> innerLi : datas){
			List<String> reinnerLi = new ArrayList<String>();
			for(String s : innerLi){
				if(null != s){
					reinnerLi.add("\'"+s.replace("\n","")+"\'");
				}else{
					reinnerLi.add("\'-'");
				}
			}
			reDatas.add(reinnerLi);
		}
		if(null == datas || datas.size()<1){
			j.setMsg("没有查询到报表信息");
			j.setSuccess(false);
			return j;
		}
		j.setMsg(reDatas.toString());
		j.setProjectId(ids);
		j.setSuccess(true);
		
		return j;
	}
	
	/**
	 * 获取项目数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:quotation:feaSingleHeatingCapacity:add","fea:quotation:feaSingleHeatingCapacity:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		List<FeaProjectB> project = new ArrayList<FeaProjectB>();
		
		project = feaSingleHeatingCapacityService.getProjectDatas();
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