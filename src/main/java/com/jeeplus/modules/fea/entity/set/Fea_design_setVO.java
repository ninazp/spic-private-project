/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.set;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 基本参数Entity
 * @author jw
 * @version 2018-01-20
 */
public class Fea_design_setVO extends DataEntity<Fea_design_setVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private Double hddept;		// 水下深度（米）
	private Double hdlose;		// 压力损失（米）
	private Double hx1;		// 热泵冷凝器侧循环水泵扬程
	private Double hx2;		// 热泵蒸发器侧循环水泵扬程
	private Double hb2;		// 地下位置和末端高差
	private Double mpumpcoe;		// 补水泵流量系数（%）
	private Double cq1price;		// 潜水泵单价
	private Double cb1price;		// 补水泵单价
	private Double cx1price;		// 循环水泵单价
	private Double cxb1price;		// 变频控制柜单价
	private String office;		// 部门
	
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
	
	@ExcelField(title="热泵冷凝器侧循环水泵扬程", align=2, sort=9)
	public Double getHx1() {
		return hx1;
	}

	public void setHx1(Double hx1) {
		this.hx1 = hx1;
	}
	
	@ExcelField(title="热泵蒸发器侧循环水泵扬程", align=2, sort=10)
	public Double getHx2() {
		return hx2;
	}

	public void setHx2(Double hx2) {
		this.hx2 = hx2;
	}
	
	@ExcelField(title="地下位置和末端高差", align=2, sort=11)
	public Double getHb2() {
		return hb2;
	}

	public void setHb2(Double hb2) {
		this.hb2 = hb2;
	}
	
	@ExcelField(title="补水泵流量系数（%）", align=2, sort=12)
	public Double getMpumpcoe() {
		return mpumpcoe;
	}

	public void setMpumpcoe(Double mpumpcoe) {
		this.mpumpcoe = mpumpcoe;
	}
	
	@ExcelField(title="潜水泵单价", align=2, sort=13)
	public Double getCq1price() {
		return cq1price;
	}

	public void setCq1price(Double cq1price) {
		this.cq1price = cq1price;
	}
	
	@ExcelField(title="补水泵单价", align=2, sort=14)
	public Double getCb1price() {
		return cb1price;
	}

	public void setCb1price(Double cb1price) {
		this.cb1price = cb1price;
	}
	
	@ExcelField(title="循环水泵单价", align=2, sort=15)
	public Double getCx1price() {
		return cx1price;
	}

	public void setCx1price(Double cx1price) {
		this.cx1price = cx1price;
	}
	
	@ExcelField(title="变频控制柜单价", align=2, sort=16)
	public Double getCxb1price() {
		return cxb1price;
	}

	public void setCxb1price(Double cxb1price) {
		this.cxb1price = cxb1price;
	}
	
	@ExcelField(title="部门", fieldType=String.class, value="office.name", align=2, sort=17)
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
}