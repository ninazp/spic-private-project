package com.jeeplus.modules.fea.pub.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeeplus.modules.fea.pub.util.FinanceHandler;
import com.jeeplus.modules.fea.pub.util.FinanceShortHandler;
import com.jeeplus.modules.fea.pub.util.LoanRcrDscrHandler;
import com.jeeplus.modules.fea.pub.util.LoanRepayHandler;
import com.jeeplus.modules.fea.pub.util.ProfitHandler;
import com.jeeplus.modules.fea.pub.util.ShortLoanHander;
import com.jeeplus.modules.fea.pub.util.TotalCostHander;

public class GetBaseReportDMO {

	public static Map<String,List<List<Double>>> getbasereport(List<List<Double>> zjcktable,Map<String,Object> parammap,List<List<String>> designresult){
		Map<String,List<List<Double>>> retmap = new HashMap<String, List<List<Double>>>();

		//2总成本费用表
		List<List<Double>> totalcostfinaltable = new ArrayList<List<Double>>();
		//3利润表
		List<List<Double>> lrfinaltable = new ArrayList<List<Double>>();
		//4利息表
		List<List<Double>> interestFinaltable = new ArrayList<List<Double>>();
		//5---财务计划现金流量表
		List<List<Double>> financeplanfinaltable = new ArrayList<List<Double>>();

		Double countyear = (Double) parammap.get("countyear");
		Double shortloanrate = (Double) parammap.get("shortloanrate");

		List<List<Double>> interesttable = LoanRepayHandler.getLoanRepayTable(zjcktable, parammap);
		List<List<Double>> costtable = TotalCostHander.getTotalcosttable(parammap, interesttable, designresult);
		List<List<Double>> lrtable = ProfitHandler.getprofittable(zjcktable, costtable, interesttable, parammap);
		List<List<Double>> financeplan = FinanceHandler.getFinanceTable(countyear, lrtable, costtable, zjcktable, interesttable);

		List<Double> shortlst = FinanceShortHandler.getFinanceTable(financeplan, shortloanrate);
		List<List<Double>> retlst = ShortLoanHander.getShortLoanList(countyear, shortloanrate, shortlst);
		interesttable.set(10, retlst.get(0));
		interesttable.set(11, retlst.get(1));
		interesttable.set(12, retlst.get(2));

		//2  -- 总成本费用表
		totalcostfinaltable = TotalCostHander.getTotalcosttable(parammap, interesttable, designresult);
		//3	
		lrfinaltable = ProfitHandler.getprofittable(zjcktable, totalcostfinaltable, interesttable, parammap);
		//利息表备付率和偿付率
		List<List<Double>> RcrDscrtable = LoanRcrDscrHandler.getRcrDscrtable(lrfinaltable, interesttable);

		interesttable.set(13, RcrDscrtable.get(0));
		interesttable.set(14,RcrDscrtable.get(1));

		interestFinaltable = interesttable;

		//5---财务计划现金流量表
		financeplanfinaltable = FinanceHandler.getFinanceTable(countyear,
				lrfinaltable, totalcostfinaltable, zjcktable, interestFinaltable);

		retmap.put("lrfinaltable", lrfinaltable);
		retmap.put("totalcostfinaltable", totalcostfinaltable);
		retmap.put("financeplanfinaltable", financeplanfinaltable);
		retmap.put("interestFinaltable", interestFinaltable);

		return retmap;
	}


}
