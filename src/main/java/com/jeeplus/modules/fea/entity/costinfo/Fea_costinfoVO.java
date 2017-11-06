/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.costinfo;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 成本种类及产量Entity
 * @author jw
 * @version 2017-11-06
 */
public class Fea_costinfoVO extends DataEntity<Fea_costinfoVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private String costype;		// 成本种类
	private String unit;		// 单位
	private String year;		// 年度
	private Double product;		// 产量
	private String productrate;		// 产品税（%）
	
	public Fea_costinfoVO() {
		super();
	}

	public Fea_costinfoVO(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=1)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=2)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="成本种类", align=2, sort=3)
	public String getCostype() {
		return costype;
	}

	public void setCostype(String costype) {
		this.costype = costype;
	}
	
	@ExcelField(title="单位", align=2, sort=4)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="年度", align=2, sort=5)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@ExcelField(title="产量", align=2, sort=6)
	public Double getProduct() {
		return product;
	}

	public void setProduct(Double product) {
		this.product = product;
	}
	
	@ExcelField(title="产品税（%）", align=2, sort=12)
	public String getProductrate() {
		return productrate;
	}

	public void setProductrate(String productrate) {
		this.productrate = productrate;
	}
	
}