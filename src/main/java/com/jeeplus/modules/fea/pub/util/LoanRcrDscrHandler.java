package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class LoanRcrDscrHandler {

	public static List<List<Double>> getRcrDscrtable(List<List<Double>> lrtable, List<List<Double>> loantable){
		
		List<List<Double>> rettable = new ArrayList<List<Double>>();
		
		List<Double> rcrtable = new ArrayList<Double>();
		List<Double> dscrtable = new ArrayList<Double>();
		for(int i =0;i<lrtable.get(0).size();i++){
			if(i==0 || i==1){
				rcrtable.add(0.00);
				dscrtable.add(0.00);
			}else if(loantable.get(1).get(i)>0){
				Double beforetax = lrtable.get(19).get(i);
				Double interest = loantable.get(4).get(i)+loantable.get(8).get(i)+loantable.get(12).get(i);
				Double rcrdoub = beforetax/interest;
				rcrtable.add(rcrdoub);
				
				Double lrdoub = lrtable.get(20).get(i)-lrtable.get(9).get(i);
				Double bldoub = loantable.get(2).get(i)+loantable.get(8).get(i)+loantable.get(9).get(i)+
						loantable.get(11).get(i)+loantable.get(12).get(i);
                Double dscrdoub = lrdoub/bldoub;
                dscrtable.add(dscrdoub);
			}else{
				rcrtable.add(0.00);
				dscrtable.add(0.00);
			}
		}
		
		rettable.add(rcrtable);
		rettable.add(dscrtable);
		
		
		return rettable;
	}
	
	
}
