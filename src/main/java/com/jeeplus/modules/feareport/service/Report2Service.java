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
import com.jeeplus.modules.feareport.entity.Report2;
import com.jeeplus.modules.feareport.mapper.Report2Mapper;

/**
 * 项目投资现金流量表Service
 * @author zp
 * @version 2017-12-03
 */
@Service
@Transactional(readOnly = true)
public class Report2Service extends CrudService<Report2Mapper, Report2> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;

	public Report2 get(String id) {
		return super.get(id);
	}
	
	public List<Report2> findList(Report2 report2) {
		return super.findList(report2);
	}
	
	public Page<Report2> findPage(Page<Report2> page, Report2 report2) {
		return super.findPage(page, report2);
	}
	
	@Transactional(readOnly = false)
	public void save(Report2 report2) {
		super.save(report2);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report2 report2) {
		super.delete(report2);
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
		
		return null != reportmap ? reportmap.get("项目投资现金流量表") : null;
	}
	
	public List<List<Double>> getReportDatas2(List<List<Double>> investHandlerTable){
		List<Double> reportList = new ArrayList<Double>();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object reportbean = wac.getBean("createReportPubDMO");
		
		if(null!=reportbean){
			reportList = ((createReportPubDMO)reportbean).getinvest_irrnpv(investHandlerTable);
		}
		
		List<List<Double>> reList = new ArrayList<List<Double>>();
		
		for(int i=0; i< reportList.size(); i++){
			Double temp = reportList.get(i);
			List<Double> tempLi = new ArrayList<Double>();
			if(i<2){
				temp = temp*100;
			}
			tempLi.add(temp);
			reList.add(tempLi);
		}
		
		//reList.add(reportList);
		return reList;
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