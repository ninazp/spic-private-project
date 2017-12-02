package com.jeeplus.modules.fea.pub.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;

public class ProjectInfoHander {
	
	public static List<Double> getprojectinfo(
			FeaProjectB projectvo){

		List<Double> prjectdouble = new ArrayList<Double>();

		Double heatarea = projectvo.getHeatArea();//供暖面积
		Date startupdate = projectvo.getStartupDate();
		int year = startupdate.getYear();
		int month = startupdate.getMonth();
		Double consperiod = projectvo.getConstructPeriod();
		Double currentproductmonth = 12-consperiod-month;
		Double countyear = projectvo.getCountyears();

		prjectdouble.add(currentproductmonth);//当年供热月份
		prjectdouble.add(countyear);//计算期，比如21年
		prjectdouble.add(year+0.0);//计算开始年
		prjectdouble.add(heatarea);//供热面积
		
		return prjectdouble;
	}
	
	public static Double getdekuje(Fea_fundssrcVOMapper fea_fundssrcVOMapper,FeaProjectB projectvo){
		Double deductval = 0.0;
		//查询资金来源
		List<Fea_fundssrcVO>   distrvolst = (List<Fea_fundssrcVO>) PubBaseDAO.
				getMutiParentVO("fea_fundssrc", "id", " projectcode='"+projectvo.getProjectCode()+"' ", fea_fundssrcVOMapper);
		if(null!=distrvolst && distrvolst.size()>0){
			for(Fea_fundssrcVO fvo : distrvolst){
				deductval = deductval+fvo.getDeductvtax();
			}
		}
		
		return deductval;
	}
	
	public static List<List<Double>> getzjcctable(
			Fea_investdisVOMapper fea_investdisVOMapper,
			Fea_investdisBVOMapper fea_investdisBVOMapper,FeaProjectB projectvo){
		
		List<List<Double>> rettable = new ArrayList<List<Double>>();
		
		List<List<Double>> zjcktable = new ArrayList<List<Double>>();
		
		//查询资金来源
		List<Fea_investdisVO>   distrvolst = (List<Fea_investdisVO>) PubBaseDAO.
				getMutiParentVO("fea_investdis", "id", " projectcode='B0001' ", fea_investdisVOMapper);
		if(null!=distrvolst && distrvolst.size()>0){
			for(Fea_investdisVO fvo : distrvolst){
				List<Fea_investdisBVO>   distriblst = (List<Fea_investdisBVO>) PubBaseDAO.getMutiParentVO("fea_investdis_b", "id", " pkinvestdis='"+fvo.getId()+"' ", fea_investdisBVOMapper);
				fvo.setFea_investdisBVOList(distriblst);
				List<Double> cktable = ProjectInfoHander.getzicc(fvo);
				zjcktable.add(cktable);
			}
		}
		
		for(int i=0;i<zjcktable.size();i++){
			for(int j=0;j<zjcktable.get(i).size();j++){
				if(i==0){
				    List<Double> zktable = new ArrayList<Double>();
				    zktable.add(0.0);
				    zktable.add(zjcktable.get(i).get(j));
				    rettable.add(zktable);
				}else{
					rettable.get(j).add(zjcktable.get(i).get(j));
				}
			}
		}
		
		for(int i=0;i<rettable.size();i++){
			Double tmpsum = 0.0;
			for(int j=0;j<rettable.get(i).size();j++){
				tmpsum = tmpsum+rettable.get(i).get(j);
			}
			rettable.get(i).set(0, tmpsum);
		}
		
		return rettable;
	}

	/**
	 * 返回资金筹措表 -- 未计算建设期利息
	 * @param fundsrcvo
	 * @return
	 */
	public static List<Double> getzicc(Fea_investdisVO fundsrcvo){
		List<Double> zjcclst = new ArrayList<Double>();
		List<Fea_investdisBVO>  bvolst = fundsrcvo.getFea_investdisBVOList();

		//1,1.1,1.2,1.3
		Double d1=0.0;
		Double d11=0.0;
		Double d12=0.0;
		Double d13=0.0;

		Double d2 =0.0;
		//2.1,2.1.1,2.1.2
		Double d21=0.0;
		Double d211=0.0;
		Double d212=0.0;

		//2.2,2.2.1,2.2.2
		Double d22=0.0;
		Double d221=0.0;//长期借款
		Double d2211=0.0;//长期借款本金
		Double d2212=0.0;//建设期利息
		Double d222=0.0;//流动资金借款

		//资金筹备
		for(Fea_investdisBVO bvo :bvolst){
			if(bvo.getInvesttype().equals("1")){
				d211 = d211 + bvo.getJsamt();
				d212 = d212+bvo.getLdamt();
				d21= d211+d212;
			}
		}

		//借款
		for(Fea_investdisBVO bvo :bvolst){
			if(bvo.getInvesttype().equals("2")){
				d2211 = d2211 + bvo.getJsamt();//长期借款本金
				d2212 = d2212+20.65;//？？？？？建设期利息

				//长期借款
				d221= d2211+d2212;
				//短期借款
				d222 = d222+bvo.getLdamt();

				d22 = d221+d222;
			}
		}

		//建设投资 = d211自筹+d2211长期贷款本金
		d11 = d2211+d211;
		d12 = d2212;//建设期利息
		d13 = d212+d222;//自筹资金流动资金+贷款流动资金
		d1 = d11+d12+d13;
		d2 = d21+d22;

		BigDecimal   b22   =   new   BigDecimal(d22);
		double   f22   =   b22.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
		BigDecimal   b221   =   new   BigDecimal(d221);
		double   f221   =   b221.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  

		zjcclst.add(d1);
		zjcclst.add(d11);
		zjcclst.add(d12);
		zjcclst.add(d13);
		zjcclst.add(d2);
		zjcclst.add(d21);
		zjcclst.add(d211);
		zjcclst.add(d212);
		zjcclst.add(f22);
		zjcclst.add(f221);
		zjcclst.add(d2211);
		zjcclst.add(d2212);
		zjcclst.add(d222);

		return zjcclst;
	}




}
