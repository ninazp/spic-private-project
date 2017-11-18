/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 投资分配组成Entity
 * @author jw
 * @version 2017-11-18
 */
public class Fea_investdisBVO extends DataEntity<Fea_investdisBVO> {
	
	private static final long serialVersionUID = 1L;
	private String investtype;		// 投资类别
	private Double investprop;		// 投资比例（%）
	private Double investamt;		// 投资金额
	private Fea_investdisVO fea_investdis;		// 主表主键 父类
	
	public Fea_investdisBVO() {
		super();
	}

	public Fea_investdisBVO(String id){
		super(id);
	}

	public Fea_investdisBVO(Fea_investdisVO fea_investdis){
		this.fea_investdis = fea_investdis;
	}

	@ExcelField(title="投资类别", align=2, sort=1)
	public String getInvesttype() {
		return investtype;
	}

	public void setInvesttype(String investtype) {
		this.investtype = investtype;
	}
	
	@ExcelField(title="投资比例（%）", align=2, sort=2)
	public Double getInvestprop() {
		return investprop;
	}

	public void setInvestprop(Double investprop) {
		this.investprop = investprop;
	}
	
	@ExcelField(title="投资金额", align=2, sort=3)
	public Double getInvestamt() {
		return investamt;
	}

	public void setInvestamt(Double investamt) {
		this.investamt = investamt;
	}
	
	public Fea_investdisVO getFea_investdis() {
		return fea_investdis;
	}

	public void setFea_investdis(Fea_investdisVO fea_investdis) {
		this.fea_investdis = fea_investdis;
	}
	
}