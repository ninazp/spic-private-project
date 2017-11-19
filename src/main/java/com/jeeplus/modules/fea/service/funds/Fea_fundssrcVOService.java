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
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcBVO;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcBVOMapper;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcTVO;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcTVOMapper;

/**
 * 资金来源Service
 * @author jw
 * @version 2017-11-19
 */
@Service
@Transactional(readOnly = true)
public class Fea_fundssrcVOService extends CrudService<Fea_fundssrcVOMapper, Fea_fundssrcVO> {

	@Autowired
	private Fea_fundssrcBVOMapper fea_fundssrcBVOMapper;
	@Autowired
	private Fea_fundssrcTVOMapper fea_fundssrcTVOMapper;
	
	public Fea_fundssrcVO get(String id) {
		Fea_fundssrcVO fea_fundssrcVO = super.get(id);
		fea_fundssrcVO.setFea_fundssrcBVOList(fea_fundssrcBVOMapper.findList(new Fea_fundssrcBVO(fea_fundssrcVO)));
		fea_fundssrcVO.setFea_fundssrcTVOList(fea_fundssrcTVOMapper.findList(new Fea_fundssrcTVO(fea_fundssrcVO)));
		return fea_fundssrcVO;
	}
	
	public List<Fea_fundssrcVO> findList(Fea_fundssrcVO fea_fundssrcVO) {
		return super.findList(fea_fundssrcVO);
	}
	
	public Page<Fea_fundssrcVO> findPage(Page<Fea_fundssrcVO> page, Fea_fundssrcVO fea_fundssrcVO) {
		return super.findPage(page, fea_fundssrcVO);
	}
	
	@Transactional(readOnly = false)
	public void save(Fea_fundssrcVO fea_fundssrcVO) {
		super.save(fea_fundssrcVO);
		for (Fea_fundssrcBVO fea_fundssrcBVO : fea_fundssrcVO.getFea_fundssrcBVOList()){
			if (fea_fundssrcBVO.getId() == null){
				continue;
			}
			if (Fea_fundssrcBVO.DEL_FLAG_NORMAL.equals(fea_fundssrcBVO.getDelFlag())){
				if (StringUtils.isBlank(fea_fundssrcBVO.getId())){
					fea_fundssrcBVO.setFea_fundssrc(fea_fundssrcVO);
					fea_fundssrcBVO.preInsert();
					fea_fundssrcBVOMapper.insert(fea_fundssrcBVO);
				}else{
					fea_fundssrcBVO.preUpdate();
					fea_fundssrcBVOMapper.update(fea_fundssrcBVO);
				}
			}else{
				fea_fundssrcBVOMapper.delete(fea_fundssrcBVO);
			}
		}
		for (Fea_fundssrcTVO fea_fundssrcTVO : fea_fundssrcVO.getFea_fundssrcTVOList()){
			if (fea_fundssrcTVO.getId() == null){
				continue;
			}
			if (Fea_fundssrcTVO.DEL_FLAG_NORMAL.equals(fea_fundssrcTVO.getDelFlag())){
				if (StringUtils.isBlank(fea_fundssrcTVO.getId())){
					fea_fundssrcTVO.setFea_fundssrc(fea_fundssrcVO);
					fea_fundssrcTVO.preInsert();
					fea_fundssrcTVOMapper.insert(fea_fundssrcTVO);
				}else{
					fea_fundssrcTVO.preUpdate();
					fea_fundssrcTVOMapper.update(fea_fundssrcTVO);
				}
			}else{
				fea_fundssrcTVOMapper.delete(fea_fundssrcTVO);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Fea_fundssrcVO fea_fundssrcVO) {
		super.delete(fea_fundssrcVO);
		fea_fundssrcBVOMapper.delete(new Fea_fundssrcBVO(fea_fundssrcVO));
		fea_fundssrcTVOMapper.delete(new Fea_fundssrcTVO(fea_fundssrcVO));
	}
	
}