/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.totaltab;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.totaltab.Fea_finansumVO;
import com.jeeplus.modules.fea.mapper.totaltab.Fea_finansumVOMapper;

/**
 * 财务指标汇总表Service
 * @author jw
 * @version 2017-12-06
 */
@Service
@Transactional(readOnly = true)
public class Fea_finansumVOService extends CrudService<Fea_finansumVOMapper, Fea_finansumVO> {

	public Fea_finansumVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_finansumVO> findList(Fea_finansumVO fea_finansumVO) {
		return super.findList(fea_finansumVO);
	}
	
	public Page<Fea_finansumVO> findPage(Page<Fea_finansumVO> page, Fea_finansumVO fea_finansumVO) {
		return super.findPage(page, fea_finansumVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_finansumVO fea_finansumVO) {
		super.save(fea_finansumVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_finansumVO fea_finansumVO) {
		super.delete(fea_finansumVO);
	}
	
}