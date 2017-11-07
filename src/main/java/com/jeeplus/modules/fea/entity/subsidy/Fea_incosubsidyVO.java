/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.subsidy;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 补贴收入Entity
 * @author jw
 * @version 2017-11-08
 */
public class Fea_incosubsidyVO extends DataEntity<Fea_incosubsidyVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private String subsidytype;		// 补贴类型
	private String unitname;		// 单位
	private String ispaytax;		// 是否纳税
	private Double year17;		// 2017
	private Double year18;		// 2018
	private Double year19;		// 2019
	private Double year20;		// 2020
	private Double year21;		// 2021
	private Double year22;		// 2022
	private Double year23;		// 2023
	private Double year24;		// 2024
	private Double year25;		// 2025
	private Double year26;		// 2026
	
	public Fea_incosubsidyVO() {
		super();
	}

	public Fea_incosubsidyVO(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=7)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=8)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="补贴类型", align=2, sort=9)
	public String getSubsidytype() {
		return subsidytype;
	}

	public void setSubsidytype(String subsidytype) {
		this.subsidytype = subsidytype;
	}
	
	@ExcelField(title="单位", align=2, sort=10)
	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	
	@ExcelField(title="是否纳税", dictType="yes_no", align=2, sort=11)
	public String getIspaytax() {
		return ispaytax;
	}

	public void setIspaytax(String ispaytax) {
		this.ispaytax = ispaytax;
	}
	
	@ExcelField(title="2017", align=2, sort=12)
	public Double getYear17() {
		return year17;
	}

	public void setYear17(Double year17) {
		this.year17 = year17;
	}
	
	@ExcelField(title="2018", align=2, sort=13)
	public Double getYear18() {
		return year18;
	}

	public void setYear18(Double year18) {
		this.year18 = year18;
	}
	
	@ExcelField(title="2019", align=2, sort=14)
	public Double getYear19() {
		return year19;
	}

	public void setYear19(Double year19) {
		this.year19 = year19;
	}
	
	@ExcelField(title="2020", align=2, sort=15)
	public Double getYear20() {
		return year20;
	}

	public void setYear20(Double year20) {
		this.year20 = year20;
	}
	
	@ExcelField(title="2021", align=2, sort=16)
	public Double getYear21() {
		return year21;
	}

	public void setYear21(Double year21) {
		this.year21 = year21;
	}
	
	@ExcelField(title="2022", align=2, sort=17)
	public Double getYear22() {
		return year22;
	}

	public void setYear22(Double year22) {
		this.year22 = year22;
	}
	
	@ExcelField(title="2023", align=2, sort=18)
	public Double getYear23() {
		return year23;
	}

	public void setYear23(Double year23) {
		this.year23 = year23;
	}
	
	@ExcelField(title="2024", align=2, sort=19)
	public Double getYear24() {
		return year24;
	}

	public void setYear24(Double year24) {
		this.year24 = year24;
	}
	
	@ExcelField(title="2025", align=2, sort=20)
	public Double getYear25() {
		return year25;
	}

	public void setYear25(Double year25) {
		this.year25 = year25;
	}
	
	@ExcelField(title="2026", align=2, sort=21)
	public Double getYear26() {
		return year26;
	}

	public void setYear26(Double year26) {
		this.year26 = year26;
	}
	
}