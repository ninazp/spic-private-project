package com.jeeplus.modules.fea.designcal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.fea.entity.costinfo.Fea_costinfoVO;
import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.entity.downhole.Fea_design_downholeVO;
import com.jeeplus.modules.fea.entity.heatben.Fea_design_heatbenVO;
import com.jeeplus.modules.fea.entity.heattrans.Fea_design_heattransVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.set.Fea_design_setVO;
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.design.Fea_design_heatVOMapper;
import com.jeeplus.modules.fea.mapper.downhole.Fea_design_downholeVOMapper;
import com.jeeplus.modules.fea.mapper.heatben.Fea_design_heatbenVOMapper;
import com.jeeplus.modules.fea.mapper.heattrans.Fea_design_heattransVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.result.Fea_design_resultVOMapper;
import com.jeeplus.modules.fea.mapper.set.Fea_design_setVOMapper;
import com.jeeplus.modules.fea.mapper.transfer.Fea_design_transferVOMapper;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;

public class PubDesignCal extends Exception{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private Fea_design_heatVOMapper	heatVOMapper;
	@Autowired
	private Fea_design_downholeVOMapper	downholeVOMapper;
	@Autowired
	private Fea_design_transferVOMapper	transferVOMapper;
	@Autowired
	private Fea_design_heatbenVOMapper heatbenVOMapper;
	@Autowired
	private Fea_design_heattransVOMapper heattransVOMapper;
	@Autowired
	private Fea_design_setVOMapper setVOMapper;
	@Autowired
	private Fea_costinfoVOMapper fea_costinfomapper;
	@Autowired
	private FeaProjectBMapper projectBMapper;
	@Autowired
	private Fea_design_resultVOMapper resultVOMapper;
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> calprocess(String  projectid) throws Exception{
		
		Map<String,Object> retmap = new HashMap<String, Object>();
		
		FeaProjectB projectvo = projectBMapper.get(projectid);
		
		List<Fea_design_heatVO>  heatVO = (List<Fea_design_heatVO>) PubBaseDAO.
				getMutiParentVO("fea_design_heat", "id", " project_id='"+projectvo.getId()+"' ", heatVOMapper);
		
		List<Fea_design_downholeVO>   downholeVO = (List<Fea_design_downholeVO>) PubBaseDAO.
				getMutiParentVO("fea_design_downhole", "id", " project_id='"+projectvo.getId()+"' ", downholeVOMapper);
		
		List<Fea_design_transferVO>  transferVO = (List<Fea_design_transferVO>) PubBaseDAO.
				getMutiParentVO("fea_design_transfer", "id", " project_id='"+projectvo.getId()+"' ", transferVOMapper);
		
		List<Fea_design_heatbenVO>   heatbenVO = (List<Fea_design_heatbenVO>) PubBaseDAO.getMutiParentVOByorder
				("fea_design_heatben", "id", " del_flag = 0 ", "qr2"  ,heatbenVOMapper);
		
		List<Fea_design_heattransVO>   heattransVO = (List<Fea_design_heattransVO>) PubBaseDAO.
				getMutiParentVO("Fea_design_heattrans", "id", " del_flag = 0  ", heattransVOMapper);
		
		List<Fea_design_setVO>   distrvolst = (List<Fea_design_setVO>) PubBaseDAO.
				getMutiParentVO("fea_design_set", "id", " project_id='"+projectvo.getId()+"' ", setVOMapper);
		
		FeaProjectB  projectqueryvo = projectBMapper.get(projectvo.getId());
		
		List<Double>  fea_costinfo = getproductlst(projectqueryvo.getCountyears(), fea_costinfomapper, " project_id='"+projectvo.getId()+"' ");
		
		List<List<Double>> pricech = getpricech(heattransVO);
		List<List<Double>> heatpumpprice = getheatpumpprice(heatbenVO);
		
		if(null!=heatVO && null!=downholeVO && null!=transferVO){
			if(heatVO.get(0).getAreaselect().equals("Y")){
				retmap =  singlewellyes.calsinglewellyes(heatVO.get(0), downholeVO.get(0),
						transferVO.get(0), pricech, heatpumpprice, fea_costinfo,resultVOMapper);
			}else{
				retmap = singlewellNo.calsinglewelNo(heatVO.get(0), downholeVO.get(0),
						transferVO.get(0), pricech, heatpumpprice, fea_costinfo,resultVOMapper);
			}
		}
		
		return retmap;
	}
	private List<List<Double>> getheatpumpprice(List<Fea_design_heatbenVO> heatbenVO){
		List<List<Double>> heatpumpprice = new ArrayList<List<Double>>();
		if(null!=heatbenVO && heatbenVO.size()>0){
			for(Fea_design_heatbenVO fvo : heatbenVO){
				Double qr2 = (null!=fvo.getQr2())?fvo.getQr2():0.00;
				Double pr2 = (null!=fvo.getPr2())?fvo.getPr2():0.00;
				Double cr2 = (null!=fvo.getCr2())?Double.valueOf(fvo.getCr2()):0.00;
			    List<Double> priced = new ArrayList<Double>();
			    priced.add(qr2);
			    priced.add(pr2);
			    priced.add(cr2);
			    heatpumpprice.add(priced);
			}
		}
		return heatpumpprice;
	}
	
	
	private List<List<Double>> getpricech(List<Fea_design_heattransVO> heattransVO){
		List<List<Double>> pricechlst = new ArrayList<List<Double>>();
		if(null!=heattransVO && heattransVO.size()>0){
			for(Fea_design_heattransVO fvo : heattransVO){
				Double price = (null!=fvo.getPrice())?Double.valueOf(fvo.getPrice()):0.00;
				Double lowarea = (null!=fvo.getLowarea())?Double.valueOf(fvo.getLowarea()):0.00;
				Double higharea = (null!=fvo.getHigharea())?Double.valueOf(fvo.getHigharea()):0.00;
			
			    List<Double> priced = new ArrayList<Double>();
			    priced.add(price);
			    priced.add(lowarea);
			    priced.add(higharea);
			    pricechlst.add(priced);
			}
		}
		return pricechlst;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<Double> getproductlst(Double countyear,BaseMapper basemaper,String wheresql){
		List<Fea_costinfoVO>   fea_costinfovo = (List<Fea_costinfoVO>) PubBaseDAO.
				getMutiParentVO("fea_costinfo", "id", wheresql,
						basemaper);
		List<Double> costrate = new ArrayList<Double>();	
		for(Fea_costinfoVO fcvo : fea_costinfovo){
				for(int j=0;j<countyear;j++){
					try {
						Method m ;
						if(j==0){
							m = fcvo.getClass().getMethod("getYear");
						}else{
							m = fcvo.getClass().getMethod("getYear"+(j+1));
						}
						Object rated = m.invoke(fcvo);
						if(null!=rated){
							costrate.add(((Double)rated));
						}else{
							costrate.add(0.00);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
		}
		
		return costrate;
	}
	
	
}
