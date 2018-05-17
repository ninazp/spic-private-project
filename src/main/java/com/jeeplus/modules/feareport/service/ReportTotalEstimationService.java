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
import com.jeeplus.modules.fea.designcal.BusiIndexCal;
import com.jeeplus.modules.fea.designcal.PubDesignCal;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.feareport.entity.ReportTotalEstimation;
import com.jeeplus.modules.feareport.mapper.ReportTotalEstimationMapper;

/**
 * 总估算表Service
 * @author zp
 * @version 2018-02-08
 */
@Service
@Transactional(readOnly = true)
public class ReportTotalEstimationService extends CrudService<ReportTotalEstimationMapper, ReportTotalEstimation> {

	
	@Autowired
	private FeaProjectBMapper projectmapper;
	
	
	public ReportTotalEstimation get(String id) {
		return super.get(id);
	}
	
	public List<ReportTotalEstimation> findList(ReportTotalEstimation reportTotalEstimation) {
		return super.findList(reportTotalEstimation);
	}
	
	public Page<ReportTotalEstimation> findPage(Page<ReportTotalEstimation> page, ReportTotalEstimation reportTotalEstimation) {
		return super.findPage(page, reportTotalEstimation);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportTotalEstimation reportTotalEstimation) {
		super.save(reportTotalEstimation);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportTotalEstimation reportTotalEstimation) {
		super.delete(reportTotalEstimation);
	}
	
	@Transactional(readOnly = false)
    public List<List<String>> getReportdata(String id) throws Exception{
		
		List<List<String>> reportmap = new ArrayList<List<String>>();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		//com.jeeplus.modules.fea.designcal.BusiIndexCal
		BusiIndexCal busiIndexCal = (BusiIndexCal) wac.getBean("busiIndexCal");
		if(null!=busiIndexCal){
			reportmap = busiIndexCal.getInitIvdesMny(id);
		}
		if(null!=reportmap) {
			projectmapper.execUpdateSql("update fea_project_b  set  ordercol = (   case   id  when  '"+id+"' then 1  else 0 end) ");
		}
		
		return null != reportmap ? reportmap : null;
	}
	
	public List<List<String>> getReportDatas(String ids) throws Exception{
		
		Map<String,Object> reportmap = new HashMap<String, Object>();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object reportbean = wac.getBean("pubDesignCal");
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("projectid", ids);
		
		if(null!=reportbean){
			reportmap = ((PubDesignCal)reportbean).calprocess(ids);
		}
		return null != reportmap ? (List<List<String>>)reportmap.get("设备清单") : null;
	}
	
	public List<FeaProjectB> getProjectDatas(){
		return projectmapper.findAllList(new FeaProjectB());
	}
	
}