/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.totaltab;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.costinfo.Fea_costinfoVO;
import com.jeeplus.modules.fea.entity.funds.Fea_capformVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostBVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.totaltab.Fea_finansumVO;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.totaltab.Fea_finansumVOMapper;
import com.jeeplus.modules.fea.pub.util.CountHander;
import com.jeeplus.modules.fea.pub.util.FlowLoanHander;
import com.jeeplus.modules.fea.pub.util.LangLoanHander;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;
import com.jeeplus.modules.fea.pub.util.ShortLoanHander;
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

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void delete(Fea_finansumVO fea_finansumVO) {
		//资金筹措表
		List<List<Double>> zijinchoucuo = new ArrayList<List<Double>>();
		List<List<Double>> zjcktable = new ArrayList<List<Double>>();
		//总成本费用表
		List<List<Double>> totaltable = new ArrayList<List<Double>>();
		//利润表与利润分配表
		List<List<Double>> profittable = new ArrayList<List<Double>>();
		//借款还利息表
		List<List<Double>> interesttable = new ArrayList<List<Double>>();

		//通过id查询得到项目信息
		FeaProjectB  projectvo =  projectmapper.get("dc940aa030b04f9ab32e543574cc847d");
		//项目信息
		List<Double> projectinfo = CountHander.handprject(projectvo);

		Double deductval = 0.0;
		//查询资金来源
		List<Fea_investdisVO>   distrvolst = (List<Fea_investdisVO>) PubBaseDAO.getMutiParentVO("fea_investdis", "id", " projectcode='B0001' ", fea_investdisVOMapper);
		if(null!=distrvolst && distrvolst.size()>0){
			for(Fea_investdisVO fvo : distrvolst){
				List<Fea_investdisBVO>   distriblst = (List<Fea_investdisBVO>) PubBaseDAO.getMutiParentVO("fea_investdis_b", "id", " pkinvestdis='"+fvo.getId()+"' ", fea_investdisBVOMapper);
				fvo.setFea_investdisBVOList(distriblst);
				List<Double> cktable = CountHander.getzicc(fvo);
				zjcktable.add(cktable);
				deductval = deductval+fvo.getDeductvtax();
			}
		}

		for(int i=0;i<zjcktable.size();i++){
			for(int j=0;j<zjcktable.get(i).size();j++){
				if(i==0){
				    List<Double> zktable = new ArrayList<Double>();
				    zktable.add(0.0);
				    zktable.add(zjcktable.get(i).get(j));
				    zijinchoucuo.add(zktable);
				}else{
					zijinchoucuo.get(j).add(zjcktable.get(i).get(j));
				}
			}
		}
		
		for(int i=0;i<zijinchoucuo.size();i++){
			Double tmpsum = 0.0;
			for(int j=0;j<zijinchoucuo.get(i).size();j++){
				tmpsum = tmpsum+zijinchoucuo.get(i).get(j);
			}
			zijinchoucuo.get(i).set(0, tmpsum);
		}
		

		Double assetval = zjcktable.get(0).get(1)+zjcktable.get(0).get(2)-deductval;
		Double assetwbval = zjcktable.get(0).get(1)-deductval;

		//总成本表
		//查询成本+基本参数+借款还利利息
		/**
		 * 折旧固定资产原值，维修和保险固定资产原值，
		 *                   折旧年限，残值率，维修费率，
		 *                   保险费率，工资,福利，供热,泵热费，
		 *                   第一年的供热月份，总计算年限，摊销原值，摊销年限
		 */
		List<Fea_capformVO>   fea_capform = (List<Fea_capformVO>) PubBaseDAO.getMutiParentVO("fea_capform", "id", " projectcode='B0001' ", fea_capformVOMapper);
		List<Fea_productcostVO>   productcost = (List<Fea_productcostVO>) PubBaseDAO.getMutiParentVO("fea_productcost", "id", " projectcode='B0001' ", fea_productcostVOmapper);
		if(null!=fea_capform && fea_capform.size()>0 && null!=productcost && productcost.size()>0){
			for(Fea_productcostVO pcost : productcost){
				List<Fea_productcostBVO>   pbvolst = (List<Fea_productcostBVO>) PubBaseDAO.getMutiParentVO("fea_productcostb", "id", " pkproductcost='"+pcost.getId()+"' ", fea_productcostBVOmapper);
				List<Double> repairrate = new ArrayList<Double>();
				for(Fea_productcostBVO bvo : pbvolst){
					if(bvo.getCosttype().contains("维修费") || bvo.getCosttype().equals("1")){
						for(int j=0;j<projectinfo.get(1);j++){
							try {
								Method m = bvo.getClass().getMethod("getYear"+(j+1));
								Double rated = (Double) m.invoke(bvo);
								repairrate.add(rated);
							} catch (Exception e) {
								e.printStackTrace();
							} 
						}
					}
				}

				List<Double> paramdoub = new ArrayList<Double>();

				paramdoub.add(assetval);
				paramdoub.add(assetwbval);

				// 折旧年限，残值率
				paramdoub.add(fea_capform.get(0).getUselifefat());
				paramdoub.add(fea_capform.get(0).getResidualrate());
				// * 保险费率，工资,福利，供热,泵热费，
				paramdoub.add(pcost.getInsurance());
				Double yearwage = pcost.getPersons()*pcost.getPerwage(); 
				paramdoub.add(yearwage);
				paramdoub.add(pcost.getWelfare());
				paramdoub.add(pcost.getHeatdeposit());
				paramdoub.add(pcost.getWateramt());

				// * 第一年的供热月份，总计算年限，摊销原值，摊销年限
				paramdoub.add(projectinfo.get(0));
				paramdoub.add(projectinfo.get(1));

				paramdoub.add(0.0);
				paramdoub.add(0.0);
				paramdoub.add(0.0);

				totaltable = TotalCostHander.getTotalCost(paramdoub, repairrate);
			}
		}
		
		//获取长期借款金额
		List<Double> cqjkje = new ArrayList<Double>();
		for(int i=0;i<zjcktable.size();i++){
			cqjkje.add(zjcktable.get(i).get(9));
		}
		//利息表 - 长期借款的利息 怎么获取，流动借款的业务场景，短期借款的算法；
		List<List<Double>> langloanlst = LangLoanHander.getlanghbfx(cqjkje, 4.9, 15.0, projectinfo.get(0),projectinfo.get(1));
		List<List<Double>> flowloanlst = FlowLoanHander.getflowhbfx(1, 24.50, 4.35,projectinfo.get(0),projectinfo.get(1));
		List<List<Double>>  shortlst = ShortLoanHander.getShortLoanList(projectinfo.get(1), 4.35);
		
		//计算完成利润表才能计算
		List<List<Double>> bfrate = new ArrayList<List<Double>>();
	
		//更新总成本费用表 -- 利息支出
		List<Double> lxzc = TotalCostHander.getrepaylx(langloanlst, flowloanlst, shortlst);
		totaltable.set(6, lxzc);
		//利润表
		
		List<Fea_costinfoVO>   fea_costinfovo = (List<Fea_costinfoVO>) PubBaseDAO.getMutiParentVO("fea_costinfo", "id", " projectcode='B0001' ", fea_costinfoVOMapper);

		
		
		
		
		//super.delete(fea_finansumVO);
	}
}