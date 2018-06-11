package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfitHandler {

	@SuppressWarnings("unchecked")
	public static List<List<Double>> getprofittable(List<List<Double>> zjcktable,
			List<List<Double>> totalcosttable,List<List<Double>> loanrepay,
			Map<String,Object> parammap){

		//补贴收入
		List<Double> subincome = (List<Double>) parammap.get("income");
		Double countyear = (Double) parammap.get("countyear");
		Double incomerate = (Double) parammap.get("incomerate");//所得税税率
		Double legalaccfund = (Double) parammap.get("legalaccfund");//法定盈余公积金比率
		Double yflrprop = (Double) parammap.get("yflrprop");//应付利润比率
		String issdssjsm = (String) parammap.get("issdssjsm");

		//利润表与利润分配表
		List<List<Double>> profittable = new ArrayList<List<Double>>();

		List<Double> profit1 = getproductincome(parammap);
		List<Double> profit2 = gettax(0.0,countyear);
		List<Double> profit21 = gettax(0.0, countyear);
		List<Double> profit22 = gettax(0.0, countyear);
		List<Double> profit4 = subincome;//gettax(0.0, countyear);//补贴收入

		List<Double> profit3 = new ArrayList<Double>();//总成本费用
		List<Double> profit5 = new ArrayList<Double>();
		List<Double> profit6 = new ArrayList<Double>();;
		List<Double> profit7 = new ArrayList<Double>();
		List<Double> profit8 = new ArrayList<Double>();
		List<Double> profit9 = gettax(0.0, countyear);//补贴含税
		List<Double> profit10 = new ArrayList<Double>();
		List<Double> profit11 = new ArrayList<Double>();
		List<Double> profit12 = new ArrayList<Double>();
		List<Double> profit13 = new ArrayList<Double>();
		List<Double> profit14 = new ArrayList<Double>();
		List<Double> profit15= new ArrayList<Double>();
		List<Double> profit16= new ArrayList<Double>();
		List<Double> profit17 = new ArrayList<Double>();
		List<Double> profit18 = new ArrayList<Double>();
		List<Double> profit19 = new ArrayList<Double>();

		Double zjckamt = 0.00;
		int miancount = 0;
		int dcount = 0;
		List<Double> profit5bak = new ArrayList<Double>();
		for(int i=0;i<totalcosttable.get(10).size();i++){
			if(i==0){
				profit3.add(0.0);profit5.add(0.0);profit6.add(0.0);profit7.add(0.0);
				profit8.add(0.0);profit10.add(0.0);profit11.add(0.0);profit12.add(0.0);
				profit13.add(0.0);profit14.add(0.0);profit15.add(0.0);
				profit16.add(0.0);profit17.add(0.0);
				profit18.add(0.0);profit19.add(0.0);profit5bak.add(0.0);
			}else{
				profit3.add(totalcosttable.get(10).get(i));//总成本费用

				profit5.add(profit1.get(i)-profit2.get(i)-profit3.get(i)+profit4.get(i));
				profit5bak.add(profit1.get(i)-profit2.get(i)-profit3.get(i)+profit4.get(i));
				//弥补以前年度
				if(profit5.get(i)>0){
					Double lramt = profit5.get(i);
					boolean fortrue = false;
					for(int j=i-5;(j<i && j>=1 && profit5bak.get(j)<0);j++){
						fortrue = true;
						lramt = lramt+profit5bak.get(j);
						if(lramt<=0){
							if(i<profit6.size() && profit6.get(i)>0 ){
								profit6.set(i,profit5.get(i));
							}else{
								profit6.add(profit5.get(i));
							}
							profit5bak.set(j, lramt);
							break;
						}else if(lramt>0){
							if(i<profit6.size() && profit6.get(i)>0){
								profit6.set(i,profit5.get(i)-lramt);
							}else{
								profit6.add(profit5.get(i)-lramt);
							}

							profit5bak.set(j, 0.0);
						}
					}
					if(!fortrue) profit6.add(0.0);
				}else{
					profit6.add(0.0);
				}

				//所得额及所得税
				if(profit5.get(i)-profit6.get(i)>0){
					profit7.add(profit5.get(i)-profit6.get(i));
				}else{
					profit7.add(0.0);
				}
				if(profit7.get(i)>0){
					if(issdssjsm.equals("1")){
						if(dcount<3){
							profit8.add(0.0);
						}else if(miancount<3){
							profit8.add(profit7.get(i)*incomerate/200);
							miancount++;
						}else{
							profit8.add(profit7.get(i)*incomerate/100);
						}
						dcount = dcount+1;
					}else{
						profit8.add(profit7.get(i)*incomerate/100);
					}
				}else{
					profit8.add(0.0);
				}
				profit10.add(profit5.get(i)-profit8.get(i)+profit9.get(i));//净利润
				if(i==1) {//期初未分配
					profit11.add(0.0);
				}else{
					profit11.add(profit17.get(i-1));
				}
				profit12.add(profit10.get(i)+profit11.get(i));
				if(profit10.get(i)>0){//法定公积金
					profit13.add(profit10.get(i)*legalaccfund/100);
				}else{
					profit13.add(0.0);
				}
				profit14.add(profit12.get(i)-profit13.get(i));
				profit15.add(0.0);
				if(profit14.get(i)>0){
					if(i<zjcktable.get(5).size()){
						for(int j=i;j<zjcktable.get(5).size();j++){
							zjckamt = zjckamt + zjcktable.get(5).get(j);
						}
					}else{
						zjckamt = zjcktable.get(5).get(0);
					}
					Double yflramt = zjckamt*yflrprop/100;
					if(profit14.get(i)>=yflramt){//可分配利润大于出资金额的10%，只需要分可分配金额就行了
						profit16.add(yflramt);
					}else{
						profit16.add(profit14.get(i));
					}
				}else{
					profit16.add(0.0);
				}
				profit17.add(profit14.get(i)-profit15.get(i)-profit16.get(i));

				profit18.add(profit5.get(i)+totalcosttable.get(6).get(i));
				profit19.add(profit18.get(i)+totalcosttable.get(0).get(i));
			}

			profit3.set(0,profit3.get(0)+profit3.get(i));
			profit5.set(0,profit5.get(0)+profit5.get(i));
			profit6.set(0,profit6.get(0)+profit6.get(i));
			profit7.set(0,profit7.get(0)+profit7.get(i));
			profit8.set(0,profit8.get(0)+profit8.get(i));
			profit10.set(0,profit10.get(0)+profit10.get(i));
			profit12.set(0,profit12.get(0)+profit12.get(i));
			profit13.set(0,profit13.get(0)+profit13.get(i));
			profit14.set(0,profit14.get(0)+profit14.get(i));
			profit15.set(0,profit15.get(0)+profit15.get(i));
			profit16.set(0,profit16.get(0)+profit16.get(i));
			profit18.set(0,profit18.get(0)+profit18.get(i));
			profit19.set(0,profit19.get(0)+profit19.get(i));
		}

		profittable.add(profit1);profittable.add(profit2);profittable.add(profit21);profittable.add(profit22);
		profittable.add(profit3);profittable.add(profit4);profittable.add(profit5);profittable.add(profit6);
		profittable.add(profit7);profittable.add(profit8);profittable.add(profit9);profittable.add(profit10);
		profittable.add(profit11);profittable.add(profit12);profittable.add(profit13);profittable.add(profit14);
		profittable.add(profit15);profittable.add(profit16);profittable.add(profit17);profittable.add(profit18);
		profittable.add(profit19);

		return profittable;
	}


/**
 * 入住面积
 * @param parammap
 * @return
 */
	public static List<Double> getproductincome(Map<String,Object> parammap){
		List<Double> retlist = new ArrayList<Double>();
		List<Double> occupancylst = (List<Double>) parammap.get("occupancy");
		Double price = (Double) parammap.get("price");
		Double countyear = (Double) parammap.get("countyear");
		retlist.add(0.0);
		for(int i=0;i<countyear;i++){
			Double repval = price*occupancylst.get(i);
			retlist.add(repval);
			retlist.set(0, retlist.get(0)+repval);
		}
		if(null!=parammap.get("otherpro")) {
			List<Double> otherpro = (List<Double>) parammap.get("otherpro");
			for(int i = 1;i<countyear;i++) {
				if(null!=otherpro.get(i-1)) {
					retlist.set(i,retlist.get(i)+otherpro.get(i-1));
					retlist.set(0, retlist.get(0)+retlist.get(i));
				}
			}
		}
		return retlist;
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


}
