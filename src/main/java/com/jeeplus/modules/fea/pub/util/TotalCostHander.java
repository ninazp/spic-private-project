package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalCostHander {


	public static List<List<Double>> getTotalcosttable(
			Map<String,Object> parammap,
			List<List<Double>> interestTable,List<List<String>> designres){

		List<List<Double>> totaltable = new ArrayList<List<Double>>();

		totaltable = getbaseCost(parammap,interestTable,designres);

		List<List<Double>> rettable = getcombineCost(totaltable, Double.valueOf(parammap.get("countyear").toString()));

		return rettable;
	}

	/**
	 * 总成本费用表  -  计算再利息表之后
	 * @param costparam：折旧固定资产原值，维修和保险固定资产原值，
	 *                   折旧年限，残值率，维修费率，
	 *                   保险费率，工资,福利，供热,泵热费，
	 *                   第一年的供热月份，总计算年限，摊销原值，摊销年限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<List<Double>> getbaseCost(Map<String,Object> parammap ,
			List<List<Double>> interesttable,List<List<String>> designresult){
		List<List<Double>> retlistlist = new ArrayList<List<Double>>();

		Double countyear = (Double) parammap.get("countyear");
		Double currentproductmonth = (Double) parammap.get("currentproductmonth");

		List<Double> repairrate = (List<Double>) parammap.get("repairrate");
		List<Double> costparam = (List<Double>) parammap.get("costparam");
		List<Double> person = (List<Double>) parammap.get("person");
		List<Double> heatcostlst = (List<Double>) parammap.get("heatcost");
		List<Double> othercostlst = (List<Double>) parammap.get("othercost");
		Double dkjeamt = (Double) parammap.get("dkjeamt");

		//折旧年限，残值率，保险费率，工资,福利，供热,泵热费
		Double depyears = costparam.get(0);
		Double depleftrate = costparam.get(1);
		Double insurance = costparam.get(2);
		Double perwage = costparam.get(3);
		Double welfare =costparam.get(4);
		
		String jsamttotal = designresult.get(1).get(6);
		String othertotal = designresult.get(1).get(5);
		Double totaljsamt = 0.00;
		if(null!=jsamttotal) totaljsamt = Double.valueOf(jsamttotal);
		if(null!=othertotal) totaljsamt = totaljsamt - Double.valueOf(othertotal);

		Map<Integer,Double> assetValmap=  new HashMap<Integer,Double>();
		
		assetValmap.put(1, totaljsamt);
		
		parammap.put("depreciation", totaljsamt);
		
		Map<Integer,Double> assetValnolxmap=  new HashMap<Integer,Double>();

		assetValnolxmap.put(1, totaljsamt);

		//折旧费  
		List<Double> zjlist = getdepreciation(assetValmap, depyears, depleftrate,
				currentproductmonth,countyear);
		//维修费
		List<Double> wxlist = getRepairsCost(repairrate, countyear);
		//工资及福利 
		List<Double> wagelist = getWagebenefit( person, perwage,welfare, currentproductmonth, countyear);
		//保险费 
		List<Double> bxlist = getBXcost(assetValnolxmap, insurance, currentproductmonth, countyear);
		//供暖费
		List<Double> heatlist = getheatbrcost(heatcostlst, currentproductmonth, countyear);
		//摊销
		List<Double> txlist = gettx( countyear);
		//其他费用
		List<Double> otherlist = gettx( countyear);
		if(null!=othercostlst && othercostlst.size()==countyear) {
			otherlist = getRepairsCost(othercostlst, countyear);
		}
		//利息支出  先去计算利息表--再计算这个
		List<Double> interestlist = getrepaylx(interesttable);

		retlistlist.add(zjlist);
		retlistlist.add(wxlist);
		retlistlist.add(wagelist);
		retlistlist.add(bxlist);
		retlistlist.add(heatlist);
		retlistlist.add(txlist);
		retlistlist.add(interestlist);
		retlistlist.add(otherlist);

		return retlistlist;
	}

	/**
	 *   retlistlist.add(zjlist);
		retlistlist.add(wxlist);
		retlistlist.add(wagelist);
		retlistlist.add(bxlist);
		retlistlist.add(heatlist);
		retlistlist.add(txlist);
		retlistlist.add(interestlist);
	 * @param retlistlist
	 * @param totalcalyears
	 * @return
	 */
	public static List<List<Double>> getcombineCost(List<List<Double>> retlst,Double totalcalyears){

		//固定成本
		List<Double> fixlist = new ArrayList<Double>();
		for(int i=0;i<=totalcalyears;i++){
			Double doub = retlst.get(0).get(i)+retlst.get(1).get(i)+retlst.get(2).get(i)+retlst.get(3).get(i)
					+retlst.get(5).get(i)+retlst.get(6).get(i);
			fixlist.add(doub);
		}

		//可变成本
		List<Double> changelist = retlst.get(4);

		//总成本
		List<Double> totallist = new ArrayList<Double>();
		for(int i=0;i<=totalcalyears;i++){
			Double doub = retlst.get(0).get(i)+retlst.get(1).get(i)+retlst.get(2).get(i)+retlst.get(3).get(i)
					+retlst.get(4).get(i)+retlst.get(5).get(i)+retlst.get(6).get(i);
			totallist.add(doub);
		}

		//经营成本 = =维修费+工资及福利+保险费+取暖费+泵热费
		List<Double> salecostlist = new ArrayList<Double>();
		for(int i=0;i<=totalcalyears;i++){
			Double doub = retlst.get(1).get(i)+retlst.get(2).get(i)+retlst.get(3).get(i)
					+retlst.get(4).get(i)+retlst.get(7).get(i);

			salecostlist.add(doub);
		}

		retlst.add(fixlist);
		retlst.add(changelist);
		retlst.add(totallist);
		retlst.add(salecostlist);

		return retlst;
	}


	/**
	 * 折旧费
	 * 参数：固定资产原值(减掉抵扣的原值=建设投资+建设期利息-可抵扣金额)，
	 *      折旧年限，残值率，总共计算年，第一年计算月份
	 * @param assetVal
	 * @param depyears
	 * @return
	 */
	public static List<Double> getdepreciation(
			Map<Integer,Double> assetValmap ,Double depyears,Double depleftrate,
			Double firstmths,Double totalcalyears){

		List<Double> retList = new ArrayList<Double>();
		retList.add(0.0);

		for(Integer key : assetValmap.keySet()){
			Double yearval = (assetValmap.get(key)/depyears)*(1-depleftrate/100);

			if(retList.size()==1){
				for(int i=0;i<key-1;i++){
					retList.add(0.00);
				}
			}
			for(int i=key;i<=totalcalyears;i++){
				Double doub1 =0.0;
				if(i==key){
					doub1 = yearval*(firstmths/12);
				}else if(i<Math.min(depyears+key,totalcalyears)){
					doub1 = yearval;
				}else if(i==Math.min(depyears+key,totalcalyears)){
					if((depyears+key)<totalcalyears){
						doub1 = yearval-yearval*(firstmths/12);
					}else{
						doub1 = yearval;
					}
				}
				if(retList.size()<(totalcalyears+1)){
					retList.add(doub1);
				}else{
					retList.set(i, retList.get(i)+doub1);
				}

				retList.set(0, retList.get(0)+doub1);
			}
		}
		return retList;
	}

	public static Map<Integer,Double> getjsamt(List<List<Double>> zjcktable,Double dkjeamt){
		Map<Integer,Double>  retmap = new HashMap<Integer, Double>();
		for(int i=1;i<zjcktable.get(0).size();i++){
			Double amt = zjcktable.get(1).get(i)+zjcktable.get(2).get(i);
			retmap.put(i, amt-dkjeamt);
		}
		return retmap;
	}

	public static Map<Integer,Double> getjsamtnolx(List<List<Double>> zjcktable,Double dkjeamt){
		Map<Integer,Double>  retmap = new HashMap<Integer, Double>();
		for(int i=1;i<zjcktable.get(0).size();i++){
			Double amt = zjcktable.get(1).get(i);
			retmap.put(i, amt-dkjeamt);
		}
		return retmap;
	}

	/**
	 * 维修费
	 * @param assetVal
	 * @param bxrate
	 * @param firstmths
	 * @param totalcalyears
	 * @return
	 */
	public static List<Double> getRepairsCost(List<Double> repairrate,Double totalcalyears){

		List<Double> retlist = new ArrayList<Double>();
		retlist.add(0.0);

		for(int i=0;i<repairrate.size();i++){
			Double repval = repairrate.get(i);
			retlist.add(repval);
			retlist.set(0, retlist.get(0)+repval);
		}
		return retlist;
	}

	/**
	 * 保险费 计算方法
	 * @param assetVal 固定资产原值=建设投资+建设利息-可抵扣金额-建设利息
	 * @param repairs 保险费率
	 * @param firstmths 第一年月份
	 * @return totalyears 总计计算年份
	 */
	public static List<Double> getBXcost(Map<Integer,Double> assetnolxmap,Double bxrate,Double firstmths,Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		retlist.add( 0.00);

		for(Integer key : assetnolxmap.keySet()){
			if(retlist.size()==1){
				for(int i=0;i<key-1;i++){
					retlist.add(0.00);
				}
			}
			for(int i=key;i<=totalcalyears;i++){
				Double bxval = assetnolxmap.get(key)*bxrate/100;
				Double amt = 0.00;
				if(i==key){
					amt = bxval*(firstmths/12);
				}else{
					amt = bxval;
				}
				if(retlist.size()-1<totalcalyears){
					retlist.add(amt);
				}else{
					retlist.set(i, retlist.get(i)+amt);
				}
				retlist.set(0, retlist.get(0)+amt);
			}
		}

		return retlist;
	}
	/**
	 * 工资及福利
	 * @param persons 人数
	 * @param wage 年工资（万元）
	 * @param benefit 福利及其他（%）
	 * @param firstmths 第一年有收入的月份
	 * @param totalcalyears 计算总年份
	 * @return
	 */
	public static List<Double> getWagebenefit(List<Double> person,Double perwage,Double benefit,Double firstmths,Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		retlist.add(0.00);
		Double personnum = 0.00;
		for(int i=1;i<=totalcalyears;i++){

			Double amt = 0.00;
			if(personnum!=person.get(i-1)){
				Double wage1 = (person.get(i-1)-personnum)*perwage*(1+benefit)/100;
				Double wage = personnum*perwage*(1+benefit/100);
				amt = wage1+wage;
			}else{
				amt = person.get(i-1)*perwage*(1+benefit/100);
			}
			retlist.add(amt);
			retlist.set(0, retlist.get(0)+amt);
			personnum = person.get(i-1);
		}
		return retlist;
	}

	/**
	 * 供暖费
	 * @param heatcost 固定值
	 * @param firstmths
	 * @param totalcalyears
	 * @return
	 */
	public static List<Double> getheatbrcost(List<Double> heatcostlst,Double firstmths,Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		retlist.add(0.00);

		for(int i=1;i<=totalcalyears;i++){
			Double heatval = heatcostlst.get(i-1);
			Double amt = 0.00;
			if(i==1){
				amt = heatval*(firstmths/12);
			}else{
				amt = heatval;
			}
			retlist.add(amt);
			retlist.set(0, retlist.get(0)+amt);
		}
		return retlist;
	}

	/**
	 * 摊销费
	 * @param heatcost
	 * @param firstmths
	 * @param totalcalyears
	 * @return
	 */
	public static List<Double> gettx(Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		Double sumval = 0.0;
		retlist.add(sumval);
		for(int i=1;i<=totalcalyears;i++){
			retlist.add(0.0);
		}
		return retlist;
	}

	public static List<Double> getrepaylx(List<List<Double>> interesttable){
		List<Double> retlst = new ArrayList<Double>();
		for(int i = 0;i<interesttable.get(0).size();i++){
			retlst.add(interesttable.get(4).get(i)+interesttable.get(8).get(i)+interesttable.get(12).get(i));
		}
		return retlst;
	}

}
