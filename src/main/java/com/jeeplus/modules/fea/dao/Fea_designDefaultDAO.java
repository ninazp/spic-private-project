package com.jeeplus.modules.fea.dao;

import java.util.Date;

import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.entity.heatben.Fea_design_heatbenVO;
import com.jeeplus.modules.fea.entity.heattrans.Fea_design_heattransVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.set.Fea_design_setVO;
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;
import com.jeeplus.modules.fea.mapper.design.Fea_design_heatVOMapper;
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
		vo.setOffice(UserUtils.getOfficeList().get(0).getId());
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
	
}
