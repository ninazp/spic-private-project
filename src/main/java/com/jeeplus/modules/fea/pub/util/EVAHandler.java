package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class EVAHandler {
	public static List<List<Double>> getFinanceTable(Double totalyears,List<List<Double>> lrtable,
			List<List<Double>> costtable,List<List<Double>> zcfztable){

		List<List<Double>>  rettable = new ArrayList<List<Double>>();

		List<Double> ret12 = new ArrayList<Double>();//
		List<Double> ret11 = new ArrayList<Double>();//
		List<Double> ret1 = new ArrayList<Double>();//
		List<Double> ret21= new ArrayList<Double>();//
		ret21.add(0.0);
		List<Double> ret22= new ArrayList<Double>();//
		ret22.add(0.0);
		List<Double> ret23= new ArrayList<Double>();//
		ret23.add(0.0);
		List<Double> ret2 = new ArrayList<Double>();//合计
		List<Double> ret3 = new ArrayList<Double>();//合计
		List<Double> ret4 = new ArrayList<Double>();//合计

		Double syzqyamt = 0.0;
		Double fzsumamt = 0.0;
		Double zjgcamt = 0.0;
		for(int i=0;i<=totalyears;i++){
			ret11.add(lrtable.get(11).get(i));
			ret12.add(costtable.get(6).get(i));
			ret1.add(ret11.get(i)+ret12.get(i));
			
			ret2.add(0.0);
			
			if(i>0){
			  ret21.add(zcfztable.get(15).get(i-1));
			  syzqyamt = syzqyamt+zcfztable.get(15).get(i-1);
			
			  ret22.add(zcfztable.get(14).get(i-1));
			  fzsumamt = fzsumamt+zcfztable.get(14).get(i-1);
			
			  ret23.add(zcfztable.get(4).get(i-1));
			  zjgcamt = zjgcamt+zcfztable.get(4).get(i-1);
			}
			ret3.add(ret1.get(i));
			
			if(i==0){
				ret4.add(0.0);
			}else if(i==1){
				ret4.add(ret3.get(i));
			}else{
				ret4.add(ret3.get(i)-ret4.get(i-1));
			}
		}
		
		rettable.add(ret1);
		rettable.add(ret11);
		rettable.add(ret12);
		rettable.add(ret2);
		rettable.add(ret21);
		rettable.add(ret22);
		rettable.add(ret23);
		ret21.set(0, syzqyamt);
		ret22.set(0, fzsumamt);
		ret23.set(0, zjgcamt);
		
		rettable.add(ret3);
		rettable.add(ret4);
		
		return rettable;
	}

}
