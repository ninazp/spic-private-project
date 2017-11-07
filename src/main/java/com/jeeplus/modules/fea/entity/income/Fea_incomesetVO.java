/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.income;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 基本参数Entity
 * @author jw
 * @version 2017-11-07
 */
public class Fea_incomesetVO extends DataEntity<Fea_incomesetVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private Double salerate;		// 售热销项税率（%）
	private Double umctax;		// 城市维护建设税率（%）
	private Double surtax;		// 教育费附加费率（%）
	private String isvtax;		// 综合增值税测算
	private Double vtaxrate;		// 综合增值税税率（%）
	private Double fuelrate;		// 燃料费扣税率（%）
	private Double freightrate;		// 运费扣税率（%）
	private Double waterrate;		// 水费扣税率（%）
	private Double materialrate;		// 材料费扣税率（%）
	private Double materialprop;		// 维修费中材料费比例（%）
	private Double incomerate;		// 所得税税率（%）
	private Double legalaccfund;		// 法定公积金
	private Double accfund;		// 任意公积金
	private Double benchmarkrate;		// 项目基准收益率（%）
	private Double averagerate;		// 平均资本成本率（%）
	private String isaccfundwsd;		// 公积金提取不超过资本金的50%
	
	public Fea_incomesetVO() {
		super();
	}

	public Fea_incomesetVO(String id){
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
	
	@ExcelField(title="售热销项税率（%）", align=2, sort=8)
	public Double getSalerate() {
		return salerate;
	}

	public void setSalerate(Double salerate) {
		this.salerate = salerate;
	}
	
	@ExcelField(title="城市维护建设税率（%）", align=2, sort=9)
	public Double getUmctax() {
		return umctax;
	}

	public void setUmctax(Double umctax) {
		this.umctax = umctax;
	}
	
	@ExcelField(title="教育费附加费率（%）", align=2, sort=10)
	public Double getSurtax() {
		return surtax;
	}

	public void setSurtax(Double surtax) {
		this.surtax = surtax;
	}
	
	@ExcelField(title="综合增值税测算", dictType="yes_no", align=2, sort=11)
	public String getIsvtax() {
		return isvtax;
	}

	public void setIsvtax(String isvtax) {
		this.isvtax = isvtax;
	}
	
	@ExcelField(title="综合增值税税率（%）", align=2, sort=12)
	public Double getVtaxrate() {
		return vtaxrate;
	}

	public void setVtaxrate(Double vtaxrate) {
		this.vtaxrate = vtaxrate;
	}
	
	@ExcelField(title="燃料费扣税率（%）", align=2, sort=13)
	public Double getFuelrate() {
		return fuelrate;
	}

	public void setFuelrate(Double fuelrate) {
		this.fuelrate = fuelrate;
	}
	
	@ExcelField(title="运费扣税率（%）", align=2, sort=14)
	public Double getFreightrate() {
		return freightrate;
	}

	public void setFreightrate(Double freightrate) {
		this.freightrate = freightrate;
	}
	
	@ExcelField(title="水费扣税率（%）", align=2, sort=15)
	public Double getWaterrate() {
		return waterrate;
	}

	public void setWaterrate(Double waterrate) {
		this.waterrate = waterrate;
	}
	
	@ExcelField(title="材料费扣税率（%）", align=2, sort=16)
	public Double getMaterialrate() {
		return materialrate;
	}

	public void setMaterialrate(Double materialrate) {
		this.materialrate = materialrate;
	}
	
	@ExcelField(title="维修费中材料费比例（%）", align=2, sort=17)
	public Double getMaterialprop() {
		return materialprop;
	}

	public void setMaterialprop(Double materialprop) {
		this.materialprop = materialprop;
	}
	
	@ExcelField(title="所得税税率（%）", align=2, sort=18)
	public Double getIncomerate() {
		return incomerate;
	}

	public void setIncomerate(Double incomerate) {
		this.incomerate = incomerate;
	}
	
	@ExcelField(title="法定公积金", align=2, sort=19)
	public Double getLegalaccfund() {
		return legalaccfund;
	}

	public void setLegalaccfund(Double legalaccfund) {
		this.legalaccfund = legalaccfund;
	}
	
	@ExcelField(title="任意公积金", align=2, sort=20)
	public Double getAccfund() {
		return accfund;
	}

	public void setAccfund(Double accfund) {
		this.accfund = accfund;
	}
	
	@ExcelField(title="项目基准收益率（%）", align=2, sort=21)
	public Double getBenchmarkrate() {
		return benchmarkrate;
	}

	public void setBenchmarkrate(Double benchmarkrate) {
		this.benchmarkrate = benchmarkrate;
	}
	
	@ExcelField(title="平均资本成本率（%）", align=2, sort=22)
	public Double getAveragerate() {
		return averagerate;
	}

	public void setAveragerate(Double averagerate) {
		this.averagerate = averagerate;
	}
	
	@ExcelField(title="公积金提取不超过资本金的50%", dictType="yes_no", align=2, sort=23)
	public String getIsaccfundwsd() {
		return isaccfundwsd;
	}

	public void setIsaccfundwsd(String isaccfundwsd) {
		this.isaccfundwsd = isaccfundwsd;
	}
	
}