package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class BalanceHandler {

	
	public static List<List<Double>> getFinanceTable(Double totalyears,List<List<Double>> lrtable,
			List<List<Double>> costtable,List<List<Double>> cctable,List<List<Double>> loanrepay
			,List<List<Double>> capsrttable,Double assetval,Double dicudoub){


		List<List<Double>>  rettable = new ArrayList<List<Double>>();

		List<Double> ret15= new ArrayList<Double>();//
		List<Double> ret14 = new ArrayList<Double>();//
		List<Double> ret13 = new ArrayList<Double>();//
		List<Double> ret12 = new ArrayList<Double>();//
		List<Double> ret112 = new ArrayList<Double>();//
		List<Double> ret111 = new ArrayList<Double>();//
		List<Double> ret11 = new ArrayList<Double>();//
		List<Double> ret1 = new ArrayList<Double>();//
		List<Double> ret212 = new ArrayList<Double>();//
		List<Double> ret211 = new ArrayList<Double>();//
		List<Double> ret21= new ArrayList<Double>();//
		List<Double> ret22= new ArrayList<Double>();//
		List<Double> ret23= new ArrayList<Double>();//
		List<Double> ret24= new ArrayList<Double>();//
		List<Double> ret25= new ArrayList<Double>();//
		List<Double> ret251= new ArrayList<Double>();//
		List<Double> ret252= new ArrayList<Double>();//
		List<Double> ret253= new ArrayList<Double>();//
		List<Double> ret254= new ArrayList<Double>();//
		List<Double> ret2 = new ArrayList<Double>();//合计
		List<Double> ret3 = new ArrayList<Double>();//合计
		List<Double> ret4 = new ArrayList<Double>();//合计

		Double flowasset = 0.0;
		Double capamt = 0.0;
		Double gjjamt = 0.0;
		for(int i=1;i<=totalyears;i++){
			ret111.add(capsrttable.get(20).get(i));
			if(i<=cctable.get(3).size()){
				flowasset = flowasset+cctable.get(3).get(i);
			}
			ret112.add(flowasset);
			
			ret11.add(ret111.get(i)+ret112.get(i));
			ret12.add(cctable.get(1).get(i)+cctable.get(2).get(i));
			
			if(i==1){
				ret13.add(0-costtable.get(0).get(1));
				ret14.add(dicudoub);
			}else{
				ret13.add(assetval-costtable.get(0).get(i));
				ret15.add(dicudoub);
			}
			
			ret14.add(0.0);
			
			ret1.add(ret11.get(i)+ret12.get(i)+ret13.get(i)+ret14.get(i)+ret15.get(i));
			
			ret211.add(loanrepay.get(10).get(i));
			ret212.add(0.0);
			if(i<totalyears) ret22.add(loanrepay.get(1).get(i+1));
			ret23.add(loanrepay.get(7).get(i));
			
			ret24.add(ret21.get(i)+ret22.get(i)+ret23.get(i));
			
			if(i<=cctable.get(3).size()){
				capamt = capamt+cctable.get(5).get(i);
			}
			ret251.add(capamt);
			ret252.add(0.0);
			
			gjjamt = gjjamt+lrtable.get(14).get(i);
			ret253.add(gjjamt);
			ret254.add(lrtable.get(18).get(i));
			
			ret25.add(ret251.get(i)+ret252.get(i)+ret253.get(i)+ret254.get(i));
			
			ret2.add(ret24.get(i)+ret25.get(i));
			
			ret3.add(ret1.get(i)-ret2.get(i));
			
			ret4.add(ret24.get(i)/ret1.get(i));
			
		}
		
		rettable.add(ret1);
		rettable.add(ret11);
		rettable.add(ret111);
		rettable.add(ret112);
		rettable.add(ret12);
		rettable.add(ret13);
		rettable.add(ret14);
		rettable.add(ret15);
		rettable.add(ret2);
		rettable.add(ret21);
		rettable.add(ret211);
		rettable.add(ret212);
		rettable.add(ret22);
		rettable.add(ret23);
		rettable.add(ret24);
		rettable.add(ret25);
		rettable.add(ret251);
		rettable.add(ret252);
		rettable.add(ret253);
		rettable.add(ret254);
		rettable.add(ret3);
		rettable.add(ret4);
		
		return rettable;
	}

}
