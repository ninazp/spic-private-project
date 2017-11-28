package com.jeeplus.modules.fea.pub.util;

import java.math.BigDecimal;
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
		
		prjectdouble.add(currentproductmonth);//当年供热月份
		prjectdouble.add(countyear);//计算期，比如21年
		prjectdouble.add(year+0.0);//计算开始年
		prjectdouble.add(heatarea);//常规供热面积
		
		return prjectdouble; 
	}
	
	/**
	 * 返回资金筹措表 -- 未计算建设期利息
	 * @param fundsrcvo
	 * @return
	 */
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
		
		//资金筹备
		for(Fea_fundssrcBVO bvo :bvolst){
//		    d211 = d211 + bvo.getJstzamt();
//		    d212 = d212+bvo.getLdtzamt();
		    d21= d211+d212;
		}
		
		//借款
		for(Fea_fundssrcTVO tvo :tvolst){
//		    d2211 = d2211 + tvo.getLangamt();//长期借款本金
		    d2212 = d2212+20.65;//？？？？？建设期利息
		    
		    //长期借款
		    d221= d2211+d2212;
		    //短期借款
//		    d222 = d222+tvo.getShortamt();
		    
		    d22 = d221+d222;
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
