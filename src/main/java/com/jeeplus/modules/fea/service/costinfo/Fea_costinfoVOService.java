/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.costinfo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.costinfo.Fea_costinfoVO;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;

/**
 * 成本种类及产量Service
 * @author jw
 * @version 2017-11-09
 */
@Service
@Transactional(readOnly = true)
public class Fea_costinfoVOService extends CrudService<Fea_costinfoVOMapper, Fea_costinfoVO> {

	public Fea_costinfoVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_costinfoVO> findList(Fea_costinfoVO fea_costinfoVO) {
		return super.findList(fea_costinfoVO);
	}
	
	public Page<Fea_costinfoVO> findPage(Page<Fea_costinfoVO> page, Fea_costinfoVO fea_costinfoVO) {
		return super.findPage(page, fea_costinfoVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_costinfoVO fea_costinfoVO) {
		super.save(fea_costinfoVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_costinfoVO fea_costinfoVO) {
		super.delete(fea_costinfoVO);
	}
	
}