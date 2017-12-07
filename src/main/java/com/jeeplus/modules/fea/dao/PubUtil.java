package com.jeeplus.modules.fea.dao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.modules.fea.entity.fecl.Fea_costfecfVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcTVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.income.Fea_incomesetVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;

public class PubUtil {

	@Autowired
	private Fea_fundssrcVOMapper fea_fundssrcVOMapper;
	@Autowired
	private Fea_fundssrcTVOMapper fea_fundssrcTVOMapper;
	@Autowired
	private Fea_fundssrcBVOMapper fea_fundssrcBVOMapper;
	@Autowired
	private Fea_investdisVOMapper fea_investdisVOMapper;
	@Autowired
	private Fea_investdisBVOMapper fea_investdisBVOMapper;
	@Autowired
	private Fea_productcostVOMapper fea_productcostVOmapper;
	@Autowired
	private Fea_productcostBVOMapper fea_productcostBVOmapper;
	@Autowired
	private Fea_capformVOMapper fea_capformVOMapper;
	@Autowired
	private Fea_costinfoVOMapper fea_costinfoVOMapper;
	@Autowired
	private Fea_incosubsidyVOMapper fea_incosubsidyVOMapper;
	@Autowired
	private Fea_incomesetVOMapper fea_incomesetVOMapper;
	@Autowired
	private Fea_costfecfVOMapper fea_costfecfVOMapper;
	
	public static String getid(int number){
		if(number < 1){ 
			return null; 
		} 
		StringBuffer rtstr = new StringBuffer();
		for(int i=0;i<1;i++){ 
			String uuid = UUID.randomUUID().toString(); 
			String idn = uuid.replaceAll("-", "");
			rtstr.append(idn); 
		} 
		return rtstr.toString(); 
	}
	
	public void setdefaultData(FeaProjectB projectvo){
		//基本参数
		IncomesetDAO.insertIncome(fea_incomesetVOMapper, projectvo);
		//生产成本 - 带年份
		Fea_productcostDAO.insertFea_productcost(fea_productcostVOmapper, fea_productcostBVOmapper, projectvo);
		//带年份
		Fea_incosubsidyDAO.insertFea_incosubsidy(fea_incosubsidyVOMapper, projectvo);
		//财务费用
		Fea_costfecfVO ecfvo = Fea_costfecfDAO.insertFea_costfecf(fea_costfecfVOMapper, projectvo);
		//资产形成
		Fea_capformDAO.insertFea_capform(fea_capformVOMapper, projectvo);
		//产品种类 -- 带年份
		Fea_costinfoDAO.insertFea_costinfo(fea_costinfoVOMapper, projectvo);
		Fea_fundssrcVO fsrvo = Fea_fundssrcDAO.insertFea_fundssrc(fea_fundssrcVOMapper,fea_fundssrcBVOMapper, fea_fundssrcTVOMapper, projectvo);
		Double flowloanamt = ecfvo.getFlowamt()*ecfvo.getFlowloanprop()/100;
		Double flowzyamt = ecfvo.getFlowamt() - flowloanamt;
		Fea_fundssrcDAO.insertFea_investdis(fsrvo, fea_investdisVOMapper, fea_investdisBVOMapper,
				projectvo, flowloanamt, flowzyamt);
	}
}
