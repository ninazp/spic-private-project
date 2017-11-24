package com.jeeplus.modules.fea.service.totaltab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcTVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;

public class CountHander {

	public static List<Double> handprject(FeaProjectB  projectvo){
		List<Double> prjectdouble = new ArrayList<Double>();
		
		Double heatarea = projectvo.getHeatArea();//供暖面积
		Date startupdate = projectvo.getStartupDate();
		int year = startupdate.getYear();
		int month = startupdate.getMonth();
		Double consperiod = projectvo.getConstructPeriod();
		Double currentproductmonth = 12-consperiod-month;
		Double countyear = projectvo.getCountyears();
		
		prjectdouble.add(currentproductmonth);
		prjectdouble.add(countyear);
		
		return prjectdouble; 
	}
	
	public static List<Double> getzicc(Fea_fundssrcVO fundsrcvo){
		List<Double> zjcclst = new ArrayList<Double>();
		List<Fea_fundssrcBVO>  bvolst = fundsrcvo.getFea_fundssrcBVOList();
		List<Fea_fundssrcTVO>  tvolst = fundsrcvo.getFea_fundssrcTVOList();
		
		
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
		
		for(Fea_fundssrcBVO bvo :bvolst){
		    d211 = d211 + bvo.getJstzamt();
		    d212 = d212+bvo.getLdtzamt();
		    d21= d211+d212;
		}
		
		for(Fea_fundssrcTVO tvo :tvolst){
		    d2211 = d2211 + tvo.getLangamt();
		    d2212 = d2212+0.0;//？？？？？
		    
		    d211= d2211+d2212;
		    
		    d222 = d222+tvo.getShortamt();
		}
		
		
		
		return zjcclst;
	}
	
}
