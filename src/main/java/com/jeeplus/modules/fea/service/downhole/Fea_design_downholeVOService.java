/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.downhole;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.downhole.Fea_design_downholeVO;
import com.jeeplus.modules.fea.mapper.downhole.Fea_design_downholeVOMapper;

/**
 * 井下参数Service
 * @author jw
 * @version 2018-01-20
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_downholeVOService extends CrudService<Fea_design_downholeVOMapper, Fea_design_downholeVO> {

	public Fea_design_downholeVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_downholeVO> findList(Fea_design_downholeVO fea_design_downholeVO) {
		return super.findList(fea_design_downholeVO);
	}
	
	public Page<Fea_design_downholeVO> findPage(Page<Fea_design_downholeVO> page, Fea_design_downholeVO fea_design_downholeVO) {
		return super.findPage(page, fea_design_downholeVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_downholeVO fea_design_downholeVO) {
		super.save(fea_design_downholeVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_downholeVO fea_design_downholeVO) {
		super.delete(fea_design_downholeVO);
	}
	
}