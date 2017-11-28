/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 融资来源Entity
 * @author jw
 * @version 2017-11-28
 */
public class Fea_fundssrcTVO extends DataEntity<Fea_fundssrcTVO> {
	
	private static final long serialVersionUID = 1L;
	private String loantyp;		// 借款方
	private String currency;		// 币种
	private Double exchangerate;		// 汇率
	private Double loanprop;		// 比例
	private Double loanamt;		// 借款金额
	private Double interestcount;		// 计息次数（年）
	private Double principalrate;		// 本金利率（%）
	private Double langrate;		// 利息利率（%）
	private String repaytype;		// 还款方式
	private Double repayperiod;		// 还款期
	private Double commitrate;		// 承诺费率（%）
	private Double graceperiod;		// 宽限期
	private Fea_fundssrcVO fea_fundssrc;		// 主表外键 父类
	
	public Fea_fundssrcTVO() {
		super();
	}

	public Fea_fundssrcTVO(String id){
		super(id);
	}

	public Fea_fundssrcTVO(Fea_fundssrcVO fea_fundssrc){
		this.fea_fundssrc = fea_fundssrc;
	}

	@ExcelField(title="借款方", align=2, sort=2)
	public String getLoantyp() {
		return loantyp;
	}

	public void setLoantyp(String loantyp) {
		this.loantyp = loantyp;
	}
	
	@ExcelField(title="币种", align=2, sort=3)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@ExcelField(title="汇率", align=2, sort=4)
	public Double getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(Double exchangerate) {
		this.exchangerate = exchangerate;
	}
	
	@ExcelField(title="比例", align=2, sort=5)
	public Double getLoanprop() {
		return loanprop;
	}

	public void setLoanprop(Double loanprop) {
		this.loanprop = loanprop;
	}
	
	@ExcelField(title="借款金额", align=2, sort=6)
	public Double getLoanamt() {
		return loanamt;
	}

	public void setLoanamt(Double loanamt) {
		this.loanamt = loanamt;
	}
	
	@ExcelField(title="计息次数（年）", align=2, sort=7)
	public Double getInterestcount() {
		return interestcount;
	}

	public void setInterestcount(Double interestcount) {
		this.interestcount = interestcount;
	}
	
	@ExcelField(title="本金利率（%）", align=2, sort=8)
	public Double getPrincipalrate() {
		return principalrate;
	}

	public void setPrincipalrate(Double principalrate) {
		this.principalrate = principalrate;
	}
	
	@ExcelField(title="利息利率（%）", align=2, sort=9)
	public Double getLangrate() {
		return langrate;
	}

	public void setLangrate(Double langrate) {
		this.langrate = langrate;
	}
	
	@ExcelField(title="还款方式", align=2, sort=10)
	public String getRepaytype() {
		return repaytype;
	}

	public void setRepaytype(String repaytype) {
		this.repaytype = repaytype;
	}
	
	@ExcelField(title="还款期", align=2, sort=11)
	public Double getRepayperiod() {
		return repayperiod;
	}

	public void setRepayperiod(Double repayperiod) {
		this.repayperiod = repayperiod;
	}
	
	@ExcelField(title="承诺费率（%）", align=2, sort=12)
	public Double getCommitrate() {
		return commitrate;
	}

	public void setCommitrate(Double commitrate) {
		this.commitrate = commitrate;
	}
	
	@ExcelField(title="宽限期", align=2, sort=13)
	public Double getGraceperiod() {
		return graceperiod;
	}

	public void setGraceperiod(Double graceperiod) {
		this.graceperiod = graceperiod;
	}
	
	public Fea_fundssrcVO getFea_fundssrc() {
		return fea_fundssrc;
	}

	public void setFea_fundssrc(Fea_fundssrcVO fea_fundssrc) {
		this.fea_fundssrc = fea_fundssrc;
	}
	
}