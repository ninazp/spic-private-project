/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.set;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 基本参数Entity
 * @author jw
 * @version 2018-02-08
 */
public class Fea_design_setVO extends DataEntity<Fea_design_setVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private Double sumheatefficient;		// 综合热效率
	private Double loadrate;		// 负荷率
	private Double pumprate;		// 热泵效率
	private Double hddept;		// 潜水泵水下位置（米）
	private Double hdlose;		// 潜水泵压力损失（米）
	private Double rou;		// 水密度
	private Double hx2;		// 循环水泵扬程
	private Double hb2;		// 蒸发器侧水泵扬程
	private Double mpumpcoe;		// 补水泵流量系数（%）
	private Office office;		// 部门
	
	public Fea_design_setVO() {
		super();
	}

	public Fea_design_setVO(String id){
		super(id);
	}

	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=1)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="综合热效率", align=2, sort=7)
	public Double getSumheatefficient() {
		return sumheatefficient;
	}

	public void setSumheatefficient(Double sumheatefficient) {
		this.sumheatefficient = sumheatefficient;
	}
	
	@ExcelField(title="负荷率", align=2, sort=8)
	public Double getLoadrate() {
		return loadrate;
	}

	public void setLoadrate(Double loadrate) {
		this.loadrate = loadrate;
	}
	
	@ExcelField(title="热泵效率", align=2, sort=9)
	public Double getPumprate() {
		return pumprate;
	}

	public void setPumprate(Double pumprate) {
		this.pumprate = pumprate;
	}
	
	@ExcelField(title="潜水泵水下位置（米）", align=2, sort=10)
	public Double getHddept() {
		return hddept;
	}

	public void setHddept(Double hddept) {
		this.hddept = hddept;
	}
	
	@ExcelField(title="潜水泵压力损失（米）", align=2, sort=11)
	public Double getHdlose() {
		return hdlose;
	}

	public void setHdlose(Double hdlose) {
		this.hdlose = hdlose;
	}
	
	@ExcelField(title="水密度", align=2, sort=12)
	public Double getRou() {
		return rou;
	}

	public void setRou(Double rou) {
		this.rou = rou;
	}
	
	@ExcelField(title="循环水泵扬程", align=2, sort=13)
	public Double getHx2() {
		return hx2;
	}

	public void setHx2(Double hx2) {
		this.hx2 = hx2;
	}
	
	@ExcelField(title="蒸发器侧水泵扬程", align=2, sort=14)
	public Double getHb2() {
		return hb2;
	}

	public void setHb2(Double hb2) {
		this.hb2 = hb2;
	}
	
	@ExcelField(title="补水泵流量系数（%）", align=2, sort=15)
	public Double getMpumpcoe() {
		return mpumpcoe;
	}

	public void setMpumpcoe(Double mpumpcoe) {
		this.mpumpcoe = mpumpcoe;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=16)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}