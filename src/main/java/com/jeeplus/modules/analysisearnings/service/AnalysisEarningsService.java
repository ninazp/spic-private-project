/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.analysisearnings.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.analysisearnings.entity.AnalysisEarnings;
import com.jeeplus.modules.analysisearnings.mapper.AnalysisEarningsMapper;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
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
	
}