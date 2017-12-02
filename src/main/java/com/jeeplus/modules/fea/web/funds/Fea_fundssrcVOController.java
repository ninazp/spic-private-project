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
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.service.funds.Fea_fundssrcVOService;

/**
 * 资金来源Controller
 * @author jw
 * @version 2017-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/funds/fea_fundssrcVO")
public class Fea_fundssrcVOController extends BaseController {

	@Autowired
	private Fea_fundssrcVOService fea_fundssrcVOService;
	
	@ModelAttribute
	public Fea_fundssrcVO get(@RequestParam(required=false) String id) {
		Fea_fundssrcVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_fundssrcVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_fundssrcVO();
		}
		return entity;
	}
	
	/**
	 * 资金来源列表页面
	 */
	@RequiresPermissions("fea:funds:fea_fundssrcVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/funds/fea_fundssrcVOList";
	}
	
		/**
	 * 资金来源列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_fundssrcVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_fundssrcVO fea_fundssrcVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_fundssrcVO> page = fea_fundssrcVOService.findPage(new Page<Fea_fundssrcVO>(request, response), fea_fundssrcVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑资金来源表单页面
	 */
	@RequiresPermissions(value={"fea:funds:fea_fundssrcVO:view","fea:funds:fea_fundssrcVO:add","fea:funds:fea_fundssrcVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_fundssrcVO fea_fundssrcVO, Model model) {
		model.addAttribute("fea_fundssrcVO", fea_fundssrcVO);
		return "modules/fea/funds/fea_fundssrcVOForm";
	}

	/**
	 * 保存资金来源
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:funds:fea_fundssrcVO:add","fea:funds:fea_fundssrcVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_fundssrcVO fea_fundssrcVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_fundssrcVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		//新增或编辑表单保存
		fea_fundssrcVOService.save(fea_fundssrcVO);//保存
		j.setSuccess(true);
		j.setMsg("保存资金来源成功");
		return j;
	}
	
	/**
	 * 删除资金来源
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_fundssrcVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_fundssrcVO fea_fundssrcVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_fundssrcVOService.delete(fea_fundssrcVO);
		j.setMsg("删除资金来源成功");
		return j;
	}
	
	/**
	 * 批量删除资金来源
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_fundssrcVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_fundssrcVOService.delete(fea_fundssrcVOService.get(id));
		}
		j.setMsg("删除资金来源成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:funds:fea_fundssrcVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_fundssrcVO fea_fundssrcVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "资金来源"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_fundssrcVO> page = fea_fundssrcVOService.findPage(new Page<Fea_fundssrcVO>(request, response, -1), fea_fundssrcVO);
    		new ExportExcel("资金来源", Fea_fundssrcVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出资金来源记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public Fea_fundssrcVO detail(String id) {
		return fea_fundssrcVOService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:funds:fea_fundssrcVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_fundssrcVO> list = ei.getDataList(Fea_fundssrcVO.class);
			for (Fea_fundssrcVO fea_fundssrcVO : list){
				try{
					fea_fundssrcVOService.save(fea_fundssrcVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资金来源记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资金来源记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资金来源失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/funds/fea_fundssrcVO/?repage";
    }
	
	/**
	 * 下载导入资金来源数据模板
	 */
	@RequiresPermissions("fea:funds:fea_fundssrcVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资金来源数据导入模板.xlsx";
    		List<Fea_fundssrcVO> list = Lists.newArrayList(); 
    		new ExportExcel("资金来源数据", Fea_fundssrcVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/funds/fea_fundssrcVO/?repage";
    }
	

}