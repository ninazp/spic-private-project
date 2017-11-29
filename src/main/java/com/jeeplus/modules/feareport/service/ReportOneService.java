/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.feareport.entity.ReportOne;
import com.jeeplus.modules.feareport.mapper.ReportOneMapper;

/**
 * 报表Service
 * @author zp
 * @version 2017-11-28
 */
@Service
@Transactional(readOnly = true)
public class ReportOneService extends CrudService<ReportOneMapper, ReportOne> {

	public ReportOne get(String id) {
		return super.get(id);
	}
	
	public List<ReportOne> findList(ReportOne reportOne) {
		return super.findList(reportOne);
	}
	
	public Page<ReportOne> findPage(Page<ReportOne> page, ReportOne reportOne) {
		return super.findPage(page, reportOne);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportOne reportOne) {
		super.save(reportOne);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportOne reportOne) {
		super.delete(reportOne);
	}
	
}