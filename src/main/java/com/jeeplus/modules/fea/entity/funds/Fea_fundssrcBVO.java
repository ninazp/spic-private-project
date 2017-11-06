/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资金来源投资子表Entity
 * @author jw
 * @version 2017-11-05
 */
public class Fea_fundssrcBVO extends DataEntity<Fea_fundssrcBVO> {
	
	private static final long serialVersionUID = 1L;
	private String capital_type ;		// 注资方
	private String currency;		// 币种
	private String exchangerate;		// 汇率
	private Double cap_prop;		// 比例
	private Double cap_amt;		// 额度
	private Fea_fundssrcVO fea_fundssrc;		// 主外键 父类
	
	public Fea_fundssrcBVO() {
		super();
	}

	public Fea_fundssrcBVO(String id){
		super(id);
	}

	public Fea_fundssrcBVO(Fea_fundssrcVO fea_fundssrc){
		this.fea_fundssrc = fea_fundssrc;
	}

	@ExcelField(title="注资方", align=2, sort=7)
	public String getCapital_type () {
		return capital_type ;
	}

	public void setCapital_type (String capital_type ) {
		this.capital_type  = capital_type ;
	}
	
	@ExcelField(title="币种", align=2, sort=8)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@ExcelField(title="汇率", align=2, sort=9)
	public String getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(String exchangerate) {
		this.exchangerate = exchangerate;
	}
	
	@ExcelField(title="比例", align=2, sort=10)
	public Double getCap_prop() {
		return cap_prop;
	}

	public void setCap_prop(Double cap_prop) {
		this.cap_prop = cap_prop;
	}
	
	@ExcelField(title="额度", align=2, sort=11)
	public Double getCap_amt() {
		return cap_amt;
	}

	public void setCap_amt(Double cap_amt) {
		this.cap_amt = cap_amt;
	}
	
	public Fea_fundssrcVO getFea_fundssrc() {
		return fea_fundssrc;
	}

	public void setFea_fundssrc(Fea_fundssrcVO fea_fundssrc) {
		this.fea_fundssrc = fea_fundssrc;
	}
	
}