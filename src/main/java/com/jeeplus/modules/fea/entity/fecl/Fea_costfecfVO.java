/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.fecl;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 财务费用及流动资金Entity
 * @author jw
 * @version 2017-12-06
 */
public class Fea_costfecfVO extends DataEntity<Fea_costfecfVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private Double circularate;		// 短期借款利率（%）
	private Double flowamt;		// 流动资金指标（万元）
	private Double flowloanprop;		// 流动资金贷款比例（%）
	private Double flowcaprate;		// 流动资金贷款利率（%）
	
	public Fea_costfecfVO() {
		super();
	}

	public Fea_costfecfVO(String id){
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
	
	@ExcelField(title="短期借款利率（%）", align=2, sort=9)
	public Double getCircularate() {
		return circularate;
	}

	public void setCircularate(Double circularate) {
		this.circularate = circularate;
	}
	
	@ExcelField(title="流动资金指标（万元）", align=2, sort=10)
	public Double getFlowamt() {
		return flowamt;
	}

	public void setFlowamt(Double flowamt) {
		this.flowamt = flowamt;
	}
	
	@ExcelField(title="流动资金贷款比例（%）", align=2, sort=11)
	public Double getFlowloanprop() {
		return flowloanprop;
	}

	public void setFlowloanprop(Double flowloanprop) {
		this.flowloanprop = flowloanprop;
	}
	
	@ExcelField(title="流动资金贷款利率（%）", align=2, sort=12)
	public Double getFlowcaprate() {
		return flowcaprate;
	}

	public void setFlowcaprate(Double flowcaprate) {
		this.flowcaprate = flowcaprate;
	}
	
}