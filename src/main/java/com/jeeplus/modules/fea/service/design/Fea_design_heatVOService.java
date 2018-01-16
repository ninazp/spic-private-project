/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.design;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.mapper.design.Fea_design_heatVOMapper;

/**
 * 供热参数Service
 * @author jw
 * @version 2018-01-13
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_heatVOService extends CrudService<Fea_design_heatVOMapper, Fea_design_heatVO> {

	public Fea_design_heatVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_heatVO> findList(Fea_design_heatVO fea_design_heatVO) {
		return super.findList(fea_design_heatVO);
	}
	
	public Page<Fea_design_heatVO> findPage(Page<Fea_design_heatVO> page, Fea_design_heatVO fea_design_heatVO) {
		return super.findPage(page, fea_design_heatVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_heatVO fea_design_heatVO) {
		super.save(fea_design_heatVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_heatVO fea_design_heatVO) {
		super.delete(fea_design_heatVO);
	}
	
}