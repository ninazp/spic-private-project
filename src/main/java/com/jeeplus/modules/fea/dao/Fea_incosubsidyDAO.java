package com.jeeplus.modules.fea.dao;

import java.lang.reflect.Method;
import java.util.Date;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.subsidy.Fea_incosubsidyVO;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class Fea_incosubsidyDAO {

	public static void insertFea_incosubsidy(Fea_incosubsidyVOMapper  basemapper,FeaProjectB projectvo){
		Fea_incosubsidyVO vo = new Fea_incosubsidyVO();
		vo.setFeaProjectB(projectvo);
		vo.setProjectcode(projectvo.getProjectCode());
		vo.setProjectname(projectvo.getProjectName());
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());
		vo.setCreateDate(new Date());
		
		vo.setSubsidytype("配套费");
		vo.setUnitname("万元");;		// 单位
		vo.setIspaytax("2");		// 是否纳税
		
		if(null!=projectvo.getCountyears() && projectvo.getCountyears()>0){
		   for(int i=0;i<projectvo.getCountyears();i++){
				try {
					Method m = vo.getClass().getMethod("setYear"+(i+1));
					m.invoke(vo, 0.00);
				} catch(Exception e) {
					e.printStackTrace();
				}
		   }
		}
		
		basemapper.insert(vo);
	}
	
}
