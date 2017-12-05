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
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;
import com.jeeplus.modules.fea.pub.util.BalanceHandler;
import com.jeeplus.modules.fea.pub.util.CapitalHandler;
import com.jeeplus.modules.fea.pub.util.CapitalSrcHandler;
import com.jeeplus.modules.fea.pub.util.EVAHandler;
import com.jeeplus.modules.fea.pub.util.FinanceHandler;
import com.jeeplus.modules.fea.pub.util.InvestFlowHandler;
import com.jeeplus.modules.fea.pub.util.LoanRepayHandler;
import com.jeeplus.modules.fea.pub.util.ProfitHandler;
import com.jeeplus.modules.fea.pub.util.ProjectInfoHander;
import com.jeeplus.modules.fea.pub.util.ReadExcelCal;
import com.jeeplus.modules.fea.pub.util.TotalCostHander;
import com.jeeplus.modules.fea.pub.util.WriteExcelCal;
import com.jeeplus.modules.fea.service.totaltab.Fea_finansumVOService;
import com.jeeplus.modules.feareport.entity.Report4;
import com.jeeplus.modules.feareport.mapper.Report4Mapper;

/**
 * 财务计划现金流量表Service
 * @author zp
 * @version 2017-12-03
 */
@Service
@Transactional(readOnly = true)
public class Report4Service extends CrudService<Report4Mapper, Report4> {
	
	public Report4 get(String id) {
		return super.get(id);
	}
	
	public List<Report4> findList(Report4 report4) {
		return super.findList(report4);
	}
	
	public Page<Report4> findPage(Page<Report4> page, Report4 report4) {
		return super.findPage(page, report4);
	}
	
	@Transactional(readOnly = false)
	public void save(Report4 report4) {
		super.save(report4);
	}
	
	@Transactional(readOnly = false)
	public void delete(Report4 report4) {
		super.delete(report4);
	}
	
	public List<List<Double>> getReportDatas(String ids){
		Fea_finansumVOService service = new Fea_finansumVOService();
		Map<String,List<List<Double>>> map = service.getReportDatas(ids);
		List<List<Double>> retLi = new ArrayList<List<Double>>();
		if(null != map && map.size()>0){
			retLi = map.get("财务计划现金流量表");
		}
		return retLi;
	}
	
}