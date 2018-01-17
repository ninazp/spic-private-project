/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.heattrans;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.heattrans.Fea_design_heattransVO;
import com.jeeplus.modules.fea.mapper.heattrans.Fea_design_heattransVOMapper;

/**
 * 换热器价格Service
 * @author jw
 * @version 2018-01-17
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_heattransVOService extends CrudService<Fea_design_heattransVOMapper, Fea_design_heattransVO> {

	public Fea_design_heattransVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_heattransVO> findList(Fea_design_heattransVO fea_design_heattransVO) {
		return super.findList(fea_design_heattransVO);
	}
	
	public Page<Fea_design_heattransVO> findPage(Page<Fea_design_heattransVO> page, Fea_design_heattransVO fea_design_heattransVO) {
		return super.findPage(page, fea_design_heattransVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_heattransVO fea_design_heattransVO) {
		super.save(fea_design_heattransVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_heattransVO fea_design_heattransVO) {
		super.delete(fea_design_heattransVO);
	}
	
}