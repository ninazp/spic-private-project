package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class FlowLoanHander {

	/**
	 * 流动借款 
	 * @param changejk 流动借款
	 * @param loanrate 利息
	 * @param returnnum 还利息次数
	 * @return 还本付息
	 */
	public static List<List<Double>> getlanghbfx(int year,Double flowloan,Double loanrate,Double returnnum,Double totalcalyears){
		List<List<Double>>  retlst = new ArrayList<List<Double>>();
		List<Double> flowloanl = new ArrayList<Double>();
		flowloanl.add(0.0);
		List<Double> flowloansum = new ArrayList<Double>();
		flowloansum.add(0.0);
		List<Double> flowloanlx = new ArrayList<Double>();
		flowloanlx.add(0.0);
		List<Double> flowloanhk = new ArrayList<Double>();
		flowloanhk.add(0.0);
		for(int i=1;i<=totalcalyears;i++){
			if(i<year){
				flowloanl.add(0.0);
				flowloansum.add(0.0);
				flowloanlx.add(0.0);
				flowloanhk.add(0.0);
			}else if(i==year){
				flowloanl.add(flowloan);
				flowloansum.add(flowloan);
//				flowloanlx.add(e);
//				flowloanhk.add(e);
			}
		}
		
		
		
		return retlst;
	}

	
}
