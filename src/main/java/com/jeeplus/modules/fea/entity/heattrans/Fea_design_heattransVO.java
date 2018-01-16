/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.heattrans;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 换热器价格Entity
 * @author jw
 * @version 2018-01-16
 */
public class Fea_design_heattransVO extends DataEntity<Fea_design_heattransVO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private Double price;		// 价格ch
	private Double lowarea;		// 最低面积
	private Double higharea;		// 最高面积
	private Office office;		// 部门
	
	public Fea_design_heattransVO() {
		super();
	}

	public Fea_design_heattransVO(String id){
		super(id);
	}

	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=6)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="价格ch", align=2, sort=7)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="最低面积", align=2, sort=8)
	public Double getLowarea() {
		return lowarea;
	}

	public void setLowarea(Double lowarea) {
		this.lowarea = lowarea;
	}
	
	@ExcelField(title="最高面积", align=2, sort=9)
	public Double getHigharea() {
		return higharea;
	}

	public void setHigharea(Double higharea) {
		this.higharea = higharea;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=10)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}