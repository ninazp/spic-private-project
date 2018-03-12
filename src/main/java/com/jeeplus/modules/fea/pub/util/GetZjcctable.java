package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class GetZjcctable {
	
	
	public static List<List<Double>> getzjcctable(Double tzamt,
			Double equipamt,Double rate,Double startmth ){
		
		List<List<Double>> rettable = new ArrayList<List<Double>>();

		Double ldamt = 35.00;
		
		
		Double a1 = rate*(12-startmth+1)/2400;
		Double a2 = (1.25+0.25*a1);
		//长期借款本金
		Double y = tzamt/a2;
		//建设期利息
		Double x = a1*y;
		//长期借款
		Double d221 = x+y;
		//流动资金借款
		Double d222 = ldamt *0.7;
		//借款
		Double d22 = d221+d222;
		
		//建设投资资本金
		Double d211 = tzamt - y;
		Double d212 =  ldamt*0.3;
		Double d21 = d211 +d212;
		//资金筹措
		Double d2 = d21+d22;
		
		Double d13 = 35.00;
		Double d12 = x;
		Double d11 = tzamt;
		
		Double d1 = x+tzamt+35.00;
		
		
		List<Double> r1 = new ArrayList<Double>() ;
		r1.add(d1);r1.add(d1);
		List<Double> r11 = new ArrayList<Double>();
		r11.add(d11);r11.add(d11);
		List<Double> r12 = new ArrayList<Double>();
		r12.add(d12);r12.add(d12);
		List<Double> r13 = new ArrayList<Double>();
		r13.add(d13);r13.add(d13);
		List<Double> r2 = new ArrayList<Double>();
		r2.add(d2);r2.add(d2);
		List<Double> r21 = new ArrayList<Double>();
		r21.add(d21);r21.add(d21);
		List<Double> r211 = new ArrayList<Double>();
		r211.add(d211);r211.add(d211);
		List<Double> r212 = new ArrayList<Double>();
		r212.add(d212);r212.add(d212);
		List<Double> r22 = new ArrayList<Double>();
		r22.add(d22);r22.add(d22);
		List<Double> r221 = new ArrayList<Double>();
		r221.add(d221);r221.add(d221);
		List<Double> r2211 = new ArrayList<Double>();
		r2211.add(y);r2211.add(y);
		List<Double> r2212 = new ArrayList<Double>();
		r2212.add(x);r2212.add(x);
		List<Double> r222 = new ArrayList<Double>();
		r222.add(d222);r222.add(d222);

		
		rettable.add(r1);rettable.add(r11);
		rettable.add(r12);rettable.add(r13);
		rettable.add(r2);rettable.add(r21);
		rettable.add(r211);rettable.add(r212);
		rettable.add(r22);rettable.add(r221);
		rettable.add(r2211);rettable.add(r2212);
		rettable.add(r222);
		
		
		return rettable;
		
	}
 	

}
