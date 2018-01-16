/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.result;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 方案运行费用结果表Entity
 * @author jw
 * @version 2018-01-16
 */
public class Fea_design_resultVO extends DataEntity<Fea_design_resultVO> {
	
	private static final long serialVersionUID = 1L;
	private String resulttype;		// 结果类别
	private FeaProjectB feaProjectB;		// 项目名称
	private Double year1;		// 第1年
	private Double year2;		// 第2年
	private Double year3;		// 第3年
	private Double year4;		// 第4年
	private Double year5;		// 第5年
	private Double year6;		// 第6年
	private Double year7;		// 第7年
	private Double year8;		// 第8年
	private Double year9;		// 第9年
	private Double year10;		// 第10年
	private Double year11;		// 第11年
	private Double year12;		// 第12年
	private Double year13;		// 第13年
	private Double year14;		// 第14年
	private Double year15;		// 第15年
	private Double year16;		// 第16年
	private Double year17;		// 第17年
	private Double year18;		// 第18年
	private Double year19;		// 第19年
	private Double year20;		// 第20年
	private Double year21;		// 第21年
	private Double year22;		// 第22年
	private Double year23;		// 第23年
	private Double year24;		// 第24年
	private Double year25;		// 第25年
	private Double year26;		// 第26年
	private Double year27;		// 第27年
	private Double year28;		// 第28年
	private Double year29;		// 第29年
	private Double year30;		// 第30年
	private Double year31;		// 第31年
	private Double year32;		// 第32年
	private Double year33;		// 第33年
	private Double year34;		// 第34年
	private Double year35;		// 第35年
	private Office office;		// 部门
	
	public Fea_design_resultVO() {
		super();
	}

	public Fea_design_resultVO(String id){
		super(id);
	}

	@ExcelField(title="结果类别", align=2, sort=7)
	public String getResulttype() {
		return resulttype;
	}

