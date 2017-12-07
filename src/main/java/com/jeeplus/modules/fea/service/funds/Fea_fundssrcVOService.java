/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.service.funds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fea.dao.Fea_fundssrcDAO;
import com.jeeplus.modules.fea.entity.fecl.Fea_costfecfVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcTVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.fecl.Fea_costfecfVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcTVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;
import com.jeeplus.modules.fea.pub.util.PubUtilHandler;

/**
 * 资金来源Service
 * @author jw
 * @version 2017-12-06
 */
@Service
@Transactional(readOnly = true)
public class Fea_fundssrcVOService extends CrudService<Fea_fundssrcVOMapper, Fea_fundssrcVO> {

	@Autowired
	private Fea_fundssrcBVOMapper fea_fundssrcBVOMapper;
	@Autowired
	private Fea_fundssrcTVOMapper fea_fundssrcTVOMapper;
	@Autowired
	private Fea_investdisVOMapper basemapper4;
	@Autowired
	private Fea_investdisBVOMapper basemapper5;
	@Autowired
	private FeaProjectBMapper basemapper2;
	@Autowired 
	private Fea_costfecfVOMapper fea_costfecfVOMapper;


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
					fea_fundssrcBVO.preInsert();
					fea_fundssrcBVOMapper.insert(fea_fundssrcBVO);
				}else{
					fea_fundssrcBVO.setFea_fundssrc(fea_fundssrcVO);
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
					fea_fundssrcTVO.preInsert();
					fea_fundssrcTVOMapper.insert(fea_fundssrcTVO);
				}else{
					fea_fundssrcTVO.setFea_fundssrc(fea_fundssrcVO);
					fea_fundssrcTVO.preUpdate();
					fea_fundssrcTVOMapper.update(fea_fundssrcTVO);
				}
			}else{
				fea_fundssrcTVOMapper.delete(fea_fundssrcTVO);
			}
		}
		FeaProjectB projectvo = basemapper2.get(fea_fundssrcVO.getFeaProjectB().getId());

		List<Fea_costfecfVO> fea_costfecfvos = (List<Fea_costfecfVO>) PubBaseDAO.getMutiParentVO("fea_costfecf", "id", 
				"project_id='"+fea_fundssrcVO.getFeaProjectB().getId()+"'", fea_costfecfVOMapper);

		Double flowloanamt = 0.00;
		Double flowcapamt = 0.00;

		if(null!=fea_costfecfvos && fea_costfecfvos.size()>0){
			for(int i=0;i<fea_costfecfvos.size();i++){
				flowloanamt = flowloanamt+fea_costfecfvos.get(i).getFlowamt()*fea_costfecfvos.get(i).getFlowloanprop()/100;
				flowcapamt =flowcapamt+fea_costfecfvos.get(i).getFlowamt() - flowloanamt;
			}
		}
		basemapper4.execDeleteSql("delete from fea_investdis s where s.project_id='"+fea_fundssrcVO.getFeaProjectB().getId()+"'");
		basemapper5.execDeleteSql("delete from fea_investdis_b b where b.pkinvestdis='"+fea_fundssrcVO.getId()+"'");

		Fea_fundssrcDAO.insertFea_investdis(fea_fundssrcVO,
				basemapper4, basemapper5,
				projectvo,
				flowloanamt, flowcapamt);
	}

	@Transactional(readOnly = false)
	public void delete(Fea_fundssrcVO fea_fundssrcVO) {
		super.delete(fea_fundssrcVO);
		fea_fundssrcBVOMapper.delete(new Fea_fundssrcBVO(fea_fundssrcVO));
		fea_fundssrcTVOMapper.delete(new Fea_fundssrcTVO(fea_fundssrcVO));
	}

}