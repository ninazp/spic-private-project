/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.project;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.dao.PubUtil;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;

/**
 * 项目（子表）Service
 * @author zp
 * @version 2017-12-02
 */
@Service
@Transactional(readOnly = true)
public class FeaProjectBService extends CrudService<FeaProjectBMapper, FeaProjectB> {

	public FeaProjectB get(String id) {
		return super.get(id);
	}
	
	public List<FeaProjectB> findList(FeaProjectB feaProjectB) {
		return super.findList(feaProjectB);
	}
	
	public Page<FeaProjectB> findPage(Page<FeaProjectB> page, FeaProjectB feaProjectB) {
		return super.findPage(page, feaProjectB);
	}
	
	@Transactional(readOnly = false)
	public void save(FeaProjectB feaProjectB) {
        boolean isnew = false;
		if(feaProjectB.getIsNewRecord()){
			isnew = true;
		}
		super.save(feaProjectB);
		
		if(isnew){
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			Object reportbean = wac.getBean("pubutil");
			((PubUtil)reportbean).setdefaultData(feaProjectB);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(FeaProjectB feaProjectB) {
		super.delete(feaProjectB);
	}
	
}