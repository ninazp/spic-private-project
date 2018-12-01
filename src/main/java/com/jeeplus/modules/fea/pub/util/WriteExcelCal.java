package com.jeeplus.modules.fea.pub.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import com.jeeplus.modules.fea.pub.report.ColName;
import com.jeeplus.modules.fea.pub.report.createReportPubDMO;
public class WriteExcelCal {
	public void exportExcel(String projectname,
			Map<String,List<List<Double>>>  retmap, OutputStream out,List<List<String>> totalgs)  
	{  
		String [] sheetname = new String [] {
				"投资计划与资金筹措表","总成本费用表","借款还本付息计划表","利润和利润分配表","财务计划现金流量表",
				"项目投资现金流量表","项目资本金现金流量表","资金来源与运用表","资产负债表","EVA测算表"
		};
		
		// 声明一个工作薄  
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		if(null!=totalgs && totalgs.size()>0) {
			// 生成一个表格  
			HSSFSheet sheet = workbook.createSheet("总估算表");  
			HSSFCellStyle cellStyle1 =  workbook.createCellStyle();
			HSSFCellStyle cellStyle2 =  workbook.createCellStyle();
			
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			
			// 遍历集合数据，产生数据行  
			for(int i=0;i< totalgs.size()+4;i++)
			{  
				HSSFRow row1 = sheet.createRow(i);  
				if(i<4) {
					for (int m = 0; m < totalgs.get(0).size(); m++)  {
						HSSFCell cell = row1.createCell(m);
						if(i==0) {
							cell.setCellValue("总估算表");
						}else if(i==1) {
							cell.setCellValue("工程名称："+projectname);
						}else if(i==2) {
							if(m==0) {
								cell.setCellValue("序号");
							}else if(m==1) {
								cell.setCellValue("工程和费用名称");
							}else if(m<7) {
								cell.setCellValue("估    算   价   值   （万  元 ）");
							}else if(m<10) {
								cell.setCellValue("技术经济指标（万元）");
							}else if(m==10) {
								cell.setCellValue("备注");
							}
						}else if(i==3) {
							if(m==0) {
								cell.setCellValue("序号");
							}else if(m==1) {
								cell.setCellValue("工程和费用名称");
							}else if(m==2) {
								cell.setCellValue("建筑工程");
							}else if(m==3) {
								cell.setCellValue("设备及工器具购置费");
								
							}else  if(m==4) {
								cell.setCellValue("安装工程");
								
							}else if(m==5) {
								cell.setCellValue("其他费用");
								
							}else if(m==6) {
								cell.setCellValue("合计");
								
							}else if(m==7) {
								cell.setCellValue("单位");
								
							}else if(m==8) {
								cell.setCellValue("数量");
								
							}else if(m==9) {
								cell.setCellValue("指标");
								
							}else if(m==10) {
								cell.setCellValue("备注");
							}
						}
					}
				}else {
					for (int m = 0; m < totalgs.get(0).size(); m++)  {
						HSSFCell cell = row1.createCell(m);
						cell.setCellValue(totalgs.get(i-4).get(m));
					}
				}
			}
			sheet.addMergedRegion(new Region(0, (short) 0, (short)0, (short)(totalgs.get(0).size()+1)));
			sheet.addMergedRegion(new Region(1, (short) 0, (short)1, (short)(totalgs.get(0).size()+1)));
			sheet.addMergedRegion(new Region(2, (short) 0, (short)3, (short)(0)));
			sheet.addMergedRegion(new Region(2, (short) 1, (short)3, (short)(1)));
			sheet.addMergedRegion(new Region(2, (short)2, (short)2, (short)(6)));
			sheet.addMergedRegion(new Region(2, (short)7, (short)2, (short)(9)));
			sheet.addMergedRegion(new Region(2, (short)(10), (short)3, (short)(10)));
			
			sheet.addMergedRegion(new Region(35, (short)0, (short)(35), (short)(1)));
			sheet.addMergedRegion(new Region(36, (short)0, (short)(36), (short)(1)));
			
			sheet.getRow(0).getCell(0).setCellStyle(cellStyle1);
			sheet.getRow(1).getCell(0).setCellStyle(cellStyle2);
			
		}
		
		
		// 声明一个工作薄  
		if(null!=retmap.get("财务指标汇总表") && retmap.get("财务指标汇总表").size()>0) {
			// 生成一个表格  
			HSSFSheet sheet = workbook.createSheet("财务指标汇总表");  
			
			HSSFCellStyle cellStyle1 =  workbook.createCellStyle();
			HSSFCellStyle cellStyle2 =  workbook.createCellStyle();
			
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			List<Double> reporttotal = retmap.get("财务指标汇总表").get(0);
			// 遍历集合数据，产生数据行  
			for(int i=0;i< 2;i++)
			{  
				HSSFRow row1 = sheet.createRow(i);  
					for (int m = 0; m < 4; m++)  {
						HSSFCell cell = row1.createCell(m);
						if(i==0) {
							cell.setCellValue("财务指标汇总表");
						}else if(i==1) {			
							if(m==0) cell.setCellValue("序号");
							if(m==1) cell.setCellValue("项目");
							if(m==2) cell.setCellValue("单位");
							if(m==3) cell.setCellValue("数值");
						}
					}
			}
			String [] [] elevencol =ColName.getcolmap().get("财务指标汇总表");
			for(int j=0;j<reporttotal.size();j++) {
				HSSFRow row1 = sheet.createRow(j+2); 
				for (int m = 0; m < 4; m++)  {
					HSSFCell cell = row1.createCell(m);
					if(m<3) {
						if(m==0) cell.setCellValue(elevencol[0][j]);
						if(m==1) cell.setCellValue(elevencol[1][j]);
						if(m==2)cell.setCellValue(elevencol[2][j]);
					}else {
						cell.setCellValue(reporttotal.get(j));
					}
				}
			}
			sheet.addMergedRegion(new Region(0, (short)0, (short)(0), (short)(3)));
			sheet.getRow(0).getCell(0).setCellStyle(cellStyle1);
			sheet.getRow(1).getCell(0).setCellStyle(cellStyle2);
		}
		
		if(null!=retmap && retmap.size()>0) {
			for(String title :sheetname) {
				List<List<Double>> dataset  = retmap.get(title);
				// 生成一个表格  
				HSSFSheet sheet = workbook.createSheet(title);  
				HSSFCellStyle cellStyle1 =  workbook.createCellStyle();
				HSSFCellStyle cellStyle2 =  workbook.createCellStyle();
				
				cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				
				// 遍历集合数据，产生数据行  
				for(int i=0;i< dataset.size()+4;i++)
				{  
					HSSFRow row1 = sheet.createRow(i);  
					if(i<4) {
						for (int m = 0; m < dataset.get(0).size()+2; m++)  {
							HSSFCell cell = row1.createCell(m);
							if(i==0) {
								cell.setCellValue(title);
							}else if(i==1) {
								cell.setCellValue("人民币单位：万元");
							}else if(i==2) {
								if(title.equals("资产负债表")) {
									if(m==0) {
										cell.setCellValue("序号");
									}else if(m==1) {
										cell.setCellValue("项目");
									}if(m==2) {
										cell.setCellValue("建设期");
									} else{
										cell.setCellValue("运行期");
									}
								}else {
									if(m==0) {
										cell.setCellValue("序号");
									}else if(m==1) {
										cell.setCellValue("项目");
									}else if(m==2) {
										cell.setCellValue("合计");
									}else if(m==3) {
										cell.setCellValue("建设期");
									} else{
										cell.setCellValue("运行期");
									}
								}
							}else if(i==3) {
							   if(title.equals("资产负债表")) {
								   cell.setCellValue("第"+(m-1)+"期");
							   }else {
								   cell.setCellValue("第"+(m-2)+"期");
							   }
							   
							}
						}
					}else {
						for (int m = 0; m < dataset.get(0).size()+2; m++)  
						{  
							HSSFCell cell = row1.createCell(m);  
							if(m==0) {
								cell.setCellValue(ColName.getcolmap().get(title)[0][i-4]);
							}else if(m==1) {
								cell.setCellValue(ColName.getcolmap().get(title)[1][i-4]);
							}else {
							    cell.setCellValue(dataset.get(i-4).get(m-2));
							}
						}  
					}
				}
				
				
				if(title.equals("项目投资现金流量表")) {
					
					String [] colname = new String[] {
							"计算指标：	",
							"项目投资财务内部收益率（%）（所得税前）",
							"项目投资财务内部收益率（%）（所得税后）",
							"项目投资财务净现值（万元）（所得税前）",
							"项目投资财务净现值（万元）（所得税后）",
							"项目投资回收期（年）（所得税前）",
							"项目投资回收期（年）（所得税后）"
					};
					
					createReportPubDMO dmo = new createReportPubDMO();
					List<Double> retlst = dmo.getinvest_irrnpv(dataset);
						
					int rr = 0;
					for(int i= dataset.size()+4;i<dataset.size()+11;i++)
					{  
						HSSFRow row1 = sheet.createRow(i);
						for(int j=0;j<3;j++) {
							HSSFCell cell = row1.createCell(j);
							cell.setCellValue(colname[rr]);
							if(rr!=0 && j==2) {
								if(rr==1 || rr==2) {
									cell.setCellValue(getDouble2float(retlst.get(rr-1)*100));
								}else {
									cell.setCellValue(getDouble2float(retlst.get(rr-1)));
								}
								
							}
						}
						rr++;//行数
					}
					for(int r = dataset.size()+4;r<dataset.size()+11;r++) {
						sheet.addMergedRegion(new Region(r, (short)0, (short)r, (short)(1)));
					}
				}
				
	           if(title.equals("项目资本金现金流量表")) {
					String [] colname = new String[] {
							"计算指标： ",
							"资本金财务内部收益率（%）"
					};
					createReportPubDMO dmo = new createReportPubDMO();
					List<Double> retlst = dmo.getcapital_irrnpv(dataset);
						
					int rr = 0;
					for(int i= dataset.size()+4;i<dataset.size()+6;i++)
					{  
						HSSFRow row1 = sheet.createRow(i);
						for(int j=0;j<3;j++) {
							HSSFCell cell = row1.createCell(j);
							cell.setCellValue(colname[rr]);
							if(rr!=0 && j==2) {
								if(rr==1 || rr==2) {
									cell.setCellValue(getDouble2float(retlst.get(rr-1)*100));
								}else {
									cell.setCellValue(getDouble2float(retlst.get(rr-1)));
								}
								
							}
						}
						rr++;//行数
					}
					for(int r = dataset.size()+4;r<dataset.size()+11;r++) {
						sheet.addMergedRegion(new Region(r, (short)0, (short)r, (short)(1)));
					}
				}
				
	            //表头的单元格合并
				sheet.addMergedRegion(new Region(0, (short) 0, (short)0, (short)(dataset.get(0).size()+1)));
				sheet.addMergedRegion(new Region(1, (short) 0, (short)1, (short)(dataset.get(0).size()+1)));
				sheet.addMergedRegion(new Region(2, (short) 0, (short)3, (short)(0)));
				sheet.addMergedRegion(new Region(2, (short) 1, (short)3, (short)(1)));
				if(!title.equals("资产负债表")) {
					sheet.addMergedRegion(new Region(2, (short)2, (short)3, (short)(2)));
				}
				sheet.getRow(0).getCell(0).setCellStyle(cellStyle1);
				sheet.getRow(1).getCell(0).setCellStyle(cellStyle2);
			}
		}
		try  
		{  
			workbook.write(out);  
		}  
		catch (IOException e)  
		{  
			e.printStackTrace();  
		}  
	}  

