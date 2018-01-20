/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.quotation;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设备选型报价清单Entity
 * @author zp
 * @version 2018-01-20
 */
public class FeaDesignReport extends DataEntity<FeaDesignReport> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private String name;		// 设备名称
	private String parameter;		// 选型参考性能参数
	private Double number;		// 数量
	private Double price;		// 参考价格（万元）
	private String rownum;		// 行号
	
	public FeaDesignReport() {
		super();
	}

	public FeaDesignReport(String id){
		super(id);
	}

	@ExcelField(title="项目", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=1)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="设备名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="选型参考性能参数", align=2, sort=9)
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	@ExcelField(title="数量", align=2, sort=10)
	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}
	
	@ExcelField(title="参考价格（万元）", align=2, sort=11)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="行号", align=2, sort=12)
	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}
	
}