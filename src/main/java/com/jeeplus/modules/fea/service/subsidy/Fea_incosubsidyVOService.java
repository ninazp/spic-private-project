/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.subsidy;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.subsidy.Fea_incosubsidyVO;
import com.jeeplus.modules.fea.mapper.subsidy.Fea_incosubsidyVOMapper;

/**
 * 补贴收入Service
 * @author jw
 * @version 2017-12-03
 */
@Service
@Transactional(readOnly = true)
public class Fea_incosubsidyVOService extends CrudService<Fea_incosubsidyVOMapper, Fea_incosubsidyVO> {

	public Fea_incosubsidyVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_incosubsidyVO> findList(Fea_incosubsidyVO fea_incosubsidyVO) {
		return super.findList(fea_incosubsidyVO);
	}
	
	public Page<Fea_incosubsidyVO> findPage(Page<Fea_incosubsidyVO> page, Fea_incosubsidyVO fea_incosubsidyVO) {
		return super.findPage(page, fea_incosubsidyVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_incosubsidyVO fea_incosubsidyVO) {
		super.save(fea_incosubsidyVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_incosubsidyVO fea_incosubsidyVO) {
		super.delete(fea_incosubsidyVO);
	}
	
}