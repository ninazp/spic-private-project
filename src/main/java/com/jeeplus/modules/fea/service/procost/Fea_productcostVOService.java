/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.procost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostVO;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.entity.procost.Fea_productcostBVO;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;

/**
 * 生成成本Service
 * @author jw
 * @version 2018-02-06
 */
@Service
@Transactional(readOnly = true)
public class Fea_productcostVOService extends CrudService<Fea_productcostVOMapper, Fea_productcostVO> {

	@Autowired
	private Fea_productcostBVOMapper fea_productcostBVOMapper;
	
	public Fea_productcostVO get(String id) {
		Fea_productcostVO fea_productcostVO = super.get(id);
		fea_productcostVO.setFea_productcostBVOList(fea_productcostBVOMapper.findList(new Fea_productcostBVO(fea_productcostVO)));
		return fea_productcostVO;
	}
	
	public List<Fea_productcostVO> findList(Fea_productcostVO fea_productcostVO) {
		return super.findList(fea_productcostVO);
	}
	
	public Page<Fea_productcostVO> findPage(Page<Fea_productcostVO> page, Fea_productcostVO fea_productcostVO) {
		return super.findPage(page, fea_productcostVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_productcostVO fea_productcostVO) {
		super.save(fea_productcostVO);
		for (Fea_productcostBVO fea_productcostBVO : fea_productcostVO.getFea_productcostBVOList()){
			if (fea_productcostBVO.getId() == null){
				continue;
			}
			if (Fea_productcostBVO.DEL_FLAG_NORMAL.equals(fea_productcostBVO.getDelFlag())){
				if (StringUtils.isBlank(fea_productcostBVO.getId())){
					fea_productcostBVO.setFea_productcost(fea_productcostVO);
					fea_productcostBVO.preInsert();
					fea_productcostBVOMapper.insert(fea_productcostBVO);
				}else{
					fea_productcostBVO.preUpdate();
					fea_productcostBVOMapper.update(fea_productcostBVO);
				}
			}else{
				fea_productcostBVOMapper.delete(fea_productcostBVO);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_productcostVO fea_productcostVO) {
		super.delete(fea_productcostVO);
		fea_productcostBVOMapper.delete(new Fea_productcostBVO(fea_productcostVO));
	}
	
}