package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class ProfitHandler {

	public static List<Double> getincome(List<Double> occuprate,Double heararea,Double price,Double totalyears){
		List<Double> occuplst = new ArrayList<Double>();
		occuplst.add(0.0);
		Double sumdoub = 0.0;
		for(int i=0;i<totalyears;i++){
			Double amtdoub = heararea*price*occuprate.get(i);
			occuplst.add(amtdoub);
			sumdoub = sumdoub+amtdoub;
		}
		occuplst.set(0, sumdoub);
		return occuplst;
	}
	
	public static List<Double> gettax(Double taxrate,Double totalyears){
		List<Double> occuplst = new ArrayList<Double>();
		occuplst.add(0.0);
		Double sumdoub = 0.0;
		for(int i=0;i<totalyears;i++){
			occuplst.add(0.0);
			sumdoub = sumdoub+0.0;
		}
		occuplst.set(0, sumdoub);
		return occuplst;
	}
	
	public static List<Double> getmbloss(List<Double> profit5){
		List<Double> mbloss = new ArrayList<Double>();
		mbloss.add(0.0);
	    Double sumd = 0.0;
	  
	    List<Integer> indexlst = new ArrayList<Integer>();
		for(int i=1;i<profit5.size();i++){
			if(profit5.get(i)<0){
				indexlst.add(i);
			}
			mbloss.add(0.0);
		}
		for(int keyi : indexlst){
			Double temp = profit5.get(keyi);
			for(int i=keyi+1;i<keyi+6;i++){
				if(profit5.get(i)>0 && temp<0){
					if(profit5.get(keyi)+profit5.get(i)>=0){
						
						sumd = sumd+0.0-profit5.get(keyi);
						mbloss.set(i, 0.0-profit5.get(keyi));
						profit5.set(i, profit5.get(keyi)+profit5.get(i));
						
						
						
						temp = 0.0;
						
					}else if(profit5.get(keyi)+profit5.get(i)<0){
						sumd = sumd+profit5.get(i);
						mbloss.set(i, profit5.get(i));
						profit5.set(i, 0.0);
						temp = profit5.get(keyi)+profit5.get(i);
					}
				}
			}
		}
		
		mbloss.set(0, sumd);
		
		return mbloss;
	}
	
	/**
	 * 补贴收入
	 * @param totalyears
	 * @param subamt
	 * @return
	 */
	public static List<Double> getsubincome(Double totalyears,Double subamt){
		List<Double> income = new ArrayList<Double>();
		income.add(0.0);
		Double sumd = 0.0;
		for(int i=0;i<totalyears;i++){
			if(i==1){
				income.add(subamt);
				
				sumd = sumd+subamt;
			}else{
				income.add(0.0);
			}
		}
		income.set(0, subamt);
		return income;
	}
	
	public static List<List<Double>> getprofitpart(List<Double> profit10,Double gjrate,Double proprate,Double distramt){
		List<List<Double>> profitpart = new ArrayList<List<Double>>();
		List<Double> profit11 = new ArrayList<Double>();
		List<Double> profit12 = new ArrayList<Double>();
		List<Double> profit13 = new ArrayList<Double>();
		List<Double> profit14 = new ArrayList<Double>();
		List<Double> profit15= new ArrayList<Double>();
		List<Double> profit16= new ArrayList<Double>();
		List<Double> profit17 = new ArrayList<Double>();
		
		profit11.add(0.0);profit12.add(0.0);profit13.add(0.0);profit14.add(0.0);profit15.add(0.0);
		profit16.add(0.0);profit17.add(0.0);
		
		Double sum12 = 0.0;Double sum13 = 0.0;Double sum14 = 0.0;Double sum15 = 0.0;
		Double sum16 = 0.0;
		for(int i=1;i<profit10.size();i++){
			 if(i==1){
				 profit11.add(0.0);
			 }else if(i>1){
				 profit11.add(profit17.get(i-1));
			 }
			 profit12.add(profit11.get(i)+profit10.get(i));
			 profit13.add(profit10.get(i)*gjrate);
			 profit14.add(profit12.get(i)-profit13.get(i));
			 profit15.add(0.0);
			 if(profit14.get(i)>0 && profit14.get(i)>distramt){
				 profit16.add(distramt);
				 sum16 = sum16+distramt;
			 }else if(profit14.get(i)>0){
				 profit16.add(profit14.get(i));
				 sum16 = sum16+profit14.get(i);
			 }else{
				 profit16.add(0.0);
			 }
			 profit17.add(profit14.get(i)-profit16.get(i)-profit15.get(i));
			 
			 sum12 = sum12+profit11.get(i)+profit10.get(i);
			 sum13 = sum13+profit10.get(i)*gjrate;
			 sum14 = sum14+profit12.get(i)-profit13.get(i);
		}
		profit12.set(0, sum12);
		profit13.set(0, sum13);
		profit14.set(0, sum14);
		profit15.set(0, sum15);
		profit15.set(0, sum16);
		
		profitpart.add(profit11);
		profitpart.add(profit12);
		profitpart.add(profit13);
		profitpart.add(profit14);
		profitpart.add(profit15);
		profitpart.add(profit16);
		profitpart.add(profit17);
		
		return profitpart;
	}
	
}
