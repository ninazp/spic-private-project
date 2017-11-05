/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.projectmng.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 项目管理子表Entity
 * @author zhaopeng
 * @version 2017-08-09
 */
public class ProjectMngB extends DataEntity<ProjectMngB> {
	
	private static final long serialVersionUID = 1L;
	private PorjectMng pid;		// 关联主表 父类
	private String name;		// 工程名称
	private String configuration;		// 机组配置
	private Integer productionPeriod;		// 生产期(年)
	private Date startDate;		// 开工日期
	
	public ProjectMngB() {
		super();
	}

	public ProjectMngB(String id){
		super(id);
	}

	public ProjectMngB(PorjectMng pid){
		this.pid = pid;
	}

	public PorjectMng getPid() {
		return pid;
	}

	public void setPid(PorjectMng pid) {
		this.pid = pid;
	}
	
	@ExcelField(title="工程名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="机组配置", align=2, sort=9)
	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	
	@ExcelField(title="生产期(年)", align=2, sort=10)
	public Integer getProductionPeriod() {
		return productionPeriod;
	}

	public void setProductionPeriod(Integer productionPeriod) {
		this.productionPeriod = productionPeriod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开工日期", align=2, sort=11)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}