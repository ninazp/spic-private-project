package com.jeeplus.modules.fea.service.totaltab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.totaltab.Fea_finansumVO;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcTVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.income.Fea_incomesetVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;
import com.jeeplus.modules.fea.mapper.totaltab.Fea_finansumVOMapper;
import com.jeeplus.modules.fea.pub.util.CreateReportPubDMO;

/**
 * 财务指标汇总表Service
 * @author jw
 * @version 2017-11-22
 */
@Service
@Transactional(readOnly = true)
public class Fea_finansumVOService extends CrudService<Fea_finansumVOMapper, Fea_finansumVO> {

	@Autowired
	private FeaProjectBMapper projectmapper;
	@Autowired
	private Fea_fundssrcVOMapper fea_fundssrcVOMapper;
	@Autowired
	private Fea_fundssrcTVOMapper fea_fundssrcTVOMapper;
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

	public Fea_finansumVO get(String id) {
		return super.get(id);
	}

	public List<Fea_finansumVO> findList(Fea_finansumVO fea_finansumVO) {
		return super.findList(fea_finansumVO);
	}

	public Page<Fea_finansumVO> findPage(Page<Fea_finansumVO> page, Fea_finansumVO fea_finansumVO) {
		return super.findPage(page, fea_finansumVO);
	}

	@Transactional(readOnly = false)
	public void save(Fea_finansumVO fea_finansumVO) {
		super.save(fea_finansumVO);
	}

	@Transactional(readOnly = false)
	public void delete(Fea_finansumVO fea_finansumVO) {
		Map<String,BaseMapper> maper = new HashMap<String, BaseMapper>();
		maper.put("projectmapper", projectmapper);
		maper.put("fea_fundssrcVOMapper", fea_fundssrcVOMapper);
		maper.put("fea_fundssrcTVOMapper", fea_fundssrcTVOMapper);
		maper.put("fea_investdisVOMapper", fea_investdisVOMapper);
		maper.put("fea_investdisBVOMapper", fea_investdisBVOMapper);
		maper.put("fea_productcostVOmapper", fea_productcostVOmapper);
		maper.put("fea_productcostBVOmapper", fea_productcostBVOmapper);
		maper.put("fea_capformVOMapper", fea_capformVOMapper);
		maper.put("fea_costinfoVOMapper", fea_costinfoVOMapper);
		maper.put("fea_incosubsidyVOMapper", fea_incosubsidyVOMapper);
		maper.put("fea_incomesetVOMapper", fea_incomesetVOMapper);
		maper.put("fea_costfecfVOMapper", fea_costfecfVOMapper);
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("projectid", "dc940aa030b04f9ab32e543574cc847d");
		Map<String,List<List<Double>>> reporttable = CreateReportPubDMO.getallreporttable(maper, param);
		
		List<Double> investirrnpv = CreateReportPubDMO.getinvest_irrnpv(reporttable.get("项目投资现金流量表"));
		List<Double> capitalirrnpv =  CreateReportPubDMO.getcapital_irrnpv(reporttable.get("项目资本金现金流量表"));
		
		System.out.println(investirrnpv+""+capitalirrnpv);
	}
	
}