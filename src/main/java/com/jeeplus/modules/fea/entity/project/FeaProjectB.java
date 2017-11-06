/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.project;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 项目（子表）Entity
 * @author zp
 * @version 2017-11-06
 */
public class FeaProjectB extends DataEntity<FeaProjectB> {
	
	private static final long serialVersionUID = 1L;
	private String projectCode;		// 项目编号
	private String projectName;		// 项目名称
	private Double heatArea;		// 供暖面积
	private FeaProject fea_project;		// 类型 父类
	private String address;		// 项目地址
	private Double heatDays;		// 供暖天数
	private Date projectStart;		// 项目起始日期
	private Date startupDate;		// 开工日期
	private Double constructPeriod;		// 建设期(月)
	private Date productDate;		// 投产日期
	private Office office;		// 部门
	private String pkCorp;		// 公司
	
	public FeaProjectB() {
		super();
	}

	public FeaProjectB(String id){
		super(id);
	}

	public FeaProjectB(FeaProject fea_project){
		this.fea_project = fea_project;
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
	
	@NotNull(message="供暖面积不能为空")
	@ExcelField(title="供暖面积", align=2, sort=9)
	public Double getHeatArea() {
		return heatArea;
	}

	public void setHeatArea(Double heatArea) {
		this.heatArea = heatArea;
	}
	
	public FeaProject getFea_project() {
		return fea_project;
	}

	public void setFea_project(FeaProject fea_project) {
		this.fea_project = fea_project;
	}
	
	@ExcelField(title="项目地址", align=2, sort=11)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotNull(message="供暖天数不能为空")
	@ExcelField(title="供暖天数", align=2, sort=12)
	public Double getHeatDays() {
		return heatDays;
	}

	public void setHeatDays(Double heatDays) {
		this.heatDays = heatDays;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="项目起始日期不能为空")
	@ExcelField(title="项目起始日期", align=2, sort=13)
	public Date getProjectStart() {
		return projectStart;
	}

	public void setProjectStart(Date projectStart) {
		this.projectStart = projectStart;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开工日期不能为空")
	@ExcelField(title="开工日期", align=2, sort=14)
	public Date getStartupDate() {
		return startupDate;
	}

	public void setStartupDate(Date startupDate) {
		this.startupDate = startupDate;
	}
	
	@NotNull(message="建设期(月)不能为空")
	@ExcelField(title="建设期(月)", align=2, sort=15)
	public Double getConstructPeriod() {
		return constructPeriod;
	}

	public void setConstructPeriod(Double constructPeriod) {
		this.constructPeriod = constructPeriod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="投产日期不能为空")
	@ExcelField(title="投产日期", align=2, sort=16)
	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}
	
	@NotNull(message="部门不能为空")
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=17)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ExcelField(title="公司", align=2, sort=18)
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
}