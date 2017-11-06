/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.fecl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.fecl.Fea_costfecfVO;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;

/**
 * 财务费用及流动资金Service
 * @author jw
 * @version 2017-11-06
 */
@Service
@Transactional(readOnly = true)
public class Fea_costfecfVOService extends CrudService<Fea_costfecfVOMapper, Fea_costfecfVO> {

	public Fea_costfecfVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_costfecfVO> findList(Fea_costfecfVO fea_costfecfVO) {
		return super.findList(fea_costfecfVO);
	}
	
	public Page<Fea_costfecfVO> findPage(Page<Fea_costfecfVO> page, Fea_costfecfVO fea_costfecfVO) {
		return super.findPage(page, fea_costfecfVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_costfecfVO fea_costfecfVO) {
		super.save(fea_costfecfVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_costfecfVO fea_costfecfVO) {
		super.delete(fea_costfecfVO);
	}
	
}