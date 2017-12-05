package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class ShortLoanHander {

	public static List<List<Double>> getShortLoanList(Double totalcalyears,Double loanrate,List<Double> shortlst){
		List<List<Double>> retlst = new ArrayList<List<Double>>();
		List<Double> loanlst = new ArrayList<Double>();
		if(null!=shortlst){
			loanlst = shortlst;
		}else{
			loanlst =  getShortLoan(totalcalyears);
		}
		
		
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
		for(int i=0;i<totalcalyears;i++){
				retdouble.add(0.00);
		}
		return retdouble;
	}
	
	
}
