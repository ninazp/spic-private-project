package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class CapitalSrcHandler {

	/**
	 * 资金来源与运用表
	 * @param totalyears
	 * @param lrtable
	 * @param costtable
	 * @param cctable
	 * @param loanrepay
	 * @return
	 */
	public static List<List<Double>> getCapitalSrcTable(Double totalyears,List<List<Double>> lrtable,
			List<List<Double>> costtable,List<List<Double>> cctable,List<List<Double>> loanrepay
			){

		List<List<Double>>  rettable = new ArrayList<List<Double>>();

		List<Double> ret14 = new ArrayList<Double>();//
		List<Double> ret13 = new ArrayList<Double>();//
		List<Double> ret11 = new ArrayList<Double>();//
		List<Double> ret12 = new ArrayList<Double>();//
		List<Double> ret15 = new ArrayList<Double>();//
		List<Double> ret16= new ArrayList<Double>();//
		List<Double> ret17 = new ArrayList<Double>();//
		List<Double> ret18 = new ArrayList<Double>();//
		List<Double> ret19 = new ArrayList<Double>();//
		List<Double> ret10 = new ArrayList<Double>();//
		List<Double> ret1 = new ArrayList<Double>();//
		List<Double> ret27 = new ArrayList<Double>();//
		List<Double> ret26 = new ArrayList<Double>();//
		List<Double> ret25 = new ArrayList<Double>();//
		List<Double> ret24 = new ArrayList<Double>();//
		List<Double> ret23 = new ArrayList<Double>();//
		List<Double> ret21= new ArrayList<Double>();//
		List<Double> ret22 = new ArrayList<Double>();//
		List<Double> ret2 = new ArrayList<Double>();//合计
		List<Double> ret3 = new ArrayList<Double>();//合计
		List<Double> ret4 = new ArrayList<Double>();//合计

		for(int i=0;i<=totalyears;i++){
			ret11.add(lrtable.get(6).get(i));//利润总额
			ret12.add(costtable.get(0).get(i));//折旧费
			ret13.add(costtable.get(5).get(i));//摊销费
			ret14.add(loanrepay.get(0).get(i));//长期借款
			ret15.add(loanrepay.get(6).get(i));//长期借款
			ret16.add(loanrepay.get(10).get(i));//长期借款
			if(i>=cctable.get(5).size()){
				ret17.add(0.0);
			}else{
				ret17.add(cctable.get(5).get(i));
			}
			ret18.add(lrtable.get(10).get(i));
			
			if(i==0 || i==totalyears){
				ret10.add(cctable.get(3).get(0));
				ret19.add(costtable.get(0).get(0));
			}else{
				ret10.add(0.0);
				ret19.add(0.0);
			}
			
			ret1.add(ret11.get(i)+ret12.get(i)+ret13.get(i)+ret14.get(i)+
					ret15.get(i)+ret16.get(i)+ret17.get(i)+ret18.get(i)+ret19.get(i)+ret10.get(i));
			
			if(i>=cctable.get(1).size()){
				ret21.add(0.0);
				ret22.add(0.0);
				ret23.add(0.0);
			}else{
				ret21.add(cctable.get(1).get(i));
				ret22.add(cctable.get(2).get(i));
				ret23.add(cctable.get(3).get(i));
			}
			
			ret24.add(lrtable.get(11).get(i));
			ret25.add(lrtable.get(17).get(i));
			ret26.add(loanrepay.get(3).get(i)+loanrepay.get(9).get(i)+loanrepay.get(11).get(i));
			ret27.add(0.0);
			ret2.add(ret21.get(i)+ret22.get(i)+ret23.get(i)+ret24.get(i)+ret25.get(i)+ret26.get(i)+
					ret27.get(i));
			
			ret3.add(ret1.get(i)-ret2.get(i));
			
			if(i==0){
				ret4.add(0.0);
			}else if(i==1){
				ret4.add(ret3.get(i));
			}else {
				ret4.add(ret3.get(i)+ret4.get(i-1));
			}

			
		}
		rettable.add(ret1);
		rettable.add(ret11);
		rettable.add(ret12);
		rettable.add(ret13);
		rettable.add(ret14);
		rettable.add(ret15);
		rettable.add(ret16);
		rettable.add(ret17);
		rettable.add(ret18);
		rettable.add(ret19);
		rettable.add(ret10);
		rettable.add(ret2);
		rettable.add(ret21);
		rettable.add(ret22);
		rettable.add(ret23);
		rettable.add(ret24);
		rettable.add(ret25);
		rettable.add(ret26);
		rettable.add(ret27);
		rettable.add(ret3);
		rettable.add(ret4);
		return rettable;
	}

}
