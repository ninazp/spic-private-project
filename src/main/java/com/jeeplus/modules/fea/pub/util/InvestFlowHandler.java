package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class InvestFlowHandler {

	/**
	 * 项目投资现金流量表
	 * @param totalyears
	 * @param lrtable
	 * @param costtable
	 * @param cctable
	 * @param loanrepay
	 * @return
	 */
	
	public static List<List<Double>> getInvestHandlerTable(Double totalyears,List<List<Double>> lrtable,
			List<List<Double>> costtable,List<List<Double>> cctable,List<List<Double>> loanrepay,
			Double ftaxrate,Double assetval){
		
		int start =0;
		
		List<List<Double>>  rettable = new ArrayList<List<Double>>();
		
		List<Double> ret14 = new ArrayList<Double>();//
		List<Double> ret13 = new ArrayList<Double>();//
		List<Double> ret11 = new ArrayList<Double>();//
		List<Double> ret12 = new ArrayList<Double>();//
		List<Double> ret1 = new ArrayList<Double>();//
		List<Double> ret24 = new ArrayList<Double>();//
		List<Double> ret23 = new ArrayList<Double>();//
		List<Double> ret21= new ArrayList<Double>();//
		List<Double> ret22 = new ArrayList<Double>();//
		List<Double> ret2 = new ArrayList<Double>();//合计
		List<Double> ret3 = new ArrayList<Double>();//合计
		List<Double> ret4 = new ArrayList<Double>();//合计
		List<Double> ret5 = new ArrayList<Double>();//合计
		List<Double> ret6 = new ArrayList<Double>();//合计
		List<Double> ret7 = new ArrayList<Double>();//合计
		Double sum5 = 0.0;
		
		for(int i=0;i<=totalyears;i++){
			ret11.add(lrtable.get(0).get(i));
			ret12.add(lrtable.get(10).get(i));
			
			if(i==0 || i==totalyears){
				ret14.add(cctable.get(3).get(0));
				ret13.add(assetval-costtable.get(0).get(0));
			}else{
				ret13.add(0.0);
				ret14.add(0.0);
			}
			ret1.add(ret11.get(i)+ret12.get(i)+ret13.get(i)+ret14.get(i));
			if(i>=cctable.get(1).size()){
				ret21.add(0.0);
				ret22.add(0.0);
			}else{
			   ret21.add(cctable.get(1).get(i));
			   ret22.add(cctable.get(3).get(i));
			}
			ret23.add(costtable.get(11).get(i));
			ret24.add(lrtable.get(1).get(i));
			ret2.add(ret21.get(i)+ret22.get(i)+ret23.get(i)+ret24.get(i));
			
			ret3.add(ret1.get(i)-ret2.get(i));
			if(i==0){
				ret4.add(0.0);
			}else if(i==1){
				ret4.add(ret3.get(i));
			}else{
				ret4.add(ret3.get(i)+ret4.get(i-1));
			}
			if(i>0 && ret3.get(i)>0){
				start = start+1;
			}
			if(start>3 && start<7 && lrtable.get(19).get(i)>0){
				ret5.add(lrtable.get(19).get(i)*ftaxrate/200);
				sum5 = sum5+lrtable.get(19).get(i)*ftaxrate/200;
			}else if(start>=7 && lrtable.get(19).get(i)>0){
				ret5.add(lrtable.get(19).get(i)*ftaxrate/100);
				sum5 = sum5+lrtable.get(19).get(i)*ftaxrate/100;
			}else{
				ret5.add(0.0);
			}
			
			ret6.add(ret3.get(i)-ret5.get(i));
			
			if(i==0){
				ret7.add(0.0);
			}else if(i==1){
				ret7.add(ret6.get(1));
			}else{
				ret7.add(ret6.get(i)+ret7.get(i-1));
			}
		}
		ret5.set(0, sum5);
		ret6.set(0, ret3.get(0)-ret5.get(0));
		
		rettable.add(ret1);
		rettable.add(ret11);
		rettable.add(ret12);
		rettable.add(ret13);
		rettable.add(ret14);
		rettable.add(ret2);
		rettable.add(ret21);
		rettable.add(ret22);
		rettable.add(ret23);
		rettable.add(ret24);
		rettable.add(ret3);
		rettable.add(ret4);
		rettable.add(ret5);
		rettable.add(ret6);
		rettable.add(ret7);
		
		return rettable;
	}
}
