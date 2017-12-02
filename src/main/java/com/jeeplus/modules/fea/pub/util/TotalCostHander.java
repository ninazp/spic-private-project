package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class TotalCostHander {
	/**
	 * 总成本费用表  -  计算再利息表之后
	 * @param paramdoub：折旧固定资产原值，维修和保险固定资产原值，
	 *                   折旧年限，残值率，维修费率，
	 *                   保险费率，工资,福利，供热,泵热费，
	 *                   第一年的供热月份，总计算年限，摊销原值，摊销年限
	 * @return
	 */
	public static List<List<Double>> getTotalCost(List<Double> paramdoub,List<Double> repairrate ){
		List<List<Double>> retlistlist = new ArrayList<List<Double>>();

		Double assetVal = paramdoub.get(0);
		Double assetValwb = paramdoub.get(1);
		Double depyears = paramdoub.get(2);
		Double depleftrate = paramdoub.get(3);
		Double bxrate = paramdoub.get(4);
		Double wage = paramdoub.get(5);
		Double benefit =paramdoub.get(6);
		Double heatcost = paramdoub.get(7);
		Double brcost = paramdoub.get(8);
		Double firstmths = paramdoub.get(9);
		Double totalcalyears = paramdoub.get(10);
		Double txval = paramdoub.get(11);
		Double txvalyear = paramdoub.get(12);

		//折旧费 
		List<Double> zjlist = getdepreciation(assetVal, depyears, depleftrate,
				firstmths,totalcalyears);
		//维修费
		List<Double> wxlist = getRepairsCost(assetValwb, repairrate, firstmths, totalcalyears);
		//工资及福利
		List<Double> wagelist = getWagebenefit( wage, benefit, firstmths, totalcalyears);
		//保险费
		List<Double> bxlist = getBXcost(assetValwb, bxrate, firstmths, totalcalyears);
		//供暖费
		List<Double> heatlist = getheatbrcost( heatcost, firstmths, totalcalyears);
		//摊销
		List<Double> txlist = gettx(txval, firstmths, totalcalyears);
		//利息支出  先去计算利息表--再计算这个
		List<Double> interestlist = gettx(txval, firstmths, totalcalyears);
		//泵热费
		List<Double> brlist = getheatbrcost( brcost, firstmths, totalcalyears);

		//固定成本
		List<Double> fixlist = new ArrayList<Double>();
		for(int i=0;i<=totalcalyears;i++){
			Double doub = zjlist.get(i)+wxlist.get(i)+wagelist.get(i)+bxlist.get(i)
			+txlist.get(i)+interestlist.get(i)+brlist.get(i);
			fixlist.add(doub);
		}

		//可变成本
		List<Double> changelist = heatlist;

		//总成本
		List<Double> totallist = new ArrayList<Double>();
		for(int i=0;i<=totalcalyears;i++){
			Double doub = zjlist.get(i)+wxlist.get(i)+wagelist.get(i)+bxlist.get(i)
			+txlist.get(i)+interestlist.get(i)+brlist.get(i)+heatlist.get(i);
			totallist.add(doub);
		}

		//经营成本 = =维修费+工资及福利+保险费+取暖费+泵热费
		List<Double> salecostlist = new ArrayList<Double>();
		for(int i=0;i<=totalcalyears;i++){
			Double doub = wxlist.get(i)+wagelist.get(i)+bxlist.get(i)
			               +brlist.get(i)+heatlist.get(i);
			
			salecostlist.add(doub);
		}
		
		retlistlist.add(zjlist);
		retlistlist.add(wxlist);
		retlistlist.add(wagelist);
		retlistlist.add(bxlist);
		retlistlist.add(heatlist);
		retlistlist.add(txlist);
		retlistlist.add(interestlist);
		retlistlist.add(brlist);
		retlistlist.add(fixlist);
		retlistlist.add(changelist);
		retlistlist.add(totallist);
		retlistlist.add(salecostlist);

		return retlistlist;
	}

	/**
	 * 折旧费
	 * 参数：固定资产原值(减掉抵扣的原值=建设投资+建设期利息-可抵扣金额)，
	 *      折旧年限，残值率，总共计算年，第一年计算月份
	 * @param assetVal
	 * @param depyears
	 * @return
	 */
	public static List<Double> getdepreciation(Double assetVal,Double depyears,Double depleftrate,
			Double firstmths,Double totalcalyears){

		List<Double> retList = new ArrayList<Double>();
		Double yearval = (assetVal/depyears)*(1-depleftrate/100);
		Double sumval = 0.0;
		retList.add(sumval);
		for(int i=1;i<=totalcalyears;i++){
			Double doub1 =0.0;
			if(i==1){
				doub1 = yearval*(firstmths/12);
			}else if(i<Math.min(depyears,totalcalyears)+1){
				doub1 = yearval;
			}else if(i==Math.min(depyears,totalcalyears)+1){
				if(depyears<totalcalyears){
					doub1 = yearval-yearval*(firstmths/12);
				}else{
					doub1 = yearval;
				}
			}else if(i>Math.min(depyears, totalcalyears)+1){
				if(depyears<totalcalyears){
					doub1 = 0.0;
				}else{
					doub1 = yearval;
				}
			}
			retList.add(doub1);
			sumval = sumval+doub1;
		}
		
		retList.set(0, sumval);

		return retList;
	}
	
	/**
	 * 维修费
	 * @param assetVal
	 * @param bxrate
	 * @param firstmths
	 * @param totalcalyears
	 * @return
	 */
	public static List<Double> getRepairsCost(Double assetVal,List<Double> bxrate,Double firstmths,Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		
		
		Double sumval = 0.0;
		retlist.add(sumval);
		Double temp = 0.0;
		for(int i=1;i<=totalcalyears;i++){
			Double repval = assetVal*bxrate.get(i-1)/100;
			if(i==1){
				temp = repval*(firstmths/12);
				retlist.add(temp);
			}else{
				temp = repval;
			}
			retlist.add(temp);
			sumval = sumval+temp;
		}
		retlist.set(0, sumval);
		
		return retlist;
	}

	/**
	 * 保险费 计算方法
	 * @param assetVal 固定资产原值=建设投资+建设利息-可抵扣金额-建设利息
	 * @param repairs 保险费率
	 * @param firstmths 第一年月份
	 * @return totalyears 总计计算年份
	 */
	public static List<Double> getBXcost(Double assetVal,Double bxrate,Double firstmths,Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		Double repval = assetVal*bxrate/1000;
		
		Double sumval = 0.0;
		retlist.add(sumval);
		Double temp = 0.0;
		for(int i=1;i<=totalcalyears;i++){
			if(i==1){
				temp = repval*(firstmths/12);
			}else{
				temp = repval;
			}
			retlist.add(temp);
			sumval = sumval+temp;
		}
		retlist.set(0, sumval);
		
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
	public static List<Double> getWagebenefit(Double wage,Double benefit,Double firstmths,Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		Double wageval = wage*(1+benefit/100);
		
		Double sumval = 0.0;
		retlist.add(sumval);
		Double temp = 0.0;
		for(int i=1;i<=totalcalyears;i++){
			if(i==1){
				temp = wageval*(firstmths/12);
			}else{
				temp = wageval;
			}
			retlist.add(temp);
			sumval = sumval+temp;
		}
		retlist.set(0, sumval);
		return retlist;
	}

	/**
	 * 供暖费+泵热费
	 * @param heatcost 固定值
	 * @param firstmths
	 * @param totalcalyears
	 * @return
	 */
	public static List<Double> getheatbrcost(Double heatcost,Double firstmths,Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		Double wageval = heatcost;
		
		Double sumval = 0.0;
		retlist.add(sumval);
		
		Double temp = 0.0;
		for(int i=1;i<=totalcalyears;i++){
			if(i==1){
				temp = wageval*(firstmths/12);
			}else{
				temp = wageval;
			}
			
			retlist.add(temp);
			sumval = sumval+temp;
		}
		
		retlist.set(0, sumval);

		return retlist;
	}

	/**
	 * 摊销费
	 * @param heatcost
	 * @param firstmths
	 * @param totalcalyears
	 * @return
	 */
	public static List<Double> gettx(Double txdoub,Double firstmths,Double totalcalyears){
		List<Double> retlist = new ArrayList<Double>();
		Double sumval = 0.0;
		retlist.add(sumval);
		for(int i=1;i<=totalcalyears;i++){
			retlist.add(0.0);
		}
		return retlist;
	}

	
	public static List<Double> getrepaylx(List<List<Double>> langlst,List<List<Double>> flowlst,List<List<Double>> shortlst){
		List<Double> retlst = new ArrayList<Double>();
		for(int i = 0;i<langlst.get(0).size();i++){
			retlst.add(langlst.get(4).get(i)+flowlst.get(2).get(i)+shortlst.get(2).get(i));
		}
		return retlst;
	}

}
