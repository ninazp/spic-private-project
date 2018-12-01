/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.report_totaltab;

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
import com.jeeplus.modules.fea.entity.report_totaltab.Fea_totaltabVO;
import com.jeeplus.modules.fea.service.report_totaltab.Fea_totaltabVOService;

/**
 * 财务指标汇总表Controller
 * @author jw
 * @version 2018-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/report_totaltab/fea_totaltabVO")
public class Fea_totaltabVOController extends BaseController {

	@Autowired
	private Fea_totaltabVOService fea_totaltabVOService;
	
	@ModelAttribute
	public Fea_totaltabVO get(@RequestParam(required=false) String id) {
		Fea_totaltabVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_totaltabVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_totaltabVO();
		}
		return entity;
	}
	
	/**
	 * 财务指标汇总表列表页面
	 */
	@RequiresPermissions("fea:report_totaltab:fea_totaltabVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/report_totaltab/fea_totaltabVOList";
	}
	
		/**
	 * 财务指标汇总表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:report_totaltab:fea_totaltabVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_totaltabVO fea_totaltabVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_totaltabVO> page = fea_totaltabVOService.findPage(new Page<Fea_totaltabVO>(request, response), fea_totaltabVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑财务指标汇总表表单页面
	 */
	@RequiresPermissions(value={"fea:report_totaltab:fea_totaltabVO:view","fea:report_totaltab:fea_totaltabVO:add","fea:report_totaltab:fea_totaltabVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_totaltabVO fea_totaltabVO, Model model) {
		model.addAttribute("fea_totaltabVO", fea_totaltabVO);
		return "modules/fea/report_totaltab/fea_totaltabVOForm";
	}

	/**
	 * 保存财务指标汇总表
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:report_totaltab:fea_totaltabVO:add","fea:report_totaltab:fea_totaltabVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_totaltabVO fea_totaltabVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_totaltabVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_totaltabVOService.save(fea_totaltabVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存财务指标汇总表成功");
		return j;
	}
	
	/**
	 * 删除财务指标汇总表
	 */
	@ResponseBody
	@RequiresPermissions("fea:report_totaltab:fea_totaltabVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_totaltabVO fea_totaltabVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_totaltabVOService.delete(fea_totaltabVO);
		j.setMsg("删除财务指标汇总表成功");
		return j;
	}
	
	/**
	 * 批量删除财务指标汇总表
	 */
	@ResponseBody
	@RequiresPermissions("fea:report_totaltab:fea_totaltabVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_totaltabVOService.delete(fea_totaltabVOService.get(id));
		}
		j.setMsg("删除财务指标汇总表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:report_totaltab:fea_totaltabVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_totaltabVO fea_totaltabVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "财务指标汇总表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_totaltabVO> page = fea_totaltabVOService.findPage(new Page<Fea_totaltabVO>(request, response, -1), fea_totaltabVO);
    		new ExportExcel("财务指标汇总表", Fea_totaltabVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出财务指标汇总表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:report_totaltab:fea_totaltabVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_totaltabVO> list = ei.getDataList(Fea_totaltabVO.class);
			for (Fea_totaltabVO fea_totaltabVO : list){
				try{
					fea_totaltabVOService.save(fea_totaltabVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条财务指标汇总表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条财务指标汇总表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入财务指标汇总表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/report_totaltab/fea_totaltabVO/?repage";
    }
	
	/**
	 * 下载导入财务指标汇总表数据模板
	 */
	@RequiresPermissions("fea:report_totaltab:fea_totaltabVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "财务指标汇总表数据导入模板.xlsx";
    		List<Fea_totaltabVO> list = Lists.newArrayList(); 
    		new ExportExcel("财务指标汇总表数据", Fea_totaltabVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/report_totaltab/fea_totaltabVO/?repage";
    }
	
	@ResponseBody
	@RequiresPermissions(value={"fea:report_totaltab:fea_totaltabVO:add","fea:report_totaltab:fea_totaltabVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "getReportDatas")
    public AjaxJson getReportDatas(String ids, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		AjaxJson j = new AjaxJson();

		List<List<Double>> datas = fea_totaltabVOService.getReportDatas(ids);
		
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

}