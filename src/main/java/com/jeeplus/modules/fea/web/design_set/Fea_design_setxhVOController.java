/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.design_set;

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
import com.jeeplus.modules.fea.entity.design_set.Fea_design_setxhVO;
import com.jeeplus.modules.fea.entity.set.Fea_design_setVO;
import com.jeeplus.modules.fea.service.design_set.Fea_design_setxhVOService;
import com.jeeplus.modules.fea.service.set.Fea_design_setVOService;

/**
 * 循环水泵价格Controller
 * @author jw
 * @version 2018-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/design_set/fea_design_setxhVO")
public class Fea_design_setxhVOController extends BaseController {

	@Autowired
	private Fea_design_setxhVOService fea_design_setxhVOService;
	
	@Autowired
	private Fea_design_setVOService fea_design_setVOService;
	
	@ModelAttribute
	public Fea_design_setxhVO get(@RequestParam(required=false) String id) {
		Fea_design_setxhVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_design_setxhVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_design_setxhVO();
		}
		return entity;
	}
	
	/**
	 * 循环水泵价格列表页面
	 */
	@RequiresPermissions("fea:design_set:fea_design_setxhVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/design_set/fea_design_setxhVOList";
	}
	
		/**
	 * 循环水泵价格列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:design_set:fea_design_setxhVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_design_setxhVO fea_design_setxhVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_design_setxhVO> page = fea_design_setxhVOService.findPage(new Page<Fea_design_setxhVO>(request, response), fea_design_setxhVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑循环水泵价格表单页面
	 */
	@RequiresPermissions(value={"fea:design_set:fea_design_setxhVO:view","fea:design_set:fea_design_setxhVO:add","fea:design_set:fea_design_setxhVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_design_setxhVO fea_design_setxhVO, Model model) {
		model.addAttribute("fea_design_setxhVO", fea_design_setxhVO);
		if(StringUtils.isBlank(fea_design_setxhVO.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/fea/design_set/fea_design_setxhVOForm";
	}
	
	
	@ResponseBody
	@RequiresPermissions(value={"fea:set:fea_design_setxhVO:add","fea:set:fea_design_setxhVO:edit","fea:set:fea_design_setxhVO:list"},logical=Logical.OR)
	@RequestMapping(value = "getFormByProjectId")
	public AjaxJson getFormByProjectId(String feaProjectId, Fea_design_setVO fea_design_setVO, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		fea_design_setVO = fea_design_setVOService.getFea_design_setVOByProjectId(feaProjectId);
		if(null == fea_design_setVO || null == fea_design_setVO.getId()){
			j.setSuccess(false);
			j.setMsg("该项目没有数据，请点击修改进行数据录入");
			return j;
		}
		
		
		Fea_design_setVO re = new Fea_design_setVO();
		re = fea_design_setVOService.get(fea_design_setVO.getId());
		//model.addAttribute("fea_design_heatVO", re);
		j.setSuccess(true);
		j.put("fea_design_heatVO", re);
		j.setMsg("获取信息成功");
		return j;
	}


	/**
	 * 保存循环水泵价格
	 */
	@RequiresPermissions(value={"fea:design_set:fea_design_setxhVO:add","fea:design_set:fea_design_setxhVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Fea_design_setxhVO fea_design_setxhVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, fea_design_setxhVO)){
			return form(fea_design_setxhVO, model);
		}
		//新增或编辑表单保存
		fea_design_setxhVOService.save(fea_design_setxhVO);//保存
		addMessage(redirectAttributes, "保存循环水泵价格成功");
		return "redirect:"+Global.getAdminPath()+"/fea/design_set/fea_design_setxhVO/?repage";
	}
	
	/**
	 * 删除循环水泵价格
	 */
	@ResponseBody
	@RequiresPermissions("fea:design_set:fea_design_setxhVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_design_setxhVO fea_design_setxhVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_design_setxhVOService.delete(fea_design_setxhVO);
		j.setMsg("删除循环水泵价格成功");
		return j;
	}
	
	/**
	 * 批量删除循环水泵价格
	 */
	@ResponseBody
	@RequiresPermissions("fea:design_set:fea_design_setxhVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_design_setxhVOService.delete(fea_design_setxhVOService.get(id));
		}
		j.setMsg("删除循环水泵价格成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:design_set:fea_design_setxhVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_design_setxhVO fea_design_setxhVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "循环水泵价格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_design_setxhVO> page = fea_design_setxhVOService.findPage(new Page<Fea_design_setxhVO>(request, response, -1), fea_design_setxhVO);
    		new ExportExcel("循环水泵价格", Fea_design_setxhVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出循环水泵价格记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:design_set:fea_design_setxhVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_design_setxhVO> list = ei.getDataList(Fea_design_setxhVO.class);
			for (Fea_design_setxhVO fea_design_setxhVO : list){
				try{
					fea_design_setxhVOService.save(fea_design_setxhVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条循环水泵价格记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条循环水泵价格记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入循环水泵价格失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/design_set/fea_design_setxhVO/?repage";
    }
	
	/**
	 * 下载导入循环水泵价格数据模板
	 */
	@RequiresPermissions("fea:design_set:fea_design_setxhVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "循环水泵价格数据导入模板.xlsx";
    		List<Fea_design_setxhVO> list = Lists.newArrayList(); 
    		new ExportExcel("循环水泵价格数据", Fea_design_setxhVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/design_set/fea_design_setxhVO/?repage";
    }

}