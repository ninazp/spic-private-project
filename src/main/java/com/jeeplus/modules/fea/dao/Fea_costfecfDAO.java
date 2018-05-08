package com.jeeplus.modules.fea.dao;

import java.util.Date;

import com.jeeplus.modules.fea.entity.fecl.Fea_costfecfVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class Fea_costfecfDAO {

	public static Fea_costfecfVO insertFea_costfecf(Fea_costfecfVOMapper  basemapper,FeaProjectB projectvo){
		
		Fea_costfecfVO vo = new Fea_costfecfVO();
		vo.setFeaProjectB(projectvo);
		vo.setProjectcode(projectvo.getProjectCode());
		vo.setProjectname(projectvo.getProjectName());
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());	
		vo.setCreateDate(new Date());
		
		Double area = projectvo.getHeatArea();
		Double areaprice =  projectvo.getPrice();

		Double flowamt = area*areaprice*0.85*0.2;
		
		vo.setCircularate(4.35);		// 短期借款利率（%）
		vo.setFlowamt(flowamt);		// 流动资金指标（万元）
		vo.setFlowloanprop(70.00);		// 流动资金贷款比例（%）
	    vo.setFlowcaprate(4.35);
	    vo.setLangrate(4.9);
	    vo.setLangyear(15);
		
		basemapper.insert(vo);
		
		return vo;
	}
	
	
	
}
