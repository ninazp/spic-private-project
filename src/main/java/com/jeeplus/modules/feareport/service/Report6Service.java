/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.pub.report.createReportPubDMO;
import com.jeeplus.modules.feareport.entity.Report6;
import com.jeeplus.modules.feareport.mapper.Report6Mapper;

/**
 * 借款还本付息计划表Service
 * @author zp
 * @version 2017-12-05
 */
@Service
@Transactional(readOnly = true)
public class Report6Service extends CrudService<Report6Mapper, Report6> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;

	public Report6 get(String id) {
		return super.get(id);
	}
	
	public List<Report6> findList(Report6 report6) {
		return super.findList(report6);
	}
	
	public Page<Report6> findPage(Page<Report6> page, Report6 report6) {
		return super.findPage(page, report6);
	}
	
	@Transactional(readOnly = false)
	public void save(Report6 report6) {
		super.save(report6);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report6 report6) {
		super.delete(report6);
	}
	
	public List<List<Double>> getReportDatas(String ids){
		Map<String,List<List<Double>>> reportmap = new HashMap<String,List<List<Double>>>();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object reportbean = wac.getBean("createReportPubDMO");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("projectid", ids);
		
		if(null!=reportbean){
			 reportmap = ((createReportPubDMO)reportbean).getallreportnostatic(param);
		}
		
		return null != reportmap ? reportmap.get("借款还本付息计划表") : null;
	}
	
	public List<FeaProjectB> getProjectDatas(){
		return projectmapper.findAllList(new FeaProjectB());
	}
	
}