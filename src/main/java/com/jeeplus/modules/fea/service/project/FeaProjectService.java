/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.project;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.fea.entity.project.FeaProject;
import com.jeeplus.modules.fea.mapper.project.FeaProjectMapper;

/**
 * 项目登记Service
 * @author zp
 * @version 2017-12-24
 */
@Service
@Transactional(readOnly = true)
public class FeaProjectService extends TreeService<FeaProjectMapper, FeaProject> {

	public FeaProject get(String id) {
		return super.get(id);
	}
	
	public List<FeaProject> findList(FeaProject feaProject) {
		if (StringUtils.isNotBlank(feaProject.getParentIds())){
			feaProject.setParentIds(","+feaProject.getParentIds()+",");
		}
		return super.findList(feaProject);
	}
	
	@Transactional(readOnly = false)
	public void save(FeaProject feaProject) {
		super.save(feaProject);
	}
	
	@Transactional(readOnly = false)
	public void delete(FeaProject feaProject) {
		super.delete(feaProject);
	}
	
}