/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.calmethod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.fea.entity.calmethod.Fea_incocalmethodVO;
import com.jeeplus.modules.fea.mapper.calmethod.Fea_incocalmethodVOMapper;
import com.jeeplus.modules.fea.entity.calmethod.Fea_incocalmethodBVO;
import com.jeeplus.modules.fea.mapper.calmethod.Fea_incocalmethodBVOMapper;
import com.jeeplus.modules.fea.entity.calmethod.Fea_incocalmethodTVO;
import com.jeeplus.modules.fea.mapper.calmethod.Fea_incocalmethodTVOMapper;

/**
 * 测算方式Service
 * @author jw
 * @version 2017-11-08
 */
@Service
@Transactional(readOnly = true)
public class Fea_incocalmethodVOService extends CrudService<Fea_incocalmethodVOMapper, Fea_incocalmethodVO> {

	@Autowired
	private Fea_incocalmethodBVOMapper fea_incocalmethodBVOMapper;
	@Autowired
	private Fea_incocalmethodTVOMapper fea_incocalmethodTVOMapper;
	
	public Fea_incocalmethodVO get(String id) {
		Fea_incocalmethodVO fea_incocalmethodVO = super.get(id);
		fea_incocalmethodVO.setFea_incocalmethodBVOList(fea_incocalmethodBVOMapper.findList(new Fea_incocalmethodBVO(fea_incocalmethodVO)));
		fea_incocalmethodVO.setFea_incocalmethodTVOList(fea_incocalmethodTVOMapper.findList(new Fea_incocalmethodTVO(fea_incocalmethodVO)));
		return fea_incocalmethodVO;
	}
	
	public List<Fea_incocalmethodVO> findList(Fea_incocalmethodVO fea_incocalmethodVO) {
		return super.findList(fea_incocalmethodVO);
	}
	
	public Page<Fea_incocalmethodVO> findPage(Page<Fea_incocalmethodVO> page, Fea_incocalmethodVO fea_incocalmethodVO) {
		return super.findPage(page, fea_incocalmethodVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_incocalmethodVO fea_incocalmethodVO) {
		super.save(fea_incocalmethodVO);
		for (Fea_incocalmethodBVO fea_incocalmethodBVO : fea_incocalmethodVO.getFea_incocalmethodBVOList()){
			if (fea_incocalmethodBVO.getId() == null){
				continue;
			}
			if (Fea_incocalmethodBVO.DEL_FLAG_NORMAL.equals(fea_incocalmethodBVO.getDelFlag())){
				if (StringUtils.isBlank(fea_incocalmethodBVO.getId())){
					fea_incocalmethodBVO.setFea_incocalmethod(fea_incocalmethodVO);
					fea_incocalmethodBVO.preInsert();
					fea_incocalmethodBVOMapper.insert(fea_incocalmethodBVO);
				}else{
					fea_incocalmethodBVO.preUpdate();
					fea_incocalmethodBVOMapper.update(fea_incocalmethodBVO);
				}
			}else{
				fea_incocalmethodBVOMapper.delete(fea_incocalmethodBVO);
			}
		}
		for (Fea_incocalmethodTVO fea_incocalmethodTVO : fea_incocalmethodVO.getFea_incocalmethodTVOList()){
			if (fea_incocalmethodTVO.getId() == null){
				continue;
			}
			if (Fea_incocalmethodTVO.DEL_FLAG_NORMAL.equals(fea_incocalmethodTVO.getDelFlag())){
				if (StringUtils.isBlank(fea_incocalmethodTVO.getId())){
					fea_incocalmethodTVO.setFea_incocalmethod(fea_incocalmethodVO);
					fea_incocalmethodTVO.preInsert();
					fea_incocalmethodTVOMapper.insert(fea_incocalmethodTVO);
				}else{
					fea_incocalmethodTVO.preUpdate();
					fea_incocalmethodTVOMapper.update(fea_incocalmethodTVO);
				}
			}else{
				fea_incocalmethodTVOMapper.delete(fea_incocalmethodTVO);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_incocalmethodVO fea_incocalmethodVO) {
		super.delete(fea_incocalmethodVO);
		fea_incocalmethodBVOMapper.delete(new Fea_incocalmethodBVO(fea_incocalmethodVO));
		fea_incocalmethodTVOMapper.delete(new Fea_incocalmethodTVO(fea_incocalmethodVO));
	}
	
}