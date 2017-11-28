/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.analysisearnings.entity;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 敏感分析（单因素）Entity
 * @author zp
 * @version 2017-11-28
 */
public class AnalysisEarnings extends DataEntity<AnalysisEarnings> {
	
	private static final long serialVersionUID = 1L;
	private String lineOptions;		// 线条选项
	private String initialOutlay;		// 初始投资
	private String electricityAmount;		// 电费
	private String waterAmount;		// 水费
	private String laborAmount;		// 人工费
	private String warmAmount;		// 取暖费
	private FeaProjectB feaProjectB;		// 项目
	private List<AnalysisEarningsB> analysisEarningsBList = Lists.newArrayList();		// 子表列表
	
	public AnalysisEarnings() {
		super();
	}

	public AnalysisEarnings(String id){
		super(id);
	}

	@ExcelField(title="线条选项", dictType="line_options", align=2, sort=7)
	public String getLineOptions() {
		return lineOptions;
	}

	public void setLineOptions(String lineOptions) {
		this.lineOptions = lineOptions;
	}
	
	@ExcelField(title="初始投资", align=2, sort=8)
	public String getInitialOutlay() {
		return initialOutlay;
	}

	public void setInitialOutlay(String initialOutlay) {
		this.initialOutlay = initialOutlay;
	}
	
	@ExcelField(title="电费", align=2, sort=9)
	public String getElectricityAmount() {
		return electricityAmount;
	}

	public void setElectricityAmount(String electricityAmount) {
		this.electricityAmount = electricityAmount;
	}
	
	@ExcelField(title="水费", align=2, sort=10)
	public String getWaterAmount() {
		return waterAmount;
	}

	public void setWaterAmount(String waterAmount) {
		this.waterAmount = waterAmount;
	}
	
	@ExcelField(title="人工费", align=2, sort=11)
	public String getLaborAmount() {
		return laborAmount;
	}

	public void setLaborAmount(String laborAmount) {
		this.laborAmount = laborAmount;
	}
	
	@ExcelField(title="取暖费", align=2, sort=12)
	public String getWarmAmount() {
		return warmAmount;
	}

	public void setWarmAmount(String warmAmount) {
		this.warmAmount = warmAmount;
	}
	
	@ExcelField(title="项目", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=13)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	public List<AnalysisEarningsB> getAnalysisEarningsBList() {
		return analysisEarningsBList;
	}

	public void setAnalysisEarningsBList(List<AnalysisEarningsB> analysisEarningsBList) {
		this.analysisEarningsBList = analysisEarningsBList;
	}
}