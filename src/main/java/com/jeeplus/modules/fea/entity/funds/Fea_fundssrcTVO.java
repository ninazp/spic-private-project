/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资金来源融资子表Entity
 * @author jw
 * @version 2017-11-05
 */
public class Fea_fundssrcTVO extends DataEntity<Fea_fundssrcTVO> {
	
	private static final long serialVersionUID = 1L;
	private String loan_typ;		// 借款类别
	private String currency;		// 币种
	private Double exchangerate;		// 汇率
	private Double loan_prop;		// 比例
	private Double loan_amt;		// 额度
	private Double fix_amt;		// 固定额度
	private Double interest_count;		// 计息次数
	private Double principal_rate;		// 本金利率
	private Double interest_rate;		// 利息利率
	private Double commit_rate;		// 承诺费率
	private Double grace_period;		// 宽限期
	private Double repay_period;		// 还款期
	private String repay_type;		// 还款方式
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

	@ExcelField(title="借款类别", align=2, sort=7)
	public String getLoan_typ() {
		return loan_typ;
	}

	public void setLoan_typ(String loan_typ) {
		this.loan_typ = loan_typ;
	}
	
	@ExcelField(title="币种", align=2, sort=8)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@ExcelField(title="汇率", align=2, sort=9)
	public Double getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(Double exchangerate) {
		this.exchangerate = exchangerate;
	}
	
	@ExcelField(title="比例", align=2, sort=10)
	public Double getLoan_prop() {
		return loan_prop;
	}

	public void setLoan_prop(Double loan_prop) {
		this.loan_prop = loan_prop;
	}
	
	@ExcelField(title="额度", align=2, sort=11)
	public Double getLoan_amt() {
		return loan_amt;
	}

	public void setLoan_amt(Double loan_amt) {
		this.loan_amt = loan_amt;
	}
	
	@ExcelField(title="固定额度", align=2, sort=12)
	public Double getFix_amt() {
		return fix_amt;
	}

	public void setFix_amt(Double fix_amt) {
		this.fix_amt = fix_amt;
	}
	
	@ExcelField(title="计息次数", align=2, sort=13)
	public Double getInterest_count() {
		return interest_count;
	}

	public void setInterest_count(Double interest_count) {
		this.interest_count = interest_count;
	}
	
	@ExcelField(title="本金利率", align=2, sort=14)
	public Double getPrincipal_rate() {
		return principal_rate;
	}

	public void setPrincipal_rate(Double principal_rate) {
		this.principal_rate = principal_rate;
	}
	
	@ExcelField(title="利息利率", align=2, sort=15)
	public Double getInterest_rate() {
		return interest_rate;
	}

	public void setInterest_rate(Double interest_rate) {
		this.interest_rate = interest_rate;
	}
	
	@ExcelField(title="承诺费率", align=2, sort=16)
	public Double getCommit_rate() {
		return commit_rate;
	}

	public void setCommit_rate(Double commit_rate) {
		this.commit_rate = commit_rate;
	}
	
	@ExcelField(title="宽限期", align=2, sort=17)
	public Double getGrace_period() {
		return grace_period;
	}

	public void setGrace_period(Double grace_period) {
		this.grace_period = grace_period;
	}
	
	@ExcelField(title="还款期", align=2, sort=18)
	public Double getRepay_period() {
		return repay_period;
	}

	public void setRepay_period(Double repay_period) {
		this.repay_period = repay_period;
	}
	
	@ExcelField(title="还款方式", align=2, sort=19)
	public String getRepay_type() {
		return repay_type;
	}

	public void setRepay_type(String repay_type) {
		this.repay_type = repay_type;
	}
	
	public Fea_fundssrcVO getFea_fundssrc() {
		return fea_fundssrc;
	}

	public void setFea_fundssrc(Fea_fundssrcVO fea_fundssrc) {
		this.fea_fundssrc = fea_fundssrc;
	}
	
}