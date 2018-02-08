package com.jeeplus.modules.fea.designcal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.mapper.project.FeaProjectBMapper;

public class BusiIndexCal {
	
	@Autowired
	private FeaProjectBMapper projectBMapper;
	
	public List<List<String>> getInitIvdesMny(String projectid){
		List<List<String>> retlst = new ArrayList<List<String>>();
		Double heattransfee = 345.97 ;
		Double heattransset = 51.47 ;
		Double nk = 2.00;
		
		Double [] busiindex = new Double[]{
				1630.0 	,
				35.0 	,
				45.0 	,
				50.0 ,
				1000.0 ,
				700.0 ,
				2600000.0 };
		
		Double[][] jsgl = new Double[][]{{1000.00,0.02,20.00},{5000.00,0.015,80.00}};
		

		FeaProjectB projectvo = projectBMapper.get(projectid);
		
		Double heatarea = projectvo.getHeatArea();
		Double heatnum = heatarea*24*1.1;
		
		//建筑工程
		Double heatbuild = heatnum*busiindex[0]/10000;//土建
		Double waterset = heatnum*busiindex[1]/10000;//给排水安装
		Double getheat = heatnum*busiindex[2]/10000;//给暖安装
		Double lightbuy = heatnum*busiindex[3]*0.92/10000;//购置
		Double lightset = heatnum*busiindex[3]*0.08/10000;//安装
		
		
		//市政工程
		Double pumpDN300num = heatarea*259.16;
		Double pumpDN200num = heatarea*48.74;
		
		Double pumpDN300build  = pumpDN300num*busiindex[4]*0.15/10000;
		Double pumpDN200build = pumpDN200num*busiindex[5]*0.15/10000;
		
		Double pumpDN300set  = pumpDN300num*busiindex[4]*0.85/10000;
		Double pumpDN200set = pumpDN200num*busiindex[5]*0.85/10000;
		
		Double pumpquip = (busiindex[4]+busiindex[5])*0.004;
		
		Double holehighbuild = nk*busiindex[6]*0.9/10000;
		Double holehighbuy = nk*18;
		Double holehighset = nk*busiindex[6]*0.1/10000;
		
		List<Double> l10 = new ArrayList<Double>();//换热站工程
		l10.add(heatbuild);l10.add(heattransfee+lightbuy);l10.add(waterset+getheat+lightset+heattransset);l10.add(0.00);
		List<Double> l11 = new ArrayList<Double>();//热网工程
		l11.add(pumpDN300build+pumpDN200build+holehighbuild);
		l11.add(holehighbuy);
		l11.add(pumpDN300set+pumpDN200set+holehighset);
		l11.add(pumpquip);
		
		Double summny = 0.00;
		Double sumbuild = 0.00;
		for(int i=0;i<4;i++){
			sumbuild = sumbuild + l10.get(i)+l11.get(i);
		}
		summny = sumbuild+sumbuild*0.22;
		
		
		//建设管理费
		Double d1 = (summny-jsgl[0][0])*jsgl[1][1]+jsgl[0][2];
		
		//参数----
		Double [] qqzx1 = new Double[]{0.00,0.00,0.00,0.00,0.00,1.00};
		Double [] qqzx2 = new Double[]{3000.00,6.00,12.00,4.00,5.00,2.00};
		
		//项目可行性研究编制费
		Double d21 = (summny-qqzx1[0])*(qqzx2[1]-qqzx1[1])/(qqzx2[0]-qqzx1[0])+qqzx1[1];
		Double d22 = (summny-qqzx1[0])*(qqzx2[2]-qqzx1[2])/(qqzx2[0]-qqzx1[0])+qqzx1[2];
		Double d23 = (summny-qqzx1[0])*(qqzx2[3]-qqzx1[3])/(qqzx2[0]-qqzx1[0])+qqzx1[3];
		Double d24 = (summny-qqzx1[0])*(qqzx2[4]-qqzx1[4])/(qqzx2[0]-qqzx1[0])+qqzx1[4];
		Double d2=d22+d24;
		
		//参数--
		Double persons = 6.00;Double personmny = 2500.00;Double months = 6.00;
		//生产准备费
		Double d13 = persons*0.6*personmny*months/10000;
		Double d14 = persons*1000/10000;
		
		//工程设计费 -- 参数
		Double [] design1 = new Double[]{1000.00,38.8};
		Double [] design2 = new Double[]{3000.00,103.8};
		Double d5 = (design2[1]-design1[1])*(sumbuild+d14-design1[0])/(design2[0]-design1[0])+design1[1];
		Double d4 = d5*0.25;//参数
		
		//施工图审查费
		Double d6 = d5*0.1;//参数
		//竣工图编制费
		Double d7 = d5*0.08;//参数
		
		//工程建设监理费 -- 参数
		Double [] design81 = new Double[]{1000.00,30.1};
		Double [] design82 = new Double[]{3000.00,78.1};
		Double d8 = (design82[1]-design81[1])*(sumbuild-design81[0])/(design82[0]-design81[0])+design81[1];
		
		//环境影响咨询费--参数
		Double [] hj1 =new Double[]{0.00,5.00,1.00,0.8,0.5};
		Double [] hj2 =new Double[]{0.3,6.0,2.0,1.5,0.8};
		Double d91 = (summny/10000 - hj1[0])*(hj2[1]-hj1[1])/(hj2[0]-hj1[0])+hj1[1];
		Double d92 = (summny/10000 - hj1[0])*(hj2[2]-hj1[2])/(hj2[0]-hj1[0])+hj1[2];
		Double d93 = (summny/10000 - hj1[0])*(hj2[3]-hj1[3])/(hj2[0]-hj1[0])+hj1[3];
		Double d94 = (summny/10000 - hj1[0])*(hj2[4]-hj1[4])/(hj2[0]-hj1[0])+hj1[4];
		Double d9 = d91+d93;
		//联合试运转费
		Double d10 = (l10.get(1)+l11.get(1))*0.01;//-参数
		//招标代理服务费
		Double []d11set = new Double[]{100.0,0.0110,1.50,100.0,0.0080,1.50,1000.0,0.0035,6.55 };
		Double []d11set2 = new Double[]{500.0,0.0080,5.90,500.0,0.0045,4.70,5000.0,0.0020,20.55};
		Double d111 = d11set[2]+ (d11set2[0]-d11set[0])*d11set[1];
		Double d112 = d11set[5] + (d11set2[3]-d11set[3])*d11set[4];
		Double d113 = d11set[8] + (d11set2[6]-d11set[6])*d11set[7];
		Double d11  = d111+d112+d113;
		
		//工程保险费
		Double d12 = summny*0.0025;//参数
		
		//施工人员意外保险费
		Double d15 = (sumbuild<10000)?sumbuild*0.0008:sumbuild*0.0006;
		
		//节能专篇编制和评审费
		Double d16 = d22*0.7;
		//城市基础设施建设费
		Double d17 = heatnum/100;
		
		List<String> ll8 = new ArrayList<String>();
		ll8.add("");ll8.add("固定资产投资");
		
		//第一部分工程费用
		List<String> ll9 = new ArrayList<String>();
		ll9.add("I");ll9.add("第一部分工程费用");
		ll9.add(""+getDouble2float(l10.get(0)+l11.get(0)));
		ll9.add(""+getDouble2float(l10.get(1)+l11.get(1)));
		ll9.add(""+getDouble2float(l10.get(2)+l11.get(2)));
		ll9.add(""+getDouble2float(l10.get(3)+l11.get(3)));
		ll9.add(""+getDouble2float(sumbuild));
		ll9.add("");ll9.add("");ll9.add("");ll9.add("");
		
		//换热站工程
		List<String> ll10 = new ArrayList<String>();
		ll10.add("1");ll10.add("换热站工程");ll10.add(getDouble2float(l10.get(0))+"");
		ll10.add(getDouble2float(l10.get(1))+"");ll10.add(getDouble2float(l10.get(2))+"");
		ll10.add(getDouble2float(l10.get(3))+"");ll10.add(getDouble2float(l10.get(0)+l10.get(1)+l10.get(2)+l10.get(3))+"");
		ll10.add("");ll10.add("");ll10.add("");ll10.add("");
		//热网工程
		List<String> ll11 = new ArrayList<String>();
		ll11.add("2"); ll11.add("热网工程"); ll11.add(getDouble2float(l11.get(0))+"");
		ll11.add(getDouble2float(l11.get(1))+""); ll11.add(getDouble2float(l11.get(2))+"");
		ll11.add(getDouble2float(l11.get(3))+""); ll11.add(getDouble2float(l11.get(0)+l11.get(1)+l11.get(2)+l11.get(3))+"");
		ll10.add("");ll10.add("");ll10.add("");ll10.add("");
		
		//工程其他费用
		Double ld13 = getDouble2float(d1)+getDouble2float(d2)+getDouble2float(d5)+
					getDouble2float(d6)+getDouble2float(d7)+getDouble2float(d8)+
				getDouble2float(d10)+getDouble2float(d11)+getDouble2float(d12)+
				getDouble2float(d13)+getDouble2float(d14)+getDouble2float(d15)+
				getDouble2float(d4)+getDouble2float(d9)+getDouble2float(d16)+getDouble2float(d17);
		List<String> ll13 = getotherfee("II", "工程其他费用", getDouble2float(ld13), "财建[2016]504号");
		List<String> ll14 = getotherfee("1", "建设单位管理费",getDouble2float(d1), "财建[2016]504号");
		List<String> ll15 = getotherfee("2", "项目可行性研究编制费",getDouble2float(d2), "计价格[1999]1283号");
		List<String> ll16 = getotherfee("3", "工程设计费",getDouble2float(d5), "计价格(2002)10号");
		List<String> ll17 = getotherfee("4", "施工图审查费",getDouble2float(d6), "");
		List<String> ll18 = getotherfee("5", "竣工图编制费",getDouble2float(d7), "设计费*8%");
		List<String> ll19 = getotherfee("6", "工程建设监理费",getDouble2float(d8), "发改价格(2007)670号");
		List<String> ll20 = getotherfee("7", "联合试运转费",getDouble2float(d10), "设备购置费×1.0%");
		List<String> ll21 = getotherfee("8", "招标代理服务费",getDouble2float(d11), "计价格(2002)1980号");
		List<String> ll22 = getotherfee("8.1", "货物招标代理服务费",getDouble2float(d111), "计价格(2002)1981号");
		List<String> ll23 = getotherfee("8.2", "服务招标代理服务费",getDouble2float(d112), "计价格(2002)1982号");
		List<String> ll24 = getotherfee("8.3", "工程招标代理服务费",getDouble2float(d113), "计价格(2002)1983号");
		List<String> ll25 = getotherfee("9", "工程保险费",getDouble2float(d12), "");
		List<String> ll26 = getotherfee("10", "生产准备费",getDouble2float(d13), "6人×60%×2500元/人×6个月");
		List<String> ll27 = getotherfee("11", "办公和生活家具购置费",getDouble2float(d14), "6人×1000元/人");
		List<String> ll28 = getotherfee("12", "施工人员意外保险费",getDouble2float(d15), "6人×1000元/人");
		List<String> ll29 = getotherfee("13", "工程勘察费",getDouble2float(d4), "计价格(2002)10号");
		List<String> ll30 = getotherfee("14", "环境影响咨询费",getDouble2float(d9), "参照可研");
		List<String> ll31 = getotherfee("15", "节能专篇编制和评审费",getDouble2float(d16), "按可研编制费的70%");
		List<String> ll32 = getotherfee("16", "城市基础设施建设费",getDouble2float(d17), "100元/平米");
		List<String> ll33 = getotherfee("17", "收购探矿权",0.00, "");
		
		List<String> ll36 = getotherfee("III", "工程预备费", getDouble2float((sumbuild+ld13)*0.05), "");
		List<String> ll37 = getotherfee("一", "基本预备费", getDouble2float((sumbuild+ld13)*0.05), "5%");
		List<String> ll38 = getotherfee("二", "涨价预备费", 0.00, "");
		
		List<String> ll39 = getotherfee("IV", "建设期贷款利息", 0.00, "");
		List<String> ll40 = getotherfee("V", "铺底流动资金", 35.00, "");
		
		List<String> ll41 = new ArrayList<String>();
		ll41.add(""); ll41.add("建设项目总投资");
		Double d411 = getDouble2float(l10.get(0)+l11.get(0));
		ll41.add(""+d411);
		Double d412 = getDouble2float(l10.get(1)+l11.get(1));
		ll41.add(""+d412); 
		Double d413 = getDouble2float(l10.get(2)+l11.get(2));
		ll41.add(""+d413);
		Double d414 = getDouble2float(l10.get(3)+l11.get(3)+35+getDouble2float((sumbuild+ld13)*0.05)+getDouble2float(ld13));
		ll41.add(d414+""); 
		Double d41 = getDouble2float(d411+d412+d413+d414);
		ll41.add(d41+"");
		ll41.add("");ll41.add("");ll41.add("");ll41.add("");
		
		List<String> ll42 = new ArrayList<String>();
		ll42.add(""); ll42.add("各工程费用占建设项目总投资的比例(%)"); 
		Double r1 = getDouble2float(d411*100/d41);
		ll42.add("");
		ll42.add(r1+"%");
		Double r2 = getDouble2float(d412*100/d41);
		ll42.add(r2+"%");
		Double r3 = getDouble2float(d413*100/d41);
		ll42.add(r3+"%");
		Double r4 = getDouble2float(d414*100/d41);
		ll42.add(r4+"%");
		ll42.add("");ll42.add("");ll42.add("");ll42.add("");
		
		retlst.add(ll9);retlst.add(ll10);retlst.add(ll13);
		retlst.add(ll14);retlst.add(ll15);retlst.add(ll16);
		retlst.add(ll17);retlst.add(ll18);retlst.add(ll19);
		retlst.add(ll20);retlst.add(ll21);retlst.add(ll22);
		retlst.add(ll23);retlst.add(ll24);retlst.add(ll25);
		retlst.add(ll26);retlst.add(ll27);retlst.add(ll28);
		retlst.add(ll29);retlst.add(ll30);retlst.add(ll31);
		retlst.add(ll32);retlst.add(ll33);retlst.add(ll36);
		retlst.add(ll37);retlst.add(ll38);retlst.add(ll39);
		retlst.add(ll40);retlst.add(ll41);retlst.add(ll42);
		return retlst;
	}
	
	public List<String> getotherfee(String rowno,String name,Double fee,String remark){
		List<String> ll15 = new ArrayList<String>();
		ll15.add(rowno); ll15.add(name); ll15.add("");
		ll15.add(""); ll15.add("");
		ll15.add(fee+""); ll15.add(fee+"");
		ll15.add("");ll15.add("");ll15.add("");ll15.add(remark);
		
		return ll15;
	}
	
	public Double getDouble2float(Double m){
		BigDecimal  bm = new BigDecimal(m);
		Double bm1 = bm.setScale(2, RoundingMode.HALF_UP).doubleValue();

		return bm1;
	}


}
