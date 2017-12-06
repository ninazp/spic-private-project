package com.jeeplus.modules.fea.dao;

import java.lang.reflect.Method;
import java.util.Date;

import com.jeeplus.modules.fea.entity.costinfo.Fea_costinfoVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class Fea_costinfoDAO {

	public static void insertFea_costinfo(Fea_costinfoVOMapper  basemapper,FeaProjectB projectvo){
		Fea_costinfoVO vo = new Fea_costinfoVO();
		vo.setFeaProjectB(projectvo);
		vo.setProjectcode(projectvo.getProjectCode());
		vo.setProjectname(projectvo.getProjectName());
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());	
		vo.setCreateDate(new Date());
		
		vo.setCostype("入住率");
		vo.setUnit("%");
		
		if(null!=projectvo.getCountyears() && projectvo.getCountyears()>0){
		   for(int i=0;i<projectvo.getCountyears();i++){
				try {
					if(i==0){
					   Method m = vo.getClass().getMethod("setYear",Double.class);
					   m.invoke(vo, 0.00);
					}else{
						 Method m = vo.getClass().getMethod("setYear"+(i+1),Double.class);
						   m.invoke(vo, 0.00);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
		   }
		}
		
		basemapper.insert(vo);
	}
	
}
