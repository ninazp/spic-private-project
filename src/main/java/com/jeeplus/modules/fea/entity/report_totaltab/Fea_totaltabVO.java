/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.report_totaltab;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 财务指标汇总表Entity
 * @author jw
 * @version 2018-12-01
 */
public class Fea_totaltabVO extends DataEntity<Fea_totaltabVO> {
	
	private static final long serialVersionUID = 1L;
	private Double hzje;		// 汇总金额
	
	public Fea_totaltabVO() {
		super();
	}

	public Fea_totaltabVO(String id){
		super(id);
	}

	@ExcelField(title="汇总金额", align=2, sort=7)
	public Double getHzje() {
		return hzje;
	}

	public void setHzje(Double hzje) {
		this.hzje = hzje;
	}
	
}