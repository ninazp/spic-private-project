/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.pub.util.LoanRepayHandler;
import com.jeeplus.modules.fea.pub.util.ProjectInfoHander;
import com.jeeplus.modules.fea.pub.util.TotalCostHander;
import com.jeeplus.modules.fea.service.project.FeaProjectBService;
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
	@Autowired
	private Fea_fundssrcVOMapper fea_fundssrcVOMapper;
	@Autowired
	private Fea_investdisVOMapper fea_investdisVOMapper;
	@Autowired
	private Fea_investdisBVOMapper fea_investdisBVOMapper;
	@Autowired
	private Fea_productcostVOMapper fea_productcostVOmapper;
	@Autowired
	private Fea_productcostBVOMapper fea_productcostBVOmapper;
	@Autowired
	private Fea_capformVOMapper fea_capformVOMapper;
	@Autowired
	private Fea_costinfoVOMapper fea_costinfoVOMapper;

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
	
	public List<List<Double>> gettest(){
		//利润表与利润分配表
		List<List<Double>> profittable = new ArrayList<List<Double>>();
		//借款还利息表

		//通过id查询得到项目信息
		FeaProjectB  projectvo =  projectmapper.get("dc940aa030b04f9ab32e543574cc847d");
		List<Double> projectinfo = ProjectInfoHander.getprojectinfo(projectvo);

		//资金筹措表 -- 如果计算方式不一样可以改动此处获取资金筹措表的逻辑
		List<List<Double>> zjcktable = ProjectInfoHander.getzjcctable(fea_investdisVOMapper, fea_investdisBVOMapper, projectvo);
		
		Double  deductval = ProjectInfoHander.getdekuje(fea_fundssrcVOMapper, projectvo);
		Double assetval = zjcktable.get(1).get(0)+zjcktable.get(2).get(0)-deductval;//计算折旧费固定资产价格
		Double assetwbval = zjcktable.get(1).get(0)-deductval;//计算维修费-去掉

		//贷款利息未计算
		List<List<Double>> totaltable = 
				TotalCostHander.getTotalcosttable(fea_capformVOMapper, fea_productcostVOmapper, 
						fea_productcostBVOmapper, projectinfo, assetval, assetwbval);
		
		List<List<Double>> interestTable = LoanRepayHandler.getLoanRepayTable(zjcktable, projectinfo, 4.9, 15.0, 4.35);
		//更新总成本费用表 -- 利息支出
		List<Double> lxzc = TotalCostHander.getrepaylx(interestTable);
		totaltable.set(6, lxzc);
		List<List<Double>> totalcostall = TotalCostHander.getallTotalCost(totaltable, projectinfo.get(1));
		return totalcostall;
	}
	
}