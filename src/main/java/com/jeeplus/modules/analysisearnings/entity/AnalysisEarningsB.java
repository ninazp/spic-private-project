/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.analysisearnings.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 敏感分析（单因素）子表Entity
 * @author zp
 * @version 2017-11-19
 */
public class AnalysisEarningsB extends DataEntity<AnalysisEarningsB> {
	
	private static final long serialVersionUID = 1L;
	private AnalysisEarnings pid;		// 主表主键 父类
	
	public AnalysisEarningsB() {
		super();
	}

	public AnalysisEarningsB(String id){
		super(id);
	}

	public AnalysisEarningsB(AnalysisEarnings pid){
		this.pid = pid;
	}

	public AnalysisEarnings getPid() {
		return pid;
	}

	public void setPid(AnalysisEarnings pid) {
		this.pid = pid;
	}
	
}