	public void setResulttype(String resulttype) {
		this.resulttype = resulttype;
	}
	
	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=8)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="第1年", align=2, sort=9)
	public Double getYear1() {
		return year1;
	}

	public void setYear1(Double year1) {
		this.year1 = year1;
	}
	
	@ExcelField(title="第2年", align=2, sort=10)
	public Double getYear2() {
		return year2;
	}

	public void setYear2(Double year2) {
		this.year2 = year2;
	}
	
	@ExcelField(title="第3年", align=2, sort=11)
	public Double getYear3() {
		return year3;
	}

	public void setYear3(Double year3) {
		this.year3 = year3;
	}
	
	@ExcelField(title="第4年", align=2, sort=12)
	public Double getYear4() {
		return year4;
	}

	public void setYear4(Double year4) {
		this.year4 = year4;
	}
	
	@ExcelField(title="第5年", align=2, sort=13)
	public Double getYear5() {
		return year5;
	}

	public void setYear5(Double year5) {
		this.year5 = year5;
	}
	
	@ExcelField(title="第6年", align=2, sort=14)
	public Double getYear6() {
		return year6;
	}

	public void setYear6(Double year6) {
		this.year6 = year6;
	}
	
	@ExcelField(title="第7年", align=2, sort=15)
	public Double getYear7() {
		return year7;
	}

	public void setYear7(Double year7) {
		this.year7 = year7;
	}
	
	@ExcelField(title="第8年", align=2, sort=16)
	public Double getYear8() {
		return year8;
	}

	public void setYear8(Double year8) {
		this.year8 = year8;
	}
	
	@ExcelField(title="第9年", align=2, sort=17)
	public Double getYear9() {
		return year9;
	}

	public void setYear9(Double year9) {
		this.year9 = year9;
	}
	
	@ExcelField(title="第10年", align=2, sort=18)
	public Double getYear10() {
		return year10;
	}

	public void setYear10(Double year10) {
		this.year10 = year10;
	}
	
	@ExcelField(title="第11年", align=2, sort=19)
	public Double getYear11() {
		return year11;
	}

	public void setYear11(Double year11) {
		this.year11 = year11;
	}
	
	@ExcelField(title="第12年", align=2, sort=20)
	public Double getYear12() {
		return year12;
	}

	public void setYear12(Double year12) {
		this.year12 = year12;
	}
	
	@ExcelField(title="第13年", align=2, sort=21)
	public Double getYear13() {
		return year13;
	}

	public void setYear13(Double year13) {
		this.year13 = year13;
	}
	
	@ExcelField(title="第14年", align=2, sort=22)
	public Double getYear14() {
		return year14;
	}

	public void setYear14(Double year14) {
		this.year14 = year14;
	}
	
	@ExcelField(title="第15年", align=2, sort=23)
	public Double getYear15() {
		return year15;
	}

	public void setYear15(Double year15) {
		this.year15 = year15;
	}
	
	@ExcelField(title="第16年", align=2, sort=24)
	public Double getYear16() {
		return year16;
	}

	public void setYear16(Double year16) {
		this.year16 = year16;
	}
	
	@ExcelField(title="第17年", align=2, sort=25)
	public Double getYear17() {
		return year17;
	}

	public void setYear17(Double year17) {
		this.year17 = year17;
	}
	
	@ExcelField(title="第18年", align=2, sort=26)
	public Double getYear18() {
		return year18;
	}

	public void setYear18(Double year18) {
		this.year18 = year18;
	}
	
	@ExcelField(title="第19年", align=2, sort=27)
	public Double getYear19() {
		return year19;
	}

	public void setYear19(Double year19) {
		this.year19 = year19;
	}
	
	@ExcelField(title="第20年", align=2, sort=28)
	public Double getYear20() {
		return year20;
	}

	public void setYear20(Double year20) {
		this.year20 = year20;
	}
	
	@ExcelField(title="第21年", align=2, sort=29)
	public Double getYear21() {
		return year21;
	}

	public void setYear21(Double year21) {
		this.year21 = year21;
	}
	
	@ExcelField(title="第22年", align=2, sort=30)
	public Double getYear22() {
		return year22;
	}

	public void setYear22(Double year22) {
		this.year22 = year22;
	}
	
	@ExcelField(title="第23年", align=2, sort=31)
	public Double getYear23() {
		return year23;
	}

	public void setYear23(Double year23) {
		this.year23 = year23;
	}
	
	@ExcelField(title="第24年", align=2, sort=32)
	public Double getYear24() {
		return year24;
	}

	public void setYear24(Double year24) {
		this.year24 = year24;
	}
	
	@ExcelField(title="第25年", align=2, sort=33)
	public Double getYear25() {
		return year25;
	}

	public void setYear25(Double year25) {
		this.year25 = year25;
	}
	
	@ExcelField(title="第26年", align=2, sort=34)
	public Double getYear26() {
		return year26;
	}

	public void setYear26(Double year26) {
		this.year26 = year26;
	}
	
	@ExcelField(title="第27年", align=2, sort=35)
	public Double getYear27() {
		return year27;
	}

	public void setYear27(Double year27) {
		this.year27 = year27;
	}
	
	@ExcelField(title="第28年", align=2, sort=36)
	public Double getYear28() {
		return year28;
	}

	public void setYear28(Double year28) {
		this.year28 = year28;
	}
	
	@ExcelField(title="第29年", align=2, sort=37)
	public Double getYear29() {
		return year29;
	}

	public void setYear29(Double year29) {
		this.year29 = year29;
	}
	
	@ExcelField(title="第30年", align=2, sort=38)
	public Double getYear30() {
		return year30;
	}

	public void setYear30(Double year30) {
		this.year30 = year30;
	}
	
	@ExcelField(title="第31年", align=2, sort=39)
	public Double getYear31() {
		return year31;
	}

	public void setYear31(Double year31) {
		this.year31 = year31;
	}
	
	@ExcelField(title="第32年", align=2, sort=40)
	public Double getYear32() {
		return year32;
	}

	public void setYear32(Double year32) {
		this.year32 = year32;
	}
	
	@ExcelField(title="第33年", align=2, sort=41)
	public Double getYear33() {
		return year33;
	}

	public void setYear33(Double year33) {
		this.year33 = year33;
	}
	
	@ExcelField(title="第34年", align=2, sort=42)
	public Double getYear34() {
		return year34;
	}

	public void setYear34(Double year34) {
		this.year34 = year34;
	}
	
	@ExcelField(title="第35年", align=2, sort=43)
	public Double getYear35() {
		return year35;
	}

	public void setYear35(Double year35) {
		this.year35 = year35;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=44)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}