/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.report_totaltab;

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
import com.jeeplus.modules.fea.entity.report_totaltab.Fea_totaltabVO;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.report_totaltab.Fea_totaltabVOMapper;
import com.jeeplus.modules.fea.pub.report.createReportPubDMO;

/**
 * 财务指标汇总表Service
 * @author jw
 * @version 2018-12-01
 */
@Service
@Transactional(readOnly = true)
public class Fea_totaltabVOService extends CrudService<Fea_totaltabVOMapper, Fea_totaltabVO> {

	@Autowired
	private FeaProjectBMapper projectmapper;
	
	public Fea_totaltabVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_totaltabVO> findList(Fea_totaltabVO fea_totaltabVO) {
		return super.findList(fea_totaltabVO);
	}
	
	public Page<Fea_totaltabVO> findPage(Page<Fea_totaltabVO> page, Fea_totaltabVO fea_totaltabVO) {
		return super.findPage(page, fea_totaltabVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_totaltabVO fea_totaltabVO) {
		super.save(fea_totaltabVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_totaltabVO fea_totaltabVO) {
		super.delete(fea_totaltabVO);
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