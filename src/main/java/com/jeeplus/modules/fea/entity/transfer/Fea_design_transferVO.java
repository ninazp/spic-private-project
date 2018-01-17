/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.transfer;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 换热参数Entity
 * @author jw
 * @version 2018-01-17
 */
public class Fea_design_transferVO extends DataEntity<Fea_design_transferVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private Double oneoutheat;		// 一级一次侧出口温度（摄氏度）
	private Double twooutheat;		// 二次侧供水温度（摄氏度）
	private Double twobackheat;		// 二次侧回水温度（摄氏度）
	private Double twozfoutheat;		// 二级二次蒸发器侧供水温度（摄氏度）
	private Double twozfbacktheat;		// 二级二次蒸发器侧回水温度（摄氏度）
	private Double backhgheat;		// 回灌水温度（摄氏度）
	private Double sumheatefficient;		// 综合热效率
	private Double loadrate;		// 负荷率
	private Double pumprate;		// 热泵效率
	private Office office;		// 部门
	
	public Fea_design_transferVO() {
		super();
	}

	public Fea_design_transferVO(String id){
		super(id);
	}

	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=7)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="一级一次侧出口温度（摄氏度）", align=2, sort=8)
	public Double getOneoutheat() {
		return oneoutheat;
	}

	public void setOneoutheat(Double oneoutheat) {
		this.oneoutheat = oneoutheat;
	}
	
	@ExcelField(title="二次侧供水温度（摄氏度）", align=2, sort=9)
	public Double getTwooutheat() {
		return twooutheat;
	}

	public void setTwooutheat(Double twooutheat) {
		this.twooutheat = twooutheat;
	}
	
	@ExcelField(title="二次侧回水温度（摄氏度）", align=2, sort=10)
	public Double getTwobackheat() {
		return twobackheat;
	}

	public void setTwobackheat(Double twobackheat) {
		this.twobackheat = twobackheat;
	}
	
	@ExcelField(title="二级二次蒸发器侧供水温度（摄氏度）", align=2, sort=11)
	public Double getTwozfoutheat() {
		return twozfoutheat;
	}

	public void setTwozfoutheat(Double twozfoutheat) {
		this.twozfoutheat = twozfoutheat;
	}
	
	@ExcelField(title="二级二次蒸发器侧回水温度（摄氏度）", align=2, sort=12)
	public Double getTwozfbacktheat() {
		return twozfbacktheat;
	}

	public void setTwozfbacktheat(Double twozfbacktheat) {
		this.twozfbacktheat = twozfbacktheat;
	}
	
	@ExcelField(title="回灌水温度（摄氏度）", align=2, sort=13)
	public Double getBackhgheat() {
		return backhgheat;
	}

	public void setBackhgheat(Double backhgheat) {
		this.backhgheat = backhgheat;
	}
	
	@ExcelField(title="综合热效率", align=2, sort=14)
	public Double getSumheatefficient() {
		return sumheatefficient;
	}

	public void setSumheatefficient(Double sumheatefficient) {
		this.sumheatefficient = sumheatefficient;
	}
	
	@ExcelField(title="负荷率", align=2, sort=15)
	public Double getLoadrate() {
		return loadrate;
	}

	public void setLoadrate(Double loadrate) {
		this.loadrate = loadrate;
	}
	
	@ExcelField(title="热泵效率", align=2, sort=16)
	public Double getPumprate() {
		return pumprate;
	}

	public void setPumprate(Double pumprate) {
		this.pumprate = pumprate;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=17)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}