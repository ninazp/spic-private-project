/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.design;

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
import com.jeeplus.core.mapper.JsonMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.service.design.Fea_design_heatVOService;

/**
 * 供热参数Controller
 * @author jw
 * @version 2018-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/design/fea_design_heatVO")
public class Fea_design_heatVOController extends BaseController {

	@Autowired
	private Fea_design_heatVOService fea_design_heatVOService;
	
	@ModelAttribute
	public Fea_design_heatVO get(@RequestParam(required=false) String id) {
		Fea_design_heatVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_design_heatVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_design_heatVO();
		}
		return entity;
	}
	
	/**
	 * 供热参数列表页面
	 */
	@RequiresPermissions("fea:design:fea_design_heatVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/design/fea_design_heatVOList";
	}
	
		/**
	 * 供热参数列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:design:fea_design_heatVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_design_heatVO fea_design_heatVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_design_heatVO> page = fea_design_heatVOService.findPage(new Page<Fea_design_heatVO>(request, response), fea_design_heatVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑供热参数表单页面
	 */
	@RequiresPermissions(value={"fea:design:fea_design_heatVO:view","fea:design:fea_design_heatVO:add","fea:design:fea_design_heatVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_design_heatVO fea_design_heatVO, Model model) {
		model.addAttribute("fea_design_heatVO", fea_design_heatVO);
		return "modules/fea/design/fea_design_heatVOForm";
	}
	
	/*@ResponseBody
	@RequiresPermissions(value={"fea:design:fea_design_heatVO:add","fea:design:fea_design_heatVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "findtest")
	public AjaxJson findtest(String feaProjectId, Fea_design_heatVO fea_design_heatVO, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		fea_design_heatVO = fea_design_heatVOService.getFea_design_heatVOByProjectId(feaProjectId);
		Fea_design_heatVO re = new Fea_design_heatVO();
		re = fea_design_heatVOService.get(fea_design_heatVO.getId());
		model.addAttribute("fea_design_heatVO", re);
		j.setSuccess(true);
		j.put("fea_design_heatVO", re);
		j.setMsg("你妹");
		return j;
	}*/
	
	@ResponseBody
	@RequiresPermissions(value={"fea:design:fea_design_heatVO:add","fea:design:fea_design_heatVO:edit","fea:design:fea_design_heatVO:list"},logical=Logical.OR)
	@RequestMapping(value = "getFormByProjectId")
	public AjaxJson getFormByProjectId(String feaProjectId, Fea_design_heatVO fea_design_heatVO, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		fea_design_heatVO = fea_design_heatVOService.getFea_design_heatVOByProjectId(feaProjectId);
		if(null == fea_design_heatVO || null == fea_design_heatVO.getId()){
			j.setSuccess(false);
			j.setMsg("该项目未进行供热参数录入，请点击修改进行数据录入");
			return j;
		}
		
		
		Fea_design_heatVO re = new Fea_design_heatVO();
		re = fea_design_heatVOService.get(fea_design_heatVO.getId());
		//model.addAttribute("fea_design_heatVO", re);
		j.setSuccess(true);
		j.put("fea_design_heatVO", re);
		j.setMsg("获取信息成功");
		return j;
	}

	/**
	 * 保存供热参数
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:design:fea_design_heatVO:add","fea:design:fea_design_heatVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_design_heatVO fea_design_heatVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_design_heatVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_design_heatVOService.save(fea_design_heatVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存供热参数成功");
		return j;
	}
	
	/**
	 * 删除供热参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:design:fea_design_heatVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_design_heatVO fea_design_heatVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_design_heatVOService.delete(fea_design_heatVO);
		j.setMsg("删除供热参数成功");
		return j;
	}
	
	/**
	 * 批量删除供热参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:design:fea_design_heatVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_design_heatVOService.delete(fea_design_heatVOService.get(id));
		}
		j.setMsg("删除供热参数成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:design:fea_design_heatVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_design_heatVO fea_design_heatVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "供热参数"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_design_heatVO> page = fea_design_heatVOService.findPage(new Page<Fea_design_heatVO>(request, response, -1), fea_design_heatVO);
    		new ExportExcel("供热参数", Fea_design_heatVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出供热参数记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:design:fea_design_heatVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_design_heatVO> list = ei.getDataList(Fea_design_heatVO.class);
			for (Fea_design_heatVO fea_design_heatVO : list){
				try{
					fea_design_heatVOService.save(fea_design_heatVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条供热参数记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条供热参数记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入供热参数失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/design/fea_design_heatVO/?repage";
    }
	
	/**
	 * 下载导入供热参数数据模板
	 */
	@RequiresPermissions("fea:design:fea_design_heatVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "供热参数数据导入模板.xlsx";
    		List<Fea_design_heatVO> list = Lists.newArrayList(); 
    		new ExportExcel("供热参数数据", Fea_design_heatVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/design/fea_design_heatVO/?repage";
    }

}