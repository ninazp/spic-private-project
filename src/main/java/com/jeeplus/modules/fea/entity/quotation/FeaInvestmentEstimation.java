/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.quotation;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 换热站设备购置费及安装工程投资估算Entity
 * @author zp
 * @version 2018-02-07
 */
public class FeaInvestmentEstimation extends DataEntity<FeaInvestmentEstimation> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目
	private Office office;		// 部门
	
	public FeaInvestmentEstimation() {
		super();
	}

	public FeaInvestmentEstimation(String id){
		super(id);
	}

	@NotNull(message="项目不能为空")
	@ExcelField(title="项目", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=1)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=8)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}