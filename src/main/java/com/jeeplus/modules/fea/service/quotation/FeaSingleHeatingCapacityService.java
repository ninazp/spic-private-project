/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.quotation;

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
import com.jeeplus.modules.fea.entity.quotation.FeaSingleHeatingCapacity;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.quotation.FeaSingleHeatingCapacityMapper;
import com.jeeplus.modules.fea.pub.report.createReportPubDMO;

/**
 * 单井供热能力Service
 * @author zp
 * @version 2018-02-08
 */
@Service
@Transactional(readOnly = true)
public class FeaSingleHeatingCapacityService extends CrudService<FeaSingleHeatingCapacityMapper, FeaSingleHeatingCapacity> {

	@Autowired
	private FeaProjectBMapper projectmapper;
	
	public FeaSingleHeatingCapacity get(String id) {
		return super.get(id);
	}
	
	public List<FeaSingleHeatingCapacity> findList(FeaSingleHeatingCapacity feaSingleHeatingCapacity) {
		return super.findList(feaSingleHeatingCapacity);
	}
	
	public Page<FeaSingleHeatingCapacity> findPage(Page<FeaSingleHeatingCapacity> page, FeaSingleHeatingCapacity feaSingleHeatingCapacity) {
		return super.findPage(page, feaSingleHeatingCapacity);
	}
	
	@Transactional(readOnly = false)
	public void save(FeaSingleHeatingCapacity feaSingleHeatingCapacity) {
		super.save(feaSingleHeatingCapacity);
	}
	
	@Transactional(readOnly = false)
	public void delete(FeaSingleHeatingCapacity feaSingleHeatingCapacity) {
		super.delete(feaSingleHeatingCapacity);
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
		
		return null != reportmap ? reportmap.get("总成本费用表") : null;
	}
	
	public List<FeaProjectB> getProjectDatas(){
		return projectmapper.findAllList(new FeaProjectB());
	}
	
}