/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.web;

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
import com.jeeplus.modules.fea.designcal.BusiIndexCal;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.pub.report.createReportPubDMO;
import com.jeeplus.modules.fea.pub.util.ReadExcelCal;
import com.jeeplus.modules.feareport.entity.ReportTotalEstimation;
import com.jeeplus.modules.feareport.service.ReportTotalEstimationService;

/**
 * 总估算表Controller
 * @author zp
 * @version 2018-02-08
 */
@Controller
@RequestMapping(value = "${adminPath}/feareport/reportTotalEstimation")
public class ReportTotalEstimationController extends BaseController {

	@Autowired
	private ReportTotalEstimationService reportTotalEstimationService;
	
	@ModelAttribute
	public ReportTotalEstimation get(@RequestParam(required=false) String id) {
		ReportTotalEstimation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = reportTotalEstimationService.get(id);
		}
		if (entity == null){
			entity = new ReportTotalEstimation();
		}
		return entity;
	}
	
	/**
	 * 总估算表列表页面
	 */
	@RequiresPermissions("feareport:reportTotalEstimation:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/feareport/reportTotalEstimationList";
	}
	
		/**
	 * 总估算表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("feareport:reportTotalEstimation:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ReportTotalEstimation reportTotalEstimation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReportTotalEstimation> page = reportTotalEstimationService.findPage(new Page<ReportTotalEstimation>(request, response), reportTotalEstimation); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑总估算表表单页面
	 */
	@RequiresPermissions(value={"feareport:reportTotalEstimation:view","feareport:reportTotalEstimation:add","feareport:reportTotalEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ReportTotalEstimation reportTotalEstimation, Model model) {
		model.addAttribute("reportTotalEstimation", reportTotalEstimation);
		return "modules/feareport/reportTotalEstimationForm";
	}

	/**
	 * 保存总估算表
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:reportTotalEstimation:add","feareport:reportTotalEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ReportTotalEstimation reportTotalEstimation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, reportTotalEstimation)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		reportTotalEstimationService.save(reportTotalEstimation);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存总估算表成功");
		return j;
	}
	
	/**
	 * 删除总估算表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:reportTotalEstimation:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ReportTotalEstimation reportTotalEstimation, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		reportTotalEstimationService.delete(reportTotalEstimation);
		j.setMsg("删除总估算表成功");
		return j;
	}
	
	/**
	 * 批量删除总估算表
	 */
	@ResponseBody
	@RequiresPermissions("feareport:reportTotalEstimation:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			reportTotalEstimationService.delete(reportTotalEstimationService.get(id));
		}
		j.setMsg("删除总估算表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("feareport:reportTotalEstimation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ReportTotalEstimation reportTotalEstimation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "总估算表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ReportTotalEstimation> page = reportTotalEstimationService.findPage(new Page<ReportTotalEstimation>(request, response, -1), reportTotalEstimation);
    		new ExportExcel("总估算表", ReportTotalEstimation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出总估算表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("feareport:reportTotalEstimation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ReportTotalEstimation> list = ei.getDataList(ReportTotalEstimation.class);
			for (ReportTotalEstimation reportTotalEstimation : list){
				try{
					reportTotalEstimationService.save(reportTotalEstimation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条总估算表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条总估算表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入总估算表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/reportTotalEstimation/?repage";
    }
	
	/**
	 * 下载导入总估算表数据模板
	 */
	@RequiresPermissions("feareport:reportTotalEstimation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "总估算表数据导入模板.xlsx";
    		List<ReportTotalEstimation> list = Lists.newArrayList(); 
    		new ExportExcel("总估算表数据", ReportTotalEstimation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/feareport/reportTotalEstimation/?repage";
    }
	

	/**
	 * 获取报表数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:reportTotalEstimation:add","feareport:reportTotalEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
	public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();
		List<List<String>> reportmap = new ArrayList<List<String>>();

		reportmap = reportTotalEstimationService.getReportdata(ids);
//		List<List<String>> datas = feaInvestmentEstimationService.getReportDatas(ids);
		List<List<String>> reDatas = new ArrayList<List<String>>();
		
		for(List<String> innerLi : reportmap){
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
		if(null == reportmap || reportmap.size()<1){
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
	@RequiresPermissions(value={"feareport:reportTotalEstimation:add","feareport:reportTotalEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectDatas")
	public AjaxJson getProjectDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		String projectName = "";

		AjaxJson j = new AjaxJson();
		List<FeaProjectB> project = new ArrayList<FeaProjectB>();
		
		project = reportTotalEstimationService.getProjectDatas();
		// 倒叙排序去第一条作为默认值返回
		ids = project.get(0).getId();
		projectName = project.get(0).getProjectName();
		
		j.setMsg("");
		j.setProjectId(ids);
		j.setProjectName(projectName);
		j.setSuccess(true);
		
		return j;
	}
	
	/**
	 * 获取项目数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"feareport:reportTotalEstimation:add","feareport:reportTotalEstimation:edit"},logical=Logical.OR)
	@RequestMapping(value = "exportExcel")
	public AjaxJson exportExcel(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		List<List<String>> gsmap = new ArrayList<List<String>>();

		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		BusiIndexCal busiIndexCal = (BusiIndexCal) wac.getBean("busiIndexCal");
		
		if(null!=busiIndexCal){
			gsmap = busiIndexCal.getInitIvdesMny(ids);
		}
		
        Object reportbean = wac.getBean("createReportPubDMO");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("projectid", ids);
		
		String path = ((createReportPubDMO)reportbean).exportexcel(FilePathUtil.getJarPath(ReadExcelCal.class),param, gsmap);
		
		download(path, response);
		
		j.setProjectId(ids);
		j.setSuccess(true);
		
		return j;
	}
	
	public void download(String path, HttpServletResponse response) {  
        try {  
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
//            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes("gbk"), "ISO8859-1") );  
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(  
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=GBK");
            toClient.write(buffer);  
            toClient.flush();  
            toClient.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    }  

}