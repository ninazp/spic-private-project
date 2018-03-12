package com.jeeplus.modules.fea.designcal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.fea.dao.PubUtil;
import com.jeeplus.modules.fea.entity.costinfo.Fea_costinfoVO;
import com.jeeplus.modules.fea.entity.design.Fea_design_heatVO;
import com.jeeplus.modules.fea.entity.equiplst.Fea_design_equiplst2VO;
import com.jeeplus.modules.fea.entity.heatben.Fea_design_heatbenVO;
import com.jeeplus.modules.fea.entity.heattrans.Fea_design_heattransVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.quotation.FeaDesignReport;
import com.jeeplus.modules.fea.entity.set.Fea_design_setVO;
import com.jeeplus.modules.fea.entity.transfer.Fea_design_transferVO;
import com.jeeplus.modules.fea.mapper.costinfo.Fea_costinfoVOMapper;
import com.jeeplus.modules.fea.mapper.design.Fea_design_heatVOMapper;
import com.jeeplus.modules.fea.mapper.equiplst.Fea_design_equiplst2VOMapper;
import com.jeeplus.modules.fea.mapper.heatben.Fea_design_heatbenVOMapper;
import com.jeeplus.modules.fea.mapper.heattrans.Fea_design_heattransVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostBVOMapper;
import com.jeeplus.modules.fea.mapper.procost.Fea_productcostVOMapper;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;
import com.jeeplus.modules.fea.mapper.quotation.FeaDesignReportMapper;
import com.jeeplus.modules.fea.mapper.result.Fea_design_resultVOMapper;
import com.jeeplus.modules.fea.mapper.set.Fea_design_setVOMapper;
import com.jeeplus.modules.fea.mapper.transfer.Fea_design_transferVOMapper;
import com.jeeplus.modules.fea.pub.util.PubBaseDAO;
import com.jeeplus.modules.sys.utils.UserUtils;

public class PubDesignCal extends Exception{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private Fea_design_heatVOMapper	heatVOMapper;
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
	@Autowired
	private FeaDesignReportMapper feaDesignReportMapper;
	@Autowired
	private Fea_design_equiplst2VOMapper fea_design_equiplst2VOMapper;
	@Autowired
	private Fea_productcostVOMapper fea_productcostVOmapper;
	@Autowired
	private Fea_productcostBVOMapper fea_productcostBVOmapper;

	
	@SuppressWarnings("unchecked")
	public Map<String,Object> calprocess(String  projectid) throws Exception{
		
		Map<String,Object> retmap = new HashMap<String, Object>();
		
		FeaProjectB projectvo = projectBMapper.get(projectid);
		
		List<Fea_design_heatVO>  heatVO = (List<Fea_design_heatVO>) PubBaseDAO.
				getMutiParentVO("fea_design_heat", "id", " project_id='"+projectvo.getId()+"' ", heatVOMapper);
		
		List<Fea_design_transferVO>  transferVO = (List<Fea_design_transferVO>) PubBaseDAO.
				getMutiParentVO("fea_design_transfer", "id", " project_id='"+projectvo.getId()+"' ", transferVOMapper);
		
		List<Fea_design_heatbenVO>   heatbenVO = (List<Fea_design_heatbenVO>) PubBaseDAO.getMutiParentVOByorder
				("fea_design_heatben", "id", " del_flag = 0 and  project_id='"+projectvo.getId()+"' ", "qr2"  ,heatbenVOMapper);
		
		List<Fea_design_heattransVO>   heattransVO = (List<Fea_design_heattransVO>) PubBaseDAO.
				getMutiParentVO("fea_design_heattrans", "id", " del_flag = 0  and  project_id='"+projectvo.getId()+"' ", heattransVOMapper);
		
		List<Fea_design_setVO>   setvolst = (List<Fea_design_setVO>) PubBaseDAO.
				getMutiParentVO("fea_design_set", "id", " del_flag = 0 and  project_id='"+projectvo.getId()+"' ", setVOMapper);
		
		FeaProjectB  projectqueryvo = projectBMapper.get(projectvo.getId());
		
		List<Double>  fea_costinfo = getproductlst(projectqueryvo.getCountyears(), fea_costinfomapper, " project_id='"+projectvo.getId()+"' ");
		
		List<List<Double>> pricech = getpricech(heattransVO);
		List<List<Double>> heatpumpprice = getheatpumpprice(heatbenVO);
		
		if(null!=heatVO &&  null!=transferVO){
			if(heatVO.get(0).getAreaselect().equals("Y") || heatVO.get(0).getAreaselect().equals("1")){
				retmap =  singlewellyes.calsinglewellyes(heatVO.get(0), 
						transferVO.get(0), setvolst.get(0), pricech,
						heatpumpprice, fea_costinfo,resultVOMapper,
						 fea_productcostVOmapper,
						 fea_productcostBVOmapper);
			}else{
				retmap = singlewellNo.calsinglewelNo(heatVO.get(0), 
						transferVO.get(0), setvolst.get(0), pricech, 
						heatpumpprice, fea_costinfo,resultVOMapper,
						fea_productcostVOmapper,
						fea_productcostBVOmapper);
			}
			
			if(null!=retmap && retmap.size()>0 && retmap.containsKey("设备清单")){
				//为"设备清单" 追加 信息
				addRetmapByequiplst2VO(retmap, projectvo);
				
				List<List<String>> report = (List<List<String>>) retmap.get("设备清单");
				
				feaDesignReportMapper.execDeleteSql("delete from fea_design_report  where project_id='"+projectvo.getId()+"'");
				if(null!=report && report.size()>0){
					int i=1;
					for(List<String> rt : report){
						FeaDesignReport reportvo = new FeaDesignReport();
						reportvo.setId(PubUtil.getid(1));
						reportvo.setCreateBy(UserUtils.getUser());
						reportvo.setCreateDate(new Date());
						reportvo.setFeaProjectB(projectvo);
						reportvo.setName(rt.get(0));
						reportvo.setParameter(rt.get(1));
						Double n = (null!=rt.get(3) && rt.get(3).trim().length()>0)?Double.valueOf(rt.get(3).trim()):0.00;
						reportvo.setNumber(n);
						Double nval = (null!=rt.get(5) && rt.get(5).trim().length()>0)?Double.valueOf(rt.get(5).trim()):0.00;
						reportvo.setPrice(nval);
						reportvo.setRemarks(rt.get(8));
						if(i>9){
							reportvo.setRownum("9"+i+"");
						}else{
							reportvo.setRownum(i+"");
						}
						i++;
						feaDesignReportMapper.insert(reportvo);
					}
				}
				
			}
			
		}
		

		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		BusiIndexCal busiIndexCal = (BusiIndexCal) wac.getBean("busiIndexCal");
		if(null!=busiIndexCal){
		     busiIndexCal.getInitIvdesMny(projectid);
		}
		
		return retmap;
	}
	
