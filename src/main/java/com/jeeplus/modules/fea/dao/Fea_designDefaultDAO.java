package com.jeeplus.modules.fea.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.entity.equiplst.Fea_design_equiplst2VO;
import com.jeeplus.modules.fea.entity.heatben.Fea_design_heatbenVO;
import com.jeeplus.modules.fea.entity.heattrans.Fea_design_heattransVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.set.Fea_design_setVO;
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;
import com.jeeplus.modules.fea.mapper.design.Fea_design_heatVOMapper;
import com.jeeplus.modules.fea.mapper.equiplst.Fea_design_equiplst2VOMapper;
import com.jeeplus.modules.fea.mapper.heatben.Fea_design_heatbenVOMapper;
import com.jeeplus.modules.fea.mapper.heattrans.Fea_design_heattransVOMapper;
import com.jeeplus.modules.fea.mapper.set.Fea_design_setVOMapper;
import com.jeeplus.modules.fea.mapper.transfer.Fea_design_transferVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;


public class Fea_designDefaultDAO {

	/**
	 * 方案设计基本参数-预置参数
	 * @author Administrator
	 *
	 */
	public static void insertFeaDesignSet(Fea_design_setVOMapper vomapper , FeaProjectB projectvo){
		Fea_design_setVO vo = new Fea_design_setVO();
		vo.setFeaProjectB(projectvo);
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());
		vo.setCreateDate(new Date());
		vo.setOffice(UserUtils.getOfficeList().get(0));
		vo.setSumheatefficient(0.92);//综合热效率
		vo.setLoadrate(1.00);
		vo.setPumprate(5.00);
		vo.setHddept(10.00);//潜水泵水下位置（米）
		vo.setHdlose(20.00);
		vo.setRou(1000.00);
		vo.setHx2(32.00);
		vo.setHb2(20.00);
		vo.setMpumpcoe(3.00);
		vomapper.insert(vo);
	}

	/**
	 * 换热参数
	 * @param vomapper
	 * @param projectvo
	 */
	public static void insertFea_design_transfer(Fea_design_transferVOMapper vomapper,FeaProjectB projectvo){
		Fea_design_transferVO vo = new Fea_design_transferVO();
		vo.setFeaProjectB(projectvo);
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());
		vo.setCreateDate(new Date());
		vo.setOffice(UserUtils.getOfficeList().get(0));
		vo.setOneoutheat(40.00);// 一级一次侧出口温度（摄氏度）
		vo.setTwooutheat(45.00);// 二次侧供水温度（摄氏度）
		vo.setTwobackheat(35.00);// 二次侧回水温度（摄氏度）
		vo.setTwozfoutheat(18.00);// 二级二次蒸发器侧供水温度（摄氏度）
		vo.setTwozfbacktheat(10.00);// 二级二次蒸发器侧回水温度（摄氏度）
		vo.setBackhgheat(15.00);// 回灌水温度（摄氏度）

		vomapper.insert(vo);
	}

	/**
	 * 	换热器价格
	 */
	public static void insertFea_design_heattrans(Fea_design_heattransVOMapper vomapper,FeaProjectB projectvo){

		for(int i=0;i<3;i++){
			Fea_design_heattransVO vo = new Fea_design_heattransVO();
			vo.setFeaProjectB(projectvo);
			vo.setId(PubUtil.getid(1));
			vo.setCreateBy(UserUtils.getUser());
			vo.setCreateDate(new Date());
			vo.setOffice(UserUtils.getOfficeList().get(0));
			if(i==0){
				vo.setPrice(1800.0);
				vo.setLowarea(1.00);
				vo.setHigharea(40.00);
			}else if(i==1){
				vo.setPrice(1400.0);
				vo.setLowarea(40.0);
				vo.setHigharea(70.0);
			}else if(i==2){
				vo.setPrice(1200.0);
				vo.setLowarea(70.0);
				vo.setHigharea(1000000.0);
			}
			vomapper.insert(vo);
		}
	}

	public static void insertFea_design_heatben(Fea_design_heatbenVOMapper vomapper,FeaProjectB projectvo){
		Double [] qr2 = new Double []{
				3575.0	,	3259.0,3011.0,	 2652.0,
				2445.0,	 2251.0,1982.0,1788.0,
				1629.0,	 1506.0,	1326.0,	 1168.0,
				1006.0,	 851.0	,748.0	,686.0	,
				584.0	, 503.0	, 426.0	, 374.0	,
				338.0	, 271.0	, 239.0	,  209.0	,  
				174.0
		};

		Double [] pr2 = new Double []{
				739.0,	670.0	,	611.0	,541.0,	
				502.0,		450.0,	398.0,	370.0,
				335.0,	306.0,	270.0,	235.0,	
				203.0,	175.0	,154.0	,140.0	,
				117.0	,101.0	,87.4	 , 76.7	 , 
				69.0	 , 56.3	 , 49.5	 , 43.2	 , 

				34.9	
		};

		Double [] cr2 = new Double []{
				206.0,188.0,175.0,156.0,  
				145.0, 134.0,  120.0, 109.0, 
				100.0,  94.0 ,  	84.0 ,  76.0 ,  
				67.0 ,  58.0  ,  53.0  ,  49.0  ,  
				44.0  ,  40.0  ,  36.0  ,  33.0  ,  
				30.0  ,  27.0  ,  25.0  ,24.0  ,
				22.0  

		};
		for(int i=0;i<qr2.length;i++){
			Fea_design_heatbenVO vo = new Fea_design_heatbenVO();
			vo.setFeaProjectB(projectvo);
			vo.setId(PubUtil.getid(1));
			vo.setCreateBy(UserUtils.getUser());
			vo.setCreateDate(new Date());
			vo.setOffice(UserUtils.getOfficeList().get(0));
			vo.setQr2(qr2[i]);
			vo.setPr2(pr2[i]);
			vo.setCr2(cr2[i]);
			vomapper.insert(vo);
		}
	}

	public static void insertFea_design_heat(Fea_design_heatVOMapper vomapper,FeaProjectB projectvo){
		Fea_design_heatVO vo = new Fea_design_heatVO();
		vo.setFeaProjectB(projectvo);
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());
		vo.setCreateDate(new Date());
		vo.setOffice(UserUtils.getOfficeList().get(0));
		
		vo.setHeatload(45.00);
		vo.setHeatarea(projectvo.getHeatArea()*10000);
		vo.setPowerfee(0.486);
		vo.setHeatdays(projectvo.getHeatDays());
		vo.setDayheathours(24.00);
		vo.setBuildheight(45.00);
		vo.setAreaselect("0");
		vo.setHoleheight(1200.00);
		vo.setFlowcount(100.00);
		vo.setOutheat(68.00);
		vo.setWaterlevel(80.00);
		vo.setHgpbac(1.00);
		vo.setHgpbbh(1.00);
		
		vomapper.insert(vo);
	}
	
	public static void insetFea_design_equiplst2VO(Fea_design_equiplst2VOMapper vomapper,FeaProjectB projectvo){
		
		List<Fea_design_equiplst2VO> li = new ArrayList<Fea_design_equiplst2VO>();
		
		Fea_design_equiplst2VO vo1 = new Fea_design_equiplst2VO();
		vo1.setId(PubUtil.getid(1));
		vo1.setCreateBy(UserUtils.getUser());
		vo1.setCreateDate(new Date());
		vo1.setOffice(UserUtils.getOfficeList().get(0));
		vo1.setFeaProjectB(projectvo);
		vo1.setEquipname("除污器");
		vo1.setPerforparam("DN300 立式直通除污器");
		vo1.setUnit("台");
		vo1.setNum(2.0);
		vo1.setPrice(0.318);
		vo1.setRemarks("高区");
		li.add(vo1);
		
		Fea_design_equiplst2VO vo2 = new Fea_design_equiplst2VO();
		vo2.setId(PubUtil.getid(1));
		vo2.setCreateBy(UserUtils.getUser());
		vo2.setCreateDate(new Date());
		vo2.setOffice(UserUtils.getOfficeList().get(0));
		vo2.setFeaProjectB(projectvo);
		vo2.setEquipname("除污器");
		vo2.setPerforparam("DN300 立式直通除污器");
		vo2.setUnit("台");
		vo2.setNum(2.0);
		vo2.setPrice(1.17);
		vo2.setRemarks("低区");
		li.add(vo2);
		
		Fea_design_equiplst2VO vo3 = new Fea_design_equiplst2VO();
		vo3.setId(PubUtil.getid(1));
		vo3.setCreateBy(UserUtils.getUser());
		vo3.setCreateDate(new Date());
		vo3.setOffice(UserUtils.getOfficeList().get(0));
		vo3.setFeaProjectB(projectvo);
		vo3.setEquipname("高位水箱");
		vo3.setPerforparam("1000X1000X1000");
		vo3.setUnit("台");
		vo3.setNum(2.0);
		vo3.setPrice(0.32);
		vo3.setRemarks("");
		li.add(vo3);
		
		Fea_design_equiplst2VO vo4 = new Fea_design_equiplst2VO();
		vo4.setId(PubUtil.getid(1));
		vo4.setCreateBy(UserUtils.getUser());
		vo4.setCreateDate(new Date());
		vo4.setOffice(UserUtils.getOfficeList().get(0));
		vo4.setFeaProjectB(projectvo);
		vo4.setEquipname("定压补水装置");
		vo4.setPerforparam("");
		vo4.setUnit("台");
		vo4.setNum(2.0);
		vo4.setPrice(0.85);
		vo4.setRemarks("一用一备");
		li.add(vo4);
		
		Fea_design_equiplst2VO vo5 = new Fea_design_equiplst2VO();
		vo5.setId(PubUtil.getid(1));
		vo5.setCreateBy(UserUtils.getUser());
		vo5.setCreateDate(new Date());
		vo5.setOffice(UserUtils.getOfficeList().get(0));
		vo5.setFeaProjectB(projectvo);
		vo5.setEquipname("定压补水装置");
		vo5.setPerforparam("");
		vo5.setUnit("台");
		vo5.setNum(2.0);
		vo5.setPrice(1.06);
		vo5.setRemarks("低区热泵蒸发器");
		li.add(vo5);
		
		Fea_design_equiplst2VO vo6 = new Fea_design_equiplst2VO();
		vo6.setId(PubUtil.getid(1));
		vo6.setCreateBy(UserUtils.getUser());
		vo6.setCreateDate(new Date());
		vo6.setOffice(UserUtils.getOfficeList().get(0));
		vo6.setFeaProjectB(projectvo);
		vo6.setEquipname("补水箱");
		vo6.setPerforparam("3500X4500X2000");
		vo6.setUnit("台");
		vo6.setNum(2.0);
		vo6.setPrice(4.24);
		vo6.setRemarks("高区");
		li.add(vo6);
		
		Fea_design_equiplst2VO vo7 = new Fea_design_equiplst2VO();
		vo7.setId(PubUtil.getid(1));
		vo7.setCreateBy(UserUtils.getUser());
		vo7.setCreateDate(new Date());
		vo7.setOffice(UserUtils.getOfficeList().get(0));
		vo7.setFeaProjectB(projectvo);
		vo7.setEquipname("全自动软化水设备");
		vo7.setPerforparam("");
		vo7.setUnit("台");
		vo7.setNum(2.0);
		vo7.setPrice(10.60);
		vo7.setRemarks("低区");
		li.add(vo7);
		
		Fea_design_equiplst2VO vo8 = new Fea_design_equiplst2VO();
		vo8.setId(PubUtil.getid(1));
		vo8.setCreateBy(UserUtils.getUser());
		vo8.setCreateDate(new Date());
		vo8.setOffice(UserUtils.getOfficeList().get(0));
		vo8.setFeaProjectB(projectvo);
		vo8.setEquipname("电控柜");
		vo8.setPerforparam("");
		vo8.setUnit("台");
		vo8.setNum(2.0);
		vo8.setPrice(21.20);
		vo8.setRemarks("");
		li.add(vo8);
		
		Fea_design_equiplst2VO vo9 = new Fea_design_equiplst2VO();
		vo9.setId(PubUtil.getid(1));
		vo9.setCreateBy(UserUtils.getUser());
		vo9.setCreateDate(new Date());
		vo9.setOffice(UserUtils.getOfficeList().get(0));
		vo9.setFeaProjectB(projectvo);
		vo9.setEquipname("变压器");
		vo9.setPerforparam("SCB15-1250");
		vo9.setUnit("台");
		vo9.setNum(2.0);
		vo9.setPrice(12.72);
		vo9.setRemarks("");
		li.add(vo9);
		
		Fea_design_equiplst2VO vo10 = new Fea_design_equiplst2VO();
		vo10.setId(PubUtil.getid(1));
		vo10.setCreateBy(UserUtils.getUser());
		vo10.setCreateDate(new Date());
		vo10.setOffice(UserUtils.getOfficeList().get(0));
		vo10.setFeaProjectB(projectvo);
		vo10.setEquipname("无人值守换热站自控系统");
		vo10.setPerforparam("");
		vo10.setUnit("台");
		vo10.setNum(2.0);
		vo10.setPrice(21.20);
		vo10.setRemarks("");
		li.add(vo10);
		
		Fea_design_equiplst2VO vo11 = new Fea_design_equiplst2VO();
		vo11.setId(PubUtil.getid(1));
		vo11.setCreateBy(UserUtils.getUser());
		vo11.setCreateDate(new Date());
		vo11.setOffice(UserUtils.getOfficeList().get(0));
		vo11.setFeaProjectB(projectvo);
		vo11.setEquipname("设备基础");
		vo11.setPerforparam("");
		vo11.setUnit("项");
		vo11.setNum(1.0);
		vo11.setPrice(4.69);
		vo11.setRemarks("");
		li.add(vo11);
		
		Fea_design_equiplst2VO vo12 = new Fea_design_equiplst2VO();
		vo12.setId(PubUtil.getid(1));
		vo12.setCreateBy(UserUtils.getUser());
		vo12.setCreateDate(new Date());
		vo12.setOffice(UserUtils.getOfficeList().get(0));
		vo12.setFeaProjectB(projectvo);
		vo12.setEquipname("配套电气系统（综合）");
		vo12.setPerforparam("");
		vo12.setUnit("套");
		vo12.setNum(1.0);
		vo12.setPrice(11.29);
		vo12.setRemarks("");
		li.add(vo12);
		
		Fea_design_equiplst2VO vo13 = new Fea_design_equiplst2VO();
		vo13.setId(PubUtil.getid(1));
		vo13.setCreateBy(UserUtils.getUser());
		vo13.setCreateDate(new Date());
		vo13.setOffice(UserUtils.getOfficeList().get(0));
		vo13.setFeaProjectB(projectvo);
		vo13.setEquipname("配套管道及其它（综合）");
		vo13.setPerforparam("");
		vo13.setUnit("套");
		vo13.setNum(1.0);
		vo13.setPrice(33.88);
		vo13.setRemarks("");
		li.add(vo13);
		
		for(Fea_design_equiplst2VO vo : li){
			vomapper.insert(vo);
		}
	}
	
}
