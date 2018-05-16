package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;

public class ZjcctableHanderNew {

	@SuppressWarnings("unchecked")
	public static List<List<Double>> getzjcctable(
			BaseMapper baseMapper,BaseMapper baseMapperb,
			FeaProjectB projectvo, Map<String,Object> parammap){

		List<List<Double>> rettable = new ArrayList<List<Double>>();
		List<Double> r1 = new ArrayList<Double>() ;r1.add(0.0);
		List<Double> r11 = new ArrayList<Double>();r11.add(0.0);
		List<Double> r12 = new ArrayList<Double>();r12.add(0.00);
		List<Double> r13 = new ArrayList<Double>();r13.add(0.00);
		List<Double> r2 = new ArrayList<Double>();r2.add(0.00);
		List<Double> r21 = new ArrayList<Double>();r21.add(0.00);
		List<Double> r211 = new ArrayList<Double>();r211.add(0.00);
		List<Double> r212 = new ArrayList<Double>();r212.add(0.00);
		List<Double> r22 = new ArrayList<Double>();r22.add(0.00);
		List<Double> r221 = new ArrayList<Double>();r221.add(0.00);
		List<Double> r2211 = new ArrayList<Double>();r2211.add(0.00);
		List<Double> r2212 = new ArrayList<Double>();r2212.add(0.00);
		List<Double> r222 = new ArrayList<Double>();r222.add(0.00);

		//查询投资分配表
		List<Fea_investdisVO>   distrvolst = (List<Fea_investdisVO>) PubBaseDAO.
				getMutiParentVO("fea_investdis", "id", " project_id='"+projectvo.getId()+"' ", baseMapper);
		int maxindex = 1;
		for(Fea_investdisVO fvo : distrvolst){
			int startyear = (null==parammap.get("startyear"))?0:Integer.valueOf(parammap.get("startyear").toString());
			int investyear = (null==fvo.getYear())?0:Integer.valueOf(fvo.getYear());
			int investindex = investyear - startyear+1;
			if(investindex>maxindex){
				maxindex = investindex;
			}
		}
		for(int i=1;i<=maxindex;i++){
			r1.add(0.0);r11.add(0.0);r12.add(0.00);
			r13.add(0.00);r2.add(0.00);r21.add(0.00);
			r211.add(0.00);r212.add(0.00);r22.add(0.00);
			r221.add(0.00);r2211.add(0.00);r2212.add(0.00);
			r222.add(0.00);
		}
		
		Double flowamt = (Double) parammap.get("flowamt");
		//计算总投资
		Double totalamt = 0.00;
		for(Fea_investdisVO fvo : distrvolst){
			totalamt = totalamt + fvo.getInvestamt();
		}

		if(null!=distrvolst && distrvolst.size()>0){
			for(Fea_investdisVO fvo : distrvolst){
				int startyear = (null==parammap.get("startyear"))?0:Integer.valueOf(parammap.get("startyear").toString());
				int investyear = (null==fvo.getYear())?0:Integer.valueOf(fvo.getYear());
				int investindex = investyear - startyear+1;
				
				Double flowamtyear = flowamt*fvo.getInvestamt()/totalamt;//每年的流动资金比例

				List<Fea_investdisBVO>   distriblst = (List<Fea_investdisBVO>) PubBaseDAO.getMutiParentVO("fea_investdis_b", "id", " pkinvestdis='"+fvo.getId()+"' ", baseMapperb);
				if(null!=distriblst && distriblst.size()>0){
					for(Fea_investdisBVO bvo : distriblst){
						//资金筹备
						if(null!=bvo.getInvesttype() && bvo.getInvesttype().equals("1")){
							
							//先不考虑多个投资和融资机构的情况
							Double investamt = (null!=bvo.getInvestamt()?bvo.getInvestamt():0.00);
							
							r211.set(investindex,investamt);
							r212.set(investindex,flowamtyear*0.3);
							r21.set(investindex,investamt+flowamtyear*0.3);

							r211.set(0, r211.get(0)+r211.get(investindex));
							r212.set(0, r212.get(0)+r212.get(investindex));
							r21.set(0, r21.get(0)+r21.get(investindex));
						}else if(null!=bvo.getInvesttype() && bvo.getInvesttype().equals("2")){
							r2211.set(investindex,bvo.getInvestamt());//长期借款本金
							Double principalrate = Double.valueOf(parammap.get("principalrate").toString());
							
							Double jslxamt = r2211.get(investindex)*principalrate/200;
							
							r2212.set(investindex, jslxamt);//建设期利息
							r221.set(investindex, r2211.get(investindex)+r2212.get(investindex));
							r222.set(investindex, flowamtyear*0.7);
							r22.set(investindex, r221.get(investindex)+r222.get(investindex));
							
							
							r2211.set(0, r2211.get(0)+r2211.get(investindex));
							r2212.set(0, r2212.get(0)+r2212.get(investindex));
							r221.set(0, r221.get(0)+r221.get(investindex));
							r222.set(0, r222.get(0)+r222.get(investindex));
							r22.set(0,r22.get(0)+r22.get(investindex));
						}
					}
				}
				r11.set(investindex, r2211.get(investindex)+r211.get(investindex));
				r12.set(investindex, r2212.get(investindex));
				r13.set(investindex, r212.get(investindex)+r222.get(investindex));
				r1.set(investindex, r11.get(investindex)+r12.get(investindex)+r13.get(investindex));
				r2.set(investindex, r21.get(investindex)+r22.get(investindex));
				
				r11.set(0, r11.get(0)+r11.get(investindex));
				r12.set(0, r12.get(0)+r12.get(investindex));
				r13.set(0, r13.get(0)+r13.get(investindex));
				r1.set(0, r1.get(0)+r1.get(investindex));
				r2.set(0,r2.get(0)+r2.get(investindex));

			}
		}
		
		rettable.add(r1);rettable.add(r11);
		rettable.add(r12);rettable.add(r13);
		rettable.add(r2);rettable.add(r21);
		rettable.add(r211);rettable.add(r212);
		rettable.add(r22);rettable.add(r221);
		rettable.add(r2211);rettable.add(r2212);
		rettable.add(r222);
		
		return rettable;
	}




}
