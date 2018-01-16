/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.design;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 供热参数Entity
 * @author jw
 * @version 2018-01-13
 */
public class Fea_design_heatVO extends DataEntity<Fea_design_heatVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private Double heatload;		// 热负荷指标（瓦/平方米）
	private Double heatarea;		// 供热面积（平方米）
	private Double powerfee;		// 电费（元/度）
	private Double heatdays;		// 供热天数（天）
	private Double dayheathours;		// 每日供热小时数（小时）
	private Double buildheight;		// 建筑物高度（米）
	private String areaselect;		// 分区选择
	private Office office;		// 部门
	
	public Fea_design_heatVO() {
		super();
	}

	public Fea_design_heatVO(String id){
		super(id);
	}

	@ExcelField(title="项目", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=6)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="热负荷指标（瓦/平方米）", align=2, sort=7)
	public Double getHeatload() {
		return heatload;
	}

	public void setHeatload(Double heatload) {
		this.heatload = heatload;
	}
	
	@ExcelField(title="供热面积（平方米）", align=2, sort=8)
	public Double getHeatarea() {
		return heatarea;
	}

	public void setHeatarea(Double heatarea) {
		this.heatarea = heatarea;
	}
	
	@ExcelField(title="电费（元/度）", align=2, sort=9)
	public Double getPowerfee() {
		return powerfee;
	}

	public void setPowerfee(Double powerfee) {
		this.powerfee = powerfee;
	}
	
	@ExcelField(title="供热天数（天）", align=2, sort=10)
	public Double getHeatdays() {
		return heatdays;
	}

	public void setHeatdays(Double heatdays) {
		this.heatdays = heatdays;
	}
	
	@ExcelField(title="每日供热小时数（小时）", align=2, sort=11)
	public Double getDayheathours() {
		return dayheathours;
	}

	public void setDayheathours(Double dayheathours) {
		this.dayheathours = dayheathours;
	}
	
	@ExcelField(title="建筑物高度（米）", align=2, sort=12)
	public Double getBuildheight() {
		return buildheight;
	}

	public void setBuildheight(Double buildheight) {
		this.buildheight = buildheight;
	}
	
	@ExcelField(title="分区选择", dictType="yes_no", align=2, sort=13)
	public String getAreaselect() {
		return areaselect;
	}

	public void setAreaselect(String areaselect) {
		this.areaselect = areaselect;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=14)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}