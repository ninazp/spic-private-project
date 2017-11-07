/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.calmethod;

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
import com.jeeplus.modules.fea.entity.calmethod.Fea_incocalmethodVO;
import com.jeeplus.modules.fea.service.calmethod.Fea_incocalmethodVOService;

/**
 * 测算方式Controller
 * @author jw
 * @version 2017-11-08
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/calmethod/fea_incocalmethodVO")
public class Fea_incocalmethodVOController extends BaseController {

	@Autowired
	private Fea_incocalmethodVOService fea_incocalmethodVOService;
	
	@ModelAttribute
	public Fea_incocalmethodVO get(@RequestParam(required=false) String id) {
		Fea_incocalmethodVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_incocalmethodVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_incocalmethodVO();
		}
		return entity;
	}
	
	/**
	 * 测算方式列表页面
	 */
	@RequiresPermissions("fea:calmethod:fea_incocalmethodVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/calmethod/fea_incocalmethodVOList";
	}
	
		/**
	 * 测算方式列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:calmethod:fea_incocalmethodVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_incocalmethodVO fea_incocalmethodVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_incocalmethodVO> page = fea_incocalmethodVOService.findPage(new Page<Fea_incocalmethodVO>(request, response), fea_incocalmethodVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑测算方式表单页面
	 */
	@RequiresPermissions(value={"fea:calmethod:fea_incocalmethodVO:view","fea:calmethod:fea_incocalmethodVO:add","fea:calmethod:fea_incocalmethodVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_incocalmethodVO fea_incocalmethodVO, Model model) {
		model.addAttribute("fea_incocalmethodVO", fea_incocalmethodVO);
		return "modules/fea/calmethod/fea_incocalmethodVOForm";
	}

	/**
	 * 保存测算方式
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:calmethod:fea_incocalmethodVO:add","fea:calmethod:fea_incocalmethodVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_incocalmethodVO fea_incocalmethodVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_incocalmethodVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		//新增或编辑表单保存
		fea_incocalmethodVOService.save(fea_incocalmethodVO);//保存
		j.setSuccess(true);
		j.setMsg("保存测算方式成功");
		return j;
	}
	
	/**
	 * 删除测算方式
	 */
	@ResponseBody
	@RequiresPermissions("fea:calmethod:fea_incocalmethodVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_incocalmethodVO fea_incocalmethodVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_incocalmethodVOService.delete(fea_incocalmethodVO);
		j.setMsg("删除测算方式成功");
		return j;
	}
	
	/**
	 * 批量删除测算方式
	 */
	@ResponseBody
	@RequiresPermissions("fea:calmethod:fea_incocalmethodVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_incocalmethodVOService.delete(fea_incocalmethodVOService.get(id));
		}
		j.setMsg("删除测算方式成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:calmethod:fea_incocalmethodVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_incocalmethodVO fea_incocalmethodVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "测算方式"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_incocalmethodVO> page = fea_incocalmethodVOService.findPage(new Page<Fea_incocalmethodVO>(request, response, -1), fea_incocalmethodVO);
    		new ExportExcel("测算方式", Fea_incocalmethodVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出测算方式记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public Fea_incocalmethodVO detail(String id) {
		return fea_incocalmethodVOService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:calmethod:fea_incocalmethodVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_incocalmethodVO> list = ei.getDataList(Fea_incocalmethodVO.class);
			for (Fea_incocalmethodVO fea_incocalmethodVO : list){
				try{
					fea_incocalmethodVOService.save(fea_incocalmethodVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条测算方式记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条测算方式记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入测算方式失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/calmethod/fea_incocalmethodVO/?repage";
    }
	
	/**
	 * 下载导入测算方式数据模板
	 */
	@RequiresPermissions("fea:calmethod:fea_incocalmethodVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "测算方式数据导入模板.xlsx";
    		List<Fea_incocalmethodVO> list = Lists.newArrayList(); 
    		new ExportExcel("测算方式数据", Fea_incocalmethodVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/calmethod/fea_incocalmethodVO/?repage";
    }
	

}