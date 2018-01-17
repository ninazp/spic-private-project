/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.downhole;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 井下参数Entity
 * @author jw
 * @version 2018-01-17
 */
public class Fea_design_downholeVO extends DataEntity<Fea_design_downholeVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private Double holeheight;		// 井深（米）
	private Double flowcount;		// 流量（立方米/小时）
	private Double outheat;		// 出口温度（摄氏度）
	private Double waterlevel;		// 动水位（米）
	private Double hgpbac;		// 回灌配比a采
	private Double hgpbbh;		// 回灌配比b回
	private Office office;		// 部门
	
	public Fea_design_downholeVO() {
		super();
	}

	public Fea_design_downholeVO(String id){
		super(id);
	}

	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=6)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="井深（米）", align=2, sort=7)
	public Double getHoleheight() {
		return holeheight;
	}

	public void setHoleheight(Double holeheight) {
		this.holeheight = holeheight;
	}
	
	@ExcelField(title="流量（立方米/小时）", align=2, sort=8)
	public Double getFlowcount() {
		return flowcount;
	}

	public void setFlowcount(Double flowcount) {
		this.flowcount = flowcount;
	}
	
	@ExcelField(title="出口温度（摄氏度）", align=2, sort=9)
	public Double getOutheat() {
		return outheat;
	}

	public void setOutheat(Double outheat) {
		this.outheat = outheat;
	}
	
	@ExcelField(title="动水位（米）", align=2, sort=10)
	public Double getWaterlevel() {
		return waterlevel;
	}

	public void setWaterlevel(Double waterlevel) {
		this.waterlevel = waterlevel;
	}
	
	@ExcelField(title="回灌配比a采", align=2, sort=11)
	public Double getHgpbac() {
		return hgpbac;
	}

	public void setHgpbac(Double hgpbac) {
		this.hgpbac = hgpbac;
	}
	
	@ExcelField(title="回灌配比b回", align=2, sort=12)
	public Double getHgpbbh() {
		return hgpbbh;
	}

	public void setHgpbbh(Double hgpbbh) {
		this.hgpbbh = hgpbbh;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=13)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}