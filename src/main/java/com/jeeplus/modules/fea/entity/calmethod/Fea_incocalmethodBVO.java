/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.calmethod;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 测算方式价格子表Entity
 * @author jw
 * @version 2017-12-06
 */
public class Fea_incocalmethodBVO extends DataEntity<Fea_incocalmethodBVO> {
	
	private static final long serialVersionUID = 1L;
	private String itemtype;		// 项目类别
	private String unitname;		// 单位
	private String year17;		// 2017
	private String year18;		// 2018
	private String year19;		// 2019
	private String year20;		// 2020
	private String year21;		// 2021
	private String year22;		// 2022
	private String year23;		// 2023
	private String year24;		// 2024
	private String year25;		// 2025
	private String year26;		// 2026
	private Fea_incocalmethodVO fea_incocalmethod;		// 主表主键 父类
	
	public Fea_incocalmethodBVO() {
		super();
	}

	public Fea_incocalmethodBVO(String id){
		super(id);
	}

	public Fea_incocalmethodBVO(Fea_incocalmethodVO fea_incocalmethod){
		this.fea_incocalmethod = fea_incocalmethod;
	}

	@ExcelField(title="项目类别", align=2, sort=7)
	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}
	
	@ExcelField(title="单位", align=2, sort=8)
	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	
	@ExcelField(title="2017", align=2, sort=9)
	public String getYear17() {
		return year17;
	}

	public void setYear17(String year17) {
		this.year17 = year17;
	}
	
	@ExcelField(title="2018", align=2, sort=10)
	public String getYear18() {
		return year18;
	}

	public void setYear18(String year18) {
		this.year18 = year18;
	}
	
	@ExcelField(title="2019", align=2, sort=11)
	public String getYear19() {
		return year19;
	}

	public void setYear19(String year19) {
		this.year19 = year19;
	}
	
	@ExcelField(title="2020", align=2, sort=12)
	public String getYear20() {
		return year20;
	}

	public void setYear20(String year20) {
		this.year20 = year20;
	}
	
	@ExcelField(title="2021", align=2, sort=13)
	public String getYear21() {
		return year21;
	}

	public void setYear21(String year21) {
		this.year21 = year21;
	}
	
	@ExcelField(title="2022", align=2, sort=14)
	public String getYear22() {
		return year22;
	}

	public void setYear22(String year22) {
		this.year22 = year22;
	}
	
	@ExcelField(title="2023", align=2, sort=15)
	public String getYear23() {
		return year23;
	}

	public void setYear23(String year23) {
		this.year23 = year23;
	}
	
	@ExcelField(title="2024", align=2, sort=16)
	public String getYear24() {
		return year24;
	}

	public void setYear24(String year24) {
		this.year24 = year24;
	}
	
	@ExcelField(title="2025", align=2, sort=17)
	public String getYear25() {
		return year25;
	}

	public void setYear25(String year25) {
		this.year25 = year25;
	}
	
	@ExcelField(title="2026", align=2, sort=18)
	public String getYear26() {
		return year26;
	}

	public void setYear26(String year26) {
		this.year26 = year26;
	}
	
	public Fea_incocalmethodVO getFea_incocalmethod() {
		return fea_incocalmethod;
	}

	public void setFea_incocalmethod(Fea_incocalmethodVO fea_incocalmethod) {
		this.fea_incocalmethod = fea_incocalmethod;
	}
	
}