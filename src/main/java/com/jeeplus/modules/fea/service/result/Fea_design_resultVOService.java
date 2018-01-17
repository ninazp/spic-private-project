/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.result;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.result.Fea_design_resultVO;
import com.jeeplus.modules.fea.mapper.result.Fea_design_resultVOMapper;

/**
 * 方案运行费用结果表Service
 * @author jw
 * @version 2018-01-17
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_resultVOService extends CrudService<Fea_design_resultVOMapper, Fea_design_resultVO> {

	public Fea_design_resultVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_resultVO> findList(Fea_design_resultVO fea_design_resultVO) {
		return super.findList(fea_design_resultVO);
	}
	
	public Page<Fea_design_resultVO> findPage(Page<Fea_design_resultVO> page, Fea_design_resultVO fea_design_resultVO) {
		return super.findPage(page, fea_design_resultVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_resultVO fea_design_resultVO) {
		super.save(fea_design_resultVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_resultVO fea_design_resultVO) {
		super.delete(fea_design_resultVO);
	}
	
}