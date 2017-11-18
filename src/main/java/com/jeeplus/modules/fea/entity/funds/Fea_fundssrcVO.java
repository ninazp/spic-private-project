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
 * @version 2017-11-18
 */
public class Fea_fundssrcVO extends DataEntity<Fea_fundssrcVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectCode;		// 项目编码
	private String projectName;		// 项目名称
	private Double investtotal;		// 投资总额
	private String isdeductvtax;		// 增值税抵扣
	private Double deductvtax;		// 可抵扣税金
	private Double capitalprop;		// 资本金比例(%)
	private Double capitalamt;		// 资本金额度
	private Double loanprop;		// 借款比例(%)
	private Double loanamt;		// 借款金额
	private String iscapitalcy;		// 注资循环
	private Office office;		// 部门
	private String corp;		// 公司
	private List<Fea_fundssrcBVO> fea_fundssrcBVOList = Lists.newArrayList();		// 子表列表
	private List<Fea_fundssrcTVO> fea_fundssrcTVOList = Lists.newArrayList();		// 子表列表
	
	public Fea_fundssrcVO() {
		super();
	}

	public Fea_fundssrcVO(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=6)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=7)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="投资总额", align=2, sort=8)
	public Double getInvesttotal() {
		return investtotal;
	}

	public void setInvesttotal(Double investtotal) {
		this.investtotal = investtotal;
	}
	
	@ExcelField(title="增值税抵扣", dictType="yes_no", align=2, sort=9)
	public String getIsdeductvtax() {
		return isdeductvtax;
	}

	public void setIsdeductvtax(String isdeductvtax) {
		this.isdeductvtax = isdeductvtax;
	}
	
	@ExcelField(title="可抵扣税金", align=2, sort=10)
	public Double getDeductvtax() {
		return deductvtax;
	}

	public void setDeductvtax(Double deductvtax) {
		this.deductvtax = deductvtax;
	}
	
	@ExcelField(title="资本金比例(%)", align=2, sort=11)
	public Double getCapitalprop() {
		return capitalprop;
	}

	public void setCapitalprop(Double capitalprop) {
		this.capitalprop = capitalprop;
	}
	
	@ExcelField(title="资本金额度", align=2, sort=12)
	public Double getCapitalamt() {
		return capitalamt;
	}

	public void setCapitalamt(Double capitalamt) {
		this.capitalamt = capitalamt;
	}
	
	@ExcelField(title="借款比例(%)", align=2, sort=13)
	public Double getLoanprop() {
		return loanprop;
	}

	public void setLoanprop(Double loanprop) {
		this.loanprop = loanprop;
	}
	
	@ExcelField(title="借款金额", align=2, sort=14)
	public Double getLoanamt() {
		return loanamt;
	}

	public void setLoanamt(Double loanamt) {
		this.loanamt = loanamt;
	}
	
	@ExcelField(title="注资循环", dictType="yes_no", align=2, sort=15)
	public String getIscapitalcy() {
		return iscapitalcy;
	}

	public void setIscapitalcy(String iscapitalcy) {
		this.iscapitalcy = iscapitalcy;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=16)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@ExcelField(title="公司", align=2, sort=17)
	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
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