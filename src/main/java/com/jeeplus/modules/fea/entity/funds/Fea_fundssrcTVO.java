/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资金来源融资子表Entity
 * @author jw
 * @version 2017-11-06
 */
public class Fea_fundssrcTVO extends DataEntity<Fea_fundssrcTVO> {
	
	private static final long serialVersionUID = 1L;
	private String loantyp;		// 借款类别
	private String currency;		// 币种
	private Double exchangerate;		// 汇率
	private Double loanprop;		// 比例
	private Double loan_amt;		// 额度
	private Double fixamt;		// 固定额度
	private Double interestcount;		// 计息次数
	private Double principalrate;		// 本金利率
	private Double interestrate;		// 利息利率
	private Double commitrate;		// 承诺费率
	private Double graceperiod;		// 宽限期
	private Double repayperiod;		// 还款期
	private String repaytype;		// 还款方式
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

	@ExcelField(title="借款类别", align=2, sort=3)
	public String getLoantyp() {
		return loantyp;
	}

	public void setLoantyp(String loantyp) {
		this.loantyp = loantyp;
	}
	
	@ExcelField(title="币种", align=2, sort=4)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@ExcelField(title="汇率", align=2, sort=5)
	public Double getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(Double exchangerate) {
		this.exchangerate = exchangerate;
	}
	
	@ExcelField(title="比例", align=2, sort=6)
	public Double getLoanprop() {
		return loanprop;
	}

	public void setLoanprop(Double loanprop) {
		this.loanprop = loanprop;
	}
	
	@ExcelField(title="额度", align=2, sort=7)
	public Double getLoan_amt() {
		return loan_amt;
	}

	public void setLoan_amt(Double loan_amt) {
		this.loan_amt = loan_amt;
	}
	
	@ExcelField(title="固定额度", align=2, sort=8)
	public Double getFixamt() {
		return fixamt;
	}

	public void setFixamt(Double fixamt) {
		this.fixamt = fixamt;
	}
	
	@ExcelField(title="计息次数", align=2, sort=9)
	public Double getInterestcount() {
		return interestcount;
	}

	public void setInterestcount(Double interestcount) {
		this.interestcount = interestcount;
	}
	
	@ExcelField(title="本金利率", align=2, sort=10)
	public Double getPrincipalrate() {
		return principalrate;
	}

	public void setPrincipalrate(Double principalrate) {
		this.principalrate = principalrate;
	}
	
	@ExcelField(title="利息利率", align=2, sort=11)
	public Double getInterestrate() {
		return interestrate;
	}

	public void setInterestrate(Double interestrate) {
		this.interestrate = interestrate;
	}
	
	@ExcelField(title="承诺费率", align=2, sort=12)
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
	
	@ExcelField(title="还款期", align=2, sort=14)
	public Double getRepayperiod() {
		return repayperiod;
	}

	public void setRepayperiod(Double repayperiod) {
		this.repayperiod = repayperiod;
	}
	
	@ExcelField(title="还款方式", align=2, sort=15)
	public String getRepaytype() {
		return repaytype;
	}

	public void setRepaytype(String repaytype) {
		this.repaytype = repaytype;
	}
	
	public Fea_fundssrcVO getFea_fundssrc() {
		return fea_fundssrc;
	}

	public void setFea_fundssrc(Fea_fundssrcVO fea_fundssrc) {
		this.fea_fundssrc = fea_fundssrc;
	}
	
}