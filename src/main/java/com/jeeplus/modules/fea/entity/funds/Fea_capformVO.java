/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.funds;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资产形成Entity
 * @author jw
 * @version 2017-11-18
 */
public class Fea_capformVO extends DataEntity<Fea_capformVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private Double fixassetprop;		// 固定资产形成比例（%）
	private Double uselifefat;		// 折旧年限（%）
	private Double residualrate;		// 残值率（%）
	private Double intangibleprop;		// 无形资产形成比例（%）
	private Double usefullitb;		// 折旧年限（%）
	private Double otherprop;		// 其他资产形成比例（%）
	private Double uselifeother;		// 折旧年限（%）
	
	public Fea_capformVO() {
		super();
	}

	public Fea_capformVO(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=1)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=2)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="固定资产形成比例（%）", align=2, sort=3)
	public Double getFixassetprop() {
		return fixassetprop;
	}

	public void setFixassetprop(Double fixassetprop) {
		this.fixassetprop = fixassetprop;
	}
	
	@ExcelField(title="折旧年限（%）", align=2, sort=4)
	public Double getUselifefat() {
		return uselifefat;
	}

	public void setUselifefat(Double uselifefat) {
		this.uselifefat = uselifefat;
	}
	
	@ExcelField(title="残值率（%）", align=2, sort=5)
	public Double getResidualrate() {
		return residualrate;
	}

	public void setResidualrate(Double residualrate) {
		this.residualrate = residualrate;
	}
	
	@ExcelField(title="无形资产形成比例（%）", align=2, sort=6)
	public Double getIntangibleprop() {
		return intangibleprop;
	}

	public void setIntangibleprop(Double intangibleprop) {
		this.intangibleprop = intangibleprop;
	}
	
	@ExcelField(title="折旧年限（%）", align=2, sort=7)
	public Double getUsefullitb() {
		return usefullitb;
	}

	public void setUsefullitb(Double usefullitb) {
		this.usefullitb = usefullitb;
	}
	
	@ExcelField(title="其他资产形成比例（%）", align=2, sort=8)
	public Double getOtherprop() {
		return otherprop;
	}

	public void setOtherprop(Double otherprop) {
		this.otherprop = otherprop;
	}
	
	@ExcelField(title="折旧年限（%）", align=2, sort=9)
	public Double getUselifeother() {
		return uselifeother;
	}

	public void setUselifeother(Double uselifeother) {
		this.uselifeother = uselifeother;
	}
	
}