package com.jeeplus.modules.fea.dao;

import java.util.Calendar;
import java.util.Date;

import com.jeeplus.modules.fea.entity.funds.Fea_investdisBVO;
import com.jeeplus.modules.fea.entity.funds.Fea_investdisVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisBVOMapper;
import com.jeeplus.modules.fea.mapper.funds.Fea_investdisVOMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

public class Fea_fundssrcDAO {

//	public static Fea_fundssrcVO insertFea_fundssrc(
//			Fea_fundssrcVOMapper  basemapper,
//			Fea_fundssrcBVOMapper basemapper2,
//			Fea_fundssrcTVOMapper basemapper3,
//			FeaProjectB projectvo){
//
//		Fea_fundssrcVO vo = new Fea_fundssrcVO();
//		vo.setFeaProjectB(projectvo);
//		vo.setId(PubUtil.getid(1));
//		vo.setCreateBy(UserUtils.getUser());	
//		vo.setCreateDate(new Date());
//
//		vo.setInvesttotal(2228.16);;		//投资总额
//		vo.setIsdeductvtax("0");;		// 增值税抵扣
//		vo.setDeductvtax(93.79);;		// 可抵扣税金
//		vo.setCapitalprop(20.00);;		// 资本金比例(%)
//		vo.setCapitalamt(2228.16*0.2);;		// 资本金额度
//		vo.setLoanprop(80.00);;		// 借款比例(%)
//		vo.setLoanamt(2228.16*0.8);;		// 借款金额
//		vo.setIscapitalcy("1");;		// 注资循环
//
//
//		Fea_fundssrcBVO bvo = new Fea_fundssrcBVO();
//		bvo.setId(PubUtil.getid(1));
//		bvo.setCreateBy(UserUtils.getUser());	
//		bvo.setCreateDate(new Date());
//		bvo.setFea_fundssrc(vo);
//		bvo.setCapitaltype("注资方1");;		// 注资方名称
//		bvo.setCurrency("人民币");;		// 币种
//		bvo.setExchangerate("1.00");;		// 汇率
//		bvo.setCapprop(100.00);;		// 比例
//		bvo.setCapamt(2228.16*0.2);		// 注资金额
//
//		Fea_fundssrcTVO tvo = new Fea_fundssrcTVO();
//		tvo.setId(PubUtil.getid(1));
//		tvo.setCreateBy(UserUtils.getUser());	
//		tvo.setCreateDate(new Date());
//		tvo.setFea_fundssrc(vo);
//		tvo.setLoantyp("融资方1");		// 注资方名称
//		tvo.setCurrency("人民币");		// 币种
//		tvo.setExchangerate(1.00);		// 汇率
//		tvo.setLoanprop(100.00);		// 比例
//		tvo.setLoanamt(2228.16*0.8);		// 借款金额
//		tvo.setInterestcount(15.00);		// 计息次数（年）
//		tvo.setPrincipalrate(4.9);		// 本金利率（%）
//		tvo.setLangrate(4.9);		// 利息利率（%）
//		tvo.setRepaytype("1");	// 还款方式
//		tvo.setRepayperiod(15.00);		// 还款期
//		tvo.setCommitrate(0.00);		// 承诺费率（%）
//		tvo.setGraceperiod(2.00);		// 宽限期
//
//		List<Fea_fundssrcBVO> bvolst = new ArrayList<Fea_fundssrcBVO>();
//		bvolst.add(bvo);
//		vo.setFea_fundssrcBVOList(bvolst);
//		List<Fea_fundssrcTVO> tvolst = new ArrayList<Fea_fundssrcTVO>();
//		tvolst.add(tvo);
//		vo.setFea_fundssrcTVOList(tvolst);
//
//		basemapper.insert(vo);
//		basemapper2.insert(bvo);
//		basemapper3.insert(tvo);
//
//		return vo;
//	}

	public static void insertFea_investdis(Fea_investdisVOMapper basemapper4,Fea_investdisBVOMapper basemapper5,FeaProjectB projectvo){

		    Date startdate = projectvo.getStartupDate();

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
			vo.setInvestamt(2228.16);;		// 投资额度
			vo.setDeductvtax(0.00);;		// 可抵扣税金
			vo.setCappropsum(2228.16*0.2);		// 注资方合计
			vo.setLoanpropsum(2228.16*0.8);		// 融资合计

			basemapper4.insert(vo);

			for(int i=0;i<2;i++){
				Fea_investdisBVO bvo = new Fea_investdisBVO();
				bvo.setId(PubUtil.getid(1));
				bvo.setFea_investdis(vo);
				bvo.setCreateBy(UserUtils.getUser());	
				bvo.setCreateDate(new Date());
				bvo.setFea_investdis(vo);
				if(i==0) {
					bvo.setZjname("注资方1");
					bvo.setInvesttype("1");		// 资金方类别
					bvo.setInvestprop(20.00);		// 当期比例（%）
					bvo.setInvestamt(2228.16*0.2);	// 资金金额
				}else if(i==1) {
					bvo.setZjname("融资方1");
					bvo.setInvesttype("2");		// 资金方类别
					bvo.setInvestprop(80.00);	// 当期比例（%）
					bvo.setInvestamt(2228.16*0.8);		// 资金金额
				}
				basemapper5.insert(bvo);
			}

	}
}
