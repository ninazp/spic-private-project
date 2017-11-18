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
import com.jeeplus.modules.fea.entity.funds.Fea_capformVO;
import com.jeeplus.modules.fea.service.funds.Fea_capformVOService;

/**
 * 资产形成Controller
 * @author jw
 * @version 2017-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/funds/fea_capformVO")
public class Fea_capformVOController extends BaseController {

	@Autowired
	private Fea_capformVOService fea_capformVOService;
	
	@ModelAttribute
	public Fea_capformVO get(@RequestParam(required=false) String id) {
		Fea_capformVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_capformVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_capformVO();
		}
		return entity;
	}
	
	/**
	 * 资产形成列表页面
	 */
	@RequiresPermissions("fea:funds:fea_capformVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/funds/fea_capformVOList";
	}
	
		/**
	 * 资产形成列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_capformVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_capformVO fea_capformVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_capformVO> page = fea_capformVOService.findPage(new Page<Fea_capformVO>(request, response), fea_capformVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资产形成表单页面
	 */
	@RequiresPermissions(value={"fea:funds:fea_capformVO:view","fea:funds:fea_capformVO:add","fea:funds:fea_capformVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_capformVO fea_capformVO, Model model) {
		model.addAttribute("fea_capformVO", fea_capformVO);
		return "modules/fea/funds/fea_capformVOForm";
	}

	/**
	 * 保存资产形成
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:funds:fea_capformVO:add","fea:funds:fea_capformVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_capformVO fea_capformVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_capformVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_capformVOService.save(fea_capformVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存资产形成成功");
		return j;
	}
	
	/**
	 * 删除资产形成
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_capformVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_capformVO fea_capformVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_capformVOService.delete(fea_capformVO);
		j.setMsg("删除资产形成成功");
		return j;
	}
	
	/**
	 * 批量删除资产形成
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_capformVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_capformVOService.delete(fea_capformVOService.get(id));
		}
		j.setMsg("删除资产形成成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_capformVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_capformVO fea_capformVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资产形成"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_capformVO> page = fea_capformVOService.findPage(new Page<Fea_capformVO>(request, response, -1), fea_capformVO);
    		new ExportExcel("资产形成", Fea_capformVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资产形成记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:funds:fea_capformVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_capformVO> list = ei.getDataList(Fea_capformVO.class);
			for (Fea_capformVO fea_capformVO : list){
				try{
					fea_capformVOService.save(fea_capformVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资产形成记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资产形成记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资产形成失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/funds/fea_capformVO/?repage";
    }
	
	/**
	 * 下载导入资产形成数据模板
	 */
	@RequiresPermissions("fea:funds:fea_capformVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资产形成数据导入模板.xlsx";
    		List<Fea_capformVO> list = Lists.newArrayList(); 
    		new ExportExcel("资产形成数据", Fea_capformVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/funds/fea_capformVO/?repage";
    }

}