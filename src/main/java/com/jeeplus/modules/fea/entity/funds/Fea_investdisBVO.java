/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 分配组成Entity
 * @author jw
 * @version 2018-05-09
 */
public class Fea_investdisBVO extends DataEntity<Fea_investdisBVO> {
	
	private static final long serialVersionUID = 1L;
	private String zjname;		// 资金方名称
	private String investtype;		// 资金方类别
	private Double investprop;		// 当期比例（%）
	private Double investamt;		// 资金金额
	private Double jsamt;		// 用于建设金额
	private Double ldamt;		// 用于流动资金金额
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

	@ExcelField(title="资金方名称", align=2, sort=1)
	public String getZjname() {
		return zjname;
	}

	public void setZjname(String zjname) {
		this.zjname = zjname;
	}
	
	@ExcelField(title="资金方类别", dictType="distrtype", align=2, sort=2)
	public String getInvesttype() {
		return investtype;
	}

	public void setInvesttype(String investtype) {
		this.investtype = investtype;
	}
	
	@ExcelField(title="当期比例（%）", align=2, sort=3)
	public Double getInvestprop() {
		return investprop;
	}

	public void setInvestprop(Double investprop) {
		this.investprop = investprop;
	}
	
	@ExcelField(title="资金金额", align=2, sort=4)
	public Double getInvestamt() {
		return investamt;
	}

	public void setInvestamt(Double investamt) {
		this.investamt = investamt;
	}
	
	@ExcelField(title="用于建设金额", align=2, sort=5)
	public Double getJsamt() {
		return jsamt;
	}

	public void setJsamt(Double jsamt) {
		this.jsamt = jsamt;
	}
	
	@ExcelField(title="用于流动资金金额", align=2, sort=6)
	public Double getLdamt() {
		return ldamt;
	}

	public void setLdamt(Double ldamt) {
		this.ldamt = ldamt;
	}
	
	public Fea_investdisVO getFea_investdis() {
		return fea_investdis;
	}

	public void setFea_investdis(Fea_investdisVO fea_investdis) {
		this.fea_investdis = fea_investdis;
	}
	
}