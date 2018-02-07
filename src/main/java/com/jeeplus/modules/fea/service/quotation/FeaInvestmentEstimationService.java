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
import com.jeeplus.modules.fea.designcal.PubDesignCal;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.quotation.FeaInvestmentEstimation;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.quotation.FeaInvestmentEstimationMapper;

/**
 * 换热站设备购置费及安装工程投资估算Service
 * @author zp
 * @version 2018-02-07
 */
@Service
@Transactional(readOnly = true)
public class FeaInvestmentEstimationService extends CrudService<FeaInvestmentEstimationMapper, FeaInvestmentEstimation> {
	
	@Autowired
	private FeaProjectBMapper projectmapper;

	public FeaInvestmentEstimation get(String id) {
		return super.get(id);
	}
	
	public List<FeaInvestmentEstimation> findList(FeaInvestmentEstimation feaInvestmentEstimation) {
		return super.findList(feaInvestmentEstimation);
	}
	
	public Page<FeaInvestmentEstimation> findPage(Page<FeaInvestmentEstimation> page, FeaInvestmentEstimation feaInvestmentEstimation) {
		return super.findPage(page, feaInvestmentEstimation);
	}
	
	@Transactional(readOnly = false)
	public void save(FeaInvestmentEstimation feaInvestmentEstimation) {
		super.save(feaInvestmentEstimation);
	}
	
	@Transactional(readOnly = false)
	public void delete(FeaInvestmentEstimation feaInvestmentEstimation) {
		super.delete(feaInvestmentEstimation);
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