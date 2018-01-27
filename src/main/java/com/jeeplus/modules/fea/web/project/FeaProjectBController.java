/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.project;

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
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.fea.entity.project.FeaProject;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.service.project.FeaProjectBService;

/**
 * 项目（子表）Controller
 * @author zp
 * @version 2017-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/project/feaProjectB")
public class FeaProjectBController extends BaseController {

	@Autowired
	private FeaProjectBService feaProjectBService;
	
	@ModelAttribute
	public FeaProjectB get(@RequestParam(required=false) String id) {
		FeaProjectB entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feaProjectBService.get(id);
		}
		if (entity == null){
			entity = new FeaProjectB();
		}
		return entity;
	}
	
	/**
	 * 项目（子表）列表页面
	 */
	@RequiresPermissions("fea:project:feaProjectB:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/project/feaProjectBList";
	}
	
		/**
	 * 项目（子表）列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:project:feaProjectB:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FeaProjectB feaProjectB, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FeaProjectB> page = feaProjectBService.findPage(new Page<FeaProjectB>(request, response), feaProjectB); 
		return getBootstrapData(page);
	}
	
	/**
	 * 项目（子表）列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:project:feaProjectB:list")
	@RequestMapping(value = "dataAll")
	public List<FeaProjectB> dataAll(FeaProjectB feaProjectB, HttpServletRequest request, HttpServletResponse response, Model model) {
		return feaProjectBService.findList(feaProjectB);
	}

	/**
	 * 查看，增加，编辑项目（子表）表单页面
	 */
	@RequiresPermissions(value={"fea:project:feaProjectB:view","fea:project:feaProjectB:add","fea:project:feaProjectB:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FeaProjectB feaProjectB, Model model) {
		model.addAttribute("feaProjectB", feaProjectB);
		return "modules/fea/project/feaProjectBForm";
	}

	/**
	 * 保存项目（子表）
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:project:feaProjectB:add","fea:project:feaProjectB:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FeaProjectB feaProjectB, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, feaProjectB)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		feaProjectBService.save(feaProjectB);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存项目（子表）成功");
		return j;
	}
	
	/**
	 * 删除项目（子表）
	 */
	@ResponseBody
	@RequiresPermissions("fea:project:feaProjectB:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FeaProjectB feaProjectB, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		feaProjectBService.delete(feaProjectB);
		j.setMsg("删除项目（子表）成功");
		return j;
	}
	
	/**
	 * 批量删除项目（子表）
	 */
	@ResponseBody
	@RequiresPermissions("fea:project:feaProjectB:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			feaProjectBService.delete(feaProjectBService.get(id));
		}
		j.setMsg("删除项目（子表）成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:project:feaProjectB:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FeaProjectB feaProjectB, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "项目（子表）"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FeaProjectB> page = feaProjectBService.findPage(new Page<FeaProjectB>(request, response, -1), feaProjectB);
    		new ExportExcel("项目（子表）", FeaProjectB.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出项目（子表）记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:project:feaProjectB:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FeaProjectB> list = ei.getDataList(FeaProjectB.class);
			for (FeaProjectB feaProjectB : list){
				try{
					feaProjectBService.save(feaProjectB);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条项目（子表）记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条项目（子表）记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目（子表）失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/project/feaProjectB/?repage";
    }
	
	/**
	 * 下载导入项目（子表）数据模板
	 */
	@RequiresPermissions("fea:project:feaProjectB:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目（子表）数据导入模板.xlsx";
    		List<FeaProjectB> list = Lists.newArrayList(); 
    		new ExportExcel("项目（子表）数据", FeaProjectB.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/project/feaProjectB/?repage";
    }
	
	/**
	 * 获取项目数据byID
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:project:feaProjectB:add","fea:project:feaProjectB:edit"},logical=Logical.OR)
	@RequestMapping(value = "getProjectById")
	public AjaxJson getProjectById(String id, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		FeaProjectB feaProjectB = new FeaProjectB();
		
		if(null != id){
			feaProjectB = feaProjectBService.get(id);
		}
		AjaxJson j = new AjaxJson();
		j.put("feaProjectB", feaProjectB);
		j.setMsg("");
		j.setSuccess(true);
		
		return j;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<FeaProjectB> list = feaProjectBService.findList(new FeaProjectB());
		for (int i=0; i<list.size(); i++){
			FeaProjectB e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("text", e.getProjectName());
			map.put("parent", "#");
			Map<String, Object> state = Maps.newHashMap();
			state.put("opened", true);
			map.put("state", state);
			mapList.add(map);
		}
		return mapList;
	}

}