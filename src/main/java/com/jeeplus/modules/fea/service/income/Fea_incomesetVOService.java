/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.income;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.income.Fea_incomesetVO;
import com.jeeplus.modules.fea.mapper.income.Fea_incomesetVOMapper;

/**
 * 基本参数Service
 * @author jw
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class Fea_incomesetVOService extends CrudService<Fea_incomesetVOMapper, Fea_incomesetVO> {

	public Fea_incomesetVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_incomesetVO> findList(Fea_incomesetVO fea_incomesetVO) {
		return super.findList(fea_incomesetVO);
	}
	
	public Page<Fea_incomesetVO> findPage(Page<Fea_incomesetVO> page, Fea_incomesetVO fea_incomesetVO) {
		return super.findPage(page, fea_incomesetVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_incomesetVO fea_incomesetVO) {
		super.save(fea_incomesetVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_incomesetVO fea_incomesetVO) {
		super.delete(fea_incomesetVO);
	}
	
}