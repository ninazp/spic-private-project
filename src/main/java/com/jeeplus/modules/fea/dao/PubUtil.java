package com.jeeplus.modules.fea.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.modules.fea.entity.funds.Fea_investdisBVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostBVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.design.Fea_design_heatVOMapper;
import com.jeeplus.modules.fea.mapper.equiplst.Fea_design_equiplst2VOMapper;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.heatben.Fea_design_heatbenVOMapper;
import com.jeeplus.modules.fea.mapper.heattrans.Fea_design_heattransVOMapper;
import com.jeeplus.modules.fea.mapper.income.Fea_incomesetVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.quotation.FeaDesignReportMapper;
import com.jeeplus.modules.fea.mapper.result.Fea_design_resultVOMapper;
import com.jeeplus.modules.fea.mapper.set.Fea_design_setVOMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;
import com.jeeplus.modules.fea.mapper.transfer.Fea_design_transferVOMapper;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;

public class PubUtil { 

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
	@Autowired
	private Fea_design_heatVOMapper	heatVOMapper;
	@Autowired
	private Fea_design_heatbenVOMapper fea_design_heatbenVOMapper;
	@Autowired
	private Fea_design_heattransVOMapper fea_design_heattransVOMapper;
	@Autowired
	private Fea_design_setVOMapper fea_design_setVOMapper;
	@Autowired
	private Fea_design_transferVOMapper fea_design_transferVOMapper;
	@Autowired
	private Fea_design_equiplst2VOMapper fea_design_equiplst2VOMapper;
	@Autowired
	private Fea_design_resultVOMapper fea_design_resultmapper;
	@Autowired
	private FeaDesignReportMapper fea_design_reportmapper;



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
		Fea_costfecfDAO.insertFea_costfecf(fea_costfecfVOMapper, projectvo);
		//资产形成
		Fea_capformDAO.insertFea_capform(fea_capformVOMapper, projectvo);
		//产品种类 -- 带年份
		Fea_costinfoDAO.insertFea_costinfo(fea_costinfoVOMapper, projectvo);

		//投资分配
		Fea_fundssrcDAO.insertFea_investdis(fea_investdisVOMapper, fea_investdisBVOMapper,projectvo);

		heatVOMapper.execDeleteSql("DELETE from fea_design_heat where project_id='"+projectvo.getId()+"'");
		fea_design_heatbenVOMapper.execDeleteSql("DELETE from fea_design_heatben where project_id='"+projectvo.getId()+"'");
		fea_design_heattransVOMapper.execDeleteSql("DELETE from fea_design_heattrans where project_id='"+projectvo.getId()+"'");
		fea_design_transferVOMapper.execDeleteSql("DELETE from fea_design_transfer where project_id='"+projectvo.getId()+"'");
		fea_design_setVOMapper.execDeleteSql("DELETE from fea_design_set where project_id='"+projectvo.getId()+"'");
		fea_design_equiplst2VOMapper.execDeleteSql("DELETE from fea_design_equiplst2 where project_id='"+projectvo.getId()+"'");
		Fea_designDefaultDAO.insertFea_design_heat(heatVOMapper, projectvo);
		Fea_designDefaultDAO.insertFea_design_heatben(fea_design_heatbenVOMapper, projectvo);
		Fea_designDefaultDAO.insertFea_design_heattrans(fea_design_heattransVOMapper, projectvo);
		Fea_designDefaultDAO.insertFea_design_transfer(fea_design_transferVOMapper, projectvo);
		Fea_designDefaultDAO.insertFeaDesignSet(fea_design_setVOMapper, projectvo);
		Fea_designDefaultDAO.insetFea_design_equiplst2VO(fea_design_equiplst2VOMapper, projectvo);
	}

	public void deleteALLdata(FeaProjectB projectvo){

		String wheresql1 = "pkinvestdis in ( select c.id from fea_investdis c where c.project_id='"+projectvo.getId()+"')";
		List<Fea_investdisBVO> ibvolist = (List<Fea_investdisBVO>) PubBaseDAO.getMutiParentVO("fea_investdis_b", "id", wheresql1, fea_investdisBVOMapper);
		for(Fea_investdisBVO ibvo : ibvolist){
			fea_investdisBVOMapper.delete(ibvo);
		}
		fea_investdisVOMapper.execDeleteSql("delete from fea_investdis where project_id='"+projectvo.getId()+"'");

		String wheresql2 = "pkproductcost in ( select c.id from fea_productcost c where c.project_id='"+projectvo.getId()+"')";
		List<Fea_productcostBVO> pbvolist = (List<Fea_productcostBVO>) PubBaseDAO.getMutiParentVO("fea_productcostb", "id", wheresql2, fea_productcostBVOmapper);
		for(Fea_productcostBVO pbvo : pbvolist){
			fea_productcostBVOmapper.delete(pbvo);
		}
		fea_productcostVOmapper.execDeleteSql("delete from fea_productcost where project_id='"+projectvo.getId()+"'");;
		fea_capformVOMapper.execDeleteSql("delete from fea_capform where project_id='"+projectvo.getId()+"'");;
		fea_costinfoVOMapper.execDeleteSql("delete from fea_costinfo where project_id='"+projectvo.getId()+"'");;
		fea_incosubsidyVOMapper.execDeleteSql("delete from fea_incosubsidy where project_id='"+projectvo.getId()+"'");;
		fea_incomesetVOMapper.execDeleteSql("delete from fea_incomeset where project_id='"+projectvo.getId()+"'");;
		fea_costfecfVOMapper.execDeleteSql("delete from fea_costfecf where project_id='"+projectvo.getId()+"'");;
		fea_design_equiplst2VOMapper.execDeleteSql("DELETE from fea_design_equiplst2 where project_id='"+projectvo.getId()+"'");
		fea_design_heatbenVOMapper.execDeleteSql("DELETE from fea_design_heatben where project_id='"+projectvo.getId()+"'");
		fea_design_heattransVOMapper.execDeleteSql("DELETE from fea_design_heattrans where project_id='"+projectvo.getId()+"'");
		fea_design_resultmapper.execDeleteSql("DELETE from fea_design_result where project_id='"+projectvo.getId()+"'");
		fea_design_reportmapper.execDeleteSql("DELETE from fea_design_report where project_id='"+projectvo.getId()+"'");

	}
}
