/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.projectmng.web;

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
import com.jeeplus.modules.projectmng.entity.PorjectMng;
import com.jeeplus.modules.projectmng.service.PorjectMngService;

/**
 * 项目管理Controller
 * @author zhaopeng
 * @version 2017-08-09
 */
@Controller
@RequestMapping(value = "${adminPath}/projectmng/porjectMng")
public class PorjectMngController extends BaseController {

	@Autowired
	private PorjectMngService porjectMngService;
	
	@ModelAttribute
	public PorjectMng get(@RequestParam(required=false) String id) {
		PorjectMng entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = porjectMngService.get(id);
		}
		if (entity == null){
			entity = new PorjectMng();
		}
		return entity;
	}
	
	/**
	 * 项目列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(PorjectMng porjectMng,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/projectmng/porjectMngList";
	}

	/**
	 * 查看，增加，编辑项目表单页面
	 */
	@RequestMapping(value = "form")
	public String form(PorjectMng porjectMng, Model model) {
		if (porjectMng.getParent()!=null && StringUtils.isNotBlank(porjectMng.getParent().getId())){
			porjectMng.setParent(porjectMngService.get(porjectMng.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(porjectMng.getId())){
				PorjectMng porjectMngChild = new PorjectMng();
				porjectMngChild.setParent(new PorjectMng(porjectMng.getParent().getId()));
				List<PorjectMng> list = porjectMngService.findList(porjectMng); 
				if (list.size() > 0){
					porjectMng.setSort(list.get(list.size()-1).getSort());
					if (porjectMng.getSort() != null){
						porjectMng.setSort(porjectMng.getSort() + 30);
					}
				}
			}
		}
		if (porjectMng.getSort() == null){
			porjectMng.setSort(30);
		}
		model.addAttribute("porjectMng", porjectMng);
		return "modules/projectmng/porjectMngForm";
	}

	/**
	 * 保存项目
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(PorjectMng porjectMng, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, porjectMng)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		//新增或编辑表单保存
		porjectMngService.save(porjectMng);//保存
		j.setSuccess(true);
		j.put("porjectMng", porjectMng);
		j.setMsg("保存项目成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<PorjectMng> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return porjectMngService.getChildren(parentId);
	}
	
	/**
	 * 删除项目
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(PorjectMng porjectMng, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		porjectMngService.delete(porjectMng);
		j.setSuccess(true);
		j.setMsg("删除项目成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<PorjectMng> list = porjectMngService.findList(new PorjectMng());
		for (int i=0; i<list.size(); i++){
			PorjectMng e = list.get(i);
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