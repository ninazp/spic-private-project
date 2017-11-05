/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.projectmng.web;

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
import com.jeeplus.modules.projectmng.entity.ProjectMngB;
import com.jeeplus.modules.projectmng.service.ProjectMngBService;

/**
 * 项目管理子表Controller
 * @author zhaopeng
 * @version 2017-08-09
 */
@Controller
@RequestMapping(value = "${adminPath}/projectmng/projectMngB")
public class ProjectMngBController extends BaseController {

	@Autowired
	private ProjectMngBService projectMngBService;
	
	@ModelAttribute
	public ProjectMngB get(@RequestParam(required=false) String id) {
		ProjectMngB entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectMngBService.get(id);
		}
		if (entity == null){
			entity = new ProjectMngB();
		}
		return entity;
	}
	
	/**
	 * 项目管理子表列表页面
	 */
	@RequiresPermissions("projectmng:projectMngB:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/projectmng/projectMngBList";
	}
	
		/**
	 * 项目管理子表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("projectmng:projectMngB:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProjectMngB projectMngB, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectMngB> page = projectMngBService.findPage(new Page<ProjectMngB>(request, response), projectMngB); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑项目管理子表表单页面
	 */
	@RequiresPermissions(value={"projectmng:projectMngB:view","projectmng:projectMngB:add","projectmng:projectMngB:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProjectMngB projectMngB, Model model) {
		model.addAttribute("projectMngB", projectMngB);
		return "modules/projectmng/projectMngBForm";
	}

	/**
	 * 保存项目管理子表
	 */
	@ResponseBody
	@RequiresPermissions(value={"projectmng:projectMngB:add","projectmng:projectMngB:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProjectMngB projectMngB, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, projectMngB)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		projectMngBService.save(projectMngB);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存项目管理子表成功");
		return j;
	}
	
	/**
	 * 删除项目管理子表
	 */
	@ResponseBody
	@RequiresPermissions("projectmng:projectMngB:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProjectMngB projectMngB, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		projectMngBService.delete(projectMngB);
		j.setMsg("删除项目管理子表成功");
		return j;
	}
	
	/**
	 * 批量删除项目管理子表
	 */
	@ResponseBody
	@RequiresPermissions("projectmng:projectMngB:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			projectMngBService.delete(projectMngBService.get(id));
		}
		j.setMsg("删除项目管理子表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("projectmng:projectMngB:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProjectMngB projectMngB, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "项目管理子表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProjectMngB> page = projectMngBService.findPage(new Page<ProjectMngB>(request, response, -1), projectMngB);
    		new ExportExcel("项目管理子表", ProjectMngB.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出项目管理子表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("projectmng:projectMngB:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProjectMngB> list = ei.getDataList(ProjectMngB.class);
			for (ProjectMngB projectMngB : list){
				try{
					projectMngBService.save(projectMngB);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条项目管理子表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条项目管理子表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目管理子表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/projectmng/projectMngB/?repage";
    }
	
	/**
	 * 下载导入项目管理子表数据模板
	 */
	@RequiresPermissions("projectmng:projectMngB:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目管理子表数据导入模板.xlsx";
    		List<ProjectMngB> list = Lists.newArrayList(); 
    		new ExportExcel("项目管理子表数据", ProjectMngB.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/projectmng/projectMngB/?repage";
    }

}