package com.jeeplus.modules.fea.pub.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.income.Fea_incomesetVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;
import com.jeeplus.modules.fea.pub.util.BalanceHandler;
import com.jeeplus.modules.fea.pub.util.CapitalHandler;
import com.jeeplus.modules.fea.pub.util.CapitalSrcHandler;
import com.jeeplus.modules.fea.pub.util.EVAHandler;
import com.jeeplus.modules.fea.pub.util.InvestFlowHandler;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;
import com.jeeplus.modules.fea.pub.util.PubUtilHandler;
import com.jeeplus.modules.fea.pub.util.ReadExcelCal;
import com.jeeplus.modules.fea.pub.util.WriteExcelCal;
import com.jeeplus.modules.fea.pub.util.ZjcctableHanderNew;

public class createReportPubDMO {
	@Autowired
	private FeaProjectBMapper projectmapper;
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

	public Map<String,List<List<Double>>> getallreportnostatic(Map<String,Object> reportparam){

		Map<String,List<List<Double>>> retmap = new HashMap<String, List<List<Double>>>();

		FeaProjectB  projectvo = null;
		
		try{
			if(null==reportparam || (!reportparam.containsKey("projectid"))){
				return null;
			}
			
			projectvo =  (FeaProjectB) projectmapper.get(reportparam.get("projectid").toString());
			
			Map<String,Object> parammap = GetparamDMO.getparammap(projectvo, 
					fea_incomesetVOMapper,fea_costfecfVOMapper,
					fea_investdisVOMapper,fea_investdisBVOMapper,
					fea_capformVOMapper,fea_productcostVOmapper,
					fea_productcostBVOmapper,fea_costinfoVOMapper,fea_incosubsidyVOMapper);
			
			//1 -- 投资计划与资金筹措表
			List<List<Double>> zjcktable = ZjcctableHanderNew.getzjcctable(
					fea_investdisVOMapper, fea_investdisBVOMapper, projectvo,parammap);
			
			List<Fea_investdisVO> fitvo =  (List<Fea_investdisVO>) PubBaseDAO.getMutiParentVO("fea_investdis", "id",
					"project_id='"+projectvo.getId()+"'", fea_investdisVOMapper);
			
			if(null==fitvo || fitvo.size()==0){
				return retmap;
			}
			
			Double rate = (Double) parammap.get("langrate");
			int startmth = (int) parammap.get("startmth");
//			List<List<Double>> zjcktable = GetZjcctable.getzjcctable(fitvo.get(0).getInvestamt(),
//					0.00,rate, startmth+0.00);
			
			Map<String,List<List<Double>>>  basereportmap = GetBaseReportDMO.getbasereport(zjcktable, parammap);
			
			//2总成本费用表
			List<List<Double>> totalcostfinaltable = basereportmap.get("totalcostfinaltable");
			//3利润表
			List<List<Double>> lrfinaltable =  basereportmap.get("lrfinaltable");
			//4利息表
			List<List<Double>> interestFinaltable =  basereportmap.get("interestFinaltable");
			//5---财务计划现金流量表
			List<List<Double>> financeplanfinaltable =  basereportmap.get("financeplanfinaltable");			
			//6---项目投资现金流量表 
			List<List<Double>> investHandlerTable = InvestFlowHandler.getInvestHandlerTable(lrfinaltable,
					totalcostfinaltable, zjcktable, interestFinaltable,parammap);

			//7--项目资本金现金流量表
			List<List<Double>> capitalTable = CapitalHandler.getCapitalTable(
					lrfinaltable, totalcostfinaltable, zjcktable, interestFinaltable,parammap);

			//8--资金来源与运用表
			List<List<Double>> capitalsrcTable = CapitalSrcHandler.getCapitalSrcTable(
					lrfinaltable, totalcostfinaltable, zjcktable, interestFinaltable,parammap);


			//9--资产负债表
			List<List<Double>> balancetable = BalanceHandler.getBalanceTable(
					lrfinaltable, totalcostfinaltable, zjcktable, interestFinaltable,
					financeplanfinaltable,parammap);

			//10 -- EVA测算表
			List<List<Double>> eVAHandlerTable = EVAHandler.getEVAHandlerTable(lrfinaltable, totalcostfinaltable, balancetable);

			List<List<Double>> table1 = PubUtilHandler.getRoundingTable(zjcktable);
			List<List<Double>> table2 = PubUtilHandler.getRoundingTable(totalcostfinaltable);
			List<List<Double>> table3 = PubUtilHandler.getRoundingTable(interestFinaltable);
			List<List<Double>> table4 = PubUtilHandler.getRoundingTable(lrfinaltable);
			List<List<Double>> table5 = PubUtilHandler.getRoundingTable(financeplanfinaltable);
			List<List<Double>> table6 = PubUtilHandler.getRoundingTable(investHandlerTable);
			List<List<Double>> table7 = PubUtilHandler.getRoundingTable(capitalTable);
			List<List<Double>> table8 = PubUtilHandler.getRoundingTable(capitalsrcTable);
			List<List<Double>> table9 = PubUtilHandler.getRoundingTable(balancetable);
			List<List<Double>> table10 = PubUtilHandler.getRoundingTable(eVAHandlerTable);

			retmap.put("投资计划与资金筹措表", table1); retmap.put("总成本费用表", table2); 
			retmap.put("借款还本付息计划表",table3 ); retmap.put("利润和利润分配表",table4 ); 
			retmap.put("财务计划现金流量表", table5);retmap.put("项目投资现金流量表", table6);
			retmap.put("项目资本金现金流量表",table7 ); retmap.put("资金来源与运用表",table8 );
			retmap.put("资产负债表",table9 ); retmap.put("EVA测算表", table10); 

		}catch(Exception e){
			e.getMessage();
			List<List<Double>> temp = new ArrayList<List<Double>>();
			retmap.put("投资计划与资金筹措表", temp); retmap.put("总成本费用表",temp ); 
			retmap.put("借款还本付息计划表", temp); 
			retmap.put("利润和利润分配表",temp ); retmap.put("财务计划现金流量表", temp);
			retmap.put("项目投资现金流量表",temp );
			retmap.put("项目资本金现金流量表",temp ); retmap.put("资金来源与运用表",temp );retmap.put("资产负债表",temp ); 
			retmap.put("EVA测算表", temp); 
		}
	    WriteExcelCal.getexcel("E:\\",projectvo.getProjectName(),retmap);

		return retmap;
	}

