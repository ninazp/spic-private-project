/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.report.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.report.entity.ReportForm1;
import com.jeeplus.modules.report.mapper.ReportForm1Mapper;

/**
 * 报表Service
 * @author zhaopeng
 * @version 2017-10-28
 */
@Service
@Transactional(readOnly = true)
public class ReportForm1Service extends CrudService<ReportForm1Mapper, ReportForm1> {

	public ReportForm1 get(String id) {
		return super.get(id);
	}
	
	public List<ReportForm1> findList(ReportForm1 reportForm1) {
		return super.findList(reportForm1);
	}
	
	public Page<ReportForm1> findPage(Page<ReportForm1> page, ReportForm1 reportForm1) {
		return super.findPage(page, reportForm1);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportForm1 reportForm1) {
		super.save(reportForm1);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportForm1 reportForm1) {
		super.delete(reportForm1);
	}
	
}