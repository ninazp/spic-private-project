package com.jeeplus.modules.fea.dao;

import java.lang.reflect.Method;
import java.util.Date;

import com.jeeplus.modules.fea.entity.procost.Fea_productcostBVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class Fea_productcostDAO {

	public static void insertFea_productcost(Fea_productcostVOMapper basemapper1,
			Fea_productcostBVOMapper basemapper2,FeaProjectB projectvo){
		
		Fea_productcostVO vo = new Fea_productcostVO();
		vo.setFeaProjectB(projectvo);
		vo.setProjectcode(projectvo.getProjectCode());
		vo.setProjectname(projectvo.getProjectName());
		String idstr = PubUtil.getid(1);
		vo.setId(idstr);
		vo.setCreateBy(UserUtils.getUser());	
		vo.setCreateDate(new Date());
		
		vo.setPersons(2.00);
		vo.setPerwage(4.00);
		vo.setWelfare(40.00);
		vo.setMaterial(0.00);
		vo.setInsurance(0.23);
		vo.setWateramt(0.00);
		vo.setHeatdeposit(32.15);
		vo.setIntangibletx(10.00);
		vo.setOtherassettx(10.00);
		vo.setOthercost(0.00);
		
		Fea_productcostBVO bvo = new Fea_productcostBVO();
		bvo.setId(PubUtil.getid(1));
		bvo.setCreateBy(UserUtils.getUser());	
		bvo.setCreateDate(new Date());
		bvo.setFea_productcost(vo);
		
		bvo.setCosttype("3");		// 成本种类
		bvo.setCostunit("%");
		
		if(null!=projectvo.getCountyears() && projectvo.getCountyears()>0){
			   for(int i=0;i<projectvo.getCountyears();i++){
					try {
						Method m = bvo.getClass().getMethod("setYear"+(i+1),Double.class);
						if(i<3){
						   m.invoke(bvo, 0.00);
						}else if(i==3){
							m.invoke(bvo, 0.20);
						}else if(i>3 && i<7){
							m.invoke(bvo, 0.40);
						}else{
							m.invoke(bvo, 0.60);
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
			   }
			}
		
		Fea_productcostBVO bvo2 = getbvo("1","个",2.00,projectvo.getCountyears());
		bvo2.setFea_productcost(vo);
		Fea_productcostBVO bvo3 = getbvo("2","万元",32.15,projectvo.getCountyears());
		bvo3.setFea_productcost(vo);
		
		basemapper1.insert(vo);
		basemapper2.insert(bvo);
		basemapper2.insert(bvo2);
		basemapper2.insert(bvo3);
	}
	
	public static Fea_productcostBVO getbvo(String costtype,String unit,Double val,Double countyear){
		Fea_productcostBVO bvo = new Fea_productcostBVO();
		bvo.setId(PubUtil.getid(1));
		bvo.setCreateBy(UserUtils.getUser());	
		bvo.setCreateDate(new Date());
		
		bvo.setCosttype(costtype);		// 成本种类
		bvo.setCostunit(unit);
		for(int i=0;i<countyear;i++){
					try {
						Method m = bvo.getClass().getMethod("setYear"+(i+1),Double.class);
						m.invoke(bvo, val);
					} catch(Exception e) {
						e.printStackTrace();
					}
			   }
		
		return bvo;
	}
	
}
