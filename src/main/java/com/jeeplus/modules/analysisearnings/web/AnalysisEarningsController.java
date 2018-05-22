/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.analysisearnings.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
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

	@ResponseBody
	@RequiresPermissions(value={"analysisearnings:analysisEarnings:add","analysisearnings:analysisEarnings:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		AjaxJson j = new AjaxJson();

		List<FeaProjectB> projectlist = analysisEarningsService.getProjectDatas();
		FeaProjectB project = projectlist.get(0);

		Map<String,Double[]> parammap = new HashMap<String,Double[]>();

		String projectid = "";

		int changenum = 0;
		// 倒叙排序去第一条作为默认值返回
		Double[] changerate1 = new Double[] {
			-15.0,-10.0,-5.0,0.0,5.0,10.0,15.0
		};
		
		List<Double> changeratefinal = new ArrayList<Double>();
		for(Double cangedb : changerate1) {
			changeratefinal.add(cangedb);
		}
		
		if(null==ids || ids.equals("") || ids.length()==0) {
			projectid = project.getId();
			changenum = changerate1.length;
			parammap.put("investamt", changerate1);
			parammap.put("person", changerate1);
			parammap.put("powercost", changerate1);
			parammap.put("price", changerate1);
		}else if(ids.contains(";")) {
				String [] changparam = ids.split(";");

				if(null!=changparam && changparam.length>0) {
					projectid = changparam[0];

					String startamt = changparam[1];
					String setupnum = changparam[2];
					String endamt = changparam[3];

					if(null==startamt || null==setupnum || endamt==null || Double.valueOf(startamt)>Double.valueOf(endamt)) {
						changenum = changerate1.length;
						parammap.put("investamt", changerate1);
						parammap.put("person", changerate1);
						parammap.put("powercost", changerate1);
						parammap.put("price", changerate1);
					}else {
						Double dbstart = Double.valueOf(startamt);
						Double dbend = Double.valueOf(endamt);
						Double setupdb = Double.valueOf(setupnum);

						List<Double> changrate = new ArrayList<Double>();

						if(dbstart < dbend) {
							Double sumamt = dbend - dbstart ;
							if(dbstart>0) {
								changrate.add(0.00);
							}
							changrate.add(dbstart);
							while(dbstart<dbend && setupdb>0) {
								if(dbstart<0 && (dbstart + setupdb)<0) {
									changrate.add(0.00);
								}
								dbstart = dbstart + setupdb; 
								changrate.add(dbstart);
								sumamt = sumamt-setupdb;
							}
							if(sumamt<0) {
								changrate.add(-sumamt);
							}
							if(dbend>0) {
								changrate.add(0.00);
							}
						}
						
						changeratefinal =  changrate;
						
						changenum = changrate.size();
						parammap.put("investamt", changrate.toArray(new Double[0]));
						parammap.put("person", changrate.toArray(new Double[0]));
						parammap.put("powercost", changrate.toArray(new Double[0]));
						parammap.put("price", changrate.toArray(new Double[0]));
					}
				}
		}

		Map<String,List<Double>> retmap = analysisEarningsService.calmgfx(projectid, parammap);

		String [] keyname = new String[] {"investamt","person","powercost","price"};
		List<List<Double>> retlstlst = new ArrayList<List<Double>>();
		for(String key : keyname) {
			if(retmap.containsKey(key)) {
				retlstlst.add(retmap.get(key));
			}else {
				List<Double> d = new ArrayList<Double>();
				for(int i=0;i<changenum;i++) {
					d.add(0.00);
				}
				retlstlst.add(d);
			}
		}
		
		retlstlst.add(changeratefinal);

		j.setMsg(retlstlst.toString());
		j.setProjectId(projectid);
		j.setProjectName(project.getProjectName());
		j.setSuccess(true);

		return j;
	}


	@ResponseBody
	@RequiresPermissions(value={"analysisearnings:analysisEarnings:add","analysisearnings:analysisEarnings:edit"},logical=Logical.OR)
	@RequestMapping(value = "exportmgExcel")
	public AjaxJson exportmgExcel(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		try {

			FeaProjectB projectvo = analysisEarningsService.getprojectb(ids);

			if(null==projectvo) {
				j.setSuccess(false);
				j.setMsg("未选择项目");

				return j;
			}

			String path = FilePathUtil.getJarPath(ReadExcelCal.class) + "敏感性分析报表(项目名称："+projectvo.getProjectName()+").xls";
			// path是指欲下载的文件的路径。  
			File file = new File(path);  
			// 取得文件名。  
			String filename = file.getName();  
			// 以流的形式下载文件。  
			InputStream fis = new BufferedInputStream(new FileInputStream(path));  
			byte[] buffer = new byte[fis.available()];  
			fis.read(buffer);  
			fis.close();  
			// 清空response  
			response.reset();  
			// 设置response的Header  
			//		            response.setCharacterEncoding("utf-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("gbk"), "ISO8859-1") );  
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(  
					response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=GBK");
			toClient.write(buffer);  
			toClient.flush();  
			toClient.close();  
			j.setSuccess(true);
			j.setMsg("导出敏感分析（单因素）成功!");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出敏感分析（单因素）记录失败！失败信息："+e.getMessage());
		}
		return j;

	}
}