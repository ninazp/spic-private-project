/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.projectmng.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.projectmng.entity.ProjectMngB;
import com.jeeplus.modules.projectmng.mapper.ProjectMngBMapper;

/**
 * 项目管理子表Service
 * @author zhaopeng
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true)
public class ProjectMngBService extends CrudService<ProjectMngBMapper, ProjectMngB> {

	public ProjectMngB get(String id) {
		return super.get(id);
	}
	
	public List<ProjectMngB> findList(ProjectMngB projectMngB) {
		return super.findList(projectMngB);
	}
	
	public Page<ProjectMngB> findPage(Page<ProjectMngB> page, ProjectMngB projectMngB) {
		return super.findPage(page, projectMngB);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectMngB projectMngB) {
		super.save(projectMngB);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectMngB projectMngB) {
		super.delete(projectMngB);
	}
	
}