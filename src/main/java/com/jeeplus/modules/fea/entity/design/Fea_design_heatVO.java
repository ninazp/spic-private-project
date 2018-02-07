/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.design;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 供热及井下参数
 * @author jw
 * @version 2018-02-05
 */
public class Fea_design_heatVO extends DataEntity<Fea_design_heatVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private Double heatload;		// 热负荷指标（瓦/平方米）
	private Double heatarea;		// 供热面积（平方米）
	private Double powerfee;		// 电费（元/度）
	private Double heatdays;		// 供热天数（天）
	private Double dayheathours;		// 每日供热小时数（小时）
	private Double buildheight;		// 建筑物高度（米）
	private String areaselect;		// 分区选择
	private Office office;		// 部门
	private Double holeheight;		// 井深（米）
	private Double flowcount;		// 流量（立方米/小时）
	private Double outheat;		// 出口温度（摄氏度）
	private Double waterlevel;		// 动水位（米）
	private Double hgpbac;		// 回灌配比开采井
	private Double hgpbbh;		// 回灌配比回灌井
	
	public Fea_design_heatVO() {
		super();
	}

	public Fea_design_heatVO(String id){
		super(id);
	}

	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=6)
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
	
	@ExcelField(title="供热天数（天）", dictType="heating_days", align=2, sort=10)
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
	
	@ExcelField(title="井深（米）", align=2, sort=16)
	public Double getHoleheight() {
		return holeheight;
	}

	public void setHoleheight(Double holeheight) {
		this.holeheight = holeheight;
	}
	
	@ExcelField(title="流量（立方米/小时）", align=2, sort=17)
	public Double getFlowcount() {
		return flowcount;
	}

	public void setFlowcount(Double flowcount) {
		this.flowcount = flowcount;
	}
	
	@ExcelField(title="出口温度（摄氏度）", align=2, sort=18)
	public Double getOutheat() {
		return outheat;
	}

	public void setOutheat(Double outheat) {
		this.outheat = outheat;
	}
	
	@ExcelField(title="动水位（米）", align=2, sort=19)
	public Double getWaterlevel() {
		return waterlevel;
	}

	public void setWaterlevel(Double waterlevel) {
		this.waterlevel = waterlevel;
	}
	
	@ExcelField(title="回灌配比开采井", align=2, sort=20)
	public Double getHgpbac() {
		return hgpbac;
	}

	public void setHgpbac(Double hgpbac) {
		this.hgpbac = hgpbac;
	}
	
	@ExcelField(title="回灌配比回灌井", align=2, sort=21)
	public Double getHgpbbh() {
		return hgpbbh;
	}

	public void setHgpbbh(Double hgpbbh) {
		this.hgpbbh = hgpbbh;
	}
	
}