package com.jeeplus.modules.fea.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.modules.fea.entity.fecl.Fea_costfecfVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcTVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisBVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostBVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.design.Fea_design_heatVOMapper;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcTVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.heatben.Fea_design_heatbenVOMapper;
import com.jeeplus.modules.fea.mapper.heattrans.Fea_design_heattransVOMapper;
import com.jeeplus.modules.fea.mapper.income.Fea_incomesetVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.set.Fea_design_setVOMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;
import com.jeeplus.modules.fea.mapper.transfer.Fea_design_transferVOMapper;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;

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
				projectvo, flowloanamt, flowzyamt,projectvo.getHeatArea());
		
		heatVOMapper.execDeleteSql("DELETE from fea_design_heat where project_id='"+projectvo.getId()+"'");
		fea_design_heatbenVOMapper.execDeleteSql("DELETE from fea_design_heatben where project_id='"+projectvo.getId()+"'");
		fea_design_heattransVOMapper.execDeleteSql("DELETE from fea_design_heattrans where project_id='"+projectvo.getId()+"'");
		fea_design_transferVOMapper.execDeleteSql("DELETE from fea_design_transfer where project_id='"+projectvo.getId()+"'");
		fea_design_setVOMapper.execDeleteSql("DELETE from fea_design_set where project_id='"+projectvo.getId()+"'");
		Fea_designDefaultDAO.insertFea_design_heat(heatVOMapper, projectvo);
		Fea_designDefaultDAO.insertFea_design_heatben(fea_design_heatbenVOMapper, projectvo);
		Fea_designDefaultDAO.insertFea_design_heattrans(fea_design_heattransVOMapper, projectvo);
		Fea_designDefaultDAO.insertFea_design_transfer(fea_design_transferVOMapper, projectvo);
		Fea_designDefaultDAO.insertFeaDesignSet(fea_design_setVOMapper, projectvo);
	}

	public void deleteALLdata(FeaProjectB projectvo){
		String wheresql = "pkfundssrc in ( select c.id from fea_fundssrc c where c.project_id='"+projectvo.getId()+"')";
		List<Fea_fundssrcBVO> bvolist = (List<Fea_fundssrcBVO>) PubBaseDAO.getMutiParentVO("fea_fundssrc_b", "id", wheresql, fea_fundssrcBVOMapper);
		List<Fea_fundssrcTVO> tvolist = (List<Fea_fundssrcTVO>) PubBaseDAO.getMutiParentVO("fea_fundssrc_t", "id", wheresql, fea_fundssrcTVOMapper);
		if(null!=bvolist){
			for(Fea_fundssrcBVO bvo : bvolist){
				fea_fundssrcBVOMapper.delete(bvo);
			}
		}
		if(null!=tvolist){
			for(Fea_fundssrcTVO tvo : tvolist){
				fea_fundssrcTVOMapper.delete(tvo);
			}
		}
		fea_fundssrcVOMapper.execDeleteSql("delete from fea_fundssrc where project_id='"+projectvo.getId()+"'");


		String wheresql1 = "pkinvestdis in ( select c.id from fea_investdis c where c.project_id='"+projectvo.getId()+"')";
		List<Fea_investdisBVO> ibvolist = (List<Fea_investdisBVO>) PubBaseDAO.getMutiParentVO("fea_investdis_b", "id", wheresql1, fea_investdisBVOMapper);

		if(null!=bvolist){
			for(Fea_investdisBVO ibvo : ibvolist){
				fea_investdisBVOMapper.delete(ibvo);
			}
		}
		fea_investdisVOMapper.execDeleteSql("delete from fea_investdis where project_id='"+projectvo.getId()+"'");

		String wheresql2 = "pkproductcost in ( select c.id from fea_productcost c where c.project_id='"+projectvo.getId()+"')";
		List<Fea_productcostBVO> pbvolist = (List<Fea_productcostBVO>) PubBaseDAO.getMutiParentVO("fea_productcostb", "id", wheresql2, fea_productcostBVOmapper);

		if(null!=bvolist){
			for(Fea_productcostBVO pbvo : pbvolist){
				fea_productcostBVOmapper.delete(pbvo);
			}
		}
		fea_productcostVOmapper.execDeleteSql("delete from fea_productcost where project_id='"+projectvo.getId()+"'");;
		fea_capformVOMapper.execDeleteSql("delete from fea_capform where project_id='"+projectvo.getId()+"'");;
		fea_costinfoVOMapper.execDeleteSql("delete from fea_costinfo where project_id='"+projectvo.getId()+"'");;
		fea_incosubsidyVOMapper.execDeleteSql("delete from fea_incosubsidy where project_id='"+projectvo.getId()+"'");;
		fea_incomesetVOMapper.execDeleteSql("delete from fea_incomeset where project_id='"+projectvo.getId()+"'");;
		fea_costfecfVOMapper.execDeleteSql("delete from fea_costfecf where project_id='"+projectvo.getId()+"'");;


	}
}
