/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 投资分配Entity
 * @author jw
 * @version 2017-12-06
 */
public class Fea_investdisVO extends DataEntity<Fea_investdisVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private String year;		// 年度
	private Double investprop;		// 投资比例
	private Double investamt;		// 投资额度
	private Double deductvtax;		// 可抵扣税金
	private Double cappropsum;		// 注资方合计
	private Double loanpropsum;		// 融资合计
	private List<Fea_investdisBVO> fea_investdisBVOList = Lists.newArrayList();		// 子表列表
	
	public Fea_investdisVO() {
		super();
	}

	public Fea_investdisVO(String id){
		super(id);
	}

	@ExcelField(title="项目", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=1)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="项目编码", align=2, sort=2)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=3)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="年度", align=2, sort=4)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@ExcelField(title="投资比例", align=2, sort=5)
	public Double getInvestprop() {
		return investprop;
	}

	public void setInvestprop(Double investprop) {
		this.investprop = investprop;
	}
	
	@ExcelField(title="投资额度", align=2, sort=6)
	public Double getInvestamt() {
		return investamt;
	}

	public void setInvestamt(Double investamt) {
		this.investamt = investamt;
	}
	
	@ExcelField(title="可抵扣税金", align=2, sort=7)
	public Double getDeductvtax() {
		return deductvtax;
	}

	public void setDeductvtax(Double deductvtax) {
		this.deductvtax = deductvtax;
	}
	
	@ExcelField(title="注资方合计", align=2, sort=8)
	public Double getCappropsum() {
		return cappropsum;
	}

	public void setCappropsum(Double cappropsum) {
		this.cappropsum = cappropsum;
	}
	
	@ExcelField(title="融资合计", align=2, sort=9)
	public Double getLoanpropsum() {
		return loanpropsum;
	}

	public void setLoanpropsum(Double loanpropsum) {
		this.loanpropsum = loanpropsum;
	}
	
	public List<Fea_investdisBVO> getFea_investdisBVOList() {
		return fea_investdisBVOList;
	}

	public void setFea_investdisBVOList(List<Fea_investdisBVO> fea_investdisBVOList) {
		this.fea_investdisBVOList = fea_investdisBVOList;
	}
}