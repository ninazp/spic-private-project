/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.downhole;

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
import com.jeeplus.modules.fea.entity.downhole.Fea_design_downholeVO;
import com.jeeplus.modules.fea.service.downhole.Fea_design_downholeVOService;

/**
 * 井下参数Controller
 * @author jw
 * @version 2018-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/downhole/fea_design_downholeVO")
public class Fea_design_downholeVOController extends BaseController {

	@Autowired
	private Fea_design_downholeVOService fea_design_downholeVOService;
	
	@ModelAttribute
	public Fea_design_downholeVO get(@RequestParam(required=false) String id) {
		Fea_design_downholeVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_design_downholeVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_design_downholeVO();
		}
		return entity;
	}
	
	/**
	 * 井下参数列表页面
	 */
	@RequiresPermissions("fea:downhole:fea_design_downholeVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/downhole/fea_design_downholeVOList";
	}
	
		/**
	 * 井下参数列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:downhole:fea_design_downholeVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_design_downholeVO fea_design_downholeVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_design_downholeVO> page = fea_design_downholeVOService.findPage(new Page<Fea_design_downholeVO>(request, response), fea_design_downholeVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑井下参数表单页面
	 */
	@RequiresPermissions(value={"fea:downhole:fea_design_downholeVO:view","fea:downhole:fea_design_downholeVO:add","fea:downhole:fea_design_downholeVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_design_downholeVO fea_design_downholeVO, Model model) {
		model.addAttribute("fea_design_downholeVO", fea_design_downholeVO);
		return "modules/fea/downhole/fea_design_downholeVOForm";
	}

	/**
	 * 保存井下参数
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:downhole:fea_design_downholeVO:add","fea:downhole:fea_design_downholeVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_design_downholeVO fea_design_downholeVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_design_downholeVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_design_downholeVOService.save(fea_design_downholeVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存井下参数成功");
		return j;
	}
	
	/**
	 * 删除井下参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:downhole:fea_design_downholeVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_design_downholeVO fea_design_downholeVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_design_downholeVOService.delete(fea_design_downholeVO);
		j.setMsg("删除井下参数成功");
		return j;
	}
	
	/**
	 * 批量删除井下参数
	 */
	@ResponseBody
	@RequiresPermissions("fea:downhole:fea_design_downholeVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_design_downholeVOService.delete(fea_design_downholeVOService.get(id));
		}
		j.setMsg("删除井下参数成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:downhole:fea_design_downholeVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_design_downholeVO fea_design_downholeVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "井下参数"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_design_downholeVO> page = fea_design_downholeVOService.findPage(new Page<Fea_design_downholeVO>(request, response, -1), fea_design_downholeVO);
    		new ExportExcel("井下参数", Fea_design_downholeVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出井下参数记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:downhole:fea_design_downholeVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_design_downholeVO> list = ei.getDataList(Fea_design_downholeVO.class);
			for (Fea_design_downholeVO fea_design_downholeVO : list){
				try{
					fea_design_downholeVOService.save(fea_design_downholeVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条井下参数记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条井下参数记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入井下参数失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/downhole/fea_design_downholeVO/?repage";
    }
	
	/**
	 * 下载导入井下参数数据模板
	 */
	@RequiresPermissions("fea:downhole:fea_design_downholeVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "井下参数数据导入模板.xlsx";
    		List<Fea_design_downholeVO> list = Lists.newArrayList(); 
    		new ExportExcel("井下参数数据", Fea_design_downholeVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/downhole/fea_design_downholeVO/?repage";
    }

}