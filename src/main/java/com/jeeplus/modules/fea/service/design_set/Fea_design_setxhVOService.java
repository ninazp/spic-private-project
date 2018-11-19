/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.design_set;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.design_set.Fea_design_setxhVO;
import com.jeeplus.modules.fea.mapper.design_set.Fea_design_setxhVOMapper;

/**
 * 循环水泵价格Service
 * @author jw
 * @version 2018-11-19
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_setxhVOService extends CrudService<Fea_design_setxhVOMapper, Fea_design_setxhVO> {

	public Fea_design_setxhVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_setxhVO> findList(Fea_design_setxhVO fea_design_setxhVO) {
		return super.findList(fea_design_setxhVO);
	}
	
	public Page<Fea_design_setxhVO> findPage(Page<Fea_design_setxhVO> page, Fea_design_setxhVO fea_design_setxhVO) {
		return super.findPage(page, fea_design_setxhVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_setxhVO fea_design_setxhVO) {
		super.save(fea_design_setxhVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_setxhVO fea_design_setxhVO) {
		super.delete(fea_design_setxhVO);
	}
	
}