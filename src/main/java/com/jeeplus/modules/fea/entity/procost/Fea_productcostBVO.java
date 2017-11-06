/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.procost;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 成本种类Entity
 * @author jw
 * @version 2017-11-06
 */
public class Fea_productcostBVO extends DataEntity<Fea_productcostBVO> {
	
	private static final long serialVersionUID = 1L;
	private String costtype;		// 成本种类
	private String costunit;		// 单位
	private Double year2017;		// 2017
	private Double year2018;		// 2018
	private Double year2019;		// 2019
	private Fea_productcostVO fea_productcost;		// 主表主键 父类
	private Double year2020;		// 2020
	private Double year2021;		// 2021
	private Double year2022;		// 2022
	private Double year2023;		// 2023
	private Double year2024;		// 2024
	private Double year2025;		// 2025
	private Double year2026;		// 2026
	private Double year2027;		// 2027
	
	public Fea_productcostBVO() {
		super();
	}

	public Fea_productcostBVO(String id){
		super(id);
	}

	public Fea_productcostBVO(Fea_productcostVO fea_productcost){
		this.fea_productcost = fea_productcost;
	}

	@ExcelField(title="成本种类", align=2, sort=6)
	public String getCosttype() {
		return costtype;
	}

	public void setCosttype(String costtype) {
		this.costtype = costtype;
	}
	
	@ExcelField(title="单位", align=2, sort=7)
	public String getCostunit() {
		return costunit;
	}

	public void setCostunit(String costunit) {
		this.costunit = costunit;
	}
	
	@ExcelField(title="2017", align=2, sort=8)
	public Double getYear2017() {
		return year2017;
	}

	public void setYear2017(Double year2017) {
		this.year2017 = year2017;
	}
	
	@ExcelField(title="2018", align=2, sort=9)
	public Double getYear2018() {
		return year2018;
	}

	public void setYear2018(Double year2018) {
		this.year2018 = year2018;
	}
	
	@ExcelField(title="2019", align=2, sort=10)
	public Double getYear2019() {
		return year2019;
	}

	public void setYear2019(Double year2019) {
		this.year2019 = year2019;
	}
	
	public Fea_productcostVO getFea_productcost() {
		return fea_productcost;
	}

	public void setFea_productcost(Fea_productcostVO fea_productcost) {
		this.fea_productcost = fea_productcost;
	}
	
	@ExcelField(title="2020", align=2, sort=13)
	public Double getYear2020() {
		return year2020;
	}

	public void setYear2020(Double year2020) {
		this.year2020 = year2020;
	}
	
	@ExcelField(title="2021", align=2, sort=14)
	public Double getYear2021() {
		return year2021;
	}

	public void setYear2021(Double year2021) {
		this.year2021 = year2021;
	}
	
	@ExcelField(title="2022", align=2, sort=15)
	public Double getYear2022() {
		return year2022;
	}

	public void setYear2022(Double year2022) {
		this.year2022 = year2022;
	}
	
	@ExcelField(title="2023", align=2, sort=16)
	public Double getYear2023() {
		return year2023;
	}

	public void setYear2023(Double year2023) {
		this.year2023 = year2023;
	}
	
	@ExcelField(title="2024", align=2, sort=17)
	public Double getYear2024() {
		return year2024;
	}

	public void setYear2024(Double year2024) {
		this.year2024 = year2024;
	}
	
	@ExcelField(title="2025", align=2, sort=18)
	public Double getYear2025() {
		return year2025;
	}

	public void setYear2025(Double year2025) {
		this.year2025 = year2025;
	}
	
	@ExcelField(title="2026", align=2, sort=19)
	public Double getYear2026() {
		return year2026;
	}

	public void setYear2026(Double year2026) {
		this.year2026 = year2026;
	}
	
	@ExcelField(title="2027", align=2, sort=20)
	public Double getYear2027() {
		return year2027;
	}

	public void setYear2027(Double year2027) {
		this.year2027 = year2027;
	}
	
}