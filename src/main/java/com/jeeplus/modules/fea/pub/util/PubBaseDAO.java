package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.fea.entity.income.Fea_incomesetVO;

public class PubBaseDAO {

	/**
	 * 主表，主键，查询条件，baseMapper实现类
	 * @param table
	 * @param id
	 * @param wheresql
	 * @param basemapper
	 * @return
	 */
	public static List<?> getMutiParentVO(String table , String id,String wheresql,BaseMapper basemapper){
		
		List<Object> objlst = new ArrayList<Object>();
		
		List<Object>  idlst = basemapper.execSelectSql(" select  "+id+" from "+table+" where "+wheresql);
		
		if(null!=idlst && idlst.size()>0){
		    for(Object obj :idlst){
		    	if(null!=obj){
		    		Object objvo = basemapper.get(obj.toString());
		    		if(null!=objvo) objlst.add(objvo);
		    	}
		    }
		}
		return objlst;
	}
	
	public static List<Double> getFea_incomeset(String table , String id,String wheresql,BaseMapper basemapper){
		
		List<Double> retlst = new ArrayList<Double>();
		List<Fea_incomesetVO> volst = (List<Fea_incomesetVO>) getMutiParentVO(table, id, wheresql, basemapper);
		if(null!=volst && volst.size()>0){
			//incomerate 所得税
			
//			private Double incomerate;		// 所得税税率（%）
//			private Double umctax;		// 城市维护建设税率（%）
//			private Double surtax;		// 教育费附加费率（%）
//			private Double legalaccfund;		// 法定盈余公积金比例（%）
//			private Double accfund;		// 任意盈余公积金比例（%）
//			private String isaccfundwsd;		// 公积金提取不超过资本金的50%
//			private Double yflrprop;		// 应付利润比例（%）
//			private String isvtaxjzjt;		// 增值税即征即退50%
//			private String issdssjsm;		// 所得税三免三减半
//			private String capinvesttype;		// 资本金投入方式
//			private Double capinvestprop;		// 资本金比例（%）
//			private Double capinvestrate;		// 资本金基准收益率（%）
//			private Double industrysqrate;		// 行业基准收益率（所得税前）（%）
//			private Double industryshrate;		// 行业基准收益率（所得税后）（%）
//			private Double capcostrate;		// 资本成本率（%）
			retlst.add(volst.get(0).getIncomerate());//所得税
			retlst.add(volst.get(0).getLegalaccfund());//法定盈余公积金比率
			retlst.add(volst.get(0).getYflrprop());//应付利润比率
			retlst.add(volst.get(0).getCapinvestrate());//资本金基准收益率
			retlst.add(volst.get(0).getIndustrysqrate());//税前收益率
			retlst.add(volst.get(0).getIndustryshrate());//税后收益率
			retlst.add(Double.valueOf(volst.get(0).getIssdssjsm()));//所得税三减三免
		}
		return retlst;
	}
	
  public static List<Double> getLoanrateyears(String table , String id,String wheresql,BaseMapper basemapper){
		List<Double> retlst = new ArrayList<Double>();
		List<Fea_incomesetVO> volst = (List<Fea_incomesetVO>) getMutiParentVO(table, id, wheresql, basemapper);
		if(null!=volst && volst.size()>0){
			
		}
		return retlst;
  }
	
}
