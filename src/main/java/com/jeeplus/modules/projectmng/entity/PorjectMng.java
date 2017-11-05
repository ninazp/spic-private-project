/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.projectmng.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 项目管理Entity
 * @author zhaopeng
 * @version 2017-08-09
 */
public class PorjectMng extends TreeEntity<PorjectMng> {
	
	private static final long serialVersionUID = 1L;
	
	private List<ProjectMngB> projectMngBList = Lists.newArrayList();		// 子表列表
	
	public PorjectMng() {
		super();
	}

	public PorjectMng(String id){
		super(id);
	}

	public  PorjectMng getParent() {
			return parent;
	}
	
	@Override
	public void setParent(PorjectMng parent) {
		this.parent = parent;
		
	}
	
	public List<ProjectMngB> getProjectMngBList() {
		return projectMngBList;
	}

	public void setProjectMngBList(List<ProjectMngB> projectMngBList) {
		this.projectMngBList = projectMngBList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}