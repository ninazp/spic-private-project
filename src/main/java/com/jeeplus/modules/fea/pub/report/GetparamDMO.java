package com.jeeplus.modules.fea.pub.report;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.fea.entity.costinfo.Fea_costinfoVO;
import com.jeeplus.modules.fea.entity.fecl.Fea_costfecfVO;
import com.jeeplus.modules.fea.entity.funds.Fea_capformVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcTVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.entity.income.Fea_incomesetVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostBVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.subsidy.Fea_incosubsidyVO;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;

public class GetparamDMO {


	@SuppressWarnings("unchecked")
	public static Map<String,Object> getparammap(FeaProjectB  projectvo,
			BaseMapper fea_incomesetVOMapper,
			BaseMapper fea_fundssrcVOMapper1,
			BaseMapper fea_fundssrcTVOMapper,
			BaseMapper fea_costfecfVOMapper,
			BaseMapper fea_investdisVOMapper,
			BaseMapper fea_investdisBVOMapper,
			BaseMapper fea_capformVOMapper,
			BaseMapper fea_productcostVOMapper,
			BaseMapper fea_productcostBVOMapper,
			BaseMapper fea_costinfoVOMapper,
			BaseMapper fea_incosubsidyVOMapper
			){
		Map<String,Object> retmap = new HashMap<String, Object>();

		String wheresql = "project_id='"+projectvo.getId()+"'";

		//获取项目节点信息
		Date startupdate = projectvo.getStartupDate();
		int month = startupdate.getMonth();
		Double consperiod = projectvo.getConstructPeriod();
		Double currentproductmonth = 12-consperiod-month;
		Double countyear = (null==projectvo.getCountyears())?0.00:projectvo.getCountyears();

		 Calendar calendar = Calendar.getInstance(); 
		 calendar.setTime(startupdate);
		 int startyearini = calendar.get(Calendar.YEAR);
		
		retmap.put("startmth", month);
		retmap.put("constructPeriod", consperiod);
		retmap.put("project_id", projectvo.getId());
		retmap.put("countyear", countyear);
		retmap.put("currentproductmonth", currentproductmonth);
		retmap.put("startyear", startyearini);
		if(projectvo.getPrice()!=null){
			retmap.put("price", projectvo.getPrice());//供热单价
		}else{
			retmap.put("price", 14.00);//供热单价
		}
		

		//资金来源retlst.add(tvo.getInterestcount());// 计息次数（年）
//		retlst.add(tvo.getPrincipalrate());// 本金利率（%）
//		retlst.add(tvo.getLangrate());// 利息利率（%）
//		retlst.add(Double.valueOf(tvo.getRepaytype()));// 还款方式
//		List<Double>  fundssrcparam = getfundssrc(fea_fundssrcVOMapper,fea_fundssrcTVOMapper, projectvo);
	

		//资金分配 -- 获得供热面积
		Map<Integer,Double> heatareamap = new HashMap<Integer, Double>(); 
		Map<Integer,Double> dkjemap = new HashMap<Integer, Double>(); 
		List<Fea_investdisVO>   distrvolst = (List<Fea_investdisVO>) PubBaseDAO.
				getMutiParentVO("fea_investdis", "id", " project_id='"+projectvo.getId()+"' ", fea_investdisVOMapper);
		
		Double dkjeamt = 0.00;
		for(Fea_investdisVO fvo : distrvolst){
			int investyear = (null==fvo.getYear())?0:Integer.valueOf(fvo.getYear());
			int investindex = investyear - startyearini+1;
			if(null!=fvo.getHeatarea()) heatareamap.put(investindex, fvo.getHeatarea());
			if(null!=fvo.getDeductvtax()) {
				dkjemap.put(investindex, fvo.getDeductvtax());
				
				dkjeamt = dkjeamt+ fvo.getDeductvtax();
			}
		}
		retmap.put("heatarea", heatareamap);
		retmap.put("dkje", dkjemap);
		retmap.put("dkjeamt", dkjeamt);

		List<List<Double>> costparam = getcostparam(projectvo.getId(), countyear, currentproductmonth, fea_capformVOMapper, fea_productcostVOMapper, fea_productcostBVOMapper);

		retmap.put("repairrate", costparam.get(0));//维修率
		retmap.put("costparam", costparam.get(1));
		retmap.put("person", costparam.get(2));
		retmap.put("heatcost", costparam.get(3));
		
		retmap.put("income", getsubincome(fea_incosubsidyVOMapper, wheresql, countyear));//补贴收入
		
		retmap.put("occupancy", getproductlst(countyear, fea_costinfoVOMapper, wheresql));//入住率

		//账务费用
		List<Fea_costfecfVO> Fea_costfecfVOlst = (List<Fea_costfecfVO>) PubBaseDAO.getMutiParentVO("fea_costfecf", "id", wheresql,fea_costfecfVOMapper);
		if(null!=Fea_costfecfVOlst && Fea_costfecfVOlst.size()>0){
			Double shortloanrate =(null==Fea_costfecfVOlst.get(0).getCircularate())?0.00:Fea_costfecfVOlst.get(0).getCircularate();
			retmap.put("shortloanrate", shortloanrate);
			
			retmap.put("interestcount", Fea_costfecfVOlst.get(0).getLangyear());// 计息次数（年）
			retmap.put("principalrate", Fea_costfecfVOlst.get(0).getLangrate());// 本金利率（%）
			retmap.put("langrate", Fea_costfecfVOlst.get(0).getLangrate());// 利息利率（%）
			retmap.put("repaytype", 1);// 还款方式
			
		}

		//获取基本参数信息
		List<Fea_incomesetVO> volst = (List<Fea_incomesetVO>) PubBaseDAO.getMutiParentVO("fea_incomeset", "id", wheresql, fea_incomesetVOMapper);
		if(null!=volst && volst.size()>0){
			retmap.put("incomerate", volst.get(0).getIncomerate());//所得税税率
			retmap.put("legalaccfund", volst.get(0).getLegalaccfund());//法定盈余公积金比率
			retmap.put("yflrprop", volst.get(0).getYflrprop());//应付利润比率
			retmap.put("capinvestrate", volst.get(0).getCapinvestrate());//资本金基准收益率
			retmap.put("industrysqrate", volst.get(0).getIndustrysqrate());//税前收益率
			retmap.put("industryshrate", volst.get(0).getIndustryshrate());//税后收益率
			retmap.put("issdssjsm", volst.get(0).getIssdssjsm());//所得税三减三免
		}else{
			retmap.put("incomerate",0);
			retmap.put("legalaccfund",0);
			retmap.put("yflrprop",0);
			retmap.put("capinvestrate", 0);
			retmap.put("industrysqrate", 0);
			retmap.put("industryshrate", 0);
			retmap.put("issdssjsm", 0);
		}

		return retmap;
	}
	
