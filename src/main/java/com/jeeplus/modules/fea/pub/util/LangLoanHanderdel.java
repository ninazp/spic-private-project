package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class LangLoanHanderdel {

	/**
	 * 长期借款 
	 * @param changejk 借款 分年度
	 * @param loanrate 利息
	 * @param returnnum 还利息次数
	 * @return 还本付息
	 */
	public static List<List<Double>> getlanghbfx(int index,Double loanamt,Double loanrate,
			Double retyear,Double repaytype,Double countyear){

		List<List<Double>> retlst = new ArrayList<List<Double>>();

		List<Double> repayini = new ArrayList<Double>();
		List<Double> repaybjlx = new ArrayList<Double>();
		List<Double> repaybj = new ArrayList<Double>();
		List<Double> repaylx = new ArrayList<Double>();
		List<Double> repayfinal = new ArrayList<Double>();
		for(int i=0;i<index;i++){
			repayini.add(0.00);
			repaybj.add(0.00);
			repaylx.add(0.00);
			repaybjlx.add(0.00);
			repayfinal.add(0.00);
		}

		Double repbj = loanamt/retyear;//每期需还本金
		Double currentlx = loanamt*loanrate/600;//当期需还利息
		Double loanratefloat = loanrate/100;
		for(int i=index;i<=countyear;i++){
			if(i<index+retyear+1){
				if(i==index){
					repaybj.add(0.00);
					repaylx.add(currentlx);
					repayini.add(0.00);
					repayfinal.add(loanamt);
					repaybjlx.add(currentlx);
				}else{
					if(repaytype.doubleValue()==1){//等额本金
						repayini.add(repayfinal.get(i-1));
						repaybj.add(repbj);
						repaylx.add(repayini.get(i)*loanrate/100);
						repayfinal.add(repayini.get(i)-repaybj.get(i));
						repaybjlx.add(repaybj.get(i)+repaylx.get(i));
					}else{//等额本息
						repayini.add(repayfinal.get(i-1));
						Double bjlx = loanamt*loanratefloat*Math.pow(1+loanratefloat,(index-1))
								/((Math.pow(1+loanratefloat, (index-1)))-1);
						repaybjlx.add(bjlx);
						repaylx.add(repayini.get(i)*loanratefloat);
						repaybj.add(bjlx-repaylx.get(i));
						repayfinal.add(repayini.get(i)-repaybj.get(i));
					}
				}

				repaybj.set(0, repaybj.get(0)+repaybj.get(i));
				repaylx.set(0, repaylx.get(0)+repaylx.get(i));
				repaybjlx.set(0, repaybjlx.get(0)+repaybjlx.get(i));
			}else{
				repaybj.add(0.00);
				repaylx.add(0.00);
				repayini.add(0.00);
				repayfinal.add(0.00);
				repaybjlx.add(0.00);
			}
		}
		
		retlst.add(repayini);
		retlst.add(repaybjlx);
		retlst.add(repaybj);
		retlst.add(repaylx);
		retlst.add(repayfinal);

	return retlst;
}
	
	public static List<List<Double>> getflowhbfx(int index,Double flowamt,Double loanrate,
		Double countyear){
		List<List<Double>> retlst = new ArrayList<List<Double>>();
		
		List<Double> flowsum = new ArrayList<Double>();
		List<Double> flowlx = new ArrayList<Double>();
		List<Double> flowbj = new ArrayList<Double>();
		for(int i=0;i<index;i++){
			flowsum.add(0.00);
			flowlx.add(0.00);
			flowbj.add(0.00);
		}
		for(int i=index;i<=countyear;i++){
			flowsum.add(flowamt);
			flowlx.add(flowamt*loanrate/100);
			if(i<countyear){
			 flowbj.add(0.00);
			}else{
				flowbj.add(flowamt);
			}
			
			flowlx.set(0, flowlx.get(0)+flowlx.get(i));
			flowbj.set(0, flowbj.get(0)+flowbj.get(i));
		}
		
		retlst.add(flowsum);
		retlst.add(flowlx);
		retlst.add(flowbj);
		return retlst;
	}
}
