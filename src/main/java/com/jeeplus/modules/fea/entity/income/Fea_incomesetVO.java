/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.income;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 基本参数Entity
 * @author jw
 * @version 2017-12-06
 */
public class Fea_incomesetVO extends DataEntity<Fea_incomesetVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private Double vtaxrate;		// 综合增值税税率（%）
	private Double incomerate;		// 所得税税率（%）
	private Double umctax;		// 城市维护建设税率（%）
	private Double surtax;		// 教育费附加费率（%）
	private Double legalaccfund;		// 法定盈余公积金比例（%）
	private Double accfund;		// 任意盈余公积金比例（%）
	private String isaccfundwsd;		// 公积金提取不超过资本金的50%
	private Double yflrprop;		// 应付利润比例（%）
	private String isvtaxjzjt;		// 增值税即征即退50%
	private String issdssjsm;		// 所得税三免三减半
	private String capinvesttype;		// 资本金投入方式
	private Double capinvestprop;		// 资本金比例（%）
	private Double capinvestrate;		// 资本金基准收益率（%）
	private Double industrysqrate;		// 行业基准收益率（所得税前）（%）
	private Double industryshrate;		// 行业基准收益率（所得税后）（%）
	private Double capcostrate;		// 资本成本率（%）
	
	public Fea_incomesetVO() {
		super();
	}

	public Fea_incomesetVO(String id){
		super(id);
	}

	@ExcelField(title="项目", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=1)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="项目编码", align=2, sort=7)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=8)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="综合增值税税率（%）", align=2, sort=9)
	public Double getVtaxrate() {
		return vtaxrate;
	}

	public void setVtaxrate(Double vtaxrate) {
		this.vtaxrate = vtaxrate;
	}
	
	@ExcelField(title="所得税税率（%）", align=2, sort=10)
	public Double getIncomerate() {
		return incomerate;
	}

	public void setIncomerate(Double incomerate) {
		this.incomerate = incomerate;
	}
	
	@ExcelField(title="城市维护建设税率（%）", align=2, sort=11)
	public Double getUmctax() {
		return umctax;
	}

	public void setUmctax(Double umctax) {
		this.umctax = umctax;
	}
	
	@ExcelField(title="教育费附加费率（%）", align=2, sort=12)
	public Double getSurtax() {
		return surtax;
	}

	public void setSurtax(Double surtax) {
		this.surtax = surtax;
	}
	
	@ExcelField(title="法定盈余公积金比例（%）", align=2, sort=13)
	public Double getLegalaccfund() {
		return legalaccfund;
	}

	public void setLegalaccfund(Double legalaccfund) {
		this.legalaccfund = legalaccfund;
	}
	
	@ExcelField(title="任意盈余公积金比例（%）", align=2, sort=14)
	public Double getAccfund() {
		return accfund;
	}

	public void setAccfund(Double accfund) {
		this.accfund = accfund;
	}
	
	@ExcelField(title="公积金提取不超过资本金的50%", dictType="yes_no", align=2, sort=15)
	public String getIsaccfundwsd() {
		return isaccfundwsd;
	}

	public void setIsaccfundwsd(String isaccfundwsd) {
		this.isaccfundwsd = isaccfundwsd;
	}
	
	@ExcelField(title="应付利润比例（%）", align=2, sort=17)
	public Double getYflrprop() {
		return yflrprop;
	}

	public void setYflrprop(Double yflrprop) {
		this.yflrprop = yflrprop;
	}
	
	@ExcelField(title="增值税即征即退50%", dictType="yes_no", align=2, sort=18)
	public String getIsvtaxjzjt() {
		return isvtaxjzjt;
	}

	public void setIsvtaxjzjt(String isvtaxjzjt) {
		this.isvtaxjzjt = isvtaxjzjt;
	}
	
	@ExcelField(title="所得税三免三减半", dictType="yes_no", align=2, sort=19)
	public String getIssdssjsm() {
		return issdssjsm;
	}

	public void setIssdssjsm(String issdssjsm) {
		this.issdssjsm = issdssjsm;
	}
	
	@ExcelField(title="资本金投入方式", align=2, sort=20)
	public String getCapinvesttype() {
		return capinvesttype;
	}

	public void setCapinvesttype(String capinvesttype) {
		this.capinvesttype = capinvesttype;
	}
	
	@ExcelField(title="资本金比例（%）", align=2, sort=21)
	public Double getCapinvestprop() {
		return capinvestprop;
	}

	public void setCapinvestprop(Double capinvestprop) {
		this.capinvestprop = capinvestprop;
	}
	
	@ExcelField(title="资本金基准收益率（%）", align=2, sort=22)
	public Double getCapinvestrate() {
		return capinvestrate;
	}

	public void setCapinvestrate(Double capinvestrate) {
		this.capinvestrate = capinvestrate;
	}
	
	@ExcelField(title="行业基准收益率（所得税前）（%）", align=2, sort=23)
	public Double getIndustrysqrate() {
		return industrysqrate;
	}

	public void setIndustrysqrate(Double industrysqrate) {
		this.industrysqrate = industrysqrate;
	}
	
	@ExcelField(title="行业基准收益率（所得税后）（%）", align=2, sort=24)
	public Double getIndustryshrate() {
		return industryshrate;
	}

	public void setIndustryshrate(Double industryshrate) {
		this.industryshrate = industryshrate;
	}
	
	@ExcelField(title="资本成本率（%）", align=2, sort=25)
	public Double getCapcostrate() {
		return capcostrate;
	}

	public void setCapcostrate(Double capcostrate) {
		this.capcostrate = capcostrate;
	}
	
}