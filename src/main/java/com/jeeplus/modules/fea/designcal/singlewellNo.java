package com.jeeplus.modules.fea.designcal;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.jeeplus.core.service.ServiceException;
import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.entity.downhole.Fea_design_downholeVO;
import com.jeeplus.modules.fea.entity.result.Fea_design_resultVO;
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;
import com.jeeplus.modules.fea.mapper.result.Fea_design_resultVOMapper;

public class singlewellNo {

	public  static void calsinglewelNo(Fea_design_heatVO  fea_design_heatVO,
			Fea_design_downholeVO fea_design_downholeVO,
			Fea_design_transferVO fea_design_transferVO,
			List<List<Double>> pricech,
			List<List<Double>> heatpumpprice,
			List<Double> heatrate,Fea_design_resultVOMapper resultVOMapper){

		Double q = fea_design_heatVO.getHeatload();
		Double A = fea_design_heatVO.getHeatarea();
		Double E1 = fea_design_heatVO.getPowerfee();
		Double D = fea_design_heatVO.getHeatdays();
		Double T = fea_design_heatVO.getDayheathours();
		Double Hj = fea_design_heatVO.getBuildheight();

		Double  H = fea_design_downholeVO.getHoleheight();
		Double m  =fea_design_downholeVO.getFlowcount();
		Double  t1 =fea_design_downholeVO.getOutheat();
		Double  hd =fea_design_downholeVO.getWaterlevel();
		Double  a =fea_design_downholeVO.getHgpbac();
		Double  b =fea_design_downholeVO.getHgpbbh();

		Double  t2 = fea_design_transferVO.getOneoutheat();
		Double  tg = fea_design_transferVO.getTwooutheat();
		Double  th = fea_design_transferVO.getTwobackheat();
		Double  trg = fea_design_transferVO.getTwozfoutheat();
		Double  trh = fea_design_transferVO.getTwozfbacktheat();
		Double  t3 = fea_design_transferVO.getBackhgheat();
		Double  gmah = fea_design_transferVO.getSumheatefficient();
		Double  gmaq = fea_design_transferVO.getLoadrate();
		Double  cop = fea_design_transferVO.getPumprate();

		Double Q1 = 0.00;//一级板换供热量
		if(null!=m && null!=t1 && null!=t2){
			Q1 = (4.2/3.6)*m*(t1-t2);
		}

		Double A1 = 0.00;//一级板换最大供热面积
		if(null!=gmah && null!=q){
			A1 = 1000*Q1*gmah/q;
		}

		Double Q2 = 0.00;//二级板换+热泵供热量
		if(null!=m && t2!=null && t3!=null && null!=cop){
			Q2 = (4.2/3.6)*m*(t2-t3)*(cop/(cop-1));
		}
		Double A2 = 0.00;//二级板换供热面积
		if(null!=q && null!=gmah){
			A2 = 1000*Q2*gmah/q;
		}
		Double Ad = A1+A2;//单井最大供热面积

		Double Nk = Math.ceil(A/Ad);//开采井数计算

		Double Nh = Math.ceil((b/a)*Nk);//回灌井数计算

		Double N = Nk+Nh;//打井数 -- www

		Double M1 = m*((t1-t2)/(tg-th)); //单井一级板换二次侧流量

		Double Q21 = (4.2/3.6)*m*(t2-t3);//单井二级板换供热量

		Double M2 = m*(t2-t3)/(trg-trh);// 单井二级板换二次蒸发器侧流量

		Double M3 = 3.6*Q2/(4.2*(tg-th));//单井二级热泵二次冷凝器侧流量

		Double Wr2 = (4.2/3.6)*m*(t2-t3)/(cop-1);

		//*********板换模块***************//

		Double Qh1 = 0.7*Q1; //一级换热器换热量

		Double taTm1 = ((t1-tg)-(t2-th))/Math.log((t1-tg)/(t2-th));//一级换热器平均温差

		Double Ah1 = 1000*Qh1/(4000*taTm1);//一级换热器换热面积

		Double Qh2 = 0.7*Q21;//二级换热器换热量

		Double taTm2 = ((t1-trg)-(t2-trh))/Math.log((t1-trg)/(t2-trh));//二级换热器平均温差

		Double Ah2 = 1000*Qh2/(4000*taTm2);//二级换热器换热面积

		//www  -- 涉及价格区间
		Double Ch1 = 0.00;
		for(List<Double> price : pricech){
			if(Ah1>=price.get(0) && Ah1<=price.get(1)){
				Ch1 = price.get(2)*Ah1/10000;
			}
		}
		Double Nh1 = 2*Nk; //单井2台，多井一级换热器总数量

		Double Ch2 = 0.00;
		for(List<Double> price : pricech){
			if(Ah2>=price.get(0) && Ah2<=price.get(1)){
				Ch2 = price.get(2)*Ah2/10000;
			}
		}

		Double Nh2 = 2*Nk;//单井2台，多井一级换热器总数量

		//换热器总价格 -- www
		Double Ch = (Ch1*Nh1+Ch2*Nh2);

		//*************** 3、热泵模块 ****************//
		Double Q2tmp = Q2;
		Double Qr2 = 0.00;
		Double Pr2 = 0.00;
		Double Cr2 = 0.00;
		int jznum =1;
		if(Q2tmp > heatpumpprice.get(0).get(heatpumpprice.get(0).size()-1)){
			Q2tmp = Q2tmp/2;
			jznum = 2;
		}
		for(int i=0;i<heatpumpprice.get(0).size()-1;i++){
			if(Q2tmp==heatpumpprice.get(0).get(0)){
				Qr2 = heatpumpprice.get(0).get(0);
				Pr2 = heatpumpprice.get(1).get(0);
				Cr2 = heatpumpprice.get(2).get(0);
			}else if(Q2tmp>heatpumpprice.get(0).get(i) && Q2tmp<=heatpumpprice.get(0).get(i+1)){
				Qr2 = heatpumpprice.get(0).get(i+1);
				Pr2 = heatpumpprice.get(1).get(i+1);
				Cr2 = heatpumpprice.get(2).get(i+1);
			}
		}

		//************** 4、潜水泵模块 **************//
		Double rou = 1000.00;//固定参数 - 柔 - 密度 ？？？ www

		Double hq = hd+30;//（程序中备注：10米：水面下10米；20米为泵送热水系统压力损失）

		Double Pq = rou*m*hq*1.15*9.8/(3600*0.75*1000);//kwh

		Double Cq1 = Pq*2.5/55;//（程序中备注：单价为拟合公式，可更改）wwww

		Double Cq = 2*Nk*Cq1;//潜水泵总价

		//******************5、循环水泵模块 -- 否 ****************//
		//       
		Double hx1 = 32.00;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Px1 = rou*M1*hx1*1.15*9.8/(3600*0.75*1000);

		Double Cx1 = (465*Px1+5000)/10000;//万元

		Double Cxb1 = (1264*Px1+20000)/10000;//万元  变频控制柜单价 www //（程序中备注：单价为拟合公式，可更改）

		//********************（2）二级板换对应循环水泵
		Double hx2 = 20.00;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Px2 = rou*M2*hx2*1.15*9.8/(3600*0.75*1000);

		Double Cx2 = (465*Px2+5000)/10000;//万元

		Double Cxb2 = (1264*Px2+20000)/10000;//万元 //（程序中备注：单价为拟合公式，可更改）www

		Double Cx2sum = 2*Nk*Cx2; //热泵蒸发器侧循环水泵总价

		Double hx3 = 32.00;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Px3 = rou*M3*hx3*1.15*9.8/(3600*0.75*1000);

		Double Cx3 = (465*Px3+5000)/10000;//万元

		Double Cxb3 = (1264*Px3+20000)/10000;//万元 //（程序中备注：单价为拟合公式，可更改）www

		//****************6、补水泵模块 - 否******************//
		//       
		Double addwater = 0.03*(M1+ M3);// （程序备注：一般补水泵流量为循环水流量的2%-4%） www

		Double hb1 = Hj + 25;//（程序备注：根据厂家经验数据而来，25为地下位置和末端高差，可更改） www

		Double Pb1 = rou*addwater*hb1*1.15*9.8/(3600*0.75*1000);

		Double Cb1 = (629*Pb1+6000)/10000;   //（程序中备注：单价为拟合公式，可更改） www

		//****************** 四、方案运行计算

		Double Wq = rou*m*(hd+30)*1.15*9.8/(3600*0.75*1000);
		Double Wx1 = rou*M1*32*1.15*9.8/(3600*0.75*1000);
		Double Wb1 = rou*(0.03*M1)*hb1*1.15*9.8/(3600*0.75*1000);

		Double W1 = Wq+Wx1+Wb1;
		Double Wx2 = rou*M2*20*1.15*9.8/(3600*0.75*1000);
		Double Wx3 = rou*M3*20*1.15*9.8/(3600*0.75*1000);
		Double Wb2 = rou*0.03*M3*hb1*1.15*9.8/(3600*0.75*1000);

		Double W2 = Wr2+Wx2+Wx3+Wb2;

		List<Double> yearpow = new ArrayList<Double>();//全年运行能耗
		List<Double> downwater = new ArrayList<Double>();//地下水用量
		List<Double> powfeeunit = new ArrayList<Double>();//单位面积电费
		List<Double> costunit = new ArrayList<Double>();//单位面积运行成本
		List<Double> yearunit = new ArrayList<Double>();//第i年运行成本
		
		Fea_design_resultVO result1 = new Fea_design_resultVO();
		Fea_design_resultVO result2 = new Fea_design_resultVO();
		Fea_design_resultVO result3 = new Fea_design_resultVO();
		Fea_design_resultVO result4 = new Fea_design_resultVO();
		Fea_design_resultVO result5 = new Fea_design_resultVO();
		
		result1.setFeaProjectB(fea_design_heatVO.getFeaProjectB());
		result2.setFeaProjectB(fea_design_heatVO.getFeaProjectB());
		result3.setFeaProjectB(fea_design_heatVO.getFeaProjectB());
		result4.setFeaProjectB(fea_design_heatVO.getFeaProjectB());
		result5.setFeaProjectB(fea_design_heatVO.getFeaProjectB());
		
		result1.setResulttype("入住率");
		result2.setResulttype("供暖面积（平方米）");
		result3.setResulttype("地下水用量（立方米/年）");
		result4.setResulttype("单位面积电费（元/平方米）");
		result5.setResulttype("运行成本（万元/年）");
		
		int i = 1;
		for(Double rate : heatrate){
			Double yearpowdoub = 0.00;
			Double downwaterdoub = 0.00;
			Double powfeeunitdoub = 0.00;
			Double costunitdoub = 0.00;
			Double yearunitdoub = 0.00;
			if(A*rate <= Nk*A1 ){
				yearpowdoub = A*rate*W1*D*T*gmaq/(Nk*A1);
				downwaterdoub = A*rate*m*D*T/A1;
				powfeeunitdoub = yearpowdoub*E1/A1;	
			}else{
				yearpowdoub = (Nk*W1+(W2*(A*rate-Nk*A1)/A2))*D*T*gmaq;
				downwaterdoub = Nk*m*D*T;
				powfeeunitdoub = yearpowdoub*E1/(A1*rate);	
			}
			costunitdoub = powfeeunitdoub;
			yearunitdoub = powfeeunitdoub*A*rate/10000;

			yearpow.add(yearpowdoub);
			downwater.add(downwaterdoub); 
			powfeeunit.add(powfeeunitdoub);
			costunit.add(costunitdoub);
			yearunit.add(yearunitdoub);

			try {
				
				Method m1 = result1.getClass().getMethod("setyear"+(i+1), Double.class);
				m1.invoke(result1, rate);
				
				Method m2 = result2.getClass().getMethod("setyear"+(i+1), Double.class);
				BigDecimal   d1 = new BigDecimal(downwaterdoub);
				Double d11 = d1.setScale(2, RoundingMode.HALF_UP).doubleValue();
				m2.invoke(result2,d11);
				
				Method m3 = result3.getClass().getMethod("setyear"+(i+1), Double.class);
				BigDecimal   d3 = new BigDecimal(powfeeunitdoub);
				Double d31 = d3.setScale(2, RoundingMode.HALF_UP).doubleValue();
				m3.invoke(result3, d31);
				
				
				Method m4 = result4.getClass().getMethod("setyear"+(i+1), Double.class);
				BigDecimal   d4 = new BigDecimal(costunitdoub);
				Double d41 = d4.setScale(2, RoundingMode.HALF_UP).doubleValue();
				m4.invoke(result4, d41);
				
				Method m5 = result5.getClass().getMethod("setyear"+(i+1), Double.class);
				BigDecimal   d5 = new BigDecimal(yearunitdoub);
				Double d51 = d5.setScale(2, RoundingMode.HALF_UP).doubleValue();
				m4.invoke(result4, d41);
				m5.invoke(result5, d51);
				
			} catch (Exception e) {
				throw new ServiceException(e);
			}
		}
		resultVOMapper.execDeleteSql("delete from fea_design_result t where t.project_id='"+fea_design_heatVO.getFeaProjectB().getId()+"'");
		resultVOMapper.insert(result1);resultVOMapper.insert(result2);resultVOMapper.insert(result3);
		resultVOMapper.insert(result4);resultVOMapper.insert(result5);
		
		List<List<Double>> costinfolst = new ArrayList<List<Double>>();
		costinfolst.add(yearpow);
		costinfolst.add(downwater);
		costinfolst.add(powfeeunit);
		costinfolst.add(costunit);
		costinfolst.add(yearunit);
		
		//*********分区为 是*********//
		//表1 地热供暖项目设备清单1  地热供暖项目设备清单
		List<List<String>> rettable1 = new ArrayList<List<String>>();

		List<String> col1 = new ArrayList<String>();
		col1.add("地热井潜水泵");
		String col1str = "流量 "+m+" m³/h;\n "+ "扬程  "+hq+" m;\n " + "电机功率 "+Pq+" kW ";
		col1.add(col1str);
		col1.add(""+2*Nk);
		col1.add(Cq+"");
		col1.add(Nk+"用"+Nk+"备");

		List<String> col2 = new ArrayList<String>();
		col2.add("旋流除砂器");
		col2.add("");
		col2.add(""+Nk);
		col2.add(""+5*Nk);
		col2.add("");

		List<String> col3 = new ArrayList<String>();
		col3.add("汽水分离器");
		col3.add(" ");
		col3.add(" "+Nk);
		col3.add(" "+0.5*Nk);
		col3.add(" ");

		List<String> col4 = new ArrayList<String>();
		col4.add("过滤器");
		col4.add("");
		col4.add(""+Nk);
		col4.add(""+4*Nk);
		col4.add("");

		List<String> col5 = new ArrayList<String>();
		col5.add("板式换热器");
		String col5str = "换热量 "+Qh1+" kW;\n "+ "换热面积  "+Ah1+" m2;\n " + "设计压力 "+1.6+"Mpa ";
		col5.add(col5str);
		col5.add(""+Nh1);
		col5.add(""+Ch1*Nh1);
		col5.add("一级换热、钛板温度：110℃ ; \n "+"各承担70%负荷");

		List<String> col6 = new ArrayList<String>();
		col6.add("板式换热器");
		String col6str = "换热量 "+Qh2+" kW;\n "+ "换热面积  "+Ah2+" m2;\n " + "设计压力 "+1.6+"Mpa ";
		col6.add(col6str);
		col6.add(""+Nh2);
		col6.add(""+Ch2*Nh2);
		col6.add("二级换热、钛板温度：110℃ ; \n "+"各承担70%负荷");

		List<String> col7= new ArrayList<String>();
		col7.add("热泵机组");
		String col7str = "制热量 "+Qr2+" kW;\n "+ "电功率 "+Pr2 ;
		col7.add(col7str);
		col7.add(""+jznum*Nk);
		col7.add(""+jznum*Nk*Cr2);
		col7.add(" 0 ");

		List<String> col8= new ArrayList<String>();
		col8.add("循环水泵");
		String col8str = "流量"+(M1)+" m³/h;\n  扬程"+hx1+" m;\n "+ "功率 "+Px1+"kw;" ;
		col8.add(col8str);
		col8.add(""+2*Nk);
		col8.add(""+Cx1+Cxb1);
		col8.add(" 高区"+Nk+"用"+Nk+"备变频 ");

		List<String> col10= new ArrayList<String>();
		col10.add("循环水泵");
		String col10str = "流量"+(M3)+" m³/h;\n  扬程"+hx3+" m;\n "+ "功率 "+Px3+"kw;" ;
		col10.add(col10str);
		col10.add(""+2*Nk);
		col10.add(""+Cx3+Cxb3);
		col10.add("热泵冷凝器 "+Nk+"用"+Nk+"备变频 ");

		List<String> col11= new ArrayList<String>();
		col11.add("循环水泵");
		String col11str = "流量"+(M2)+" m³/h;\n  扬程"+hx2+" m;功率 "+Px2+"kW";
		col11.add(col11str);
		col11.add(""+2*Nk);
		col11.add(""+Cx2);
		col11.add(" 热泵蒸发器 "+Nk+"用"+Nk+"备变频 ");

		List<String> col12= new ArrayList<String>();
		col12.add("补水泵");
		String col12str = "流量"+(0.03*(M1+M3))+" m³/h;\n  扬程"+hb1+" m;功率 "+Pb1+"kW";
		col12.add(col12str);
		col12.add(""+2*Nk);
		col12.add(""+Cb1);
		col12.add(""+Nk+"用"+Nk+"备变频 ");
		
		rettable1.add(col1);rettable1.add(col2);rettable1.add(col3);
		rettable1.add(col4);rettable1.add(col5);rettable1.add(col6);
		rettable1.add(col7);rettable1.add(col8);
		rettable1.add(col10);rettable1.add(col11);rettable1.add(col12);
		
	    
		
	}
}
