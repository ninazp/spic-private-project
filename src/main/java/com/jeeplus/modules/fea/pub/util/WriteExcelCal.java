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
	public void exportExcel(
			Map<String,List<List<Double>>>  retmap, OutputStream out)  
	{  
		String [] sheetname = new String [] {
				"投资计划与资金筹措表","总成本费用表","借款还本付息计划表","利润和利润分配表","财务计划现金流量表",
				"项目投资现金流量表","项目资本金现金流量表","资金来源与运用表","资产负债表","EVA测算表"
		};
		
		// 声明一个工作薄  
		HSSFWorkbook workbook = new HSSFWorkbook(); 
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
						}else if(i==3) {
						   cell.setCellValue("第"+(m-2)+"期");
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
			
			sheet.addMergedRegion(new Region(0, (short) 0, (short)0, (short)(dataset.get(0).size()+1)));
			sheet.addMergedRegion(new Region(1, (short) 0, (short)1, (short)(dataset.get(0).size()+1)));
			sheet.addMergedRegion(new Region(2, (short) 0, (short)3, (short)(0)));
			sheet.addMergedRegion(new Region(2, (short) 1, (short)3, (short)(1)));
			sheet.addMergedRegion(new Region(2, (short)2, (short)3, (short)(2)));
			
			sheet.getRow(0).getCell(0).setCellStyle(cellStyle1);
			sheet.getRow(1).getCell(0).setCellStyle(cellStyle2);
			
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

	public static void getexcel(String path,String projectname,Map<String,List<List<Double>>> retmap)  
	{  
		WriteExcelCal ex = new WriteExcelCal();
		try  
		{  
			OutputStream out2 = new FileOutputStream(path+projectname+"经济性分析报表.xls");  
			ex.exportExcel(retmap, out2);
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
	
}