	public List<Double> getcapital_irrnpv(List<List<Double>> capitalTable){
		List<Double> retlst = new ArrayList<Double>();

		try{
			Double doub3 = ReadExcelCal.getirrnpvvalue(capitalTable.get(12).toArray(new Double[0]),
					0.07, 0.06, "3");
			retlst.add(doub3);  

		}catch(Exception e){
			e.getMessage();
		}
		return retlst;
	}

	public List<Double> getinvest_irrnpv(List<List<Double>> investHandlerTable){
		List<Double> retlst = new ArrayList<Double>();

		try{
			Double doub3 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(10).toArray(new Double[0]),
					0.07, 0.06, "3");
			Double doub1 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(10).toArray(new Double[0]),
					0.07, 0.06, "1");
			Double retunperiod =  ReadExcelCal.getreturnperiod(investHandlerTable.get(10).toArray(new Double[10]),
					investHandlerTable.get(11).toArray(new Double[0]));
			Double doub4 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(13).toArray(new Double[0]),
					0.07, 0.06, "4");
			Double doub2 = ReadExcelCal.getirrnpvvalue(investHandlerTable.get(13).toArray(new Double[0]),
					0.07, 0.06, "2");
			Double retunperiod2 =  ReadExcelCal.getreturnperiod(investHandlerTable.get(10).toArray(new Double[13]),
					investHandlerTable.get(14).toArray(new Double[0]));
			retlst.add(doub3);  retlst.add(doub4);
			retlst.add(doub1);  retlst.add(doub2); 
			retlst.add(retunperiod);  retlst.add(retunperiod2);
		}catch(Exception e){
			e.getMessage();
		}
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
