/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.procost;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 生成成本Entity
 * @author jw
 * @version 2017-12-06
 */
public class Fea_productcostVO extends DataEntity<Fea_productcostVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private Double persons;		// 定员（人）
	private Double perwage;		// 年人均工资
	private Double welfare;		// 福利费系数（%）
	private Double material;		// 材料费
	private Double insurance;		// 保险费率（‰）
	private Double wateramt;		// 泵送费
	private Double heatdeposit;		// 供暖费
	private Double intangibletx;		// 无形资产摊销（年）
	private Double otherassettx;		// 其他资产摊销（年）
	private Double othercost;		// 其他费用
	private List<Fea_productcostBVO> fea_productcostBVOList = Lists.newArrayList();		// 子表列表
	
	public Fea_productcostVO() {
		super();
	}

	public Fea_productcostVO(String id){
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
	
	@ExcelField(title="定员（人）", align=2, sort=9)
	public Double getPersons() {
		return persons;
	}

	public void setPersons(Double persons) {
		this.persons = persons;
	}
	
	@ExcelField(title="年人均工资", align=2, sort=10)
	public Double getPerwage() {
		return perwage;
	}

	public void setPerwage(Double perwage) {
		this.perwage = perwage;
	}
	
	@ExcelField(title="福利费系数（%）", align=2, sort=11)
	public Double getWelfare() {
		return welfare;
	}

	public void setWelfare(Double welfare) {
		this.welfare = welfare;
	}
	
	@ExcelField(title="材料费", align=2, sort=12)
	public Double getMaterial() {
		return material;
	}

	public void setMaterial(Double material) {
		this.material = material;
	}
	
	@ExcelField(title="保险费率（‰）", align=2, sort=13)
	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}
	
	@ExcelField(title="泵送费", align=2, sort=14)
	public Double getWateramt() {
		return wateramt;
	}

	public void setWateramt(Double wateramt) {
		this.wateramt = wateramt;
	}
	
	@ExcelField(title="供暖费", align=2, sort=16)
	public Double getHeatdeposit() {
		return heatdeposit;
	}

	public void setHeatdeposit(Double heatdeposit) {
		this.heatdeposit = heatdeposit;
	}
	
	@ExcelField(title="无形资产摊销（年）", align=2, sort=17)
	public Double getIntangibletx() {
		return intangibletx;
	}

	public void setIntangibletx(Double intangibletx) {
		this.intangibletx = intangibletx;
	}
	
	@ExcelField(title="其他资产摊销（年）", align=2, sort=18)
	public Double getOtherassettx() {
		return otherassettx;
	}

	public void setOtherassettx(Double otherassettx) {
		this.otherassettx = otherassettx;
	}
	
	@ExcelField(title="其他费用", align=2, sort=19)
	public Double getOthercost() {
		return othercost;
	}

	public void setOthercost(Double othercost) {
		this.othercost = othercost;
	}
	
	public List<Fea_productcostBVO> getFea_productcostBVOList() {
		return fea_productcostBVOList;
	}

	public void setFea_productcostBVOList(List<Fea_productcostBVO> fea_productcostBVOList) {
		this.fea_productcostBVOList = fea_productcostBVOList;
	}
}