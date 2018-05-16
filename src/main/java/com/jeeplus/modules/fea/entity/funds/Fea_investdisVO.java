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
 * @version 2018-05-16
 */
public class Fea_investdisVO extends DataEntity<Fea_investdisVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private Double fea_fundssrc_investtotal;		// 资金来源-总资金
	private String isreaddesgn;		// 是否依赖方案设计
	private Double djamt;		// 打井费用
	private Double transamt;		// 换热站建设费
	private Double equitamt;		// 设备购置费
	private Double setupamt;		// 安装费
	private Double gwamt;		// 管网费
	private Double otheramt;		// 其他费
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private String year;		// 年度
	private Double investprop;		// 投资比例
	private Double investamt;		// 投资额度
	private Double deductvtax;		// 可抵扣税金
	private Double cappropsum;		// 注资方合计
	private Double loanpropsum;		// 融资合计
	private String heatarea;		// 供热面积
	private String office;		// 部门
	private String pk_corp;		// 公司
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
	
	@ExcelField(title="资金来源-总资金", align=2, sort=2)
	public Double getFea_fundssrc_investtotal() {
		return fea_fundssrc_investtotal;
	}

	public void setFea_fundssrc_investtotal(Double fea_fundssrc_investtotal) {
		this.fea_fundssrc_investtotal = fea_fundssrc_investtotal;
	}
	
	@ExcelField(title="是否依赖方案设计", dictType="yes_no", align=2, sort=3)
	public String getIsreaddesgn() {
		return isreaddesgn;
	}

	public void setIsreaddesgn(String isreaddesgn) {
		this.isreaddesgn = isreaddesgn;
	}
	
	@ExcelField(title="打井费用", align=2, sort=4)
	public Double getDjamt() {
		return djamt;
	}

	public void setDjamt(Double djamt) {
		this.djamt = djamt;
	}
	
	@ExcelField(title="换热站建设费", align=2, sort=5)
	public Double getTransamt() {
		return transamt;
	}

	public void setTransamt(Double transamt) {
		this.transamt = transamt;
	}
	
	@ExcelField(title="设备购置费", align=2, sort=6)
	public Double getEquitamt() {
		return equitamt;
	}

	public void setEquitamt(Double equitamt) {
		this.equitamt = equitamt;
	}
	
	@ExcelField(title="安装费", align=2, sort=7)
	public Double getSetupamt() {
		return setupamt;
	}

	public void setSetupamt(Double setupamt) {
		this.setupamt = setupamt;
	}
	
	@ExcelField(title="管网费", align=2, sort=8)
	public Double getGwamt() {
		return gwamt;
	}

	public void setGwamt(Double gwamt) {
		this.gwamt = gwamt;
	}
	
	@ExcelField(title="其他费", align=2, sort=9)
	public Double getOtheramt() {
		return otheramt;
	}

	public void setOtheramt(Double otheramt) {
		this.otheramt = otheramt;
	}
	
	@ExcelField(title="项目编码", align=2, sort=10)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=11)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="年度", align=2, sort=12)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@ExcelField(title="投资比例", align=2, sort=13)
	public Double getInvestprop() {
		return investprop;
	}

	public void setInvestprop(Double investprop) {
		this.investprop = investprop;
	}
	
	@ExcelField(title="投资额度", align=2, sort=14)
	public Double getInvestamt() {
		return investamt;
	}

	public void setInvestamt(Double investamt) {
		this.investamt = investamt;
	}
	
	@ExcelField(title="可抵扣税金", align=2, sort=15)
	public Double getDeductvtax() {
		return deductvtax;
	}

	public void setDeductvtax(Double deductvtax) {
		this.deductvtax = deductvtax;
	}
	
	@ExcelField(title="注资方合计", align=2, sort=16)
	public Double getCappropsum() {
		return cappropsum;
	}

	public void setCappropsum(Double cappropsum) {
		this.cappropsum = cappropsum;
	}
	
	@ExcelField(title="融资合计", align=2, sort=17)
	public Double getLoanpropsum() {
		return loanpropsum;
	}

	public void setLoanpropsum(Double loanpropsum) {
		this.loanpropsum = loanpropsum;
	}
	
	@ExcelField(title="供热面积", align=2, sort=24)
	public String getHeatarea() {
		return heatarea;
	}

	public void setHeatarea(String heatarea) {
		this.heatarea = heatarea;
	}
	
	@ExcelField(title="部门", align=2, sort=25)
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
	@ExcelField(title="公司", align=2, sort=26)
	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	
	public List<Fea_investdisBVO> getFea_investdisBVOList() {
		return fea_investdisBVOList;
	}

	public void setFea_investdisBVOList(List<Fea_investdisBVO> fea_investdisBVOList) {
		this.fea_investdisBVOList = fea_investdisBVOList;
	}
}