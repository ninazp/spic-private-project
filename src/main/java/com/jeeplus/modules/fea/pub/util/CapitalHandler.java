package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class CapitalHandler {

	/**
	 * 1	现金流入
1.1	营业收入
1.2	配套收入
1.3	回收固定资产余值
1.4	回收流动资金
2	现金流出
2.1	建设投资
2.2	流动资金
2.3	经营成本
2.4	营业税金附加
3	所得税前净现金流量（1-2）
4	累计所得税前净现金流量
5	调整所得税
6	所得税后净现金流量（3-5）
7	累计所得税后净现金流量
	 * @param totalyears
	 * @param lrtable
	 * @param costtable
	 * @param cctable
	 * @param loanrepay
	 * @return
	 */

	public static List<List<Double>> getFinanceTable(Double totalyears,List<List<Double>> lrtable,
			List<List<Double>> costtable,List<List<Double>> cctable,List<List<Double>> loanrepay
		 ,Double assetval){

		List<List<Double>>  rettable = new ArrayList<List<Double>>();

		List<Double> ret14 = new ArrayList<Double>();//
		List<Double> ret13 = new ArrayList<Double>();//
		List<Double> ret11 = new ArrayList<Double>();//
		List<Double> ret12 = new ArrayList<Double>();//
		List<Double> ret1 = new ArrayList<Double>();//
		List<Double> ret26 = new ArrayList<Double>();//
		List<Double> ret25 = new ArrayList<Double>();//
		List<Double> ret24 = new ArrayList<Double>();//
		List<Double> ret23 = new ArrayList<Double>();//
		List<Double> ret21= new ArrayList<Double>();//
		List<Double> ret22 = new ArrayList<Double>();//
		List<Double> ret2 = new ArrayList<Double>();//合计
		List<Double> ret3 = new ArrayList<Double>();//合计

		for(int i=0;i<=totalyears;i++){
			ret11.add(lrtable.get(0).get(i));//营业收入
			ret12.add(lrtable.get(10).get(i));
			
			if(i==0 || i==totalyears){
				ret13.add(cctable.get(3).get(0));
				ret14.add(assetval-costtable.get(0).get(0));
			}else{
				ret13.add(0.0);
				ret14.add(0.0);
			}

			ret1.add(ret11.get(i)+ret12.get(i)+ret13.get(i)+ret14.get(i));
			if(i>=cctable.size()){
				ret21.add(0.0);
			}else{
				ret21.add(cctable.get(5).get(i));
			}
			ret22.add(loanrepay.get(3).get(i)+loanrepay.get(9).get(i));
			ret23.add(costtable.get(6).get(i));
			ret24.add(costtable.get(11).get(i));
			ret25.add(costtable.get(1).get(i));
			ret26.add(lrtable.get(9).get(i));

			ret3.add(ret1.get(i)-ret2.get(i));
		}
		rettable.add(ret1);
		rettable.add(ret11);
		rettable.add(ret12);
		rettable.add(ret13);
		rettable.add(ret14);
		rettable.add(ret2);
		rettable.add(ret21);
		rettable.add(ret22);
		rettable.add(ret23);
		rettable.add(ret24);
		rettable.add(ret25);
		rettable.add(ret26);
		rettable.add(ret3);
		return rettable;
	}

}