	@SuppressWarnings("unchecked")
	private void addRetmapByequiplst2VO(Map<String,Object> retmap, FeaProjectB projectvo){
		List<Fea_design_equiplst2VO> equiplst2VOList = (List<Fea_design_equiplst2VO>) PubBaseDAO.
				getMutiParentVO("fea_design_equiplst2", "id", " del_flag = 0  and  project_id='"+projectvo.getId()+"' ", fea_design_equiplst2VOMapper);
		if(null != equiplst2VOList && equiplst2VOList.size()>0){
			List<List<String>> temp = (List<List<String>>) retmap.get("设备清单");
			for(Fea_design_equiplst2VO equiplst2VO : equiplst2VOList){
				List<String> innerLi = new ArrayList<String>();
				innerLi.add(equiplst2VO.getEquipname());//设备名称
				innerLi.add(equiplst2VO.getPerforparam());//参数
				innerLi.add(equiplst2VO.getUnit());//单位
				innerLi.add(null != equiplst2VO.getNum() ? singlewellyes.getDouble2float(equiplst2VO.getNum()).toString() : "0.00");//数量
				innerLi.add(null != equiplst2VO.getPrice() ? singlewellyes.getDouble2float(equiplst2VO.getPrice()).toString() : "0.00");//单价
				Double tatol = equiplst2VO.getNum()*equiplst2VO.getPrice();
				innerLi.add(singlewellyes.getDouble2float(tatol).toString());
				innerLi.add("15%");
				innerLi.add(singlewellyes.getDouble2float(tatol*0.15).toString());
				innerLi.add(equiplst2VO.getRemarks());
				temp.add(innerLi);
			}
			
			Double tatolDou = 0.00;
			for(List<String> tal: temp){
				tatolDou += new Double(tal.get(5).trim());
			}
			
			List<String> colend= new ArrayList<String>();
			colend.add("换热站工程小计");
			colend.add(" ");
			colend.add(" ");
			colend.add(" ");
			colend.add(" ");
			colend.add(" "+singlewellyes.getDouble2float(tatolDou));
			colend.add("15%");
			colend.add(""+singlewellyes.getDouble2float(tatolDou*0.15));
			colend.add(" ");
			temp.add(colend);
			retmap.put("设备清单", temp);
		}
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
			    priced.add(lowarea);
			    priced.add(higharea);
			    priced.add(price);
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
