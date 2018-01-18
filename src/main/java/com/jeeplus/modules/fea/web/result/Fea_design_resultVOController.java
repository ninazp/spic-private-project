/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.result;

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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.fea.designcal.PubDesignCal;
import com.jeeplus.modules.fea.entity.result.Fea_design_resultVO;
import com.jeeplus.modules.fea.service.project.FeaProjectBService;
import com.jeeplus.modules.fea.service.result.Fea_design_resultVOService;

/**
 * 方案运行费用结果表Controller
 * @author jw
 * @version 2018-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/result/fea_design_resultVO")
public class Fea_design_resultVOController extends BaseController {

	@Autowired
	private Fea_design_resultVOService fea_design_resultVOService;
	@Autowired
	private FeaProjectBService feaProjectBService;
	
	@ModelAttribute
	public Fea_design_resultVO get(@RequestParam(required=false) String id) {
		Fea_design_resultVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_design_resultVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_design_resultVO();
		}
		return entity;
	}
	
	/**
	 * 方案运行费用结果表列表页面
	 */
	@RequiresPermissions("fea:result:fea_design_resultVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/result/fea_design_resultVOList";
	}
	
		/**
	 * 方案运行费用结果表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:result:fea_design_resultVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_design_resultVO fea_design_resultVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_design_resultVO> page = fea_design_resultVOService.findPage(new Page<Fea_design_resultVO>(request, response), fea_design_resultVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑方案运行费用结果表表单页面
	 */
	@RequiresPermissions(value={"fea:result:fea_design_resultVO:view","fea:result:fea_design_resultVO:add","fea:result:fea_design_resultVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_design_resultVO fea_design_resultVO, Model model) {
		model.addAttribute("fea_design_resultVO", fea_design_resultVO);
		return "modules/fea/result/fea_design_resultVOForm";
	}

	/**
	 * 保存方案运行费用结果表
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:result:fea_design_resultVO:add","fea:result:fea_design_resultVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_design_resultVO fea_design_resultVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_design_resultVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_design_resultVOService.save(fea_design_resultVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存方案运行费用结果表成功");
		return j;
	}
	
	/**
	 * 删除方案运行费用结果表
	 */
	@ResponseBody
	@RequiresPermissions("fea:result:fea_design_resultVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_design_resultVO fea_design_resultVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_design_resultVOService.delete(fea_design_resultVO);
		j.setMsg("删除方案运行费用结果表成功");
		return j;
	}
	
	/**
	 * 批量删除方案运行费用结果表
	 */
	@ResponseBody
	@RequiresPermissions("fea:result:fea_design_resultVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_design_resultVOService.delete(fea_design_resultVOService.get(id));
		}
		j.setMsg("删除方案运行费用结果表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:result:fea_design_resultVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_design_resultVO fea_design_resultVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "方案运行费用结果表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_design_resultVO> page = fea_design_resultVOService.findPage(new Page<Fea_design_resultVO>(request, response, -1), fea_design_resultVO);
    		new ExportExcel("方案运行费用结果表", Fea_design_resultVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出方案运行费用结果表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:result:fea_design_resultVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_design_resultVO> list = ei.getDataList(Fea_design_resultVO.class);
			for (Fea_design_resultVO fea_design_resultVO : list){
				try{
					fea_design_resultVOService.save(fea_design_resultVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条方案运行费用结果表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条方案运行费用结果表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入方案运行费用结果表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/result/fea_design_resultVO/?repage";
    }
	
	/**
	 * 下载导入方案运行费用结果表数据模板
	 */
	@RequiresPermissions("fea:result:fea_design_resultVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "方案运行费用结果表数据导入模板.xlsx";
    		List<Fea_design_resultVO> list = Lists.newArrayList(); 
    		new ExportExcel("方案运行费用结果表数据", Fea_design_resultVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/result/fea_design_resultVO/?repage";
    }
	
	/**
	 * 计算
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:result:fea_design_resultVO:add","fea:result:fea_design_resultVO:edit","fea:result:fea_design_resultVO:del"},logical=Logical.OR)
	@RequestMapping(value = "calculation")
	public AjaxJson calculation(String projectId, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			PubDesignCal pubDesignCal = (PubDesignCal) wac.getBean("pubDesignCal");
			
			pubDesignCal.calprocess(projectId);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("执行计算错误："+e.getMessage());
			return j;
		}
		
		j.setSuccess(true);
		j.setMsg("计算完成");
		return j;
	}

	public FeaProjectBService getFeaProjectBService() {
		return feaProjectBService;
	}

	public void setFeaProjectBService(FeaProjectBService feaProjectBService) {
		this.feaProjectBService = feaProjectBService;
	}

}