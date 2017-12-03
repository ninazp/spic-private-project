package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class FinanceHandler {

	/**
	 * 财务计划现金流量表
	 * @param totalyears
	 * @param lrtable
	 * @param costtable
	 * @param cctable
	 * @param loanrepay
	 * @return
	 */
	public static List<List<Double>> getFinanceTable(Double totalyears,List<List<Double>> lrtable,
			List<List<Double>> costtable,List<List<Double>> cctable,List<List<Double>> loanrepay){
		
		List<List<Double>>  rettable = new ArrayList<List<Double>>();
		
		List<Double> ret111 = new ArrayList<Double>();//营业收入 = 利润表的第1行
		List<Double> ret112 = new ArrayList<Double>();//增值税 = 利润表的第一行
		List<Double> ret113 = new ArrayList<Double>();//补贴收入 = 利润表的第6行
		List<Double> ret114 = new ArrayList<Double>();//其他收入
		List<Double> ret11 = new ArrayList<Double>();//流入合计
		List<Double> ret121 = new ArrayList<Double>();//经营成本 - 费用表的11
		List<Double> ret122 = new ArrayList<Double>();//进项税额
		List<Double> ret123 = new ArrayList<Double>();//附加税金
		List<Double> ret124 = new ArrayList<Double>();//增值税
		List<Double> ret125 = new ArrayList<Double>();//所得税
		List<Double> ret126 = new ArrayList<Double>();//其他流出
		List<Double> ret12 = new ArrayList<Double>();//流出合计
		List<Double> ret1 = new ArrayList<Double>();//合计
		
		List<Double> ret221 = new ArrayList<Double>();//
		List<Double> ret222 = new ArrayList<Double>();//
		List<Double> ret223 = new ArrayList<Double>();//
		List<Double> ret21= new ArrayList<Double>();//
		List<Double> ret22 = new ArrayList<Double>();//
		List<Double> ret2 = new ArrayList<Double>();//合计
		
		
		List<Double> ret311 = new ArrayList<Double>();//
		List<Double> ret312 = new ArrayList<Double>();//
		List<Double> ret313 = new ArrayList<Double>();//
		List<Double> ret314= new ArrayList<Double>();//
		List<Double> ret315 = new ArrayList<Double>();//
		List<Double> ret316 = new ArrayList<Double>();//合计
		List<Double> ret31 = new ArrayList<Double>();//合计
		
		List<Double> ret321 = new ArrayList<Double>();//
		List<Double> ret322 = new ArrayList<Double>();//
		List<Double> ret323 = new ArrayList<Double>();//
		List<Double> ret324= new ArrayList<Double>();//
		List<Double> ret32 = new ArrayList<Double>();//
		
		List<Double> ret3 = new ArrayList<Double>();//合计
		
		List<Double> ret4 = new ArrayList<Double>();//合计
		List<Double> ret5 = new ArrayList<Double>();//合计
		
		for(int i=0;i<=totalyears;i++){
			ret111.add(lrtable.get(0).get(i));
			ret112.add(0.0);
			ret113.add(lrtable.get(5).get(i));
			ret114.add(0.0);
			ret11.add(lrtable.get(0).get(i)+lrtable.get(5).get(i));
			
			ret121.add(costtable.get(10).get(i));//经营成本
			ret122.add(0.0);//进项税额
			ret123.add(lrtable.get(1).get(i));//税金
			ret124.add(0.0);//增值税
			ret125.add(lrtable.get(9).get(i));//所得税
			ret126.add(0.0);
			ret12.add(ret121.get(i)+ret122.get(i)+ret123.get(i)+ret124.get(i)+ret125.get(i)+ret126.get(i));
			ret1.add(ret11.get(i)+ret12.get(i));
			
			ret21.add(lrtable.get(10).get(i));
			if(i>=cctable.get(1).size()){
				ret221.add(0.0);
				ret222.add(0.0);
				ret22.add(0.0);
			}else{
				ret221.add(cctable.get(1).get(i));
				ret222.add(cctable.get(3).get(i));
				ret22.add(cctable.get(1).get(i)+cctable.get(3).get(i));
			}
			
		    ret223.add(0.0);
			ret2.add(ret21.get(i)-ret22.get(i));
			if(i>=cctable.get(1).size()){
				ret311.add(0.0);
				ret312.add(0.0);
				ret313.add(0.0);
			}else{
				ret311.add(cctable.get(5).get(i));
				ret312.add(cctable.get(10).get(i));
				ret313.add(cctable.get(11).get(i));
			}
			ret314.add(0.0);
			ret315.add(loanrepay.get(11).get(i));
			ret316.add(0.0);
			
			ret31.add(ret311.get(i)+ret312.get(i)+ret313.get(i)+ret314.get(i)+ret315.get(i)+ret315.get(i));
			
			ret321.add(costtable.get(6).get(i));
			ret322.add(loanrepay.get(3).get(i)+loanrepay.get(11).get(i));
			ret323.add(lrtable.get(17).get(i));
			ret324.add(0.0);
			ret32.add(ret321.get(i)+ret322.get(i)+ret323.get(i)+ret324.get(i));
			
			ret3.add(ret31.get(i)-ret32.get(i));
			
			ret4.add(ret1.get(i)+ret2.get(i)+ret3.get(i));
			
			if(i==0){
				ret5.add(0.0);
			}else if(i==1){
				ret5.add(ret4.get(i));
			}else{
				ret5.add(ret4.get(i)+ret5.get(i-1));
			}
		}
		
		rettable.add(ret1);
		rettable.add(ret11);
		rettable.add(ret111);
		rettable.add(ret112);
		rettable.add(ret113);
		rettable.add(ret114);
		rettable.add(ret12);
		rettable.add(ret121);
		rettable.add(ret122);
		rettable.add(ret123);
		rettable.add(ret124);
		rettable.add(ret125);
		rettable.add(ret126);
		rettable.add(ret2);
		rettable.add(ret21);
		rettable.add(ret22);
		rettable.add(ret221);
		rettable.add(ret222);
		rettable.add(ret223);
		rettable.add(ret3);
		rettable.add(ret31);
		rettable.add(ret311);
		rettable.add(ret312);
		rettable.add(ret313);
		rettable.add(ret314);
		rettable.add(ret315);
		rettable.add(ret316);
		
		rettable.add(ret32);
		rettable.add(ret321);
		rettable.add(ret322);
		rettable.add(ret323);
		rettable.add(ret324);
		
		rettable.add(ret4);
		rettable.add(ret5);
		
		return rettable;
	}
	
	
	
	
	
}
