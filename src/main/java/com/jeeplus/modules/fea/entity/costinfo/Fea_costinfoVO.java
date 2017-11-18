/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.costinfo;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 成本种类及产量Entity
 * @author jw
 * @version 2017-11-09
 */
public class Fea_costinfoVO extends DataEntity<Fea_costinfoVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private String costype;		// 产品种类
	private String unit;		// 单位
	private String productrate;		// 产品税（%）
	private Double product;		// 产量
	private String year;		// 2017
	private Double year18;		// 2018
	private Double year19;		// 2019
	private Double year20;		// 2020
	private Double year21;		// 2021
	private Double year22;		// 2022
	private Double year23;		// 2023
	private Double year24;		// 2024
	private Double year25;		// 2025
	private Double year26;		// 2026
	private Double year27;		// 2027
	
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
	
	@ExcelField(title="产品种类", align=2, sort=3)
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
	
	@ExcelField(title="产品税（%）", align=2, sort=5)
	public String getProductrate() {
		return productrate;
	}

	public void setProductrate(String productrate) {
		this.productrate = productrate;
	}
	
	@ExcelField(title="产量", align=2, sort=6)
	public Double getProduct() {
		return product;
	}

	public void setProduct(Double product) {
		this.product = product;
	}
	
	@ExcelField(title="2017", align=2, sort=7)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@ExcelField(title="2018", align=2, sort=14)
	public Double getYear18() {
		return year18;
	}

	public void setYear18(Double year18) {
		this.year18 = year18;
	}
	
	@ExcelField(title="2019", align=2, sort=15)
	public Double getYear19() {
		return year19;
	}

	public void setYear19(Double year19) {
		this.year19 = year19;
	}
	
	@ExcelField(title="2020", align=2, sort=16)
	public Double getYear20() {
		return year20;
	}

	public void setYear20(Double year20) {
		this.year20 = year20;
	}
	
	@ExcelField(title="2021", align=2, sort=17)
	public Double getYear21() {
		return year21;
	}

	public void setYear21(Double year21) {
		this.year21 = year21;
	}
	
	@ExcelField(title="2022", align=2, sort=18)
	public Double getYear22() {
		return year22;
	}

	public void setYear22(Double year22) {
		this.year22 = year22;
	}
	
	@ExcelField(title="2023", align=2, sort=19)
	public Double getYear23() {
		return year23;
	}

	public void setYear23(Double year23) {
		this.year23 = year23;
	}
	
	@ExcelField(title="2024", align=2, sort=20)
	public Double getYear24() {
		return year24;
	}

	public void setYear24(Double year24) {
		this.year24 = year24;
	}
	
	@ExcelField(title="2025", align=2, sort=21)
	public Double getYear25() {
		return year25;
	}

	public void setYear25(Double year25) {
		this.year25 = year25;
	}
	
	@ExcelField(title="2026", align=2, sort=22)
	public Double getYear26() {
		return year26;
	}

	public void setYear26(Double year26) {
		this.year26 = year26;
	}
	
	@ExcelField(title="2027", align=2, sort=23)
	public Double getYear27() {
		return year27;
	}

	public void setYear27(Double year27) {
		this.year27 = year27;
	}
	
}