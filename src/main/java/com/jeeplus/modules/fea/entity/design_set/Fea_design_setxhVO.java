/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.design_set;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 循环水泵价格Entity
 * @author jw
 * @version 2018-11-19
 */
public class Fea_design_setxhVO extends DataEntity<Fea_design_setxhVO> {
	
	private static final long serialVersionUID = 1L;
	private Double xhprice;		// 循环水泵价格
	private Double cxxs;		// 循环水泵单价系数
	private Double cxcl;		// 循环水泵单价常量
	private Double bpxs;		// 变频控制柜单价系数
	private Double bpcl;		// 变频控制柜单价常量
	private Double bsxs;		// 补水泵单价系数
	private Double bsxl;		// 补水泵单价常量
	
	
	public Fea_design_setxhVO() {
		super();
	}

	public Fea_design_setxhVO(String id){
		super(id);
	}

	@ExcelField(title="循环水泵价格", align=2, sort=7)
	public Double getXhprice() {
		return xhprice;
	}

	public void setXhprice(Double xhprice) {
		this.xhprice = xhprice;
	}
	
	@ExcelField(title="循环水泵单价系数", align=2, sort=18)
	public Double getCxxs() {
		return cxxs;
	}

	public void setCxxs(Double cxxs) {
		this.cxxs = cxxs;
	}
	
	@ExcelField(title="循环水泵单价常量", align=2, sort=19)
	public Double getCxcl() {
		return cxcl;
	}

	public void setCxcl(Double cxcl) {
		this.cxcl = cxcl;
	}
	
	@ExcelField(title="变频控制柜单价系数", align=2, sort=20)
	public Double getBpxs() {
		return bpxs;
	}

	public void setBpxs(Double bpxs) {
		this.bpxs = bpxs;
	}
	
	@ExcelField(title="变频控制柜单价常量", align=2, sort=21)
	public Double getBpcl() {
		return bpcl;
	}

	public void setBpcl(Double bpcl) {
		this.bpcl = bpcl;
	}
	
	@ExcelField(title="补水泵单价系数", align=2, sort=22)
	public Double getBsxs() {
		return bsxs;
	}

	public void setBsxs(Double bsxs) {
		this.bsxs = bsxs;
	}
	
	@ExcelField(title="补水泵单价常量", align=2, sort=23)
	public Double getBsxl() {
		return bsxl;
	}

	public void setBsxl(Double bsxl) {
		this.bsxl = bsxl;
	}
	
}