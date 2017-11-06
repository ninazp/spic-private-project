/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.funds;

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
import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.service.funds.Fea_investdisVOService;

/**
 * 投资分配Controller
 * @author jw
 * @version 2017-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/funds/fea_investdisVO")
public class Fea_investdisVOController extends BaseController {

	@Autowired
	private Fea_investdisVOService fea_investdisVOService;
	
	@ModelAttribute
	public Fea_investdisVO get(@RequestParam(required=false) String id) {
		Fea_investdisVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_investdisVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_investdisVO();
		}
		return entity;
	}
	
	/**
	 * 投资分配列表页面
	 */
	@RequiresPermissions("fea:funds:fea_investdisVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/funds/fea_investdisVOList";
	}
	
		/**
	 * 投资分配列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_investdisVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_investdisVO fea_investdisVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_investdisVO> page = fea_investdisVOService.findPage(new Page<Fea_investdisVO>(request, response), fea_investdisVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑投资分配表单页面
	 */
	@RequiresPermissions(value={"fea:funds:fea_investdisVO:view","fea:funds:fea_investdisVO:add","fea:funds:fea_investdisVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_investdisVO fea_investdisVO, Model model) {
		model.addAttribute("fea_investdisVO", fea_investdisVO);
		return "modules/fea/funds/fea_investdisVOForm";
	}

	/**
	 * 保存投资分配
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:funds:fea_investdisVO:add","fea:funds:fea_investdisVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_investdisVO fea_investdisVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_investdisVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		//新增或编辑表单保存
		fea_investdisVOService.save(fea_investdisVO);//保存
		j.setSuccess(true);
		j.setMsg("保存投资分配成功");
		return j;
	}
	
	/**
	 * 删除投资分配
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_investdisVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_investdisVO fea_investdisVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_investdisVOService.delete(fea_investdisVO);
		j.setMsg("删除投资分配成功");
		return j;
	}
	
	/**
	 * 批量删除投资分配
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_investdisVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_investdisVOService.delete(fea_investdisVOService.get(id));
		}
		j.setMsg("删除投资分配成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_investdisVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_investdisVO fea_investdisVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "投资分配"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_investdisVO> page = fea_investdisVOService.findPage(new Page<Fea_investdisVO>(request, response, -1), fea_investdisVO);
    		new ExportExcel("投资分配", Fea_investdisVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出投资分配记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public Fea_investdisVO detail(String id) {
		return fea_investdisVOService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:funds:fea_investdisVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_investdisVO> list = ei.getDataList(Fea_investdisVO.class);
			for (Fea_investdisVO fea_investdisVO : list){
				try{
					fea_investdisVOService.save(fea_investdisVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条投资分配记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条投资分配记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入投资分配失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/funds/fea_investdisVO/?repage";
    }
	
	/**
	 * 下载导入投资分配数据模板
	 */
	@RequiresPermissions("fea:funds:fea_investdisVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "投资分配数据导入模板.xlsx";
    		List<Fea_investdisVO> list = Lists.newArrayList(); 
    		new ExportExcel("投资分配数据", Fea_investdisVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/funds/fea_investdisVO/?repage";
    }
	

}