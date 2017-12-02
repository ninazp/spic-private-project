package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class ShortLoanHander {

	public static List<List<Double>> getShortLoanList(Double totalcalyears,Double loanrate){
		List<List<Double>> retlst = new ArrayList<List<Double>>();
		List<Double> loanlst =  getShortLoan(totalcalyears);
		
		//借款金额往后挪一位就是还款列表
		List<Double> repaylst = new ArrayList<Double>();
		repaylst.add(loanlst.get(0));
		for(int i=0;i<totalcalyears;i++){
			if(i==totalcalyears-1){
				repaylst.add(0.0);
			}else if(i==0){
				repaylst.add(0.0);
			}else{
				repaylst.add(loanlst.get(i));
			}
		}
		
		
		List<Double> interestlst = new ArrayList<Double>();
		for(int i=0;i<=totalcalyears;i++){
			if(null!=loanlst && null!=loanlst && loanlst.get(i)>0){
				Double temp = loanlst.get(i)*loanrate/100;
				interestlst.add(temp);
			}else{
				interestlst.add(0.0);
			}
		}
		
		retlst.add(loanlst);
		retlst.add(repaylst);
		retlst.add(interestlst);
		return retlst;
	}
	
	public static List<Double> getShortLoan(Double totalcalyears){
		List<Double> retdouble = new ArrayList<Double>();
		retdouble.add(0.0);
		Double loansum = 0.0;
		
		Double [] td = new Double[]{48.79,74.06,96.70,114.38,126.87,136.78,153.32,169.72,185.73,79.05};
		
		for(int i=0;i<totalcalyears;i++){
			if(i==0){
				retdouble.add(28.8);
				loansum = loansum+28.8;
			}else{
				retdouble.add(0.0);
			}
		}
		for(int j=0;j<td.length;j++){
			retdouble.set(j+7, td[j]);
			loansum =loansum+td[j];
		}
		
		retdouble.set(0, loansum);
		
		return retdouble;
	}
	
	
}
