/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.othercostinfo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.othercostinfo.FeaOthercostinfoVO;
import com.jeeplus.modules.fea.mapper.othercostinfo.FeaOthercostinfoVOMapper;

/**
 * 其他收入Service
 * @author jw
 * @version 2018-06-15
 */
@Service
@Transactional(readOnly = true)
public class FeaOthercostinfoVOService extends CrudService<FeaOthercostinfoVOMapper, FeaOthercostinfoVO> {

	public FeaOthercostinfoVO get(String id) {
		return super.get(id);
	}
	
	public List<FeaOthercostinfoVO> findList(FeaOthercostinfoVO feaOthercostinfoVO) {
		return super.findList(feaOthercostinfoVO);
	}
	
	public Page<FeaOthercostinfoVO> findPage(Page<FeaOthercostinfoVO> page, FeaOthercostinfoVO feaOthercostinfoVO) {
		return super.findPage(page, feaOthercostinfoVO);
	}
	
	@Transactional(readOnly = false)
	public void save(FeaOthercostinfoVO feaOthercostinfoVO) {
		super.save(feaOthercostinfoVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(FeaOthercostinfoVO feaOthercostinfoVO) {
		super.delete(feaOthercostinfoVO);
	}
	
}