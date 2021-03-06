/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.project;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 项目（子表）Entity
 * @author zp
 * @version 2017-12-24
 */
public class FeaProjectB extends DataEntity<FeaProjectB> {
	
	private static final long serialVersionUID = 1L;
	private String projectCode;		// 项目编号
	private String projectName;		// 项目名称
	private String address;		// 项目地址
	private FeaProject kind;		// 类型 父类
	private Double heatArea;		// 供暖面积
	private Double heatDays;		// 供暖天数
	private Double price;		// 供暖单价
	private Date projectStart;		// 项目起始日期
	private Date startupDate;		// 开工日期
	private Double constructPeriod;		// 建设期(月)
	private Date productDate;		// 投产日期
	private Double countyears;		// 计算期（年）
	private Office office;		// 部门
	private String pkCorp;		// 公司
	
	public FeaProjectB() {
		super();
	}

	public FeaProjectB(String id){
		super(id);
	}

	public FeaProjectB(FeaProject kind){
		this.kind = kind;
	}

	@ExcelField(title="项目编号", align=2, sort=7)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=8)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="项目地址", align=2, sort=9)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public FeaProject getKind() {
		return kind;
	}

	public void setKind(FeaProject kind) {
		this.kind = kind;
	}
	
	@ExcelField(title="供暖面积", align=2, sort=11)
	public Double getHeatArea() {
		return heatArea;
	}

	public void setHeatArea(Double heatArea) {
		this.heatArea = heatArea;
	}
	
	@ExcelField(title="供暖天数", dictType="heating_days", align=2, sort=12)
	public Double getHeatDays() {
		return heatDays;
	}

	public void setHeatDays(Double heatDays) {
		this.heatDays = heatDays;
	}
	
	@ExcelField(title="供暖单价", align=2, sort=13)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="项目起始日期", align=2, sort=14)
	public Date getProjectStart() {
		return projectStart;
	}

	public void setProjectStart(Date projectStart) {
		this.projectStart = projectStart;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开工日期", align=2, sort=15)
	public Date getStartupDate() {
		return startupDate;
	}

	public void setStartupDate(Date startupDate) {
		this.startupDate = startupDate;
	}
	
	@ExcelField(title="建设期(月)", align=2, sort=16)
	public Double getConstructPeriod() {
		return constructPeriod;
	}

	public void setConstructPeriod(Double constructPeriod) {
		this.constructPeriod = constructPeriod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="投产日期", align=2, sort=17)
	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}
	
	@ExcelField(title="计算期（年）", align=2, sort=18)
	public Double getCountyears() {
		return countyears;
	}

	public void setCountyears(Double countyears) {
		this.countyears = countyears;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=19)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ExcelField(title="公司", align=2, sort=20)
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
}