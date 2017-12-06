package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.modules.fea.entity.fecl.Fea_costfecfVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcTVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.income.Fea_incomesetVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;

public class CreateReportPubDMO {
	@Autowired
	private FeaProjectBMapper projectmapper;
	@Autowired
	private Fea_fundssrcVOMapper fea_fundssrcVOMapper;
	@Autowired
	private Fea_fundssrcTVOMapper fea_fundssrcTVOMapper;
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
	@Autowired
	private Fea_incomesetVOMapper fea_incomesetVOMapper;

	@Autowired
	private Fea_costfecfVOMapper fea_costfecfVOMapper;

	public Map<String,List<List<Double>>> getallreportnostatic(Map<String,Object> param){

		Map<String,List<List<Double>>> reporttable = getallreporttable( param);

		return reporttable;
	}

	public Map<String,List<List<Double>>> getallreporttable(Map<String,Object> parammap){

		Map<String,List<List<Double>>> retmap = new HashMap<String, List<List<Double>>>();
		if(null==parammap || (!parammap.containsKey("projectid"))){
			return null;
		}
		FeaProjectB  projectvo =  (FeaProjectB) projectmapper.get(parammap.get("projectid").toString());
		List<Double> projectinfo = ProjectInfoHander.getprojectinfo(projectvo);
		/**
		 *   -- 基本参数节点
		 * retlst.add(volst.get(0).getIncomerate());//所得税税率
			retlst.add(volst.get(0).getLegalaccfund());//法定盈余公积金比率
			retlst.add(volst.get(0).getYflrprop());//应付利润比率
			retlst.add(volst.get(0).getCapinvestrate());//资本金基准收益率
			retlst.add(volst.get(0).getIndustrysqrate());//税前收益率
			retlst.add(volst.get(0).getIndustryshrate());//税后收益率
			retlst.add(Double.valueOf(volst.get(0).getIssdssjsm()));//所得税三减三免

		 */
		String wheresql = "project_id='"+projectvo.getId()+"'";
		List<Double> incomeset = PubBaseDAO.getFea_incomeset("fea_incomeset", "id", wheresql, fea_incomesetVOMapper);

		/**
		 *  查找 资金来源表 - 抵扣金额 ，长期贷款利率，年限 等信息
		 * * private Double interestcount;		// 计息次数（年）
	                   private Double principalrate;		// 本金利率（%）
	                   private Double langrate;		// 利息利率（%）
	                   private String repaytype;		// 还款方式
		 */
		List<Double>  fundssrcparam = ProjectInfoHander.getfundssrc(fea_fundssrcVOMapper,fea_fundssrcTVOMapper, projectvo);
		Double deductval = fundssrcparam.get(0);
		Double shortloanrate = 0.00;
		List<Fea_costfecfVO> Fea_costfecfVOlst = (List<Fea_costfecfVO>) PubBaseDAO.getMutiParentVO("fea_costfecf", "id", wheresql,fea_costfecfVOMapper);
		if(null!=Fea_costfecfVOlst && Fea_costfecfVOlst.size()>0){
			shortloanrate =(null==Fea_costfecfVOlst.get(0).getCircularate())?0.00:Fea_costfecfVOlst.get(0).getCircularate();
		}
		//1 -- 投资计划与资金筹措表
		List<List<Double>> zjcktable = ProjectInfoHander.getzjcctable(fea_investdisVOMapper, fea_investdisBVOMapper, projectvo);
		Double assetval = zjcktable.get(1).get(0)+zjcktable.get(2).get(0)-deductval;//计算折旧费固定资产价格

		List<List<Double>> interestTable = LoanRepayHandler.getLoanRepayTable(zjcktable, projectinfo, fundssrcparam, shortloanrate,null);

		List<List<Double>> totalcostltable = TotalCostHander.getTotalcosttable(fea_capformVOMapper, fea_productcostVOmapper,
				fea_productcostBVOmapper, projectvo.getId(),projectinfo,deductval,interestTable,zjcktable);

		List<List<Double>> lrtable =  ProfitHandler.getprofittable(projectinfo, totalcostltable, interestTable, 
				fea_costinfoVOMapper, fea_incosubsidyVOMapper,projectvo, 14.0, incomeset.get(0), incomeset.get(6),
				shortloanrate, incomeset.get(2), zjcktable.get(5).get(0)*incomeset.get(2)/100);

		List<List<Double>> financeplantable = FinanceHandler.getFinanceTable(projectinfo.get(1),
				lrtable, totalcostltable, zjcktable, interestTable);

		List<Double> shortlst = FinanceShortHandler.getFinanceTable(financeplantable, shortloanrate);

		//4 -- 借款还本付息计划表
		List<List<Double>> interestFinaltable =
				LoanRepayHandler.getLoanRepayTable(zjcktable, projectinfo, fundssrcparam, shortloanrate,shortlst);

		//2  -- 总成本费用表
		List<List<Double>> totalcostfinaltable = TotalCostHander.getTotalcosttable(fea_capformVOMapper, fea_productcostVOmapper,
				fea_productcostBVOmapper,projectvo.getId(), projectinfo,deductval,interestFinaltable,zjcktable);
		//3	
		List<List<Double>> lrfinaltable =  ProfitHandler.getprofittable(projectinfo, totalcostfinaltable, interestFinaltable, 
				fea_costinfoVOMapper, fea_incosubsidyVOMapper,projectvo, 14.0, incomeset.get(0), incomeset.get(6),
				shortloanrate, incomeset.get(2), zjcktable.get(5).get(0)*incomeset.get(2)/100);
		
		//5---财务计划现金流量表
		List<List<Double>> financeplanfinaltable = FinanceHandler.getFinanceTable(projectinfo.get(1),
				lrfinaltable, totalcostfinaltable, zjcktable, interestFinaltable);

		//6---项目投资现金流量表 
		List<List<Double>> investHandlerTable = InvestFlowHandler.getInvestHandlerTable(projectinfo.get(1), lrtable,
				totalcostltable, zjcktable, interestTable, incomeset.get(0),incomeset.get(6) , assetval);

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


//		//       
//		       WriteExcelCal.getexcel("zjcktable.xls",zjcktable);
//		       WriteExcelCal.getexcel("totalcostltable.xls",totalcostltable);
//		       WriteExcelCal.getexcel("totalcostfinaltable.xls",totalcostfinaltable);
//		       WriteExcelCal.getexcel("lrtable.xls",lrtable);
//		       WriteExcelCal.getexcel("lrtablefinal.xls",lrfinaltable);
//		       WriteExcelCal.getexcel("interestTable.xls",interestTable);
//		       WriteExcelCal.getexcel("interestfinalTable.xls",interestFinaltable);
//		       WriteExcelCal.getexcel("financeplantable.xls",financeplantable);
//		       WriteExcelCal.getexcel("financeplanfinaltable.xls",financeplanfinaltable);
//		       WriteExcelCal.getexcel("investHandlerTable.xls",investHandlerTable);
//		       WriteExcelCal.getexcel("capitalTable.xls",capitalTable);
//		       WriteExcelCal.getexcel("capitalsrcTable.xls",capitalsrcTable);
//		       WriteExcelCal.getexcel("balancetable.xls",balancetable);
//		       WriteExcelCal.getexcel("eVAHandlerTable.xls",eVAHandlerTable);

		retmap.put("投资计划与资金筹措表", zjcktable); retmap.put("总成本费用表",totalcostfinaltable ); 
		retmap.put("借款还本付息计划表", interestFinaltable); 
		retmap.put("利润和利润分配表",lrfinaltable ); retmap.put("财务计划现金流量表", financeplanfinaltable);
		retmap.put("项目投资现金流量表",investHandlerTable );
		retmap.put("项目资本金现金流量表",capitalTable ); retmap.put("资金来源与运用表",capitalsrcTable );retmap.put("资产负债表",balancetable ); 
		retmap.put("EVA测算表", eVAHandlerTable); 

		
	      getinvest_irrnpv(investHandlerTable);

		return retmap;
	}

