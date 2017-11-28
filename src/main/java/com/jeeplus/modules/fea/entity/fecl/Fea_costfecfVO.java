/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.fecl;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 财务费用及流动资金Entity
 * @author jw
 * @version 2017-11-28
 */
public class Fea_costfecfVO extends DataEntity<Fea_costfecfVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private String loanyears;		// 借款年度
	private Double circulaamt;		// 短期借款金额
	private Double circularate;		// 短期借款利率（%）
	private Double circulaloanrate;		// 偿还短期借款金额
	private Double dqjkintrest;		// 短期借款利息
	
	public Fea_costfecfVO() {
		super();
	}

	public Fea_costfecfVO(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=6)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=7)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="借款年度", align=2, sort=8)
	public String getLoanyears() {
		return loanyears;
	}

	public void setLoanyears(String loanyears) {
		this.loanyears = loanyears;
	}
	
	@ExcelField(title="短期借款金额", align=2, sort=9)
	public Double getCirculaamt() {
		return circulaamt;
	}

	public void setCirculaamt(Double circulaamt) {
		this.circulaamt = circulaamt;
	}
	
	@ExcelField(title="短期借款利率（%）", align=2, sort=10)
	public Double getCircularate() {
		return circularate;
	}

	public void setCircularate(Double circularate) {
		this.circularate = circularate;
	}
	
	@ExcelField(title="偿还短期借款金额", align=2, sort=11)
	public Double getCirculaloanrate() {
		return circulaloanrate;
	}

	public void setCirculaloanrate(Double circulaloanrate) {
		this.circulaloanrate = circulaloanrate;
	}
	
	@ExcelField(title="短期借款利息", align=2, sort=12)
	public Double getDqjkintrest() {
		return dqjkintrest;
	}

	public void setDqjkintrest(Double dqjkintrest) {
		this.dqjkintrest = dqjkintrest;
	}
	
}