/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.projectmng.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.projectmng.entity.PorjectMng;
import com.jeeplus.modules.projectmng.mapper.PorjectMngMapper;

/**
 * 项目管理Service
 * @author zhaopeng
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true)
public class PorjectMngService extends TreeService<PorjectMngMapper, PorjectMng> {

	public PorjectMng get(String id) {
		return super.get(id);
	}
	
	public List<PorjectMng> findList(PorjectMng porjectMng) {
		if (StringUtils.isNotBlank(porjectMng.getParentIds())){
			porjectMng.setParentIds(","+porjectMng.getParentIds()+",");
		}
		return super.findList(porjectMng);
	}
	
	@Transactional(readOnly = false)
	public void save(PorjectMng porjectMng) {
		super.save(porjectMng);
	}
	
	@Transactional(readOnly = false)
	public void delete(PorjectMng porjectMng) {
		super.delete(porjectMng);
	}
	
}