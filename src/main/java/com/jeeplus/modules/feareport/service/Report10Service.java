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
import com.jeeplus.modules.feareport.entity.Report10;
import com.jeeplus.modules.feareport.mapper.Report10Mapper;

/**
 * EVA测算表Service
 * @author zp
 * @version 2017-12-05
 */
@Service
@Transactional(readOnly = true)
public class Report10Service extends CrudService<Report10Mapper, Report10> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;

	public Report10 get(String id) {
		return super.get(id);
	}
	
	public List<Report10> findList(Report10 report10) {
		return super.findList(report10);
	}
	
	public Page<Report10> findPage(Page<Report10> page, Report10 report10) {
		return super.findPage(page, report10);
	}
	
	@Transactional(readOnly = false)
	public void save(Report10 report10) {
		super.save(report10);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report10 report10) {
		super.delete(report10);
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
		
		return null != reportmap ? reportmap.get("EVA测算表") : null;
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