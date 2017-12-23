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
import com.jeeplus.modules.feareport.entity.Report3;
import com.jeeplus.modules.feareport.mapper.Report3Mapper;

/**
 * 项目资本金现金流量表Service
 * @author zp
 * @version 2017-12-03
 */
@Service
@Transactional(readOnly = true)
public class Report3Service extends CrudService<Report3Mapper, Report3> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;

	public Report3 get(String id) {
		return super.get(id);
	}
	
	public List<Report3> findList(Report3 report3) {
		return super.findList(report3);
	}
	
	public Page<Report3> findPage(Page<Report3> page, Report3 report3) {
		return super.findPage(page, report3);
	}
	
	@Transactional(readOnly = false)
	public void save(Report3 report3) {
		super.save(report3);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report3 report3) {
		super.delete(report3);
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
		
		return null != reportmap ? reportmap.get("项目资本金现金流量表") : null;
	}
	
	public List<List<Double>> getReportDatas2(List<List<Double>> capitalTable){
		List<Double> reportList = new ArrayList<Double>();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object reportbean = wac.getBean("createReportPubDMO");
		
		if(null!=reportbean){
			reportList = ((createReportPubDMO)reportbean).getcapital_irrnpv(capitalTable);
		}
		
		List<List<Double>> reList = new ArrayList<List<Double>>();
		
		for(Double temp : reportList){
			List<Double> tempLi = new ArrayList<Double>();
			tempLi.add(temp*100);
			reList.add(tempLi);
		}
		
		//reList.add(reportList);
		return reList;
	}
	
	public List<FeaProjectB> getProjectDatas(){
		return projectmapper.findAllList(new FeaProjectB());
	}
	
}