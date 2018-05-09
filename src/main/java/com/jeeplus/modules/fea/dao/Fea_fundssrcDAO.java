package com.jeeplus.modules.fea.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcTVO;
import com.jeeplus.modules.fea.entity.funds.Fea_fundssrcVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcTVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_fundssrcVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class Fea_fundssrcDAO {

	public static Fea_fundssrcVO insertFea_fundssrc(
			Fea_fundssrcVOMapper  basemapper,
			Fea_fundssrcBVOMapper basemapper2,
			Fea_fundssrcTVOMapper basemapper3,
			FeaProjectB projectvo){

		Fea_fundssrcVO vo = new Fea_fundssrcVO();
		vo.setFeaProjectB(projectvo);
		vo.setId(PubUtil.getid(1));
		vo.setCreateBy(UserUtils.getUser());	
		vo.setCreateDate(new Date());

		vo.setInvesttotal(2228.16);;		//投资总额
		vo.setIsdeductvtax("0");;		// 增值税抵扣
		vo.setDeductvtax(93.79);;		// 可抵扣税金
		vo.setCapitalprop(20.00);;		// 资本金比例(%)
		vo.setCapitalamt(2228.16*0.2);;		// 资本金额度
		vo.setLoanprop(80.00);;		// 借款比例(%)
		vo.setLoanamt(2228.16*0.8);;		// 借款金额
		vo.setIscapitalcy("1");;		// 注资循环


		Fea_fundssrcBVO bvo = new Fea_fundssrcBVO();
		bvo.setId(PubUtil.getid(1));
		bvo.setCreateBy(UserUtils.getUser());	
		bvo.setCreateDate(new Date());
		bvo.setFea_fundssrc(vo);
		bvo.setCapitaltype("注资方1");;		// 注资方名称
		bvo.setCurrency("人民币");;		// 币种
		bvo.setExchangerate("1.00");;		// 汇率
		bvo.setCapprop(100.00);;		// 比例
		bvo.setCapamt(2228.16*0.2);		// 注资金额

		Fea_fundssrcTVO tvo = new Fea_fundssrcTVO();
		tvo.setId(PubUtil.getid(1));
		tvo.setCreateBy(UserUtils.getUser());	
		tvo.setCreateDate(new Date());
		tvo.setFea_fundssrc(vo);
		tvo.setLoantyp("融资方1");		// 注资方名称
		tvo.setCurrency("人民币");		// 币种
		tvo.setExchangerate(1.00);		// 汇率
		tvo.setLoanprop(100.00);		// 比例
		tvo.setLoanamt(2228.16*0.8);		// 借款金额
		tvo.setInterestcount(15.00);		// 计息次数（年）
		tvo.setPrincipalrate(4.9);		// 本金利率（%）
		tvo.setLangrate(4.9);		// 利息利率（%）
		tvo.setRepaytype("1");	// 还款方式
		tvo.setRepayperiod(15.00);		// 还款期
		tvo.setCommitrate(0.00);		// 承诺费率（%）
		tvo.setGraceperiod(2.00);		// 宽限期

		List<Fea_fundssrcBVO> bvolst = new ArrayList<Fea_fundssrcBVO>();
		bvolst.add(bvo);
		vo.setFea_fundssrcBVOList(bvolst);
		List<Fea_fundssrcTVO> tvolst = new ArrayList<Fea_fundssrcTVO>();
		tvolst.add(tvo);
		vo.setFea_fundssrcTVOList(tvolst);

		basemapper.insert(vo);
		basemapper2.insert(bvo);
		basemapper3.insert(tvo);

		return vo;
	}

	public static void insertFea_investdis(Fea_fundssrcVO  fea_fundssrcVO,
			Fea_investdisVOMapper basemapper4,
			Fea_investdisBVOMapper basemapper5,
			FeaProjectB projectvo,
			Double flowloanamt,Double flowcapamt,Double heatarea){

		Date startdate = projectvo.getStartupDate();
		Double stupmths = projectvo.getConstructPeriod();
		int startmth = startdate.getMonth();
		if(stupmths+startmth-1<12){

			Fea_investdisVO vo = new Fea_investdisVO();
			vo.setFeaProjectB(projectvo);
			vo.setId(PubUtil.getid(1));
			vo.setCreateBy(UserUtils.getUser());	
			vo.setCreateDate(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(startdate);
			int year = cal.get(Calendar.YEAR);
			vo.setYear(year+"");;		// 年度
			vo.setInvestprop(100.00);;		// 投资比例
			vo.setInvestamt(fea_fundssrcVO.getInvesttotal());;		// 投资额度
			vo.setDeductvtax(fea_fundssrcVO.getDeductvtax());;		// 可抵扣税金
			vo.setCappropsum(fea_fundssrcVO.getCapitalamt());;		// 注资方合计
			vo.setLoanpropsum(fea_fundssrcVO.getLoanamt());;		// 融资合计

			basemapper4.insert(vo);

			for(int i=0;i<fea_fundssrcVO.getFea_fundssrcBVOList().size();i++){
				Fea_fundssrcBVO fbvo = fea_fundssrcVO.getFea_fundssrcBVOList().get(i);
				Fea_investdisBVO bvo = new Fea_investdisBVO();
				bvo.setId(PubUtil.getid(1));
				bvo.setFea_investdis(vo);
				bvo.setCreateBy(UserUtils.getUser());	
				bvo.setCreateDate(new Date());
				bvo.setFea_investdis(vo);
				bvo.setZjname(fbvo.getCapitaltype());
				bvo.setInvesttype("1");;		// 资金方类别
				bvo.setInvestprop((fbvo.getCapamt()/fea_fundssrcVO.getInvesttotal()*100));;		// 当期比例（%）
				bvo.setInvestamt(fbvo.getCapamt());;		// 资金金额
				bvo.setJsamt(fbvo.getCapamt()-flowcapamt);		// 用于建设金额
				bvo.setLdamt(flowcapamt);	// 用于流动资金金额

				basemapper5.insert(bvo);
			}

			for(int i=0;i<fea_fundssrcVO.getFea_fundssrcTVOList().size();i++){
				Fea_fundssrcTVO ftvo = fea_fundssrcVO.getFea_fundssrcTVOList().get(i);
				Fea_investdisBVO bvo = new Fea_investdisBVO();
				bvo.setId(PubUtil.getid(1));
				bvo.setCreateBy(UserUtils.getUser());	
				bvo.setCreateDate(new Date());
				bvo.setFea_investdis(vo);
				bvo.setZjname(ftvo.getLoantyp());
				bvo.setInvesttype("2");;		// 资金方类别
				bvo.setInvestprop((ftvo.getLoanamt()/fea_fundssrcVO.getInvesttotal())*100);;		// 当期比例（%）
				bvo.setInvestamt(ftvo.getLoanamt());;		// 资金金额
				bvo.setJsamt(ftvo.getLoanamt()-flowloanamt);		// 用于建设金额
				bvo.setLdamt(flowloanamt);	// 用于流动资金金额

				basemapper5.insert(bvo);
			}
		}else{

			for(int j=0;j<2;j++){
				Fea_investdisVO vo = new Fea_investdisVO();
				vo.setFeaProjectB(projectvo);
				vo.setId(PubUtil.getid(1));
				vo.setCreateBy(UserUtils.getUser());	
				vo.setCreateDate(new Date());

				vo.setYear((startdate.getYear()+j)+"");;		// 年度
				vo.setInvestprop(50.00);;		// 投资比例
				vo.setInvestamt(fea_fundssrcVO.getInvesttotal()*0.5);		// 投资额度
				vo.setDeductvtax(fea_fundssrcVO.getDeductvtax()*0.5);		// 可抵扣税金
				vo.setCappropsum(fea_fundssrcVO.getCapitalamt()*0.5);		// 注资方合计
				vo.setLoanpropsum(fea_fundssrcVO.getLoanamt()*0.5);		// 融资合计

				basemapper4.insert(vo);

				for(int i=0;i<fea_fundssrcVO.getFea_fundssrcBVOList().size();i++){
					Fea_fundssrcBVO fbvo = fea_fundssrcVO.getFea_fundssrcBVOList().get(i);
					Fea_investdisBVO bvo = new Fea_investdisBVO();
					bvo.setId(PubUtil.getid(1));
					bvo.setFea_investdis(vo);
					bvo.setCreateBy(UserUtils.getUser());	
					bvo.setCreateDate(new Date());
					bvo.setFea_investdis(vo);
					bvo.setZjname(fbvo.getCapitaltype());
					bvo.setInvesttype("1");;		// 资金方类别
					bvo.setInvestprop((fbvo.getCapamt()/fea_fundssrcVO.getInvesttotal())*100);;		// 当期比例（%）
					bvo.setInvestamt(fbvo.getCapamt()*0.5);;		// 资金金额
					bvo.setJsamt((fbvo.getCapamt()-flowcapamt)*0.5);		// 用于建设金额
					bvo.setLdamt(flowcapamt*0.5);	// 用于流动资金金额
					basemapper5.insert(bvo);
				}

				for(int i=0;i<fea_fundssrcVO.getFea_fundssrcBVOList().size();i++){
					Fea_fundssrcTVO ftvo = fea_fundssrcVO.getFea_fundssrcTVOList().get(i);
					Fea_investdisBVO bvo = new Fea_investdisBVO();
					bvo.setId(PubUtil.getid(1));
					bvo.setCreateBy(UserUtils.getUser());	
					bvo.setCreateDate(new Date());
					bvo.setFea_investdis(vo);
					bvo.setZjname(ftvo.getLoantyp());
					bvo.setInvesttype("1");;		// 资金方类别
					bvo.setInvestprop((ftvo.getLoanamt()/fea_fundssrcVO.getInvesttotal())*100);;		// 当期比例（%）
					bvo.setInvestamt(ftvo.getLoanamt()*0.5);;		// 资金金额
					bvo.setJsamt((ftvo.getLoanamt()-flowloanamt)*0.5);		// 用于建设金额
					bvo.setLdamt(flowloanamt*0.5);	// 用于流动资金金额
					basemapper5.insert(bvo);
				}
			}
		}
	}
}
