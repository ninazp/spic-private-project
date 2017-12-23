package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BalanceHandler {

	/**
	 * 资产负债表
	 * @param totalyears
	 * @param lrtable
	 * @param costtable
	 * @param cctable
	 * @param loanrepay
	 * @param capsrttable
	 * @param assetval
	 * @param dicudoub
	 * @return
	 */
	public static List<List<Double>> getBalanceTable(List<List<Double>> lrtable,
			List<List<Double>> costtable,List<List<Double>> cctable,List<List<Double>> loanrepay
			,List<List<Double>> capsrttable,Map<String,Object> parammap){
		
		Map<Integer,Double> dkjemap = (Map<Integer, Double>) parammap.get("dkje");
		Map<Integer,Double> assetvalmap = TotalCostHander.getjsamt(cctable, dkjemap);
		List<Double> assetvallist = new ArrayList<Double>();
		for(Integer key : assetvalmap.keySet()){
			for(int i=0;i<key;i++){
				assetvallist.add(0.00);
			}
			for(int i=0;i<lrtable.get(0).size();i++){
				if(assetvallist.size()<=lrtable.get(0).size()){
				    assetvallist.add(assetvalmap.get(key));
				}else{
					assetvallist.set(i,assetvallist.get(i)+assetvalmap.get(key));
				}
			}
		}
		
		List<Double> dkjelist = new ArrayList<Double>();
		for(Integer key : dkjemap.keySet()){
			for(int i=0;i<key;i++){
				dkjelist.add(0.00);
			}
			for(int i=0;i<lrtable.get(0).size();i++){
				if(dkjelist.size()<=lrtable.get(0).size()){
					dkjelist.add(dkjemap.get(key));
				}else{
					dkjelist.set(i,dkjelist.get(i)+assetvalmap.get(key));
				}
			}
		}

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
		Double assetlost = 0.0;
		for(int i=1;i<lrtable.get(0).size();i++){
			ret111.add(capsrttable.get(20).get(i));
			if(i<cctable.get(3).size()){
				flowasset = flowasset+cctable.get(3).get(i);
			}
			ret112.add(flowasset);
			
			ret11.add(ret111.get(i-1)+ret112.get(i-1));
			if(i<cctable.get(1).size()){
			   ret12.add(cctable.get(1).get(i)+cctable.get(2).get(i));
			}else{
				ret12.add(0.0);
			}
			if(i==1){
				ret13.add(0-costtable.get(0).get(1));
				assetlost = assetlost+costtable.get(0).get(1);
				ret15.add(0.0);
			}else{
				assetlost = assetlost+costtable.get(0).get(i);
				ret13.add(assetvallist.get(i)-assetlost);
				ret15.add(dkjelist.get(i));
			}
			
			ret14.add(0.0);
			
			ret1.add(ret11.get(i-1)+ret12.get(i-1)+ret13.get(i-1)+ret14.get(i-1)+ret15.get(i-1));
			
			ret211.add(loanrepay.get(10).get(i));
			ret212.add(0.0);
			if(i<lrtable.get(0).size()-1){
				ret22.add(loanrepay.get(1).get(i+1));
			}else{
				ret22.add(0.0);
			}
			ret21.add(ret211.get(i-1)+ret212.get(i-1));
			ret23.add(loanrepay.get(7).get(i)-loanrepay.get(9).get(i));
			
			ret24.add(ret21.get(i-1)+ret22.get(i-1)+ret23.get(i-1));
			
			if(i<cctable.get(3).size()){
				capamt = capamt+cctable.get(5).get(i);
			}
			ret251.add(capamt);
			ret252.add(0.0);
			
			gjjamt = gjjamt+lrtable.get(14).get(i);
			ret253.add(gjjamt);
			ret254.add(lrtable.get(18).get(i));
			
			ret25.add(ret251.get(i-1)+ret252.get(i-1)+ret253.get(i-1)+ret254.get(i-1));
			
			ret2.add(ret24.get(i-1)+ret25.get(i-1));
			
			ret3.add(ret1.get(i-1)-ret2.get(i-1));
			
			ret4.add(ret24.get(i-1)/ret1.get(i-1));
			
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
