/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.procost;

import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 生成成本Entity
 * @author jw
 * @version 2017-11-09
 */
public class Fea_productcostVO extends DataEntity<Fea_productcostVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private Double persons;		// 定员（人）
	private Double perwage;		// 年人均工资
	private Double welfare;		// 福利费系数（%）
	private Double material;		// 材料费
	private Double insurance;		// 保险费率（‰）
	private Double othercost;		// 其他费用
	private Double waterprice;		// 地热井费用
	private Double wateramt;		// 泵送热水系统
	private Double midcontcost;		// 中间换热系统
	private Double waterheatcost;		// 水源热泵系统
	private Double controlcost;		// 控制系统
	private Double checkcost;		// 设计监理费
	private Double buildcost;		// 建筑工程费
	private Double setupcost;		// 安装工程费
	private Double pipecost;		// 管网
	private Double heatdeposit;		// 供热质量保证金
	private List<Fea_productcostBVO> fea_productcostBVOList = Lists.newArrayList();		// 子表列表
	
	public Fea_productcostVO() {
		super();
	}

	public Fea_productcostVO(String id){
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
	
	@ExcelField(title="定员（人）", align=2, sort=8)
	public Double getPersons() {
		return persons;
	}

	public void setPersons(Double persons) {
		this.persons = persons;
	}
	
	@ExcelField(title="年人均工资", align=2, sort=9)
	public Double getPerwage() {
		return perwage;
	}

	public void setPerwage(Double perwage) {
		this.perwage = perwage;
	}
	
	@ExcelField(title="福利费系数（%）", align=2, sort=10)
	public Double getWelfare() {
		return welfare;
	}

	public void setWelfare(Double welfare) {
		this.welfare = welfare;
	}
	
	@ExcelField(title="材料费", align=2, sort=11)
	public Double getMaterial() {
		return material;
	}

	public void setMaterial(Double material) {
		this.material = material;
	}
	
	@ExcelField(title="保险费率（‰）", align=2, sort=12)
	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}
	
	@ExcelField(title="其他费用", align=2, sort=13)
	public Double getOthercost() {
		return othercost;
	}

	public void setOthercost(Double othercost) {
		this.othercost = othercost;
	}
	
	@ExcelField(title="地热井费用", align=2, sort=14)
	public Double getWaterprice() {
		return waterprice;
	}

	public void setWaterprice(Double waterprice) {
		this.waterprice = waterprice;
	}
	
	@ExcelField(title="泵送热水系统", align=2, sort=15)
	public Double getWateramt() {
		return wateramt;
	}

	public void setWateramt(Double wateramt) {
		this.wateramt = wateramt;
	}
	
	@ExcelField(title="中间换热系统", align=2, sort=17)
	public Double getMidcontcost() {
		return midcontcost;
	}

	public void setMidcontcost(Double midcontcost) {
		this.midcontcost = midcontcost;
	}
	
	@ExcelField(title="水源热泵系统", align=2, sort=18)
	public Double getWaterheatcost() {
		return waterheatcost;
	}

	public void setWaterheatcost(Double waterheatcost) {
		this.waterheatcost = waterheatcost;
	}
	
	@ExcelField(title="控制系统", align=2, sort=19)
	public Double getControlcost() {
		return controlcost;
	}

	public void setControlcost(Double controlcost) {
		this.controlcost = controlcost;
	}
	
	@ExcelField(title="设计监理费", align=2, sort=20)
	public Double getCheckcost() {
		return checkcost;
	}

	public void setCheckcost(Double checkcost) {
		this.checkcost = checkcost;
	}
	
	@ExcelField(title="建筑工程费", align=2, sort=21)
	public Double getBuildcost() {
		return buildcost;
	}

	public void setBuildcost(Double buildcost) {
		this.buildcost = buildcost;
	}
	
	@ExcelField(title="安装工程费", align=2, sort=22)
	public Double getSetupcost() {
		return setupcost;
	}

	public void setSetupcost(Double setupcost) {
		this.setupcost = setupcost;
	}
	
	@ExcelField(title="管网", align=2, sort=23)
	public Double getPipecost() {
		return pipecost;
	}

	public void setPipecost(Double pipecost) {
		this.pipecost = pipecost;
	}
	
	@ExcelField(title="供热质量保证金", align=2, sort=24)
	public Double getHeatdeposit() {
		return heatdeposit;
	}

	public void setHeatdeposit(Double heatdeposit) {
		this.heatdeposit = heatdeposit;
	}
	
	public List<Fea_productcostBVO> getFea_productcostBVOList() {
		return fea_productcostBVOList;
	}

	public void setFea_productcostBVOList(List<Fea_productcostBVO> fea_productcostBVOList) {
		this.fea_productcostBVOList = fea_productcostBVOList;
	}
}