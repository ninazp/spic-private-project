/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.transfer;

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
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;
import com.jeeplus.modules.fea.service.transfer.Fea_design_transferVOService;

/**
 * 换热参数Controller
 * @author jw
 * @version 2018-01-21
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/transfer/fea_design_transferVO")
public class Fea_design_transferVOController extends BaseController {

	@Autowired
	private Fea_design_transferVOService fea_design_transferVOService;
	
	@ModelAttribute
	public Fea_design_transferVO get(@RequestParam(required=false) String id) {
		Fea_design_transferVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_design_transferVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_design_transferVO();
		}
		return entity;
	}
	
	/**
	 * 换热参数列表页面
	 */
	@RequiresPermissions("fea:transfer:fea_design_transferVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/transfer/fea_design_transferVOList";
	}
	
		/**
	 * 换热参数列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:transfer:fea_design_transferVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_design_transferVO fea_design_transferVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_design_transferVO> page = fea_design_transferVOService.findPage(new Page<Fea_design_transferVO>(request, response), fea_design_transferVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑换热参数表单页面
	 */
	@RequiresPermissions(value={"fea:transfer:fea_design_transferVO:view","fea:transfer:fea_design_transferVO:add","fea:transfer:fea_design_transferVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_design_transferVO fea_design_transferVO, Model model) {
		model.addAttribute("fea_design_transferVO", fea_design_transferVO);
		return "modules/fea/transfer/fea_design_transferVOForm";
	}

	/**
	 * 保存换热参数
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:transfer:fea_design_transferVO:add","fea:transfer:fea_design_transferVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_design_transferVO fea_design_transferVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_design_transferVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_design_transferVOService.save(fea_design_transferVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存换热参数成功");
		return j;
	}
	
	/**
	 * 删除换热参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:transfer:fea_design_transferVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_design_transferVO fea_design_transferVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_design_transferVOService.delete(fea_design_transferVO);
		j.setMsg("删除换热参数成功");
		return j;
	}
	
	/**
	 * 批量删除换热参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:transfer:fea_design_transferVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_design_transferVOService.delete(fea_design_transferVOService.get(id));
		}
		j.setMsg("删除换热参数成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:transfer:fea_design_transferVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_design_transferVO fea_design_transferVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "换热参数"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_design_transferVO> page = fea_design_transferVOService.findPage(new Page<Fea_design_transferVO>(request, response, -1), fea_design_transferVO);
    		new ExportExcel("换热参数", Fea_design_transferVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出换热参数记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:transfer:fea_design_transferVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_design_transferVO> list = ei.getDataList(Fea_design_transferVO.class);
			for (Fea_design_transferVO fea_design_transferVO : list){
				try{
					fea_design_transferVOService.save(fea_design_transferVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条换热参数记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条换热参数记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入换热参数失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/transfer/fea_design_transferVO/?repage";
    }
	
	/**
	 * 下载导入换热参数数据模板
	 */
	@RequiresPermissions("fea:transfer:fea_design_transferVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "换热参数数据导入模板.xlsx";
    		List<Fea_design_transferVO> list = Lists.newArrayList(); 
    		new ExportExcel("换热参数数据", Fea_design_transferVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/transfer/fea_design_transferVO/?repage";
    }

}