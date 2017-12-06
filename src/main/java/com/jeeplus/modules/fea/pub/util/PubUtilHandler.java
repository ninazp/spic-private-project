package com.jeeplus.modules.fea.pub.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PubUtilHandler {

	public static List<List<Double>> getRoundingTable(List<List<Double>> intable){
		List<List<Double>> outtable = new ArrayList<List<Double>>();
		
		for(int i=0;i<intable.size();i++){
            List<Double> outt = new ArrayList<Double>();
            for(int j=0;j<intable.get(i).size();j++){
            	BigDecimal   b =   new BigDecimal(intable.get(i).get(j));  
            	 Double   f1   =   b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
            	 outt.add(f1);
            }
            outtable.add(outt);
		}
		return outtable;
	}
	
}
