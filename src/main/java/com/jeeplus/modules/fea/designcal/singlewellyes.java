package com.jeeplus.modules.fea.designcal;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeeplus.core.service.ServiceException;
import com.jeeplus.modules.fea.dao.PubUtil;
import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.entity.result.Fea_design_resultVO;
import com.jeeplus.modules.fea.entity.set.Fea_design_setVO;
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;
import com.jeeplus.modules.fea.mapper.result.Fea_design_resultVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class singlewellyes {

	public  static Map<String,Object> calsinglewellyes(Fea_design_heatVO  fea_design_heatVO,
			Fea_design_transferVO fea_design_transferVO,
			Fea_design_setVO fea_design_setVO,
			List<List<Double>> pricech,
			List<List<Double>> heatpumpprice,
			List<Double> heatrate,Fea_design_resultVOMapper resultVOMapper){

		Map<String,Object> retmap = new HashMap<String, Object>();
		//供热及井下参数
		Double q = fea_design_heatVO.getHeatload();
		Double A = fea_design_heatVO.getHeatarea();
		Double E1 = fea_design_heatVO.getPowerfee();
		Double D = fea_design_heatVO.getHeatdays();
		Double T = fea_design_heatVO.getDayheathours();
		Double Hj = fea_design_heatVO.getBuildheight();
		Double  H = fea_design_heatVO.getHoleheight();
		Double m  =fea_design_heatVO.getFlowcount();
		Double  t1 =fea_design_heatVO.getOutheat();
		Double  hd =fea_design_heatVO.getWaterlevel();
		Double  a =fea_design_heatVO.getHgpbac();
		Double  b =fea_design_heatVO.getHgpbbh();

		//
		Double  t2 = fea_design_transferVO.getOneoutheat();
		Double  tg = fea_design_transferVO.getTwooutheat();
		Double  th = fea_design_transferVO.getTwobackheat();
		Double  trg = fea_design_transferVO.getTwozfoutheat();
		Double  trh = fea_design_transferVO.getTwozfbacktheat();
		Double  t3 = fea_design_transferVO.getBackhgheat();

		//预置参数 - 基本参数
		Double  gmah = fea_design_setVO.getSumheatefficient();
		Double  gmaq = fea_design_setVO.getLoadrate();
		Double  cop = fea_design_setVO.getPumprate();
		Double hddept = fea_design_setVO.getHddept();
		Double hdlose = fea_design_setVO.getHdlose();
		Double rou = fea_design_setVO.getRou();
		Double hx2lang = fea_design_setVO.getHx2();
		Double hb2lang = fea_design_setVO.getHb2();
		Double mpumpcoe = (null==fea_design_setVO.getMpumpcoe())?0.00:fea_design_setVO.getMpumpcoe()/100;

		Double cxxs = fea_design_setVO.getCxxs();
		Double cxcl = fea_design_setVO.getCxcl();
		Double bpxs = fea_design_setVO.getBpxs();
		Double bpcl = fea_design_setVO.getBpcl();
		Double bsxs = fea_design_setVO.getBsxs();
		Double bscl = fea_design_setVO.getBsxl();


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
		for(int i=0;i<heatpumpprice.size()-1;i++){
			if(Q2tmp==heatpumpprice.get(i).get(0)){
				Qr2 = heatpumpprice.get(i).get(0);
				Pr2 = heatpumpprice.get(i).get(1);
				Cr2 = heatpumpprice.get(i).get(2);
			}else if(Q2tmp>heatpumpprice.get(i).get(0) && Q2tmp<=heatpumpprice.get(i+1).get(0)){
				Qr2 = heatpumpprice.get(i+1).get(0);
				Pr2 = heatpumpprice.get(i+1).get(1);
				Cr2 = heatpumpprice.get(i+1).get(2);
			}
		}
		//private Double hddept;		// 潜水泵水下位置（米）
		//		private Double hdlose;		// 潜水泵压力损失（米）
		//		private Double rou;		// 水密度
		//		private Double hx2;		// 循环水泵扬程
		//		private Double hb2;		// 蒸发器侧水泵扬程
		//		private Double mpumpcoe;		// 补水泵流量系数（%）
		//************** 4、潜水泵模块 **************//
		Double hq = hd+hddept+hdlose;//（程序中备注：10米：水面下10米；20米为泵送热水系统压力损失）

		Double Pq = rou*m*hq*1.15*9.8/(3600*0.75*1000);//kwh

		Double Cq1 = Pq*2.5/55;//（程序中备注：单价为拟合公式，可更改）wwww

		Double Cq = 2*Nk*Cq1;//潜水泵总价

		//******************5、循环水泵模块 -- 是 ****************//
		//*********高区

		Double hhx1 = hx2lang;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Phx1 = rou*(M1/2)*hhx1*1.15*9.8/(3600*0.75*1000);

		Double Chx1 = (cxxs*Phx1+cxcl)/10000;//万元

		Double Chxb1 = (bpxs*Phx1+bpcl)/10000;//万元  变频控制柜单价 www //（程序中备注：单价为拟合公式，可更改）

		//********************（2）二级板换对应循环水泵
		Double hhx3 = hx2lang;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Phx3 = rou*(M3/2)*hhx3*1.15*9.8/(3600*0.75*1000);

		Double Chx3 = (cxxs*Phx3+cxcl)/10000;//万元

		Double Chxb3 = (bpxs*Phx3+bpcl)/10000;//万元 //（程序中备注：单价为拟合公式，可更改）www

		//*********低区
		Double hdx1 = hx2lang;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Pdx1 = rou*(M1/2)*hdx1*1.15*9.8/(3600*0.75*1000);

		Double Cdx1 = (cxxs*Pdx1+cxcl)/10000;//万元

		Double Cdxb1 = (bpxs*Phx1+bpcl)/10000;//万元  变频控制柜单价 www //（程序中备注：单价为拟合公式，可更改）

		//********************（2）二级板换对应循环水泵
		Double hdx3 = hx2lang;//m （程序备注：根据厂家经验数据而来，可更改） www

		Double Pdx3 = rou*(M3/2)*hdx3*1.15*9.8/(3600*0.75*1000);

		Double Cdx3 = (cxxs*Pdx3+cxcl)/10000;//万元

		Double Cdxb3 = (bpxs*Pdx3+bpcl)/10000;//万元 //（程序中备注：单价为拟合公式，可更改）www

		Double hx2 = hb2lang;//扬程

		Double Px2 = rou*M2*hx2*1.15*9.8/(3600*0.75*1000);

		Double Cdx2 =  (cxxs*Px2+cxcl)/10000;//万元

		Double Cdxb2 = (bpxs*Px2+bpcl)/10000;//万元 //（程序中备注：单价为拟合公式，可更改）www

		//****************6、补水泵模块 - 是******************//
		//高区
		Double addwater = mpumpcoe*(M1+ M3)/2;// （程序备注：一般补水泵流量为循环水流量的2%-4%） www

		Double hhb1 = Hj + 25;//（程序备注：根据厂家经验数据而来，25为地下位置和末端高差，可更改） www

		Double Phb1 = rou*addwater*hhb1*1.15*9.8/(3600*0.75*1000);

		Double Chb1 = (bsxs*Phb1+bscl)/10000;   //（程序中备注：单价为拟合公式，可更改） www

		//低区
		Double hb2 = Hj/2 + 25;//（程序备注：根据厂家经验数据而来，25为地下位置和末端高差，可更改） www

		Double Pb2 = rou*addwater*hb2*1.15*9.8/(3600*0.75*1000);

		Double Cb2 = (bsxs*Pb2+bscl)/10000;   //（程序中备注：单价为拟合公式，可更改） www

		Double hb1 = Hj + 25;

		//****************** 四、方案运行计算

		Double Wq = rou*m*(hd+hddept+hdlose)*1.15*9.8/(3600*0.75*1000);
		Double Wx1 = rou*M1*hx2lang*1.15*9.8/(3600*0.75*1000);
		Double Wb1 = rou*(mpumpcoe*M1)*hb1*1.15*9.8/(3600*0.75*1000);

		Double W1 = Wq+Wx1+Wb1;
		Double Wx2 = rou*M2*hb2lang*1.15*9.8/(3600*0.75*1000);
		Double Wx3 = rou*M3*hx2lang*1.15*9.8/(3600*0.75*1000);
		Double Wb2 = rou*mpumpcoe*M3*hb1*1.15*9.8/(3600*0.75*1000);

		Double W2 = Wr2+Wx2+Wx3+Wb2;

		List<Double> res1 = new ArrayList<Double>();//入住率
		List<Double> res2 = new ArrayList<Double>();//供暖面积（平方米）
		List<Double> res3 = new ArrayList<Double>();//地下水用量（立方米/年）
		List<Double> res4 = new ArrayList<Double>();//单位面积电费（元/平方米）
		List<Double> res5 = new ArrayList<Double>();//第i年运行成本

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

		result1.setCreateBy(UserUtils.getUser());
		result1.setCreateDate(new Date());
		result2.setCreateBy(UserUtils.getUser());
		result2.setCreateDate(new Date());
		result3.setCreateBy(UserUtils.getUser());
		result3.setCreateDate(new Date());
		result4.setCreateBy(UserUtils.getUser());
		result4.setCreateDate(new Date());
		result5.setCreateBy(UserUtils.getUser());
		result5.setCreateDate(new Date());

		result1.setOffice(UserUtils.getOfficeList().get(0));
		result2.setOffice(UserUtils.getOfficeList().get(0));
		result3.setOffice(UserUtils.getOfficeList().get(0));
		result4.setOffice(UserUtils.getOfficeList().get(0));
		result5.setOffice(UserUtils.getOfficeList().get(0));

		result1.setId(PubUtil.getid(1));
		result2.setId(PubUtil.getid(1));
		result3.setId(PubUtil.getid(1));
		result4.setId(PubUtil.getid(1));
		result5.setId(PubUtil.getid(1));

		result1.setRownum(1+"");
		result2.setRownum(2+"");
		result3.setRownum(3+"");
		result4.setRownum(4+"");
		result5.setRownum(5+"");

		result1.setResulttype("入住率");
		result2.setResulttype("供暖面积（平方米）");
		result3.setResulttype("地下水用量（立方米/年）");
		result4.setResulttype("单位面积电费（元/平方米）");
		result5.setResulttype("运行成本（万元/年）");

		int i=1;
		for(Double rate : heatrate){
			Double yearpow = 0.00;
			Double downwater = 0.00;
			Double powfeeunit = 0.00;
			Double costunit = 0.00;
			Double yearunit = 0.00;
			if(A*rate <= Nk*A1 ){
				yearpow = A*rate*W1*D*T*gmaq/(Nk*A1);
				downwater = A*rate*m*D*T/A1;
			}else{
				yearpow = (Nk*W1+(W2*(A*rate-Nk*A1)/A2))*D*T*gmaq;
				downwater = Nk*m*D*T;
			}
			if(rate>0){
			 powfeeunit = yearpow*E1/(A*rate);	
			 costunit = powfeeunit;
			 yearunit = powfeeunit*A*rate/10000;
			}

			res1.add(rate);
			res2.add(A*rate); 
			res3.add(downwater);
			res4.add(costunit);
			res5.add(yearunit);

			try {
				Method m1 = result1.getClass().getMethod("setYear"+(i), Double.class);
				m1.invoke(result1, rate);

				Method m2 = result2.getClass().getMethod("setYear"+(i), Double.class);
				BigDecimal   d1 = new BigDecimal(A*rate);
				Double d11 = d1.setScale(2, RoundingMode.HALF_UP).doubleValue();
				m2.invoke(result2,d11);

				Method m3 = result3.getClass().getMethod("setYear"+(i), Double.class);
				BigDecimal   d3 = new BigDecimal(downwater);
				Double d31 = d3.setScale(2, RoundingMode.HALF_UP).doubleValue();
				m3.invoke(result3, d31);

				Method m4 = result4.getClass().getMethod("setYear"+(i), Double.class);
				BigDecimal   d4 = new BigDecimal(costunit);
				Double d41 = d4.setScale(2, RoundingMode.HALF_UP).doubleValue();
				m4.invoke(result4, d41);

				Method m5 = result5.getClass().getMethod("setYear"+(i), Double.class);
				BigDecimal   d5 = new BigDecimal(yearunit);
				Double d51 = d5.setScale(2, RoundingMode.HALF_UP).doubleValue();
				m4.invoke(result4, d41);
				m5.invoke(result5, d51);

			} catch (Exception e) {
				throw new ServiceException(e);
			}
			i++;
		}

		resultVOMapper.execDeleteSql("delete from fea_design_result  where project_id='"+fea_design_heatVO.getFeaProjectB().getId()+"'");
		resultVOMapper.insert(result1);resultVOMapper.insert(result2);resultVOMapper.insert(result3);
		resultVOMapper.insert(result4);resultVOMapper.insert(result5);

		//*********分区为 是*********//
		//表1 地热供暖项目设备清单1  地热供暖项目设备清单
		List<List<String>> rettable1 = new ArrayList<List<String>>();


		Double totalgs = 0.00;
		Double totalfl = 0.00;
		List<String> col1 = new ArrayList<String>();
		col1.add("地热井潜水泵");
		String col1str = "流量 "+getDouble2float(m)+" m³/h;\n "+ "扬程  "+getDouble2float(hq)+" m;\n "
				+ "电机功率 "+getDouble2float(Pq)+" kW ";
		col1.add(col1str);
		col1.add("台");
		col1.add(""+2*Nk);
		col1.add(""+getDouble2float(Cq1));
		col1.add(getDouble2float(Cq)+"");
		col1.add("15%");
		col1.add(""+getDouble2float(Cq*0.15));
		col1.add(Nk+"用"+Nk+"备");

		totalgs = getDouble2float(Cq)+totalgs;
		totalfl = totalfl+getDouble2float(Cq*0.15);

		List<String> col2 = new ArrayList<String>();
		col2.add("旋流除砂器");
		col2.add(" ");
		col2.add("台");
		col2.add(""+Nk);
		col2.add("5");
		col2.add(""+5*Nk);
		col2.add("15%");
		col2.add(""+getDouble2float(5*Nk*0.15));
		col2.add(" ");

		totalgs = totalgs+5*Nk;
		totalfl = totalfl+getDouble2float(5*Nk*0.15);

		List<String> col3 = new ArrayList<String>();
		col3.add("汽水分离器");
		col3.add(" ");
		col3.add("台");
		col3.add(" "+Nk);
		col3.add("0.5");
		col3.add(" "+0.5*Nk);
		col3.add(" 15% ");
		col3.add(" "+getDouble2float((0.5*Nk)*0.15));
		col3.add(" ");

		totalgs = getDouble2float(0.5*Nk)+totalgs;
		totalfl = totalfl+getDouble2float((0.5*Nk)*0.15);

		List<String> col4 = new ArrayList<String>();
		col4.add("过滤器");
		col4.add(" ");
		col4.add(" 套 ");
		col4.add(""+Nk);
		col4.add("4");
		col4.add(""+4*Nk);
		col4.add("15%");
		col4.add(""+getDouble2float(4*Nk*0.15));
		col4.add(" ");

		totalgs = totalgs+4*Nk;
		totalfl = totalfl+getDouble2float(4*Nk*0.15);

		List<String> col5 = new ArrayList<String>();
		col5.add("板式换热器");
		String col5str = "换热量 "+getDouble2float(Qh1)+" kW;\n "+ "换热面积  "
				+getDouble2float(Ah1)+" m2;\n " + "设计压力 "+1.6+"Mpa ";
		col5.add(col5str);
		col5.add("台");
		col5.add(""+getDouble2float(Nh1));
		col5.add(""+getDouble2float(Ch1));
		col5.add(""+getDouble2float(Ch1*Nh1));
		col5.add("15%");
		col5.add(""+getDouble2float(Ch1*Nh1*0.15));
		col5.add("一级换热、钛板温度：110℃ ; \n "+"各承担70%负荷");

		totalgs = totalgs+getDouble2float(Ch1*Nh1);
		totalfl = totalfl+getDouble2float(Ch1*Nh1*0.15);


		List<String> col6 = new ArrayList<String>();
		col6.add("板式换热器");
		String col6str = "换热量 "+getDouble2float(Qh2)+" kW;\n "+ "换热面积  "+
				getDouble2float(Ah2)+" m2;\n " + "设计压力 "+1.6+"Mpa ";
		col6.add(col6str);
		col6.add("台");
		col6.add(""+getDouble2float(Nh2));
		col6.add(""+getDouble2float(Ch2));
		col6.add(""+getDouble2float(Ch2*Nh2));
		col6.add("15%");
		col6.add(""+getDouble2float(Ch2*Nh2*0.15));
		col6.add("二级换热、钛板温度：110℃ ; \n "+"各承担70%负荷");
		totalgs = totalgs+getDouble2float(Ch2*Nh2);
		totalfl = totalfl+getDouble2float(Ch2*Nh2*0.15);

		List<String> col7= new ArrayList<String>();
		col7.add("热泵机组");
		String col7str = "制热量 "+getDouble2float(Qr2)+" kW;\n "+ "电功率 "+getDouble2float(Pr2) ;
		col7.add(col7str);
		col7.add("台");
		col7.add(""+jznum);
		col7.add(""+getDouble2float(Cr2));
		col7.add(""+getDouble2float(jznum*Cr2));
		col7.add("15%");
		col7.add(""+getDouble2float(jznum*Cr2*0.15));
		col7.add(" ");
		totalgs = totalgs+getDouble2float(jznum*Cr2);
		totalfl = totalfl+getDouble2float(jznum*Cr2*0.15);

		List<String> col8= new ArrayList<String>();
		col8.add("循环水泵");
		String col8str = "流量"+getDouble2float((M1/2))+" m³/h;\n  扬程"+
				getDouble2float(hhx1)+" m;\n "+ "功率 "+getDouble2float(Phx1)+"kw;" ;
		col8.add(col8str);
		col8.add("台");
		col8.add(""+2*Nk);
		col8.add(""+getDouble2float(Chx1));
		col8.add(""+getDouble2float(2*Nk*Chx1));
		col8.add("15%");
		col8.add(""+getDouble2float((2*Nk*Chx1)*0.15));
		col8.add(" 高区"+Nk+"用"+Nk+"备变频 "); //1
		totalgs = totalgs+getDouble2float(2*Nk*Chx1);
		totalfl = totalfl+getDouble2float((2*Nk*Chx1)*0.15);

		List<String> col9= new ArrayList<String>();
		col9.add("循环水泵");
		String col9str = "流量"+getDouble2float(M1/2)+" m³/h;\n  扬程"
				+getDouble2float(hdx1)+" m;\n "+ "功率 "+getDouble2float(Pdx1)+"kw;" ;
		col9.add(col9str);
		col9.add("台");
		col9.add(""+2*Nk);
		col9.add(""+getDouble2float(Cdx1));
		col9.add(""+getDouble2float(2*Nk*Cdx1));
		col9.add("15%");
		col9.add(""+getDouble2float((2*Nk*Cdx1)*0.15));
		col9.add(" 低区"+Nk+"用"+Nk+"备变频 ");//2
		totalgs = totalgs+getDouble2float(2*Nk*Cdx1);
		totalfl = totalfl+getDouble2float((2*Nk*Cdx1)*0.15);

		List<String> col10= new ArrayList<String>();
		col10.add("循环水泵");
		String col10str = "流量"+getDouble2float(M3/2)+" m³/h;\n  扬程"
				+getDouble2float(hhx3)+" m;\n "+ "功率 "+getDouble2float(Phx3)+"kw;" ;
		col10.add(col10str);
		col10.add("台");
		col10.add(""+2*Nk);
		col10.add(""+getDouble2float(Chx3));
		col10.add(""+getDouble2float(2*Nk*Chx3));
		col10.add("15%");
		col10.add(""+getDouble2float((2*Nk*Chx3)*0.15));
		col10.add(" 低区热泵冷凝器 "+Nk+"用"+Nk+"备变频 ");//3

		totalgs = totalgs+getDouble2float(2*Nk*Chx3);
		totalfl = totalfl+getDouble2float((2*Nk*Chx3)*0.15);

		List<String> col11= new ArrayList<String>();
		col11.add("循环水泵");
		String col11str = "流量"+getDouble2float(M2)+" m³/h;\n  扬程"+getDouble2float(hdx3)+" m;功率 "
				+getDouble2float(Pdx3)+"kW";
		col11.add(col11str);
		col11.add("台");
		col11.add(""+2*Nk);
		col11.add(""+getDouble2float(Cdx3));
		col11.add(""+getDouble2float(2*Nk*Cdx3));
		col11.add("15%");
		col11.add(""+getDouble2float((2*Nk*Cdx3)*0.15));
		col11.add(" 热泵蒸发器 "+Nk+"用"+Nk+"备变频 ");//4
		totalgs = totalgs+getDouble2float(2*Nk*Cdx3);
		totalfl = totalfl+getDouble2float((2*Nk*Cdx3)*0.15);

		List<String> col12= new ArrayList<String>();
		col12.add("循环水泵");
		String col12str = "流量"+getDouble2float(M2)+" m³/h;\n  扬程"+getDouble2float(hx2)
		+" m;功率 "+getDouble2float(Px2)+"kW";
		col12.add(col12str);
		col12.add("台");
		col12.add(""+2*Nk);
		col12.add(""+getDouble2float(Cdx2));
		col12.add(""+getDouble2float(2*Nk*Cdx2));
		col12.add("15%");
		col12.add(""+getDouble2float(2*Nk*Cdx2*0.15));
		col12.add(" 热泵蒸发器 "+Nk+"用"+Nk+"备变频 ");//5

		totalgs = totalgs+getDouble2float(2*Nk*Cdx2);
		totalfl = totalfl+getDouble2float(2*Nk*Cdx2*0.15);

		List<String> col13= new ArrayList<String>();
		col13.add("变频控制柜");
		col13.add("");
		col13.add("套");
		col13.add(""+Nk);
		col13.add(""+getDouble2float(Chxb1));
		col13.add(""+getDouble2float(Nk*Chxb1));
		col13.add("15%");
		col13.add(""+getDouble2float(Nk*Chxb1*0.15));
		col13.add(" 高区循环水泵用 ");
		totalgs = totalgs+getDouble2float(Nk*Chxb1);
		totalfl = totalfl+getDouble2float(Nk*Chxb1*0.15);

		List<String> col14= new ArrayList<String>();
		col14.add("变频控制柜");
		col14.add("");
		col14.add("套");
		col14.add(""+Nk);
		col14.add(""+getDouble2float(Cdxb1));
		col14.add(""+getDouble2float(Nk*Cdxb1));
		col14.add("15%");
		col14.add(""+getDouble2float(Nk*Cdxb1*0.15));
		col14.add(" 低区循环水泵用 ");
		totalgs = totalgs+getDouble2float(Nk*Cdxb1);
		totalfl = totalfl+getDouble2float(Nk*Cdxb1*0.15);


		List<String> col15= new ArrayList<String>();
		col15.add("变频控制柜");
		col15.add("");
		col15.add("套");
		col15.add(""+Nk);
		col15.add(""+getDouble2float(Chxb3));
		col15.add(""+getDouble2float(Nk*Chxb3));
		col15.add("15%");
		col15.add(""+getDouble2float(Nk*Chxb3*0.15));
		col15.add(" 高区热泵冷凝器循环水泵用 ");
		
		totalgs = totalgs+getDouble2float(Nk*Cdxb1);
		totalfl = totalfl+getDouble2float(Nk*Cdxb1*0.15);

		List<String> col16= new ArrayList<String>();
		col16.add("变频控制柜");
		col16.add("");
		col16.add("套");
		col16.add(""+Nk);
		col16.add(""+getDouble2float(Cdxb3));
		col16.add(""+getDouble2float(Nk*Cdxb3));
		col16.add("15%");
		col16.add(""+getDouble2float(Nk*Cdxb3*0.15));
		col16.add("低区热泵冷凝器循环水泵用");
		totalgs = totalgs+getDouble2float(Nk*Cdxb3);
		totalfl = totalfl+getDouble2float(Nk*Cdxb3*0.15);

		List<String> col17= new ArrayList<String>();
		col17.add("变频控制柜");
		col17.add("");
		col17.add("套");
		col17.add(""+Nk);
		col17.add(""+getDouble2float(Cdxb2));
		col17.add(""+getDouble2float(Nk*Cdxb2));
		col17.add("15%");
		col17.add(""+getDouble2float(Nk*Cdxb2*0.15));
		col17.add("热泵蒸发器循环水泵用");
		totalgs = totalgs+getDouble2float(Nk*Cdxb2);
		totalfl = totalfl+getDouble2float(Nk*Cdxb2*0.15);

		List<String> col18= new ArrayList<String>();
		col18.add("补水泵");
		String col18str = "流量"+getDouble2float(0.03*(M1+M3)/2)+" m³/h;\n  扬程"+getDouble2float(hhb1)
		+" m;功率 "+getDouble2float(Phb1)+"kW";
		col18.add(col18str);
		col18.add("台");
		col18.add(""+2*Nk);
		col18.add(""+getDouble2float(Chb1));
		col18.add(""+getDouble2float(2*Nk*Chb1));
		col18.add("15%");
		col18.add(""+getDouble2float(2*Nk*Chb1*0.15));
		col18.add(" 高区 "+Nk+"用"+Nk+"备变频 ");

		totalgs = totalgs+getDouble2float(2*Nk*Chb1);
		totalfl = totalfl+getDouble2float(2*Nk*Chb1*0.15);

		List<String> col19= new ArrayList<String>();
		col19.add("补水泵");
		String col19str =  "流量"+getDouble2float(0.03*(M1+M3)/2)+" m³/h;\n  扬程"
				+getDouble2float(hb2)+" m;功率 "+getDouble2float(Pb2)+"kW";
		col19.add(col19str);
		col19.add("台");
		col19.add(""+2*Nk);
		col19.add(""+getDouble2float(Cb2));
		col19.add(""+getDouble2float(2*Nk*Cb2));
		col19.add("15%");
		col19.add(""+getDouble2float(2*Nk*Cb2*0.15));
		col19.add("高区  "+Nk+"用"+Nk+"备变频 ");
		totalgs = totalgs+getDouble2float(2*Nk*Cb2);
		totalfl = totalfl+getDouble2float(2*Nk*Cb2*0.15);
		
		List<String> colend= new ArrayList<String>();
		colend.add("换热站工程小计");
		colend.add(" ");
		colend.add(" ");
		colend.add(" ");
		colend.add(" ");
		colend.add(""+getDouble2float(totalgs));
		colend.add("15%");
		colend.add(""+getDouble2float(totalfl));
		colend.add(" ");

		rettable1.add(col1);rettable1.add(col2);rettable1.add(col3);
		rettable1.add(col4);rettable1.add(col5);rettable1.add(col6);
		rettable1.add(col7);rettable1.add(col8);rettable1.add(col9);
		rettable1.add(col10);rettable1.add(col11);rettable1.add(col12);
		rettable1.add(col13);rettable1.add(col14);rettable1.add(col15);
		rettable1.add(col16);rettable1.add(col17);rettable1.add(col18);
		rettable1.add(col19);//rettable1.add(colend);

		retmap.put("设备清单", rettable1);
		
		
		List<List<String>> djlist = new ArrayList<List<String>>();
		List<String> d1 = new ArrayList<String>();
		d1.add("一级板换供热量Q1");
		d1.add(""+getDouble2float(Q1));
		d1.add("千瓦时");
		List<String> d2 = new ArrayList<String>();
		d2.add("一级板换最大供热面积A1");
		d2.add(""+getDouble2float(A1));
		d2.add("平方米");
		List<String> d3 = new ArrayList<String>();
		d3.add("二级板换+热泵供热量Q2");
		d3.add(""+getDouble2float(Q2));
		d3.add("千瓦时");
		List<String> d4 = new ArrayList<String>();
		d4.add("二级板换供热面积A2");
		d4.add(""+getDouble2float(A2));
		d4.add("平方米");
		List<String> d5 = new ArrayList<String>();
		d5.add("单井最大供热面积Ad");
		d5.add(""+getDouble2float(Ad));
		d5.add("平方米");
		djlist.add(d1);djlist.add(d2);djlist.add(d3);
		djlist.add(d4);djlist.add(d5);
		
		
		retmap.put("单井供热", djlist);
		
		return retmap;
	}

	public static Double getDouble2float(Double m){
		BigDecimal  bm = new BigDecimal(m);
		Double bm1 = bm.setScale(2, RoundingMode.HALF_UP).doubleValue();

		return bm1;
	}


}
