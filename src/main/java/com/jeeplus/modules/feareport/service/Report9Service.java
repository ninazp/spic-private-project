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
import com.jeeplus.modules.feareport.entity.Report9;
import com.jeeplus.modules.feareport.mapper.Report9Mapper;

/**
 * 资产负债表Service
 * @author zp
 * @version 2017-12-05
 */
@Service
@Transactional(readOnly = true)
public class Report9Service extends CrudService<Report9Mapper, Report9> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;

	public Report9 get(String id) {
		return super.get(id);
	}
	
	public List<Report9> findList(Report9 report9) {
		return super.findList(report9);
	}
	
	public Page<Report9> findPage(Page<Report9> page, Report9 report9) {
		return super.findPage(page, report9);
	}
	
	@Transactional(readOnly = false)
	public void save(Report9 report9) {
		super.save(report9);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report9 report9) {
		super.delete(report9);
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
		
		return null != reportmap ? reportmap.get("资产负债表") : null;
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