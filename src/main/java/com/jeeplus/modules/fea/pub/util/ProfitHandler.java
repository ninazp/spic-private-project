package com.jeeplus.modules.fea.pub.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.jeeplus.modules.fea.entity.costinfo.Fea_costinfoVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.subsidy.Fea_incosubsidyVO;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;

public class ProfitHandler {

	public static List<List<Double>> getprofittable(List<Double> projectinfo,
			List<List<Double>> totalcosttable,List<List<Double>> loanrepay,
			Fea_costinfoVOMapper fea_costinfoVOMapper,FeaProjectB projectvo,
			Double price,Double ftaxrate,Double shortrate1,
			Double gjjrate,Double lrfpje,
			List<Double> subincome){
		//利润表
		//利润表与利润分配表
		List<List<Double>> profittable = new ArrayList<List<Double>>();
		
		List<Fea_costinfoVO>   fea_costinfovo = (List<Fea_costinfoVO>) PubBaseDAO.
				getMutiParentVO("fea_costinfo", "id", " projectcode='B0001' ",
						 fea_costinfoVOMapper);
		List<Double> costrate = new ArrayList<Double>();	
		for(Fea_costinfoVO fcvo : fea_costinfovo){
			if(fcvo.getCostype().contains("入住率") || fcvo.getCostype().equals("1")){
				for(int j=0;j<projectinfo.get(1);j++){
					try {
						Method m ;
						if(j==0){
							m = fcvo.getClass().getMethod("getYear");
						}else{
							m = fcvo.getClass().getMethod("getYear"+(j+1));
						}
						Double rated = (Double) m.invoke(fcvo);
						costrate.add(rated);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}

		List<Double> profit1 = ProfitHandler.getincome(costrate, projectinfo.get(3),price, projectinfo.get(1));
		List<Double> profit2 = ProfitHandler.gettax(0.0, projectinfo.get(1));
		List<Double> profit21 = ProfitHandler.gettax(0.0, projectinfo.get(1));
		List<Double> profit22 = ProfitHandler.gettax(0.0, projectinfo.get(1));
		List<Double> profit4 = ProfitHandler.gettax(0.0, projectinfo.get(1));//补贴含税
		
		List<Double> profit3 = new ArrayList<Double>();//总成本费用
		List<Double> profit5 = new ArrayList<Double>();
		List<Double> profit6 = new ArrayList<Double>();;
		List<Double> profit7 = new ArrayList<Double>();
		List<Double> profit8 = new ArrayList<Double>();
		List<Double> profit9 = subincome;//补贴收入
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
		
		int miancount = 0;
		int dcount = 0;
		List<Double> profit5bak = new ArrayList<Double>();
		for(int i=0;i<totalcosttable.get(10).size();i++){
			if(i==0){
			  profit3.add(0.0);profit5.add(0.0);profit6.add(0.0);profit7.add(0.0);
			  profit8.add(0.0);profit9.add(0.0);profit10.add(0.0);profit11.add(0.0);profit12.add(0.0);
			  profit13.add(0.0);profit14.add(0.0);profit15.add(0.0);profit16.add(0.0);profit17.add(0.0);
			  profit18.add(0.0);profit19.add(0.0);
			}else{
				profit3.add(totalcosttable.get(10).get(i));//总成本费用
				
				profit5.add(profit1.get(i)-profit2.get(i)-profit3.get(i)+profit4.get(i));
				profit5bak.add(profit1.get(i)-profit2.get(i)-profit3.get(i)+profit4.get(i));
				//弥补以前年度
				if(profit5.get(i)>0){
					 profit6.add(0.0);
					 Double lramt = profit5.get(i);
					 for(int j=i-5;(j<i && j>=1 && profit5bak.get(j)<0);j++){
						 lramt = lramt+profit5bak.get(j);
						 if(lramt<=0){
							 profit6.add(profit5.get(i));
							 profit5bak.set(j, lramt);
						 }else if(lramt>0){
							 profit6.add(profit5.get(i)-lramt);
							 profit5bak.set(j, 0.0);
						 }
					 }
				}else{
					profit6.add(0.0);
				}
				
				//所得额及所得税
				profit7.add(profit5.get(i)-profit6.get(i));
				if(profit7.get(i)>0){
					if(dcount<3){
						profit8.add(0.0);
					}else if(miancount<3){
						profit8.add(profit7.get(i)*ftaxrate/200);
						miancount++;
					}else{
						profit8.add(profit7.get(i)*ftaxrate/100);
					}
					dcount = dcount+1;
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
				  profit13.add(profit10.get(i)*gjjrate/100);
				}else{
					profit13.add(0.0);
				}
				profit14.add(profit12.get(0)-profit13.get(i));
				profit15.add(0.0);
				if(profit14.get(i)>0){
					if(profit14.get(i)>=profit14.get(i)){
						profit16.add(lrfpje);
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
			  profit9.set(0,profit9.get(0)+profit9.get(i));
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
	
	public static List<Double> getsubincome(Fea_incosubsidyVOMapper fea_incosubsidyVOMapper,FeaProjectB projectbvo,List<Double> projectinfo){
		List<Fea_incosubsidyVO>   fea_incosubsidyvo = (List<Fea_incosubsidyVO>) PubBaseDAO.
				getMutiParentVO("fea_incosubsidy", "id", " projectcode='"+projectbvo.getProjectCode()+"' ",
						fea_incosubsidyVOMapper);
		List<Double> retlst = new ArrayList<Double>();
		for(int i=0;i<fea_incosubsidyvo.size();i++){
			if(fea_incosubsidyvo.get(i).getSubsidytype().equals("配套费")){
				Double sums = 0.0;
				retlst.add(0.0);
				for(int j=0;j<projectinfo.get(1);j++){
					try {
						Method m ;
						m = fea_incosubsidyvo.get(i).getClass().getMethod("getYear"+(j+1));
						Object rated = m.invoke(fea_incosubsidyvo.get(i));
						if(rated!=null && rated.toString().length()>0){
							retlst.add(new Double(rated.toString()));
							sums = sums+new Double(rated.toString());
						}else{
							retlst.add(0.0);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				retlst.set(0, sums);
			}
		}
		return retlst;
	}
}
