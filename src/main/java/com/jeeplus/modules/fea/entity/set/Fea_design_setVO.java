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
 * @version 2018-01-17
 */
public class Fea_design_setVO extends DataEntity<Fea_design_setVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private Double hddept;		// 水下深度（米）
	private Double hdlose;		// 压力损失（米）
	private Double hx1;		// 一级循环水泵扬程（米）
	private Double hx2;		// 二级循环水泵扬程（米）
	private Double mpumpcoe;		// 补水泵流量系数（%）
	private Office office;		// 部门
	
	public Fea_design_setVO() {
		super();
	}

	public Fea_design_setVO(String id){
		super(id);
	}

	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=6)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="水下深度（米）", align=2, sort=7)
	public Double getHddept() {
		return hddept;
	}

	public void setHddept(Double hddept) {
		this.hddept = hddept;
	}
	
	@ExcelField(title="压力损失（米）", align=2, sort=8)
	public Double getHdlose() {
		return hdlose;
	}

	public void setHdlose(Double hdlose) {
		this.hdlose = hdlose;
	}
	
	@ExcelField(title="一级循环水泵扬程（米）", align=2, sort=9)
	public Double getHx1() {
		return hx1;
	}

	public void setHx1(Double hx1) {
		this.hx1 = hx1;
	}
	
	@ExcelField(title="二级循环水泵扬程（米）", align=2, sort=10)
	public Double getHx2() {
		return hx2;
	}

	public void setHx2(Double hx2) {
		this.hx2 = hx2;
	}
	
	@ExcelField(title="补水泵流量系数（%）", align=2, sort=11)
	public Double getMpumpcoe() {
		return mpumpcoe;
	}

	public void setMpumpcoe(Double mpumpcoe) {
		this.mpumpcoe = mpumpcoe;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=12)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}