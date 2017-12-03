/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 项目登记Entity
 * @author zp
 * @version 2017-12-02
 */
public class FeaProject extends TreeEntity<FeaProject> {
	
	private static final long serialVersionUID = 1L;
	
	private List<FeaProjectB> feaProjectBList = Lists.newArrayList();		// 子表列表
	
	public FeaProject() {
		super();
	}

	public FeaProject(String id){
		super(id);
	}

	public  FeaProject getParent() {
			return parent;
	}
	
	@Override
	public void setParent(FeaProject parent) {
		this.parent = parent;
		
	}
	
	public List<FeaProjectB> getFeaProjectBList() {
		return feaProjectBList;
	}

	public void setFeaProjectBList(List<FeaProjectB> feaProjectBList) {
		this.feaProjectBList = feaProjectBList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}