package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class FinanceShortHandler {

	public static List<Double> getFinanceTable(List<List<Double>> financeTable,Double shortrate){

		List<Double> flowmnysum = financeTable.get(33);
		List<Double> retlst = new ArrayList<Double>();
		retlst.add(0.00);
		int start = 0;
		int start2=0;
		for(int i=1;i<flowmnysum.size();i++){//0经营活动净现金流量13 投资活动净现金流量  19 筹资活动净现金流量
			Double financedoub = flowmnysum.get(i);
			if(financedoub<0){
				Double shortamt = Math.abs(financedoub)/(1-(shortrate/100));
				if(retlst.get(i-1)>0 && start>2){
					if(financeTable.get(32).get(i)+financeTable.get(30).get(i)<0 
							&& financeTable.get(30).get(i)>0 && start2<2
							){
						shortamt = Math.abs(financedoub)/(1-(shortrate/100));
						shortamt = shortamt+((Math.abs(financedoub)/(1-(shortrate/100)))+financedoub)/(1-(shortrate/100));
						shortamt = shortamt/0.75;
						start2++;
					}else if(start2<1){
						shortamt = shortamt + (flowmnysum.get(i-1)+retlst.get(i-1))/(1-(shortrate/100));
					}
				}
				retlst.add(shortamt);
				start++;
			}else{
				retlst.add(0.00);
			}
		}
		Double sumamt = 0.00;
		for(int i=1;i<retlst.size();i++){
			sumamt = retlst.get(i)+sumamt;
		}
		retlst.set(0, sumamt);
		
		return retlst;
	}




}
