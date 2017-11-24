/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.totaltab;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.totaltab.Fea_finansumVO;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.totaltab.Fea_finansumVOMapper;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;

/**
 * 财务指标汇总表Service
 * @author jw
 * @version 2017-11-22
 */
@Service
@Transactional(readOnly = true)
public class Fea_finansumVOService extends CrudService<Fea_finansumVOMapper, Fea_finansumVO> {

	@Autowired
	private FeaProjectBMapper projectmapper;
	@Autowired
	private Fea_fundssrcVOMapper fundsrcVOmapper;
	
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
		//通过id查询得到项目信息
		FeaProjectB  projectvo =  projectmapper.get("dc940aa030b04f9ab32e543574cc847d");
		
		List<Double> projectinfo = CountHander.handprject(projectvo);
		
		//查询资金来源
		Fea_fundssrcVO fundsrcvo = fundsrcVOmapper.findUniqueByProperty("projectcode", "B0001");
		
		List<Object>   objlst = PubBaseDAO.getMutiParentVO("fea_fundssrc", "id", " projectcode='B0001' ", fundsrcVOmapper);
		
		
		
		System.out.println(fundsrcvo.getProjectCode());
		
//		super.delete(fea_finansumVO);
	}
	
}