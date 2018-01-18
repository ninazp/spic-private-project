/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.set;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.core.service.ServiceException;
import com.jeeplus.modules.fea.designcal.PubDesignCal;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.set.Fea_design_setVO;
import com.jeeplus.modules.fea.mapper.set.Fea_design_setVOMapper;

/**
 * 基本参数Service
 * @author jw
 * @version 2018-01-17
 */
@Service
@Transactional(readOnly = true)
public class Fea_design_setVOService extends CrudService<Fea_design_setVOMapper, Fea_design_setVO> {

	public Fea_design_setVO get(String id) {
		return super.get(id);
	}
	
	public List<Fea_design_setVO> findList(Fea_design_setVO fea_design_setVO) {
		return super.findList(fea_design_setVO);
	}
	
	public Page<Fea_design_setVO> findPage(Page<Fea_design_setVO> page, Fea_design_setVO fea_design_setVO) {
		return super.findPage(page, fea_design_setVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_design_setVO fea_design_setVO) {
		super.save(fea_design_setVO);
		
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		PubDesignCal pubDesignCal = (PubDesignCal) wac.getBean("pubDesignCal");
		
		FeaProjectB projectvo = fea_design_setVO.getFeaProjectB();
		
		try {
			pubDesignCal.calprocess(projectvo.getId());
		} catch (Exception e) {
			throw new ServiceException("计算出错："+e.getMessage());
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_design_setVO fea_design_setVO) {
		super.delete(fea_design_setVO);
	}
	
}