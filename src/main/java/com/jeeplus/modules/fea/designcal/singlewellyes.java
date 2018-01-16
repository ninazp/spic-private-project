package com.jeeplus.modules.fea.designcal;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.entity.downhole.Fea_design_downholeVO;
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;

public class singlewellyes {

	public  static void calsinglewellyes(Fea_design_heatVO  fea_design_heatVO,
			Fea_design_downholeVO fea_design_downholeVO,
			Fea_design_transferVO fea_design_transferVO,
			List<List<Double>> pricech,
			List<List<Double>> heatpumpprice,
			List<Double> heatrate){

		Double q = fea_design_heatVO.getHeatload();
		Double A = fea_design_heatVO.getHeatarea();
		Double E1 = fea_design_heatVO.getPowerfee();
		Double D = fea_design_heatVO.getHeatdays();
		Double T = fea_design_heatVO.getDayheathours();
		Double Hj = fea_design_heatVO.getBuildheight();
		String isarea = fea_design_heatVO.getAreaselect();

		//		private Double holeheight;		// 井深（米）
		//		private Double flowcount;		// 流量（立方米/小时）
		//		private Double outheat;		// 出口温度（摄氏度）
		//		private Double waterlevel;		// 动水位（米）
		//		private Double hgpbac;		// 回灌配比a采
		//		private Double hgpbbh;		// 回灌配比b回

		Double  H = fea_design_downholeVO.getHoleheight();
		Double m  =fea_design_downholeVO.getFlowcount();
		Double  t1 =fea_design_downholeVO.getOutheat();
		Double  hd =fea_design_downholeVO.getWaterlevel();
		Double  a =fea_design_downholeVO.getHgpbac();
		Double  b =fea_design_downholeVO.getHgpbbh();

		//		private Double oneoutheat;		// 一级一次侧出口温度（摄氏度）
		//		private Double twooutheat;		// 二次侧供水温度（摄氏度）
		//		private Double twobackheat;		// 二次侧回水温度（摄氏度）
		//		private Double twozfoutheat;		// 二级二次蒸发器侧供水温度（摄氏度）
		//		private Double twozfbacktheat;		// 二级二次蒸发器侧回水温度（摄氏度）
		//		private Double backhgheat;		// 回灌水温度（摄氏度）
		//		private Double sumheatefficient;		// 综合热效率
		//		private Double loadrate;		// 负荷率
		//		private Double pumprate;		// 热泵效率


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

		Double N = Nk+Nh;//打井数

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

		//换热器总价格
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
		Double rou = 1000.00;//固定参数 - 柔 - 密度 ？？？ wwww

		Double hq = hd+30;//（程序中备注：10米：水面下10米；20米为泵送热水系统压力损失）

		Double Pq = rou*m*hq*1.15*9.8/(3600*0.75*1000);//kwh

		Double Cq1 = Pq*2.5/55;//（程序中备注：单价为拟合公式，可更改）wwww

		Double Cq = 2*Nk*Cq1;//潜水泵总价

		//******************5、循环水泵模块 -- 是 ****************//
		//*********高区

		Double hhx1 = 32.00;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Phx1 = rou*(M1/2)*hhx1*1.15*9.8/(3600*0.75*1000);

		Double Chx1 = (465*Phx1+5000)/10000;//万元

		Double Chxb1 = (1264*Phx1+20000)/10000;//万元  变频控制柜单价 www //（程序中备注：单价为拟合公式，可更改）

		//********************（2）二级板换对应循环水泵
		Double hhx3 = 32.00;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Phx3 = rou*(M3/2)*hhx3*1.15*9.8/(3600*0.75*1000);

		Double Chx3 = (465*Phx3+5000)/10000;//万元

		Double Chxb3 = (1264*Phx3+20000)/10000;//万元 //（程序中备注：单价为拟合公式，可更改）www

		//*********低区
		Double hdx1 = 32.00;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Pdx1 = rou*(M1/2)*hdx1*1.15*9.8/(3600*0.75*1000);

		Double Cdx1 = (465*Pdx1+5000)/10000;//万元

		Double Cdxb1 = (1264*Phx1+20000)/10000;//万元  变频控制柜单价 www //（程序中备注：单价为拟合公式，可更改）

		//********************（2）二级板换对应循环水泵
		Double hdx3 = 32.00;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Pdx3 = rou*M3*hdx3*1.15*9.8/(3600*0.75*1000);

		Double Cdx3 = (465*Pdx3+5000)/10000;//万元

		Double Cdxb3 = (1264*Pdx3+20000)/10000;//万元 //（程序中备注：单价为拟合公式，可更改）www

		Double hx2 = 20.00;

		Double Px2 = rou*M2*hx2*1.15*9.8/(3600*0.75*1000);

		Double Cdx2 =  (465*Px2+5000)/10000;//万元

		Double Cdxb2 = (1264*Px2+20000)/10000;//万元 //（程序中备注：单价为拟合公式，可更改）www

		//****************6、补水泵模块 - 是******************//
		//高区
		Double addwater = 0.03*(M1+ M3)/2;// （程序备注：一般补水泵流量为循环水流量的2%-4%） www

		Double hhb1 = Hj + 25;//（程序备注：根据厂家经验数据而来，25为地下位置和末端高差，可更改） www

		Double Phb1 = rou*addwater*hhb1*1.15*9.8/(3600*0.75*1000);

		Double Chb1 = (629*Phb1+6000)/10000;   //（程序中备注：单价为拟合公式，可更改） www

		//低区
		Double hb2 = Hj/2 + 25;//（程序备注：根据厂家经验数据而来，25为地下位置和末端高差，可更改） www

		Double Pb2 = rou*addwater*hb2*1.15*9.8/(3600*0.75*1000);

		Double Cb2 = (629*Pb2+6000)/10000;   //（程序中备注：单价为拟合公式，可更改） www

		Double hb1 = Hj + 25;

		//****************** 四、方案运行计算

		Double Wq = rou*m*(hd+30)*1.15*9.8/(3600*0.75*1000);
		Double Wx1 = rou*M1*32*1.15*9.8/(3600*0.75*1000);
		Double Wb1 = rou*(0.03*M1)*hb1*1.15*9.8/(3600*0.75*1000);

		Double W1 = Wq+Wx1+Wb1;
		Double Wx2 = rou*M2*20*1.15*9.8/(3600*0.75*1000);
		Double Wx3 = rou*M3*20*1.15*9.8/(3600*0.75*1000);
		Double Wb2 = rou*0.03*M3*hb2*1.15*9.8/(3600*0.75*1000);

		Double W2 = Wr2+Wx2+Wx3+Wb2;

		List<Double> yearpow = new ArrayList<Double>();//全年运行能耗
		List<Double> downwater = new ArrayList<Double>();//地下水用量
		List<Double> powfeeunit = new ArrayList<Double>();//单位面积电费
		List<Double> costunit = new ArrayList<Double>();//单位面积运行成本
		List<Double> yearunit = new ArrayList<Double>();//第i年运行成本
		for(Double rate : heatrate){
			Double yearpowdoub = 0.00;
			Double downwaterdoub = 0.00;
			Double powfeeunitdoub = 0.00;
			Double costunitdoub = 0.00;
			Double yearunitdoub = 0.00;
			if(A*rate <= Nk*A1 ){
				yearpowdoub = A*rate*W1*D*T*gmaq/(Nk*A1);
				downwaterdoub = A*rate*m*D*T/A1;
			}else{
				yearpowdoub = (Nk*W1+(W2*(A*rate-Nk*A1)/A2))*D*T*gmaq;
				downwaterdoub = Nk*m*D*T/A1;
			}
			powfeeunitdoub = yearpowdoub*D*T*E1/A1;	
			costunitdoub = powfeeunitdoub;
			yearunitdoub = powfeeunitdoub*A*rate/10000;

			yearpow.add(yearpowdoub);
			downwater.add(downwaterdoub); 
			powfeeunit.add(powfeeunitdoub);
			costunit.add(costunitdoub);
			yearunit.add(yearunitdoub);

		}

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
		col2.add("");
		col2.add(""+Nk);
		col2.add(""+0.5*Nk);
		col2.add("");
		
		List<String> col4 = new ArrayList<String>();
		col4.add("过滤器");
		col4.add("");
		col4.add(""+Nk);
		col4.add(""+0.5*Nk);
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
		col7.add(""+jznum);
		col7.add(""+jznum*Cr2);
		col7.add(" ");

		List<String> col8= new ArrayList<String>();
		col8.add("循环水泵");
		String col8str = "流量"+(M1/2)+" m³/h;\n  扬程"+hhx1+" m;\n "+ "功率 "+Phx1+"kw;" ;
		col8.add(col8str);
		col8.add(""+2*Nk);
		col8.add(""+Chx1+Chxb1);
		col8.add(" 高区"+Nk+"用"+Nk+"备变频 ");
		
		List<String> col9= new ArrayList<String>();
		col9.add("循环水泵");
		String col9str = "流量"+(M1/2)+" m³/h;\n  扬程"+hdx1+" m;\n "+ "功率 "+Pdx1+"kw;" ;
		col9.add(col9str);
		col9.add(""+2*Nk);
		col9.add(""+Cdx1+Cdxb1);
		col9.add(" 低区"+Nk+"用"+Nk+"备变频 ");
		
		
		List<String> col10= new ArrayList<String>();
		col10.add("循环水泵");
		String col10str = "流量"+(M3/2)+" m³/h;\n  扬程"+hhx3+" m;\n "+ "功率 "+Pdx3+"kw;" ;
		col10.add(col10str);
		col10.add(""+2*Nk);
		col10.add(""+Chx1+Chxb1);
		col10.add(" 低区热泵冷凝器 "+Nk+"用"+Nk+"备变频 ");
		
		List<String> col11= new ArrayList<String>();
		col11.add("循环水泵");
		String col11str = "流量"+(M2)+" m³/h;\n  扬程"+hdx3+" m;功率 "+Pdx3+"kW";
		col11.add(col11str);
		col11.add(""+2*Nk);
		col11.add(""+Cdx3+Cdxb3);
		col11.add(" 热泵蒸发器 "+Nk+"用"+Nk+"备变频 ");
		
		List<String> col12= new ArrayList<String>();
		col12.add("循环水泵");
		String col12str = "流量"+(M2)+" m³/h;\n  扬程"+hx2+" m;功率 "+Px2+"kW";
		col12.add(col12str);
		col12.add(""+2*Nk);
		col12.add(""+Cdx2);
		col12.add(" 热泵蒸发器 "+Nk+"用"+Nk+"备变频 ");
		
		List<String> col13= new ArrayList<String>();
		col13.add("补水泵");
		String col13str = "流量"+(0.03*(M1+M3)/2)+" m³/h;\n  扬程"+hhb1+" m;功率 "+Phb1+"kW";
		col13.add(col13str);
		col13.add(""+2*Nk);
		col13.add("");
		col13.add(" 高区 "+Nk+"用"+Nk+"备变频 ");
		
		
		List<String> col14= new ArrayList<String>();
		col14.add("补水泵");
		String col14str =  "流量"+(0.03*(M1+M3)/2)+" m³/h;\n  扬程"+hb2+" m;功率 "+Pb2+"kW";
		col14.add(col14str);
		col14.add(""+2*Nk);
		col14.add("");
		col14.add("高区  "+Nk+"用"+Nk+"备变频 ");
		
		rettable1.add(col1);rettable1.add(col2);rettable1.add(col3);
		rettable1.add(col4);rettable1.add(col5);rettable1.add(col6);
		rettable1.add(col7);rettable1.add(col8);rettable1.add(col9);
		rettable1.add(col10);rettable1.add(col11);rettable1.add(col12);
		rettable1.add(col13);rettable1.add(col14);
		
	}


}
