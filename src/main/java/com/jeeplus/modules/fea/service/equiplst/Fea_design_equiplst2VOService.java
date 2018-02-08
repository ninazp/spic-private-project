/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.equiplst;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.equiplst.Fea_design_equiplst2VO;
import com.jeeplus.modules.fea.mapper.equiplst.Fea_design_equiplst2VOMapper;

/**
 * 地热供暖项目设备清单2Service
 * @author jw
 * @version 2018-02-08
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_equiplst2VOService extends CrudService<Fea_design_equiplst2VOMapper, Fea_design_equiplst2VO> {

	public Fea_design_equiplst2VO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_equiplst2VO> findList(Fea_design_equiplst2VO fea_design_equiplst2VO) {
		return super.findList(fea_design_equiplst2VO);
	}
	
	public Page<Fea_design_equiplst2VO> findPage(Page<Fea_design_equiplst2VO> page, Fea_design_equiplst2VO fea_design_equiplst2VO) {
		return super.findPage(page, fea_design_equiplst2VO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_equiplst2VO fea_design_equiplst2VO) {
		super.save(fea_design_equiplst2VO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_equiplst2VO fea_design_equiplst2VO) {
		super.delete(fea_design_equiplst2VO);
	}
	
}