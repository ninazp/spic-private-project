/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.service;

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
	@Autowired
	private Fea_incosubsidyVOMapper fea_incosubsidyVOMapper;

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
	
	public List<List<Double>> getReportDatas(){
		FeaProjectB  projectvo =  projectmapper.get("dc940aa030b04f9ab32e543574cc847d");
		List<Double> projectinfo = ProjectInfoHander.getprojectinfo(projectvo);

		//1 -- 投资计划与资金筹措表
		List<List<Double>> zjcktable = ProjectInfoHander.getzjcctable(fea_investdisVOMapper, fea_investdisBVOMapper, projectvo);
		Double  deductval = ProjectInfoHander.getdekuje(fea_fundssrcVOMapper, projectvo);
		Double assetval = zjcktable.get(1).get(0)+zjcktable.get(2).get(0)-deductval;//计算折旧费固定资产价格
		Double assetwbval = zjcktable.get(1).get(0)-deductval;//计算维修费-去掉
		//贷款利息未计算
		List<List<Double>> totaltable = 
				TotalCostHander.getTotalcosttable(fea_capformVOMapper, fea_productcostVOmapper, 
						fea_productcostBVOmapper, projectinfo, assetval, assetwbval);

		//4 -- 借款还本付息计划表
		List<List<Double>> interestTable = LoanRepayHandler.getLoanRepayTable(zjcktable, projectinfo, 4.9, 15.0, 4.35);

		List<Double> lxzc = TotalCostHander.getrepaylx(interestTable);
		totaltable.set(6, lxzc);

		//2  -- 总成本费用表
		List<List<Double>> totalcostltable = TotalCostHander.getallTotalCost(totaltable, projectinfo.get(1));

		//补贴收入
		List<Double> subincome = ProfitHandler.getsubincome(fea_incosubsidyVOMapper, projectvo, projectinfo);
		
		//3 -- 利润和利润分配表
        List<List<Double>> lrtable =  ProfitHandler.getprofittable(projectinfo, totalcostltable, interestTable, 
    		   fea_costinfoVOMapper, projectvo, 14.0, 25.0, 
    		   4.35, 10.0, zjcktable.get(5).get(0)*10/100, subincome);
                
        //5---财务计划现金流量表
        List<List<Double>> financeplantable = FinanceHandler.getFinanceTable(projectinfo.get(1),
        		lrtable, totalcostltable, zjcktable, interestTable);
        
        //6---项目投资现金流量表 
        List<List<Double>> investHandlerTable = InvestFlowHandler.getInvestHandlerTable(projectinfo.get(1), lrtable,
        		totalcostltable, zjcktable, interestTable, 25.0, assetval);
        
        //7--项目资本金现金流量表
        List<List<Double>> capitalTable = CapitalHandler.getCapitalTable(
        		projectinfo.get(1), 
        		lrtable, totalcostltable, zjcktable, interestTable,assetval);
        
        //8--资金来源与运用表
        List<List<Double>> capitalsrcTable = CapitalSrcHandler.getCapitalSrcTable(
        		projectinfo.get(1),lrtable, totalcostltable, zjcktable, interestTable,assetval);
        
        
        //9--资产负债表
        List<List<Double>> balancetable = BalanceHandler.getBalanceTable(
        		projectinfo.get(1),lrtable, totalcostltable, zjcktable, interestTable,
        		capitalsrcTable,
        		assetval
        		,deductval);
        
        //10 -- EVA测算表
        List<List<Double>> eVAHandlerTable = EVAHandler.getEVAHandlerTable(projectinfo.get(1), lrtable, totalcostltable, balancetable);
        
        Double doub3 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(10).toArray(new Double[0]),
        		0.07, 0.06, "3");
        
        Double doub1 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(10).toArray(new Double[0]),
        		0.07, 0.06, "1");
        
        Double retunperiod =  ReadExcelCal.getreturnperiod(investHandlerTable.get(11).toArray(new Double[0]));
        
        Double doub4 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(13).toArray(new Double[0]),
        		0.07, 0.06, "3");
        
        Double doub2 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(13).toArray(new Double[0]),
        		0.07, 0.06, "2");
        
        Double retunperiod2 =  ReadExcelCal.getreturnperiod(investHandlerTable.get(14).toArray(new Double[0]));
        

        
        System.out.println(doub1+"\n"+doub2+"\n"+doub3+"\n"+doub4+retunperiod+retunperiod2);
		return financeplantable;
	}
	
}