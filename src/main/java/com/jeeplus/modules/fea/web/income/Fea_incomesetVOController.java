/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.income;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.entity.income.Fea_incomesetVO;
import com.jeeplus.modules.fea.service.income.Fea_incomesetVOService;

/**
 * 基本参数Controller
 * @author jw
 * @version 2017-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/income/fea_incomesetVO")
public class Fea_incomesetVOController extends BaseController {

	@Autowired
	private Fea_incomesetVOService fea_incomesetVOService;
	
	@ModelAttribute
	public Fea_incomesetVO get(@RequestParam(required=false) String id) {
		Fea_incomesetVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_incomesetVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_incomesetVO();
		}
		return entity;
	}
	
	/**
	 * 基本参数列表页面
	 */
	@RequiresPermissions("fea:income:fea_incomesetVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/income/fea_incomesetVOList";
	}
	
		/**
	 * 基本参数列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:income:fea_incomesetVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_incomesetVO fea_incomesetVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_incomesetVO> page = fea_incomesetVOService.findPage(new Page<Fea_incomesetVO>(request, response), fea_incomesetVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑基本参数表单页面
	 */
	@RequiresPermissions(value={"fea:income:fea_incomesetVO:view","fea:income:fea_incomesetVO:add","fea:income:fea_incomesetVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_incomesetVO fea_incomesetVO, Model model) {
		model.addAttribute("fea_incomesetVO", fea_incomesetVO);
		return "modules/fea/income/fea_incomesetVOForm";
	}
	
	@ResponseBody
	@RequiresPermissions(value={"fea:income:fea_incomesetVO:add","fea:income:fea_incomesetVO:edit","fea:income:fea_incomesetVO:list"},logical=Logical.OR)
	@RequestMapping(value = "getFormByProjectId")
	public AjaxJson getFormByProjectId(String feaProjectId, Fea_incomesetVO fea_incomesetVO, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		fea_incomesetVO = fea_incomesetVOService.getFea_incomesetVOByProjectId(feaProjectId);
		if(null == fea_incomesetVO || null == fea_incomesetVO.getId()){
			j.setSuccess(false);
			j.setMsg("该项目没有数据，请点击修改进行数据录入");
			return j;
		}
		
		
		Fea_incomesetVO re = new Fea_incomesetVO();
		re = fea_incomesetVOService.get(fea_incomesetVO.getId());
		//model.addAttribute("fea_design_heatVO", re);
		j.setSuccess(true);
		j.put("fea_design_heatVO", re);
		j.setMsg("获取信息成功");
		return j;
	}

	/**
	 * 保存基本参数
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:income:fea_incomesetVO:add","fea:income:fea_incomesetVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_incomesetVO fea_incomesetVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_incomesetVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_incomesetVOService.save(fea_incomesetVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存基本参数成功");
		return j;
	}
	
	/**
	 * 删除基本参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:income:fea_incomesetVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_incomesetVO fea_incomesetVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_incomesetVOService.delete(fea_incomesetVO);
		j.setMsg("删除基本参数成功");
		return j;
	}
	
	/**
	 * 批量删除基本参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:income:fea_incomesetVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_incomesetVOService.delete(fea_incomesetVOService.get(id));
		}
		j.setMsg("删除基本参数成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:income:fea_incomesetVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_incomesetVO fea_incomesetVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "基本参数"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_incomesetVO> page = fea_incomesetVOService.findPage(new Page<Fea_incomesetVO>(request, response, -1), fea_incomesetVO);
    		new ExportExcel("基本参数", Fea_incomesetVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出基本参数记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:income:fea_incomesetVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_incomesetVO> list = ei.getDataList(Fea_incomesetVO.class);
			for (Fea_incomesetVO fea_incomesetVO : list){
				try{
					fea_incomesetVOService.save(fea_incomesetVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条基本参数记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条基本参数记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入基本参数失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/income/fea_incomesetVO/?repage";
    }
	
	/**
	 * 下载导入基本参数数据模板
	 */
	@RequiresPermissions("fea:income:fea_incomesetVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "基本参数数据导入模板.xlsx";
    		List<Fea_incomesetVO> list = Lists.newArrayList(); 
    		new ExportExcel("基本参数数据", Fea_incomesetVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/income/fea_incomesetVO/?repage";
    }

}