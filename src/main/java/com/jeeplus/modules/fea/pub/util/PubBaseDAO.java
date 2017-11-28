package com.jeeplus.modules.fea.pub.util;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;

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
	
	
}
