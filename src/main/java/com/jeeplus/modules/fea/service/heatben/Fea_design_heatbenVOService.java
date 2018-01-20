/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.heatben;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.heatben.Fea_design_heatbenVO;
import com.jeeplus.modules.fea.mapper.heatben.Fea_design_heatbenVOMapper;

/**
 * 热泵价格Service
 * @author jw
 * @version 2018-01-20
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_heatbenVOService extends CrudService<Fea_design_heatbenVOMapper, Fea_design_heatbenVO> {

	public Fea_design_heatbenVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_heatbenVO> findList(Fea_design_heatbenVO fea_design_heatbenVO) {
		return super.findList(fea_design_heatbenVO);
	}
	
	public Page<Fea_design_heatbenVO> findPage(Page<Fea_design_heatbenVO> page, Fea_design_heatbenVO fea_design_heatbenVO) {
		return super.findPage(page, fea_design_heatbenVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_heatbenVO fea_design_heatbenVO) {
		super.save(fea_design_heatbenVO);
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_heatbenVO fea_design_heatbenVO) {
		super.delete(fea_design_heatbenVO);
	}
	
}