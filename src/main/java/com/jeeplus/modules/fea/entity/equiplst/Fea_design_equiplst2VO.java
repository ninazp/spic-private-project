/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.equiplst;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 地热供暖项目设备清单2Entity
 * @author jw
 * @version 2018-02-08
 */
public class Fea_design_equiplst2VO extends DataEntity<Fea_design_equiplst2VO> {
	
	private static final long serialVersionUID = 1L;
	private FeaProjectB feaProjectB;		// 项目名称
	private String equipname;		// 设备名称
	private String perforparam;		// 选型参考性能参数
	private Double num;		// 数量
	private Double price;		// 参考价格
	private String unit;		// 单位
	private Office office;		// 部门
	
	public Fea_design_equiplst2VO() {
		super();
	}

	public Fea_design_equiplst2VO(String id){
		super(id);
	}

	@ExcelField(title="项目名称", fieldType=FeaProjectB.class, value="feaProjectB.projectName", align=2, sort=6)
	public FeaProjectB getFeaProjectB() {
		return feaProjectB;
	}

	public void setFeaProjectB(FeaProjectB feaProjectB) {
		this.feaProjectB = feaProjectB;
	}
	
	@ExcelField(title="设备名称", align=2, sort=7)
	public String getEquipname() {
		return equipname;
	}

	public void setEquipname(String equipname) {
		this.equipname = equipname;
	}
	
	@ExcelField(title="选型参考性能参数", align=2, sort=8)
	public String getPerforparam() {
		return perforparam;
	}

	public void setPerforparam(String perforparam) {
		this.perforparam = perforparam;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=9)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@NotNull(message="参考价格不能为空")
	@ExcelField(title="参考价格", align=2, sort=10)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="单位", align=2, sort=11)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="office.name", align=2, sort=12)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}