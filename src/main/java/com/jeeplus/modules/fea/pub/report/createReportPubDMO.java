package com.jeeplus.modules.fea.pub.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jeeplus.modules.fea.designcal.BusiIndexCal;
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


	public List<Double>  getchange_irrnpv(String projectid,String changename,List<Double> changevals){
		List<Double> retdouble = new ArrayList<Double>();
		for(Double changeval : changevals) {
			Map<String,Object> reportparam = new HashMap<String,Object>();
			reportparam.put("projectid", projectid);
			reportparam.put(changename, changeval);
			Map<String,List<List<Double>>> reportlst =  getallreportnostatic(reportparam);
			List<List<Double>> investHandlerTable = reportlst.get("项目投资现金流量表");
			List<Double> invest_irrnpv = getinvest_irrnpv(investHandlerTable);
			if(null!=invest_irrnpv && invest_irrnpv.size()>0) {
				retdouble.add(invest_irrnpv.get(0));
			}else {
				retdouble.add(0.00);
			}
		}
		return retdouble;
	}



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
			
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			BusiIndexCal busiIndexCal = (BusiIndexCal) wac.getBean("busiIndexCal");
			List<List<String>> designresult = new ArrayList<List<String>>();
			if(null!=busiIndexCal){
				designresult = busiIndexCal.getInitIvdesMny(projectvo.getId());
			}

			//*******************计算单因素影响**********************//
			Double pricechange = 0.00;//调用参数 供暖价格变化率
			Double powercostchange = 0.00;//调用参数 入住面积变化率
			Double personchange = 0.00;//调用参数 入住面积变化率
			Double investamt = 0.00;
			if(reportparam.containsKey("price") && null!=reportparam.get("price")) {
				pricechange = (Double) reportparam.get("price");
				
				//--报表计算参数
				if(parammap.containsKey("price") && null!=parammap.get("price")) {
					Double price = (Double) parammap.get("price");
					price = price*(1+pricechange);
					parammap.put("price", price);
				}
				
			}
			if(reportparam.containsKey("powercost") && null!=reportparam.get("powercost")) {
				powercostchange = (Double) reportparam.get("powercost");
				if(parammap.containsKey("heatcost") && null!=parammap.get("heatcost")) {
					Double heatcost = (Double) parammap.get("heatcost");
					heatcost = heatcost*(1+powercostchange);
					parammap.put("heatcost", heatcost);
				}
				
			}
			if(reportparam.containsKey("person") && null!=reportparam.get("person")) {
				personchange = (Double) reportparam.get("person");
				if(parammap.containsKey("person") && null!=parammap.get("person")) {
					Double personwage = (Double) parammap.get("person");
					personwage = personwage*(1+personchange);
					parammap.put("person", personwage);
				}
			}
			
			if(reportparam.containsKey("investamt") && null!=reportparam.get("investamt")) {
				investamt = (Double) reportparam.get("investamt");
				if( null!=designresult && designresult.size()>1 && null!=designresult.get(1)) {
					for(int i=0;i<designresult.get(1).size();i++) {
						if(i>1 && i<7 && designresult.size()>7 && null!=designresult.get(1).get(i)) {
							Double tmp = Double.valueOf(designresult.get(i).get(i));
							tmp = tmp*(1+investamt);
							designresult.get(1).set(i, tmp.toString());
						}
					}
				}
			}
			//1 -- 投资计划与资金筹措表
			List<List<Double>> zjcktable = ZjcctableHanderNew.getzjcctable(
					fea_investdisVOMapper, fea_investdisBVOMapper, projectvo,parammap,designresult);

			@SuppressWarnings("unchecked")
			List<Fea_investdisVO> fitvo =  (List<Fea_investdisVO>) PubBaseDAO.getMutiParentVO("fea_investdis", "id",
					"project_id='"+projectvo.getId()+"'", fea_investdisVOMapper);

			if(null==fitvo || fitvo.size()==0){
				return retmap;
			}

			Map<String,List<List<Double>>>  basereportmap = GetBaseReportDMO.getbasereport(zjcktable, parammap,designresult);

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

	public String exportexcel(String path,Map<String,Object> param ,List<List<String>> totalgs)  
	{ 

		if(null!=param && param.get("projectid")!=null) {
			Map<String,List<List<Double>>> retmap = getallreportnostatic(param);
			FeaProjectB projectvo = projectmapper.get(param.get("projectid").toString());
			path = path + "经济性分析报表(项目名称："+projectvo.getProjectName()+").xls";
			WriteExcelCal.exportexcel(path,projectvo.getProjectName(),retmap,totalgs);
		}


		return path;

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
