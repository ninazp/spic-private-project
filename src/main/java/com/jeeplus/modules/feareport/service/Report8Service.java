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
import com.jeeplus.modules.fea.pub.util.CreateReportPubDMO;
import com.jeeplus.modules.feareport.entity.Report8;
import com.jeeplus.modules.feareport.mapper.Report8Mapper;

/**
 * 资金来源与运用表Service
 * @author zp
 * @version 2017-12-05
 */
@Service
@Transactional(readOnly = true)
public class Report8Service extends CrudService<Report8Mapper, Report8> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;

	public Report8 get(String id) {
		return super.get(id);
	}
	
	public List<Report8> findList(Report8 report8) {
		return super.findList(report8);
	}
	
	public Page<Report8> findPage(Page<Report8> page, Report8 report8) {
		return super.findPage(page, report8);
	}
	
	@Transactional(readOnly = false)
	public void save(Report8 report8) {
		super.save(report8);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report8 report8) {
		super.delete(report8);
	}
	
	public List<List<Double>> getReportDatas(String ids){
		Map<String,List<List<Double>>> reportmap = new HashMap<String,List<List<Double>>>();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object reportbean = wac.getBean("createReportPubDMO");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("projectid", ids);
		
		if(null!=reportbean){
			 reportmap = ((CreateReportPubDMO)reportbean).getallreportnostatic(param);
		}
		
		return null != reportmap ? reportmap.get("资金来源与运用表") : null;
	}
	
	public List<FeaProjectB> getProjectDatas(){
		return projectmapper.findAllList(new FeaProjectB());
	}
	
}