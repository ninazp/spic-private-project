/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.quotation;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.quotation.FeaDesignReport;
import com.jeeplus.modules.fea.mapper.quotation.FeaDesignReportMapper;

/**
 * 设备选型报价清单Service
 * @author zp
 * @version 2018-01-20
 */
@Service
@Transactional(readOnly = true)
public class FeaDesignReportService extends CrudService<FeaDesignReportMapper, FeaDesignReport> {

	public FeaDesignReport get(String id) {
		return super.get(id);
	}
	
	public List<FeaDesignReport> findList(FeaDesignReport feaDesignReport) {
		return super.findList(feaDesignReport);
	}
	
	public Page<FeaDesignReport> findPage(Page<FeaDesignReport> page, FeaDesignReport feaDesignReport) {
		return super.findPage(page, feaDesignReport);
	}
	
	@Transactional(readOnly = false)
	public void save(FeaDesignReport feaDesignReport) {
		super.save(feaDesignReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(FeaDesignReport feaDesignReport) {
		super.delete(feaDesignReport);
	}
	
}