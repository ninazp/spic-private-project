/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.totaltab;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.funds.Fea_capformVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcTVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostBVO;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.totaltab.Fea_finansumVO;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcTVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.totaltab.Fea_finansumVOMapper;
import com.jeeplus.modules.fea.pub.util.CountHander;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;

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
	private Fea_fundssrcVOMapper fundsrcVOmapper;
	@Autowired
	private Fea_fundssrcBVOMapper fundsrcBVOmapper;
	@Autowired
	private Fea_fundssrcTVOMapper fundsrcTVOmapper;//资金来源
	@Autowired
	private Fea_productcostVOMapper fea_productcostVOmapper;
	@Autowired
	private Fea_productcostBVOMapper fea_productcostBVOmapper;
	@Autowired
	private Fea_capformVOMapper fea_capformVOMapper;


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
		//通过id查询得到项目信息
		FeaProjectB  projectvo =  projectmapper.get("dc940aa030b04f9ab32e543574cc847d");

		//项目信息
		List<Double> projectinfo = CountHander.handprject(projectvo);
		//资金筹措表
		List<Double> cktable = new ArrayList<Double>();
		//查询资金来源
		List<Fea_fundssrcVO>   objlst = (List<Fea_fundssrcVO>) PubBaseDAO.getMutiParentVO("fea_fundssrc", "id", " projectcode='B0001' ", fundsrcVOmapper);
		Fea_fundssrcVO fvo =null;
		if(null!=objlst && objlst.size()>0){
			fvo = objlst.get(0);
			List<Fea_fundssrcBVO>   objblst = (List<Fea_fundssrcBVO>) PubBaseDAO.getMutiParentVO("fea_fundssrc_b", "id", " pkfundssrc='"+fvo.getId()+"' ", fundsrcBVOmapper);
			List<Fea_fundssrcTVO>   objtlst = (List<Fea_fundssrcTVO>) PubBaseDAO.getMutiParentVO("fea_fundssrc_t", "id", " pkfundssrc='"+fvo.getId()+"' ", fundsrcTVOmapper);
			fvo.setFea_fundssrcBVOList(objblst);
			fvo.setFea_fundssrcTVOList(objtlst);
			cktable = CountHander.getzicc(fvo);
		}

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
				List<Fea_productcostBVO>   pbvolst = (List<Fea_productcostBVO>) PubBaseDAO.getMutiParentVO("fea_productcostb", "id", " pkproductcost='"+fvo.getId()+"' ", fea_productcostBVOmapper);
				List<Double> repairrate = new ArrayList<Double>();
				for(Fea_productcostBVO bvo : pbvolst){
					if(bvo.getCosttype().equals("维修费") || bvo.getCosttype().equals("1")){
						for(int i=0 ;i<projectinfo.get(1);i++){
							  Double years = Math.ceil(projectinfo.get(2)+i);
						}
					}
				}
				
				List<Double> paramdoub = new ArrayList<Double>();
				Double deductval = fvo.getDeductvtax();
				Double assetval = cktable.get(1)+cktable.get(2)-deductval;
				Double assetwbval = cktable.get(1)-deductval;

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


			}

		}


		//		super.delete(fea_finansumVO);
	}

}