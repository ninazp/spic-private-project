/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.heatben;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 热泵价格Entity
 * @author jw
 * @version 2018-01-20
 */
public class Fea_design_heatbenVO extends DataEntity<Fea_design_heatbenVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private Double qr2;		// 制热量Qr2(kw)
	private Double pr2;		// 电热量Pr2(kw)
	private Double cr2;		// 价格Cr2（元）
	private Office office;		// 部门
	
	public Fea_design_heatbenVO() {
		super();
	}

	public Fea_design_heatbenVO(String id){
		super(id);
	}

	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=6)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="制热量Qr2(kw)", align=2, sort=7)
	public Double getQr2() {
		return qr2;
	}

	public void setQr2(Double qr2) {
		this.qr2 = qr2;
	}
	
	@ExcelField(title="电热量Pr2(kw)", align=2, sort=8)
	public Double getPr2() {
		return pr2;
	}

	public void setPr2(Double pr2) {
		this.pr2 = pr2;
	}
	
	@ExcelField(title="价格Cr2（元）", align=2, sort=9)
	public Double getCr2() {
		return cr2;
	}

	public void setCr2(Double cr2) {
		this.cr2 = cr2;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=10)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}