/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.set;

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
import com.jeeplus.modules.fea.entity.set.Fea_design_setVO;
import com.jeeplus.modules.fea.service.set.Fea_design_setVOService;

/**
 * 基本参数Controller
 * @author jw
 * @version 2018-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/set/fea_design_setVO")
public class Fea_design_setVOController extends BaseController {

	@Autowired
	private Fea_design_setVOService fea_design_setVOService;
	
	@ModelAttribute
	public Fea_design_setVO get(@RequestParam(required=false) String id) {
		Fea_design_setVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_design_setVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_design_setVO();
		}
		return entity;
	}
	
	/**
	 * 基本参数列表页面
	 */
	@RequiresPermissions("fea:set:fea_design_setVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/set/fea_design_setVOList";
	}
	
		/**
	 * 基本参数列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:set:fea_design_setVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_design_setVO fea_design_setVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_design_setVO> page = fea_design_setVOService.findPage(new Page<Fea_design_setVO>(request, response), fea_design_setVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑基本参数表单页面
	 */
	@RequiresPermissions(value={"fea:set:fea_design_setVO:view","fea:set:fea_design_setVO:add","fea:set:fea_design_setVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_design_setVO fea_design_setVO, Model model) {
		model.addAttribute("fea_design_setVO", fea_design_setVO);
		return "modules/fea/set/fea_design_setVOForm";
	}

	/**
	 * 保存基本参数
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:set:fea_design_setVO:add","fea:set:fea_design_setVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_design_setVO fea_design_setVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_design_setVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_design_setVOService.save(fea_design_setVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存基本参数成功");
		return j;
	}
	
	/**
	 * 删除基本参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:set:fea_design_setVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_design_setVO fea_design_setVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_design_setVOService.delete(fea_design_setVO);
		j.setMsg("删除基本参数成功");
		return j;
	}
	
	/**
	 * 批量删除基本参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:set:fea_design_setVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_design_setVOService.delete(fea_design_setVOService.get(id));
		}
		j.setMsg("删除基本参数成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:set:fea_design_setVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_design_setVO fea_design_setVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "基本参数"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_design_setVO> page = fea_design_setVOService.findPage(new Page<Fea_design_setVO>(request, response, -1), fea_design_setVO);
    		new ExportExcel("基本参数", Fea_design_setVO.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("fea:set:fea_design_setVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_design_setVO> list = ei.getDataList(Fea_design_setVO.class);
			for (Fea_design_setVO fea_design_setVO : list){
				try{
					fea_design_setVOService.save(fea_design_setVO);
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
		return "redirect:"+Global.getAdminPath()+"/fea/set/fea_design_setVO/?repage";
    }
	
	/**
	 * 下载导入基本参数数据模板
	 */
	@RequiresPermissions("fea:set:fea_design_setVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "基本参数数据导入模板.xlsx";
    		List<Fea_design_setVO> list = Lists.newArrayList(); 
    		new ExportExcel("基本参数数据", Fea_design_setVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/set/fea_design_setVO/?repage";
    }

}