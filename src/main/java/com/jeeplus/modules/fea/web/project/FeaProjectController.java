/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.project;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.config.Global;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.fea.entity.project.FeaProject;
import com.jeeplus.modules.fea.service.project.FeaProjectService;

/**
 * 项目登记Controller
 * @author zp
 * @version 2017-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/project/feaProject")
public class FeaProjectController extends BaseController {

	@Autowired
	private FeaProjectService feaProjectService;
	
	@ModelAttribute
	public FeaProject get(@RequestParam(required=false) String id) {
		FeaProject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feaProjectService.get(id);
		}
		if (entity == null){
			entity = new FeaProject();
		}
		return entity;
	}
	
	/**
	 * 项目登记列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(FeaProject feaProject,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/fea/project/feaProjectList";
	}

	/**
	 * 查看，增加，编辑项目登记表单页面
	 */
	@RequestMapping(value = "form")
	public String form(FeaProject feaProject, Model model) {
		if (feaProject.getParent()!=null && StringUtils.isNotBlank(feaProject.getParent().getId())){
			feaProject.setParent(feaProjectService.get(feaProject.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(feaProject.getId())){
				FeaProject feaProjectChild = new FeaProject();
				feaProjectChild.setParent(new FeaProject(feaProject.getParent().getId()));
				List<FeaProject> list = feaProjectService.findList(feaProject); 
				if (list.size() > 0){
					feaProject.setSort(list.get(list.size()-1).getSort());
					if (feaProject.getSort() != null){
						feaProject.setSort(feaProject.getSort() + 30);
					}
				}
			}
		}
		if (feaProject.getSort() == null){
			feaProject.setSort(30);
		}
		model.addAttribute("feaProject", feaProject);
		return "modules/fea/project/feaProjectForm";
	}

	/**
	 * 保存项目登记
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(FeaProject feaProject, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, feaProject)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		//新增或编辑表单保存
		feaProjectService.save(feaProject);//保存
		j.setSuccess(true);
		j.put("feaProject", feaProject);
		j.setMsg("保存项目登记成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<FeaProject> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return feaProjectService.getChildren(parentId);
	}
	
	/**
	 * 删除项目登记
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(FeaProject feaProject, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		feaProjectService.delete(feaProject);
		j.setSuccess(true);
		j.setMsg("删除项目登记成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<FeaProject> list = feaProjectService.findList(new FeaProject());
		for (int i=0; i<list.size(); i++){
			FeaProject e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}