/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.report.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报表Entity
 * @author zhaopeng
 * @version 2017-10-28
 */
public class ReportForm1 extends DataEntity<ReportForm1> {
	
	private static final long serialVersionUID = 1L;
	
	public ReportForm1() {
		super();
	}

	public ReportForm1(String id){
		super(id);
	}

}