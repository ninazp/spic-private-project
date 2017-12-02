/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.totaltab;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.totaltab.Fea_finansumVO;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;
import com.jeeplus.modules.fea.mapper.totaltab.Fea_finansumVOMapper;
import com.jeeplus.modules.fea.pub.util.LoanRepayHandler;
import com.jeeplus.modules.fea.pub.util.ProfitHandler;
import com.jeeplus.modules.fea.pub.util.ProjectInfoHander;
import com.jeeplus.modules.fea.pub.util.TotalCostHander;

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

		//通过id查询得到项目信息
		FeaProjectB  projectvo =  projectmapper.get("dc940aa030b04f9ab32e543574cc847d");
		List<Double> projectinfo = ProjectInfoHander.getprojectinfo(projectvo);

		//资金筹措表 -- 如果计算方式不一样可以改动此处获取资金筹措表的逻辑
		List<List<Double>> zjcktable = ProjectInfoHander.getzjcctable(fea_investdisVOMapper, fea_investdisBVOMapper, projectvo);

		Double  deductval = ProjectInfoHander.getdekuje(fea_fundssrcVOMapper, projectvo);
		Double assetval = zjcktable.get(1).get(0)+zjcktable.get(2).get(0)-deductval;//计算折旧费固定资产价格
		Double assetwbval = zjcktable.get(1).get(0)-deductval;//计算维修费-去掉

		//贷款利息未计算
		List<List<Double>> totaltable = 
				TotalCostHander.getTotalcosttable(fea_capformVOMapper, fea_productcostVOmapper, 
						fea_productcostBVOmapper, projectinfo, assetval, assetwbval);

		List<List<Double>> interestTable = LoanRepayHandler.getLoanRepayTable(zjcktable, projectinfo, 4.9, 15.0, 4.35);

		//更新总成本费用表 -- 利息支出
		List<Double> lxzc = TotalCostHander.getrepaylx(interestTable);
		totaltable.set(6, lxzc);

		List<List<Double>> totalcostall = TotalCostHander.getallTotalCost(totaltable, projectinfo.get(1));

		
		List<Double> subincome = ProfitHandler.getsubincome(fea_incosubsidyVOMapper, projectvo, projectinfo);
        List<List<Double>> profittable =  ProfitHandler.getprofittable(projectinfo, totalcostall, interestTable, 
    		   fea_costinfoVOMapper, projectvo, 14.0, 25.0, 
    		   4.35, 10.0, 44.91, subincome);

        System.out.println(profittable);
	}
}