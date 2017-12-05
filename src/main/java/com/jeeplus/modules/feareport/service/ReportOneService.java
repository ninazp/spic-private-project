/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.service.totaltab.Fea_finansumVOService;
import com.jeeplus.modules.feareport.entity.ReportOne;
import com.jeeplus.modules.feareport.mapper.ReportOneMapper;

/**
 * 报表Service
 * @author zp
 * @version 2017-11-28
 */
@Service
@Transactional(readOnly = true)
public class ReportOneService extends CrudService<ReportOneMapper, ReportOne> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;
	
	

	public ReportOne get(String id) {
		return super.get(id);
	}
	
	public List<ReportOne> findList(ReportOne reportOne) {
		return super.findList(reportOne);
	}
	
	public Page<ReportOne> findPage(Page<ReportOne> page, ReportOne reportOne) {
		return super.findPage(page, reportOne);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportOne reportOne) {
		super.save(reportOne);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportOne reportOne) {
		super.delete(reportOne);
	}
	
	public List<List<Double>> getReportDatas(String ids){
		Fea_finansumVOService service = new Fea_finansumVOService();
		Map<String,List<List<Double>>> map = service.getReportDatas(ids);
		List<List<Double>> retLi = new ArrayList<List<Double>>();
		if(null != map && map.size()>0){
			retLi = map.get("总成本费用表");
		}
		return retLi;
	}
	
	public List<FeaProjectB> getProjectDatas(){
		return projectmapper.findAllList(new FeaProjectB());
	}
	
}