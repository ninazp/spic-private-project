/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.funds;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.funds.Fea_capformVO;
import com.jeeplus.modules.fea.mapper.funds.Fea_capformVOMapper;

/**
 * 资产形成Service
 * @author jw
 * @version 2017-12-06
 */
@Service
@Transactional(readOnly = true)
public class Fea_capformVOService extends CrudService<Fea_capformVOMapper, Fea_capformVO> {

	public Fea_capformVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_capformVO> findList(Fea_capformVO fea_capformVO) {
		return super.findList(fea_capformVO);
	}
	
	public Page<Fea_capformVO> findPage(Page<Fea_capformVO> page, Fea_capformVO fea_capformVO) {
		return super.findPage(page, fea_capformVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_capformVO fea_capformVO) {
		super.save(fea_capformVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_capformVO fea_capformVO) {
		super.delete(fea_capformVO);
	}
	
}