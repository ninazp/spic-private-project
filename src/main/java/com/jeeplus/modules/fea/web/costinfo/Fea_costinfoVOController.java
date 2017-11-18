/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.costinfo;

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
import com.jeeplus.modules.fea.entity.costinfo.Fea_costinfoVO;
import com.jeeplus.modules.fea.service.costinfo.Fea_costinfoVOService;

/**
 * 成本种类及产量Controller
 * @author jw
 * @version 2017-11-09
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/costinfo/fea_costinfoVO")
public class Fea_costinfoVOController extends BaseController {

	@Autowired
	private Fea_costinfoVOService fea_costinfoVOService;
	
	@ModelAttribute
	public Fea_costinfoVO get(@RequestParam(required=false) String id) {
		Fea_costinfoVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_costinfoVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_costinfoVO();
		}
		return entity;
	}
	
	/**
	 * 成本种类及产量列表页面
	 */
	@RequiresPermissions("fea:costinfo:fea_costinfoVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/costinfo/fea_costinfoVOList";
	}
	
		/**
	 * 成本种类及产量列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:costinfo:fea_costinfoVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_costinfoVO fea_costinfoVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_costinfoVO> page = fea_costinfoVOService.findPage(new Page<Fea_costinfoVO>(request, response), fea_costinfoVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑成本种类及产量表单页面
	 */
	@RequiresPermissions(value={"fea:costinfo:fea_costinfoVO:view","fea:costinfo:fea_costinfoVO:add","fea:costinfo:fea_costinfoVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_costinfoVO fea_costinfoVO, Model model) {
		model.addAttribute("fea_costinfoVO", fea_costinfoVO);
		return "modules/fea/costinfo/fea_costinfoVOForm";
	}

	/**
	 * 保存成本种类及产量
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:costinfo:fea_costinfoVO:add","fea:costinfo:fea_costinfoVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_costinfoVO fea_costinfoVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_costinfoVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_costinfoVOService.save(fea_costinfoVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存成本种类及产量成功");
		return j;
	}
	
	/**
	 * 删除成本种类及产量
	 */
	@ResponseBody
	@RequiresPermissions("fea:costinfo:fea_costinfoVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_costinfoVO fea_costinfoVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_costinfoVOService.delete(fea_costinfoVO);
		j.setMsg("删除成本种类及产量成功");
		return j;
	}
	
	/**
	 * 批量删除成本种类及产量
	 */
	@ResponseBody
	@RequiresPermissions("fea:costinfo:fea_costinfoVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_costinfoVOService.delete(fea_costinfoVOService.get(id));
		}
		j.setMsg("删除成本种类及产量成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:costinfo:fea_costinfoVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_costinfoVO fea_costinfoVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "成本种类及产量"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_costinfoVO> page = fea_costinfoVOService.findPage(new Page<Fea_costinfoVO>(request, response, -1), fea_costinfoVO);
    		new ExportExcel("成本种类及产量", Fea_costinfoVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出成本种类及产量记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:costinfo:fea_costinfoVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_costinfoVO> list = ei.getDataList(Fea_costinfoVO.class);
			for (Fea_costinfoVO fea_costinfoVO : list){
				try{
					fea_costinfoVOService.save(fea_costinfoVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条成本种类及产量记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条成本种类及产量记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入成本种类及产量失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/costinfo/fea_costinfoVO/?repage";
    }
	
	/**
	 * 下载导入成本种类及产量数据模板
	 */
	@RequiresPermissions("fea:costinfo:fea_costinfoVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "成本种类及产量数据导入模板.xlsx";
    		List<Fea_costinfoVO> list = Lists.newArrayList(); 
    		new ExportExcel("成本种类及产量数据", Fea_costinfoVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/costinfo/fea_costinfoVO/?repage";
    }

}