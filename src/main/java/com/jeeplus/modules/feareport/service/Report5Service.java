/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.service;

import java.util.ArrayList;
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
import com.jeeplus.modules.feareport.entity.Report5;
import com.jeeplus.modules.feareport.mapper.Report5Mapper;

/**
 * 投资计划与资金筹措表Service
 * @author zp
 * @version 2017-12-05
 */
@Service
@Transactional(readOnly = true)
public class Report5Service extends CrudService<Report5Mapper, Report5> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;

	public Report5 get(String id) {
		return super.get(id);
	}
	
	public List<Report5> findList(Report5 report5) {
		return super.findList(report5);
	}
	
	public Page<Report5> findPage(Page<Report5> page, Report5 report5) {
		return super.findPage(page, report5);
	}
	
	@Transactional(readOnly = false)
	public void save(Report5 report5) {
		super.save(report5);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report5 report5) {
		super.delete(report5);
	}
	
	@Transactional(readOnly = false)
	public List<List<Double>> getReportDatas(String ids){
		Map<String,List<List<Double>>> reportmap = new HashMap<String,List<List<Double>>>();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object reportbean = wac.getBean("createReportPubDMO");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("projectid", ids);
		
		if(null!=reportbean){
			 reportmap = ((createReportPubDMO)reportbean).getallreportnostatic(param);
		}
		// "投资计划与资金筹措表"
		return null != reportmap ? reportmap.get("投资计划与资金筹措表") : null;
	}
	
	@Transactional(readOnly = false)
	public List<List<Double>> getReportDatas2(String ids){
		Map<String,List<List<Double>>> reportmap = new HashMap<String,List<List<Double>>>();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object reportbean = wac.getBean("createReportPubDMO");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("projectid", ids);
		
		if(null!=reportbean){
			 reportmap = ((createReportPubDMO)reportbean).getallreportnostatic(param);
		}
		// "投资计划与资金筹措表"
		return null != reportmap ? reportmap.get("财务指标汇总表") : null;
	}
	
	public List<FeaProjectB> getProjectDatas(){
		List<FeaProjectB> list = new ArrayList<FeaProjectB>();
		FeaProjectB projectvo = projectmapper.findUniqueByProperty("ordercol", 1);
		if(null!=projectvo) {
			list.add(projectvo);
		}else {
			return projectmapper.findAllList(new FeaProjectB());
		}
		return list;
	}
	
}