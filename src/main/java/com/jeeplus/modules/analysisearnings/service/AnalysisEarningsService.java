/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.analysisearnings.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.io.FilePathUtil;
import com.jeeplus.modules.analysisearnings.entity.AnalysisEarnings;
import com.jeeplus.modules.analysisearnings.mapper.AnalysisEarningsMapper;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.pub.report.createReportPubDMO;
import com.jeeplus.modules.fea.pub.util.ReadExcelCal;
import com.jeeplus.modules.analysisearnings.entity.AnalysisEarningsB;
import com.jeeplus.modules.analysisearnings.mapper.AnalysisEarningsBMapper;

/**
 * 敏感分析（单因素）Service
 * @author zp
 * @version 2017-11-28
 */
@Service
@Transactional(readOnly = true)
public class AnalysisEarningsService extends CrudService<AnalysisEarningsMapper, AnalysisEarnings> {

	@Autowired
	private AnalysisEarningsBMapper analysisEarningsBMapper;

	@Autowired
	private FeaProjectBMapper projectmapper;

	public AnalysisEarnings get(String id) {
		AnalysisEarnings analysisEarnings = super.get(id);
		analysisEarnings.setAnalysisEarningsBList(analysisEarningsBMapper.findList(new AnalysisEarningsB(analysisEarnings)));
		return analysisEarnings;
	}

	public List<AnalysisEarnings> findList(AnalysisEarnings analysisEarnings) {
		return super.findList(analysisEarnings);
	}

	public Page<AnalysisEarnings> findPage(Page<AnalysisEarnings> page, AnalysisEarnings analysisEarnings) {
		return super.findPage(page, analysisEarnings);
	}

	@Transactional(readOnly = false)
	public void save(AnalysisEarnings analysisEarnings) {
		super.save(analysisEarnings);
		for (AnalysisEarningsB analysisEarningsB : analysisEarnings.getAnalysisEarningsBList()){
			if (analysisEarningsB.getId() == null){
				continue;
			}
			if (AnalysisEarningsB.DEL_FLAG_NORMAL.equals(analysisEarningsB.getDelFlag())){
				if (StringUtils.isBlank(analysisEarningsB.getId())){
					analysisEarningsB.setPid(analysisEarnings);
					analysisEarningsB.preInsert();
					analysisEarningsBMapper.insert(analysisEarningsB);
				}else{
					analysisEarningsB.preUpdate();
					analysisEarningsBMapper.update(analysisEarningsB);
				}
			}else{
				analysisEarningsBMapper.delete(analysisEarningsB);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(AnalysisEarnings analysisEarnings) {
		super.delete(analysisEarnings);
		analysisEarningsBMapper.delete(new AnalysisEarningsB(analysisEarnings));
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

	public Map<String,List<Double>> calmgfx(String ids,Map<String,Double[]> parammap) throws Exception {

		Map<String,List<Double>> changevalmap = new HashMap<String,List<Double>>();

		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		Object reportbean = wac.getBean("createReportPubDMO");
		Map<String,List<List<Double>>> exportexcel = new HashMap<String,List<List<Double>>>();
		//changename : 设置规则
		// * price :取暖费
		//* powercost ：电费
		// * person ： 人工费
		// * investamt ： 初投资
		if(null!=reportbean){
			for(String ky : parammap.keySet()) {
				List<List<Double>> changevals1 = ((createReportPubDMO)reportbean).getchange_irrnpv(ids, ky, parammap.get(ky));
				exportexcel.put(ky, changevals1);
				changevalmap.put(ky,changevals1.get(0));
			}
			((createReportPubDMO)reportbean).exportMGFXexcel(FilePathUtil.getJarPath(ReadExcelCal.class),ids, exportexcel);
		}

		return changevalmap;
	}
	
	public FeaProjectB getprojectb(String id) {
		FeaProjectB projectb = projectmapper.get(id);
		
		return projectb;
	}
	
}