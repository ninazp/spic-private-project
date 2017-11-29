/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.feareport.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报表Entity
 * @author zp
 * @version 2017-11-28
 */
public class ReportOne extends DataEntity<ReportOne> {
	
	private static final long serialVersionUID = 1L;
	
	public ReportOne() {
		super();
	}

	public ReportOne(String id){
		super(id);
	}

}