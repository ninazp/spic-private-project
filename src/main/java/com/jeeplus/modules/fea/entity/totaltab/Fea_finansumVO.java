/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.totaltab;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 财务指标汇总表Entity
 * @author jw
 * @version 2018-12-01
 */
public class Fea_finansumVO extends DataEntity<Fea_finansumVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private Double heatarea;		// 供热面积
	private Double cgheatarea;		// 常规年供热面积
	private Double investtotal;		// 总投资
	
	public Fea_finansumVO() {
		super();
	}

	public Fea_finansumVO(String id){
		super(id);
	}

	@ExcelField(title="项目", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=1)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="供热面积", align=2, sort=8)
	public Double getHeatarea() {
		return heatarea;
	}

	public void setHeatarea(Double heatarea) {
		this.heatarea = heatarea;
	}
	
	@ExcelField(title="常规年供热面积", align=2, sort=9)
	public Double getCgheatarea() {
		return cgheatarea;
	}

	public void setCgheatarea(Double cgheatarea) {
		this.cgheatarea = cgheatarea;
	}
	
	@ExcelField(title="总投资", align=2, sort=10)
	public Double getInvesttotal() {
		return investtotal;
	}

	public void setInvesttotal(Double investtotal) {
		this.investtotal = investtotal;
	}
	
}