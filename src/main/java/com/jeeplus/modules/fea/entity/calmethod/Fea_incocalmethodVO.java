/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.entity.calmethod;

import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 测算方式Entity
 * @author jw
 * @version 2017-11-08
 */
public class Fea_incocalmethodVO extends DataEntity<Fea_incocalmethodVO> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String projectname;		// 项目名称
	private String calmethod;		// 计算方式
	private String incometype;		// 目标收益选择
	private Double targetval;		// 目标值（%）
	private List<Fea_incocalmethodBVO> fea_incocalmethodBVOList = Lists.newArrayList();		// 子表列表
	private List<Fea_incocalmethodTVO> fea_incocalmethodTVOList = Lists.newArrayList();		// 子表列表
	
	public Fea_incocalmethodVO() {
		super();
	}

	public Fea_incocalmethodVO(String id){
		super(id);
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
	
	@ExcelField(title="计算方式", align=2, sort=9)
	public String getCalmethod() {
		return calmethod;
	}

	public void setCalmethod(String calmethod) {
		this.calmethod = calmethod;
	}
	
	@ExcelField(title="目标收益选择", align=2, sort=10)
	public String getIncometype() {
		return incometype;
	}

	public void setIncometype(String incometype) {
		this.incometype = incometype;
	}
	
	@ExcelField(title="目标值（%）", align=2, sort=11)
	public Double getTargetval() {
		return targetval;
	}

	public void setTargetval(Double targetval) {
		this.targetval = targetval;
	}
	
	public List<Fea_incocalmethodBVO> getFea_incocalmethodBVOList() {
		return fea_incocalmethodBVOList;
	}

	public void setFea_incocalmethodBVOList(List<Fea_incocalmethodBVO> fea_incocalmethodBVOList) {
		this.fea_incocalmethodBVOList = fea_incocalmethodBVOList;
	}
	public List<Fea_incocalmethodTVO> getFea_incocalmethodTVOList() {
		return fea_incocalmethodTVOList;
	}

	public void setFea_incocalmethodTVOList(List<Fea_incocalmethodTVO> fea_incocalmethodTVOList) {
		this.fea_incocalmethodTVOList = fea_incocalmethodTVOList;
	}
}