	public static void exportexcel(String path,String projectname,
			Map<String,List<List<Double>>> retmap,List<List<String>> totalgs)  
	{  
		WriteExcelCal ex = new WriteExcelCal();
		try  
		{  
			OutputStream out2 = new FileOutputStream(path);
			ex.exportExcel(projectname,retmap, out2,totalgs);
			out2.close();  
		} catch (FileNotFoundException e) {
			e.printStackTrace();  
		} catch (IOException e) {
			e.printStackTrace();  
		}  
	}  
	
	public Double getDouble2float(Double m){
		BigDecimal  bm = new BigDecimal(m);
		Double bm1 = bm.setScale(2, RoundingMode.HALF_UP).doubleValue();

		return bm1;
	}
	
	public String[][] gettotalcols (){
		String [][] cols=new String[][] {
				{   "",
					"I",
					"1",
					"2",
					"II",
					"1",
					"2",
					"3",
					"4",
					"5",
					"6",
					"7",
					"8",
					"8.1",
					"8.2",
					"8.3",
					"9",
					"10",
					"11",
					"12",
					"13",
					"14",
					"15",
					"16",
					"17",
					"III",
					"一",
					"二",
					"IV",
					"V",
					"建设项目总投资",
					"各工程费用占建设项目总投资的比例(%)"},
				{"固定资产投资",
						"第一部分工程费用",
						"换热站工程",
						"热网工程",
						"工程其他费用",
						"建设单位管理费",
						"项目可行性研究编制费",
						"工程设计费",
						"施工图审查费",
						"竣工图编制费",
						"工程建设监理费",
						"联合试运转费",
						"招标代理服务费",
						"货物招标代理服务费",
						"服务招标代理服务费",
						"工程招标代理服务费",
						"工程保险费",
						"生产准备费",
						"办公和生活家具购置费",
						"施工人员意外保险费",
						"工程勘察费",
						"环境影响咨询费",
						"节能专篇编制和评审费",
						"城市基础设施建设费",
						"收购探矿权",
						"工程预备费",
						"基本预备费",
						"涨价预备费",
						"建设期贷款利息",
						"铺底流动资金",
						"建设项目总投资",
						"各工程费用占建设项目总投资的比例(%)"},
				{"",
							"",
							"",
							"",
							"财建[2016]504号",
							"财建[2016]504号",
							"计价格[1999]1283号",
							"计价格(2002)10号",
							"",
							"设计费*8%",
							"发改价格(2007)670号",
							"设备购置费×1.0%",
							"计价格(2002)1980号",
							"计价格(2002)1981号",
							"计价格(2002)1982号",
							"计价格(2002)1983号",
							"",
							"6人×60%×2500元/人×6个月",
							"6人×1000元/人",
							"6人×1000元/人",
							"计价格(2002)10号",
							"参照可研",
							"按可研编制费的70%",
							"100元/平米",
							"",
							"",
							"0.05",
							"",
							"",
							"",
							"",
							""}
		};
		return cols;
	} 
	
}
