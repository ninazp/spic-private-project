package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoanRepayHandler {
	
	public static List<List<Double>> getLoanRepayTable(List<List<Double>> zjcktable,Map<String,Object> parammap){

		List<List<Double>> interesttable = new ArrayList<List<Double>>();

		List<Double> r1 = new ArrayList<Double>();r1.add(zjcktable.get(9).get(0));
		List<Double> r11 = new ArrayList<Double>();//r11.add(0.00);
		List<Double> r12 = new ArrayList<Double>();//r12.add(0.00);
		List<Double> r121 = new ArrayList<Double>();//r121.add(0.00);
		List<Double> r122 = new ArrayList<Double>();//r122.add(0.00);
		List<Double> r13 = new ArrayList<Double>();//r13.add(0.00);
		List<Double> r2 = new ArrayList<Double>();r2.add(zjcktable.get(12).get(0));
		List<Double> r21 = new ArrayList<Double>();//r21.add(0.00);
		List<Double> r22 = new ArrayList<Double>();//r22.add(0.00);
		List<Double> r23= new ArrayList<Double>();//r23.add(0.00);
		List<Double> r3 = new ArrayList<Double>();r3.add(0.00);
		List<Double> r31 = new ArrayList<Double>();r31.add(0.00);
		List<Double> r32 = new ArrayList<Double>();r32.add(0.00);
		List<Double> r4 = new ArrayList<Double>();r4.add(0.00);
		List<Double> r5 = new ArrayList<Double>();r5.add(0.00);

		Double countyear = Double.valueOf(parammap.get("countyear").toString());
		//长期贷款利率
		Double principalrate = Double.valueOf(parammap.get("principalrate").toString());
		//还款方式
		Double repaytype = Double.valueOf(parammap.get("repaytype").toString());
		//计息次数
		Double interestcount = Double.valueOf(parammap.get("interestcount").toString());
		//短期利息
		Double shortloanrate = Double.valueOf(parammap.get("shortloanrate").toString());

		for(int i=1;i<zjcktable.get(0).size();i++){
			List<List<Double>> langtemplst =  LangLoanHanderdel.getlanghbfx(i, zjcktable.get(9).get(i),
					principalrate, interestcount, repaytype, countyear);
			
			List<List<Double>> flowtemplst =  LangLoanHanderdel.getflowhbfx(i, zjcktable.get(12).get(i), 
					shortloanrate, countyear);
			for(int j=0;j<=countyear;j++){
				if(i==1){
					r11.add(langtemplst.get(0).get(j));
					r12.add(langtemplst.get(1).get(j));
					r121.add(langtemplst.get(2).get(j));
					r122.add(langtemplst.get(3).get(j));
					r13.add(langtemplst.get(4).get(j));
					
					r21.add(flowtemplst.get(0).get(j));
					r22.add(flowtemplst.get(1).get(j));
					r23.add(flowtemplst.get(2).get(j));
					
				}else{
					r11.add(r11.get(j)+langtemplst.get(0).get(j));
					r12.add(r12.get(j)+langtemplst.get(1).get(j));
					r121.add(r121.get(j)+langtemplst.get(2).get(j));
					r122.add(r122.get(j)+langtemplst.get(3).get(j));
					r13.add(r13.get(j)+langtemplst.get(4).get(j));
					
					r21.add(r21.get(j)+flowtemplst.get(0).get(j));
					r22.add(r22.get(j)+flowtemplst.get(1).get(j));
					r23.add(r23.get(j)+flowtemplst.get(2).get(j));
				}
			}
		}
		for(int i=1;i<=countyear;i++){
			if(i<zjcktable.get(0).size()){
				r1.add(zjcktable.get(9).get(i));
				r2.add(zjcktable.get(12).get(i));
			}else{
				r1.add(0.00);
				r2.add(0.00);
			}
			r3.add(0.00);
			r31.add(0.00);
			r32.add(0.00);
			r4.add(0.00);
			r5.add(0.00);
		}

		interesttable.add(r1);interesttable.add(r11);interesttable.add(r12);
		interesttable.add(r121);interesttable.add(r122);interesttable.add(r13);
		interesttable.add(r2);interesttable.add(r21);interesttable.add(r22);
		interesttable.add(r23);
		interesttable.add(r3);interesttable.add(r31);
		interesttable.add(r32);interesttable.add(r4);interesttable.add(r5);

		return interesttable;
	}


}
