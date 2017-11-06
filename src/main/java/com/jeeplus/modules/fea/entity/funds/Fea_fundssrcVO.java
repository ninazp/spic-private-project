/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;

import com.jeeplus.modules.sys.entity.Office;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资金来源Entity
 * @author jw
 * @version 2017-11-05
 */
public class Fea_fundssrcVO extends DataEntity<Fea_fundssrcVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectCode;		// 项目编码
	private String projectName;		// 项目名称
	private Double invest_Total;		// 投资总额
	private String isdeduct_Vtax;		// 增值税抵扣
	private Double capital_Prop;		// 资本金比例(%)
	private Double capital_Amt;		// 资本金额度
	private Double loan_Prop;		// 借款比例(%)
	private Double loan_Amt;		// 借款金额
	private String is_Capital_cy;		// 注资循环
	private Office office;		// 部门
	private String pk_corp;		// 公司
	private List<Fea_fundssrcBVO> fea_fundssrcBVOList = Lists.newArrayList();		// 子表列表
	private List<Fea_fundssrcTVO> fea_fundssrcTVOList = Lists.newArrayList();		// 子表列表
	
	public Fea_fundssrcVO() {
		super();
	}

	public Fea_fundssrcVO(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=7)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=8)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="投资总额", align=2, sort=9)
	public Double getInvest_Total() {
		return invest_Total;
	}

	public void setInvest_Total(Double invest_Total) {
		this.invest_Total = invest_Total;
	}
	
	@ExcelField(title="增值税抵扣", dictType="", align=2, sort=10)
	public String getIsdeduct_Vtax() {
		return isdeduct_Vtax;
	}

	public void setIsdeduct_Vtax(String isdeduct_Vtax) {
		this.isdeduct_Vtax = isdeduct_Vtax;
	}
	
	@ExcelField(title="资本金比例(%)", align=2, sort=11)
	public Double getCapital_Prop() {
		return capital_Prop;
	}

	public void setCapital_Prop(Double capital_Prop) {
		this.capital_Prop = capital_Prop;
	}
	
	@ExcelField(title="资本金额度", align=2, sort=12)
	public Double getCapital_Amt() {
		return capital_Amt;
	}

	public void setCapital_Amt(Double capital_Amt) {
		this.capital_Amt = capital_Amt;
	}
	
	@ExcelField(title="借款比例(%)", align=2, sort=13)
	public Double getLoan_Prop() {
		return loan_Prop;
	}

	public void setLoan_Prop(Double loan_Prop) {
		this.loan_Prop = loan_Prop;
	}
	
	@ExcelField(title="借款金额", align=2, sort=14)
	public Double getLoan_Amt() {
		return loan_Amt;
	}

	public void setLoan_Amt(Double loan_Amt) {
		this.loan_Amt = loan_Amt;
	}
	
	@ExcelField(title="注资循环", dictType="", align=2, sort=15)
	public String getIs_Capital_cy() {
		return is_Capital_cy;
	}

	public void setIs_Capital_cy(String is_Capital_cy) {
		this.is_Capital_cy = is_Capital_cy;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=16)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ExcelField(title="公司", align=2, sort=17)
	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	
	public List<Fea_fundssrcBVO> getFea_fundssrcBVOList() {
		return fea_fundssrcBVOList;
	}

	public void setFea_fundssrcBVOList(List<Fea_fundssrcBVO> fea_fundssrcBVOList) {
		this.fea_fundssrcBVOList = fea_fundssrcBVOList;
	}
	public List<Fea_fundssrcTVO> getFea_fundssrcTVOList() {
		return fea_fundssrcTVOList;
	}

	public void setFea_fundssrcTVOList(List<Fea_fundssrcTVO> fea_fundssrcTVOList) {
		this.fea_fundssrcTVOList = fea_fundssrcTVOList;
	}
}