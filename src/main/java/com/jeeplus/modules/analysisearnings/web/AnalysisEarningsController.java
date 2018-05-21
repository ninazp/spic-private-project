/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.analysisearnings.web;

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
import com.jeeplus.common.utils.io.FilePathUtil;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.analysisearnings.entity.AnalysisEarnings;
import com.jeeplus.modules.analysisearnings.service.AnalysisEarningsService;
import com.jeeplus.modules.fea.designcal.BusiIndexCal;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.pub.report.createReportPubDMO;
import com.jeeplus.modules.fea.pub.util.ReadExcelCal;

/**
 * 敏感分析（单因素）Controller
 * @author zp
 * @version 2017-11-28
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

	@RequiresPermissions("analysisearnings:analysisEarnings:add")
	@RequestMapping(value = "calculation")
	public String calculation(String ids,String vals,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {

			AjaxJson j = new AjaxJson();

			List<List<Double>> changevals = new ArrayList<List<Double>>();

			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			Object reportbean = wac.getBean("createReportPubDMO");

			
			//某个指标的变化率，百分比,前台组装参数
			Double[] changerate1 = new Double[] {
					-10.0,-5.0,0.0,5.0,10.0
			};
			Double[] changerate2 = new Double[] {
					-10.0,-5.0,0.0,5.0,10.0
			};
			Double[] changerate3= new Double[] {
					-10.0,-5.0,0.0,5.0,10.0
			};
			Double[] changerate4 = new Double[] {
					-10.0,-5.0,0.0,5.0,10.0
			};

			//changename : 设置规则
			// * price :取暖费
			//* powercost ：电费
			// * person ： 人工费
			// * investamt ： 初投资
			if(null!=reportbean){
				//初投资 ： 
				List<List<Double>> changevals1 = ((createReportPubDMO)reportbean).getchange_irrnpv(ids, "investamt", changerate4);
				//人工费 ：
				List<List<Double>> changevals2 = ((createReportPubDMO)reportbean).getchange_irrnpv(ids, "person", changerate3);
				//电费：
				List<List<Double>> changevals3 = ((createReportPubDMO)reportbean).getchange_irrnpv(ids, "powercost", changerate2);
				//取暖费 价格变化： 
				List<List<Double>> changevals4= ((createReportPubDMO)reportbean).getchange_irrnpv(ids, "price", changerate1);
			
				changevals.add(changevals1.get(0));
				changevals.add(changevals2.get(0));
				changevals.add(changevals3.get(0));
                changevals.add(changevals4.get(0));
                
                Map<String,List<List<Double>>> exportexcel = new HashMap<String,List<List<Double>>>();
                
                exportexcel.put("investamt", changevals1); exportexcel.put("powercost", changevals1);
                exportexcel.put("person", changevals1); exportexcel.put("price", changevals1);
                
                ((createReportPubDMO)reportbean).exportMGFXexcel(FilePathUtil.getJarPath(ReadExcelCal.class), exportexcel);
                 
			}
			j.setMsg(changevals.toString());
			j.setProjectId(ids);
			j.setSuccess(true);
		} catch (Exception e) {
			addMessage(redirectAttributes, "计算失败："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/analysisearnings/analysisEarnings/?repage";
	}

	@ResponseBody
	@RequiresPermissions(value={"feareport:report9:add","feareport:report9:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		String projectName = "";

		AjaxJson j = new AjaxJson();

		List<FeaProjectB> projectlist = analysisEarningsService.getProjectDatas();
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