	//入住率
	public static List<Double> getproductlst(Double countyear,BaseMapper basemaper,String wheresql){
		List<Fea_costinfoVO>   fea_costinfovo = (List<Fea_costinfoVO>) PubBaseDAO.
				getMutiParentVO("fea_costinfo", "id", wheresql,
						basemaper);
		List<Double> costrate = new ArrayList<Double>();	
		for(Fea_costinfoVO fcvo : fea_costinfovo){
				for(int j=0;j<countyear;j++){
					try {
						Method m ;
						if(j==0){
							m = fcvo.getClass().getMethod("getYear");
						}else{
							m = fcvo.getClass().getMethod("getYear"+(j+1));
						}
						Object rated = m.invoke(fcvo);
						if(null!=rated){
							costrate.add(((Double)rated));
						}else{
							costrate.add(0.00);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
		}
		
		return costrate;
	}
	
	public static List<Double> getsubincome(BaseMapper baseMapper,String wheresql,Double countyear){
		List<Fea_incosubsidyVO>   fea_incosubsidyvo = (List<Fea_incosubsidyVO>) PubBaseDAO.
				getMutiParentVO("fea_incosubsidy", "id", wheresql,
						baseMapper);
		List<Double> retlst = new ArrayList<Double>();
		for(int i=0;i<fea_incosubsidyvo.size();i++){
			if(fea_incosubsidyvo.get(i).getSubsidytype().equals("配套费")){
				Double sums = 0.0;
				retlst.add(0.0);
				for(int j=0;j<countyear;j++){
					try {
						Method m ;
						m = fea_incosubsidyvo.get(i).getClass().getMethod("getYear"+(j+1));
						Object rated = m.invoke(fea_incosubsidyvo.get(i));
						if(rated!=null && rated.toString().length()>0){
							retlst.add(new Double(rated.toString()));
							sums = sums+new Double(rated.toString());
						}else{
							retlst.add(0.0);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				retlst.set(0, sums);
			}
		}
		return retlst;
	}

	public static List<List<Double>> getcostparam(String projectid,Double countyear,Double currentproductmonth,
			BaseMapper fea_capformVOMapper, 
			BaseMapper fea_productcostVOMapper,BaseMapper fea_productcostBVOMapper){
		List<Fea_capformVO>   fea_capform = (List<Fea_capformVO>) PubBaseDAO.
				getMutiParentVO("fea_capform", "id", " project_id='"+projectid+"' ", fea_capformVOMapper);
		List<Fea_productcostVO>   productcost = (List<Fea_productcostVO>) PubBaseDAO.
				getMutiParentVO("fea_productcost", "id", " project_id='"+projectid+"' ", fea_productcostVOMapper);

		List<List<Double>> retlst = new ArrayList<List<Double>>();

		List<Double> paramdoub = new ArrayList<Double>();
		List<Double> repairrate = new ArrayList<Double>();
		List<Double> personlst = new ArrayList<Double>();
		List<Double> heatcostlst = new ArrayList<Double>();

		Double insurance = 0.0;
		Double perwage = 0.00;
		Double welfare = 0.0;
		Double heatdeposit = 0.0;
		Double wateramt = 0.0;

		if(null!=fea_capform && fea_capform.size()>0 && null!=productcost && productcost.size()>0){
			for(Fea_productcostVO pcost : productcost){
				List<Fea_productcostBVO>   pbvolst = (List<Fea_productcostBVO>) PubBaseDAO.getMutiParentVO(
						"fea_productcostb", "id", " pkproductcost='"+pcost.getId()+"' ", fea_productcostBVOMapper);
				for(Fea_productcostBVO bvo : pbvolst){

					for(int j=0;j<countyear;j++){
						try {
							Method m = bvo.getClass().getMethod("getYear"+(j+1));
							Object rated = m.invoke(bvo);
							Double rateddf = 0.00;
							if(null!=rated){
								rateddf = (Double)rated;
							}
							if(bvo.getCosttype().contains("维修") || bvo.getCosttype().equals("3")){
								repairrate.add(rateddf);
							}else if(bvo.getCosttype().contains("定员") || bvo.getCosttype().equals("1")){
								personlst.add(rateddf);
							}else if(bvo.getCosttype().contains("电费") || bvo.getCosttype().equals("2")){
								heatcostlst.add(rateddf);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
				if(null!=pcost.getInsurance()) insurance = pcost.getInsurance();
				if(null!=pcost.getWelfare())  welfare = pcost.getWelfare();
				if(null!=pcost.getHeatdeposit()) heatdeposit = pcost.getHeatdeposit();
				if(null!=pcost.getPerwage()) perwage = pcost.getPerwage();
			}
		}

		// 折旧年限，残值率
		if(null!=fea_capform && fea_capform.size()>0 && null!=fea_capform.get(0)){
			paramdoub.add(fea_capform.get(0).getUselifefat());
			paramdoub.add(fea_capform.get(0).getResidualrate());
		}else{
			paramdoub.add(0.00);
			paramdoub.add(0.00);
		}

		// * 保险费率，工资,福利，供热,泵热费，
		paramdoub.add(insurance);
		paramdoub.add(perwage);
		paramdoub.add(welfare);
		paramdoub.add(heatdeposit);
		paramdoub.add(wateramt);

		retlst.add(repairrate);
		retlst.add(paramdoub);
		retlst.add(personlst);
		retlst.add(heatcostlst);

		return retlst;
	}

	//获取长期贷款利率还款年限等信息
	@SuppressWarnings("unchecked")
	public static List<Double> getfundssrc(BaseMapper baseMapper,BaseMapper baseMappert, FeaProjectB projectvo){
		List<Double> retlst = new ArrayList<Double>();
		//查询资金来源
		List<Fea_fundssrcVO>   distrvolst = (List<Fea_fundssrcVO>) PubBaseDAO.
				getMutiParentVO("fea_fundssrc", "id", " project_id='"+projectvo.getId()+"' ", baseMapper);
		if(null!=distrvolst && distrvolst.size()>0){
			for(Fea_fundssrcVO fvo : distrvolst){
				List<Fea_fundssrcTVO>   fundssrctlst = (List<Fea_fundssrcTVO>) PubBaseDAO.getMutiParentVO("fea_fundssrc_t", "id", " pkfundssrc='"+fvo.getId()+"' ", baseMappert);
				if(null!=fundssrctlst && fundssrctlst.size()>0){
					for(Fea_fundssrcTVO tvo : fundssrctlst){//长期贷款
						retlst.add(tvo.getInterestcount());// 计息次数（年）
						retlst.add(tvo.getPrincipalrate());// 本金利率（%）
						retlst.add(tvo.getLangrate());// 利息利率（%）
						retlst.add(Double.valueOf(tvo.getRepaytype()));// 还款方式
					}
				}else{
					retlst.add(15.00);
					retlst.add(4.9);
					retlst.add(4.9);
					retlst.add(1.00);
				}
			}
		}

		return retlst;
	}

}
