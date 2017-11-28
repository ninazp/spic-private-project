/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.procost;

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
import com.jeeplus.modules.fea.entity.procost.Fea_productcostVO;
import com.jeeplus.modules.fea.service.procost.Fea_productcostVOService;

/**
 * 生成成本Controller
 * @author jw
 * @version 2017-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/procost/fea_productcostVO")
public class Fea_productcostVOController extends BaseController {

	@Autowired
	private Fea_productcostVOService fea_productcostVOService;
	
	@ModelAttribute
	public Fea_productcostVO get(@RequestParam(required=false) String id) {
		Fea_productcostVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_productcostVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_productcostVO();
		}
		return entity;
	}
	
	/**
	 * 生成成本列表页面
	 */
	@RequiresPermissions("fea:procost:fea_productcostVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/procost/fea_productcostVOList";
	}
	
		/**
	 * 生成成本列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:procost:fea_productcostVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_productcostVO fea_productcostVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_productcostVO> page = fea_productcostVOService.findPage(new Page<Fea_productcostVO>(request, response), fea_productcostVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑生成成本表单页面
	 */
	@RequiresPermissions(value={"fea:procost:fea_productcostVO:view","fea:procost:fea_productcostVO:add","fea:procost:fea_productcostVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_productcostVO fea_productcostVO, Model model) {
		model.addAttribute("fea_productcostVO", fea_productcostVO);
		return "modules/fea/procost/fea_productcostVOForm";
	}

	/**
	 * 保存生成成本
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:procost:fea_productcostVO:add","fea:procost:fea_productcostVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_productcostVO fea_productcostVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_productcostVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		//新增或编辑表单保存
		fea_productcostVOService.save(fea_productcostVO);//保存
		j.setSuccess(true);
		j.setMsg("保存生成成本成功");
		return j;
	}
	
	/**
	 * 删除生成成本
	 */
	@ResponseBody
	@RequiresPermissions("fea:procost:fea_productcostVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_productcostVO fea_productcostVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_productcostVOService.delete(fea_productcostVO);
		j.setMsg("删除生成成本成功");
		return j;
	}
	
	/**
	 * 批量删除生成成本
	 */
	@ResponseBody
	@RequiresPermissions("fea:procost:fea_productcostVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_productcostVOService.delete(fea_productcostVOService.get(id));
		}
		j.setMsg("删除生成成本成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:procost:fea_productcostVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_productcostVO fea_productcostVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生成成本"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_productcostVO> page = fea_productcostVOService.findPage(new Page<Fea_productcostVO>(request, response, -1), fea_productcostVO);
    		new ExportExcel("生成成本", Fea_productcostVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出生成成本记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public Fea_productcostVO detail(String id) {
		return fea_productcostVOService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:procost:fea_productcostVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_productcostVO> list = ei.getDataList(Fea_productcostVO.class);
			for (Fea_productcostVO fea_productcostVO : list){
				try{
					fea_productcostVOService.save(fea_productcostVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条生成成本记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条生成成本记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入生成成本失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/procost/fea_productcostVO/?repage";
    }
	
	/**
	 * 下载导入生成成本数据模板
	 */
	@RequiresPermissions("fea:procost:fea_productcostVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "生成成本数据导入模板.xlsx";
    		List<Fea_productcostVO> list = Lists.newArrayList(); 
    		new ExportExcel("生成成本数据", Fea_productcostVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/procost/fea_productcostVO/?repage";
    }
	

}