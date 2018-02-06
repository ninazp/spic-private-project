/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.transfer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;
import com.jeeplus.modules.fea.mapper.transfer.Fea_design_transferVOMapper;

/**
 * 换热参数Service
 * @author jw
 * @version 2018-02-05
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_transferVOService extends CrudService<Fea_design_transferVOMapper, Fea_design_transferVO> {

	public Fea_design_transferVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_transferVO> findList(Fea_design_transferVO fea_design_transferVO) {
		return super.findList(fea_design_transferVO);
	}
	
	public Page<Fea_design_transferVO> findPage(Page<Fea_design_transferVO> page, Fea_design_transferVO fea_design_transferVO) {
		return super.findPage(page, fea_design_transferVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_transferVO fea_design_transferVO) {
		super.save(fea_design_transferVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_transferVO fea_design_transferVO) {
		super.delete(fea_design_transferVO);
	}
	
	public Fea_design_transferVO getFea_design_transferVOByProjectId(String projectId){
		Fea_design_transferVO fea_design_transferVO = this.mapper.findUniqueByProperty("project_id", projectId);
		return fea_design_transferVO;
	}
	
}