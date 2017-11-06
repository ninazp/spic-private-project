/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.fecl;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 财务费用及流动资金Entity
 * @author jw
 * @version 2017-11-06
 */
public class Fea_costfecfVO extends DataEntity<Fea_costfecfVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private Double circularate;		// 流动资金贷款利率（%）
	private Double shortloanrate;		// 短期借款贷款利率（%）
	private Double circulaloanrate;		// 流动资金贷款比例（%）
	private Double receivacount;		// 应收账款周转次数
	private Double fuelcount;		// 燃料周转次数
	private Double origincount;		// 原材料周转次数
	private Double watercount;		// 水费周转次数
	private Double cashcount;		// 现金周转次数
	private Double heatcostdisprop;		// 发热成本摊销比例（%）
	private Double heatcapdisprop;		// 发热投资分摊次数(%)
	
	public Fea_costfecfVO() {
		super();
	}

	public Fea_costfecfVO(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=6)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=7)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="流动资金贷款利率（%）", align=2, sort=8)
	public Double getCircularate() {
		return circularate;
	}

	public void setCircularate(Double circularate) {
		this.circularate = circularate;
	}
	
	@ExcelField(title="短期借款贷款利率（%）", align=2, sort=9)
	public Double getShortloanrate() {
		return shortloanrate;
	}

	public void setShortloanrate(Double shortloanrate) {
		this.shortloanrate = shortloanrate;
	}
	
	@ExcelField(title="流动资金贷款比例（%）", align=2, sort=10)
	public Double getCirculaloanrate() {
		return circulaloanrate;
	}

	public void setCirculaloanrate(Double circulaloanrate) {
		this.circulaloanrate = circulaloanrate;
	}
	
	@ExcelField(title="应收账款周转次数", align=2, sort=11)
	public Double getReceivacount() {
		return receivacount;
	}

	public void setReceivacount(Double receivacount) {
		this.receivacount = receivacount;
	}
	
	@ExcelField(title="燃料周转次数", align=2, sort=12)
	public Double getFuelcount() {
		return fuelcount;
	}

	public void setFuelcount(Double fuelcount) {
		this.fuelcount = fuelcount;
	}
	
	@ExcelField(title="原材料周转次数", align=2, sort=13)
	public Double getOrigincount() {
		return origincount;
	}

	public void setOrigincount(Double origincount) {
		this.origincount = origincount;
	}
	
	@ExcelField(title="水费周转次数", align=2, sort=14)
	public Double getWatercount() {
		return watercount;
	}

	public void setWatercount(Double watercount) {
		this.watercount = watercount;
	}
	
	@ExcelField(title="现金周转次数", align=2, sort=15)
	public Double getCashcount() {
		return cashcount;
	}

	public void setCashcount(Double cashcount) {
		this.cashcount = cashcount;
	}
	
	@ExcelField(title="发热成本摊销比例（%）", align=2, sort=16)
	public Double getHeatcostdisprop() {
		return heatcostdisprop;
	}

	public void setHeatcostdisprop(Double heatcostdisprop) {
		this.heatcostdisprop = heatcostdisprop;
	}
	
	@ExcelField(title="发热投资分摊次数(%)", align=2, sort=17)
	public Double getHeatcapdisprop() {
		return heatcapdisprop;
	}

	public void setHeatcapdisprop(Double heatcapdisprop) {
		this.heatcapdisprop = heatcapdisprop;
	}
	
}