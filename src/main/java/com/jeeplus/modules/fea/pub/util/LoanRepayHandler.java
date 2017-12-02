package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

public class LoanRepayHandler {

	 public static List<List<Double>> getLoanRepayTable(List<List<Double>> zjcktable,List<Double> projectinfo,
			 Double langrate,Double langyear,Double shortrate){
		 
		    List<List<Double>> interesttable = new ArrayList<List<Double>>();
		 
		    //获取长期借款金额
			List<Double> cqjkje = new ArrayList<Double>();
			for(int i=1;i<zjcktable.get(9).size();i++){
				cqjkje.add(zjcktable.get(9).get(i));
			}
			//利息表 - 长期借款的利息 怎么获取，流动借款的业务场景，短期借款的算法
			List<List<Double>> langloanlst = LangLoanHander.getlanghbfx(cqjkje, langrate, langyear, projectinfo.get(0),projectinfo.get(1));
			//流动贷款
			List<List<Double>> flowloanlst = FlowLoanHander.getflowhbfx(1, zjcktable.get(7).get(0), shortrate,projectinfo.get(0),projectinfo.get(1));
			List<List<Double>>  shortlst = ShortLoanHander.getShortLoanList(projectinfo.get(1), shortrate);
			for(int i=0;i<langloanlst.size();i++){
				interesttable.add(langloanlst.get(i));
			}
			for(int i=0;i<flowloanlst.size();i++){
				interesttable.add(flowloanlst.get(i));
			}
			for(int i=0;i<shortlst.size();i++){
				interesttable.add(shortlst.get(i));
			}
		 
			return interesttable;
	 }
	
	
}
