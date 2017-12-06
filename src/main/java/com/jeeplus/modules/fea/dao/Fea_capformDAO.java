package com.jeeplus.modules.fea.dao;

import java.util.Date;

import com.jeeplus.modules.fea.entity.funds.Fea_capformVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class Fea_capformDAO {

public static void insertFea_capform(Fea_capformVOMapper  basemapper,FeaProjectB projectvo){
		
	    Fea_capformVO vo = new Fea_capformVO();
	    vo.setFeaProjectB(projectvo);
		vo.setProjectcode(projectvo.getProjectCode());
		vo.setProjectname(projectvo.getProjectName());
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());	
		vo.setCreateDate(new Date());
		
		vo.setFixassetprop(100.00);;		// 固定资产形成比例（%）
		vo.setUselifefat(20.00);;		// 折旧年限（%）
		vo.setResidualrate(5.00);;		// 残值率（%）
		vo.setIntangibleprop(0.00);;		// 无形资产形成比例（%）
		vo.setUsefullitb(15.00);;		// 折旧年限（%）
		vo.setOtherprop(0.00);;		// 其他资产形成比例（%）
		vo.setUselifeother(15.00);;
		
		basemapper.insert(vo);
		
	}
	
}
