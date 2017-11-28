/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.funds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisBVO;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;

/**
 * 投资分配Service
 * @author jw
 * @version 2017-11-28
 */
@Service
@Transactional(readOnly = true)
public class Fea_investdisVOService extends CrudService<Fea_investdisVOMapper, Fea_investdisVO> {

	@Autowired
	private Fea_investdisBVOMapper fea_investdisBVOMapper;
	
	public Fea_investdisVO get(String id) {
		Fea_investdisVO fea_investdisVO = super.get(id);
		fea_investdisVO.setFea_investdisBVOList(fea_investdisBVOMapper.findList(new Fea_investdisBVO(fea_investdisVO)));
		return fea_investdisVO;
	}
	
	public List<Fea_investdisVO> findList(Fea_investdisVO fea_investdisVO) {
		return super.findList(fea_investdisVO);
	}
	
	public Page<Fea_investdisVO> findPage(Page<Fea_investdisVO> page, Fea_investdisVO fea_investdisVO) {
		return super.findPage(page, fea_investdisVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_investdisVO fea_investdisVO) {
		super.save(fea_investdisVO);
		for (Fea_investdisBVO fea_investdisBVO : fea_investdisVO.getFea_investdisBVOList()){
			if (fea_investdisBVO.getId() == null){
				continue;
			}
			if (Fea_investdisBVO.DEL_FLAG_NORMAL.equals(fea_investdisBVO.getDelFlag())){
				if (StringUtils.isBlank(fea_investdisBVO.getId())){
					fea_investdisBVO.setFea_investdis(fea_investdisVO);
					fea_investdisBVO.preInsert();
					fea_investdisBVOMapper.insert(fea_investdisBVO);
				}else{
					fea_investdisBVO.preUpdate();
					fea_investdisBVOMapper.update(fea_investdisBVO);
				}
			}else{
				fea_investdisBVOMapper.delete(fea_investdisBVO);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_investdisVO fea_investdisVO) {
		super.delete(fea_investdisVO);
		fea_investdisBVOMapper.delete(new Fea_investdisBVO(fea_investdisVO));
	}
	
}