/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资金来源投资子表Entity
 * @author jw
 * @version 2017-11-19
 */
public class Fea_fundssrcBVO extends DataEntity<Fea_fundssrcBVO> {
	
	private static final long serialVersionUID = 1L;
	private String capitaltype ;		// 注资方
	private String currency;		// 币种
	private String exchangerate;		// 汇率
	private Double capprop;		// 比例
	private Double capamt;		// 注资金额
	private Double jstzamt;		// 其中建设投资资本金
	private Double ldtzamt;		// 其中流动资金资本金
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

	@ExcelField(title="注资方", align=2, sort=2)
	public String getCapitaltype () {
		return capitaltype ;
	}

	public void setCapitaltype (String capitaltype ) {
		this.capitaltype  = capitaltype ;
	}
	
	@ExcelField(title="币种", align=2, sort=3)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@ExcelField(title="汇率", align=2, sort=4)
	public String getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(String exchangerate) {
		this.exchangerate = exchangerate;
	}
	
	@ExcelField(title="比例", align=2, sort=5)
	public Double getCapprop() {
		return capprop;
	}

	public void setCapprop(Double capprop) {
		this.capprop = capprop;
	}
	
	@ExcelField(title="注资金额", align=2, sort=6)
	public Double getCapamt() {
		return capamt;
	}

	public void setCapamt(Double capamt) {
		this.capamt = capamt;
	}
	
	@ExcelField(title="其中建设投资资本金", align=2, sort=7)
	public Double getJstzamt() {
		return jstzamt;
	}

	public void setJstzamt(Double jstzamt) {
		this.jstzamt = jstzamt;
	}
	
	@ExcelField(title="其中流动资金资本金", align=2, sort=8)
	public Double getLdtzamt() {
		return ldtzamt;
	}

	public void setLdtzamt(Double ldtzamt) {
		this.ldtzamt = ldtzamt;
	}
	
	public Fea_fundssrcVO getFea_fundssrc() {
		return fea_fundssrc;
	}

	public void setFea_fundssrc(Fea_fundssrcVO fea_fundssrc) {
		this.fea_fundssrc = fea_fundssrc;
	}
	
}