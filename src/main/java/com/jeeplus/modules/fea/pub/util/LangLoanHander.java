package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class LangLoanHander {

	/**
	 * 长期借款 
	 * @param changejk 借款 分年度
	 * @param loanrate 利息
	 * @param returnnum 还利息次数
	 * @return 还本付息
	 */
	public static List<List<Double>> getlanghbfx(List<Double> cqjkje,Double loanrate,Double returnnum,Double firstmths,Double totalcalyears){
		List<List<Double>>  retlst = new ArrayList<List<Double>>();

		List<Double> langloanlst = new ArrayList<Double>();
		langloanlst.add(0.0);

		//长期借款
		Double langsum = 0.0;
		for(int i=0;i<totalcalyears;i++){
			if(i<cqjkje.size()){
				langloanlst.add(cqjkje.get(i));
				langsum = langsum+cqjkje.get(i);
			}else{
				langloanlst.add(0.0);
			}
		}
		langloanlst.set(0, langsum);
		
		retlst.add(langloanlst);
		
		for(int j=0;j<cqjkje.size();j++){
			List<List<Double>> yhlxsll = getyhlxs(j, cqjkje.get(j), loanrate, returnnum, firstmths, totalcalyears);
			for(int i=0;i<yhlxsll.size();i++){
				retlst.add(yhlxsll.get(i));
			}
		}
	    
	return retlst;
}

public static List<List<Double>> getyhlxs(int year,Double langamt,Double loanrate,Double returnnum,Double firstmths,Double totalcalyears){
	List<List<Double>> retlstlst = new ArrayList<List<Double>>();
	
	List<Double> returnbj =getyhbj(year, langamt, returnnum, totalcalyears);
	
	List<Double> beginbanlance = new ArrayList<Double>();
	beginbanlance.add(0.0);
	List<Double> bjlx = new ArrayList<Double>();
	bjlx.add(0.0);
	List<Double> currentlx = new ArrayList<Double>();
	currentlx.add(0.0);
	List<Double> endbanlance = new ArrayList<Double>();
	endbanlance.add(0.0);
	
	//年初借款余额 - 第一年
	Double lxsum = 0.0;
	Double bjlxsum = 0.0;
	for(int i=0;i<totalcalyears;i++){
		if(i<year){
			beginbanlance.add(0.0);
			currentlx.add(0.0);
			bjlx.add(0.0);
			endbanlance.add(0.0);
		}else if(i==year){
			beginbanlance.add(0.0);
			Double dlx = langamt*loanrate*(firstmths/12)/100;
			currentlx.add(dlx);
			bjlx.add(dlx);
			endbanlance.add(0.0);
			
			lxsum = lxsum+dlx;
			bjlxsum = bjlxsum+dlx;
		}else if(i==(year+1)){
			beginbanlance.add(langamt);
			Double dlx = langamt*loanrate/100;
			currentlx.add(dlx);
			bjlx.add(dlx+returnbj.get(i+1));
			endbanlance.add(langamt-returnbj.get(i+1));
			
			lxsum = lxsum+dlx;
			bjlxsum = bjlxsum+dlx+returnbj.get(i+1);
		}else{
			beginbanlance.add(endbanlance.get(i));
			Double dlx = (endbanlance.get(i))*loanrate/100;
			currentlx.add(dlx);
			bjlx.add(dlx+returnbj.get(i+1));
			endbanlance.add(endbanlance.get(i)-returnbj.get(i+1));
			lxsum = lxsum+dlx;
			bjlxsum = bjlxsum+dlx+returnbj.get(i+1);
		}
	}
	bjlx.set(0, bjlxsum);
	currentlx.set(0, lxsum);
	
	retlstlst.add(beginbanlance);
	retlstlst.add(bjlx);
	retlstlst.add(returnbj);
	retlstlst.add(currentlx);
	retlstlst.add(endbanlance);
	return retlstlst;
}

public static List<Double> getyhbj(int year,Double langamt,Double returnnum,Double totalcalyears){
	List<Double> retyhbj = new ArrayList<Double>();
	retyhbj.add(0.0);
	Double dsum = 0.0;
	for(int i=0;i<totalcalyears;i++){
		if(i<=year){
			retyhbj.add(0.0);
		}else if(i<=year+returnnum && i<totalcalyears){
			retyhbj.add(langamt/returnnum);
			dsum = dsum+(langamt/returnnum);
		}else{
			retyhbj.add(0.0);
		}
	}

	retyhbj.set(0, dsum);

	return retyhbj;
}
}
