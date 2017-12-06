package com.jeeplus.modules.fea.dao;

import java.util.Date;

import com.jeeplus.modules.fea.entity.income.Fea_incomesetVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.income.Fea_incomesetVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class IncomesetDAO {

	public static void insertIncome(Fea_incomesetVOMapper  basemapper,FeaProjectB projectvo){
		Fea_incomesetVO vo = new Fea_incomesetVO();
		vo.setFeaProjectB(projectvo);
		vo.setProjectcode(projectvo.getProjectCode());
		vo.setProjectname(projectvo.getProjectName());
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());
		vo.setCreateDate(new Date());
		vo.setVtaxrate(0.00);
	    vo.setIncomerate(25.00);;		// 所得税税率（%）
		vo.setUmctax(5.00);;		// 城市维护建设税率（%）
		vo.setSurtax(5.00);;		// 教育费附加费率（%）
		vo.setLegalaccfund(10.00);;		// 法定盈余公积金比例（%）
		vo.setAccfund(0.00);;		// 任意盈余公积金比例（%）
		vo.setIsaccfundwsd("0");		// 公积金提取不超过资本金的50%
		vo.setYflrprop(10.00);;		// 应付利润比例（%）
		vo.setIsvtaxjzjt("0");;		// 增值税即征即退50%
		vo.setIssdssjsm("0");;		// 所得税三免三减半
		vo.setCapinvesttype("等比例投入");;		// 资本金投入方式
		vo.setCapinvestprop(20.00);;		// 资本金比例（%）
		vo.setCapinvestrate(7.00);;		// 资本金基准收益率（%）
		vo.setIndustrysqrate(7.00);;		// 行业基准收益率（所得税前）（%）
		vo.setIndustryshrate(6.00);;		// 行业基准收益率（所得税后）（%）
		vo.setCapcostrate(0.00);;		// 资
		basemapper.insert(vo);
	}
	
	
}