	public List<Double> getcapital_irrnpv(List<List<Double>> capitalTable){
		List<Double> retlst = new ArrayList<Double>();
		Double doub3 = ReadExcelCal.getirrnpvvalue(capitalTable.get(12).toArray(new Double[0]),
				0.07, 0.06, "3");
		retlst.add(doub3);  

		//       StringBuffer retrunstr = new StringBuffer("计算指标：\n");
		//       retrunstr.append("资本金财务内部收益率（%）: "+retlst.get(0));

		return retlst;
	}

	public List<Double> getinvest_irrnpv(List<List<Double>> investHandlerTable){
		List<Double> retlst = new ArrayList<Double>();
		Double doub3 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(10).toArray(new Double[0]),
				0.07, 0.06, "3");
		Double doub1 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(10).toArray(new Double[0]),
				0.07, 0.06, "1");
		Double retunperiod =  ReadExcelCal.getreturnperiod(investHandlerTable.get(11).toArray(new Double[0]));
		Double doub4 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(13).toArray(new Double[0]),
				0.07, 0.06, "4");
		Double doub2 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(13).toArray(new Double[0]),
				0.07, 0.06, "2");
		Double retunperiod2 =  ReadExcelCal.getreturnperiod(investHandlerTable.get(14).toArray(new Double[0]));
		retlst.add(doub3);  retlst.add(doub4);
		retlst.add(doub1);  retlst.add(doub2); 
		retlst.add(retunperiod);  retlst.add(retunperiod2);

		//       StringBuffer retrunstr = new StringBuffer("计算指标：\n");
		//       retrunstr.append("项目投资财务内部收益率（%）（所得税前）: "+retlst.get(0));
		//       retrunstr.append("项目投资财务内部收益率（%）（所得税后）: "+retlst.get(1));
		//       retrunstr.append("项目投资财务净现值（万元）（所得税前）: "+retlst.get(2));
		//       retrunstr.append("项目投资财务净现值（万元）（所得税后）: "+retlst.get(3));
		//       retrunstr.append("项目投资回收期（年）（所得税前）   : "+retlst.get(4));
		//       retrunstr.append("项目投资回收期（年）（所得税后）: "+retlst.get(5));

		return retlst;
	}


}
