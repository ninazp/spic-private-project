package com.jeeplus.modules.fea.pub.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
public class WriteExcelCal {
	public void exportExcel(String title, 
			List<List<Double>> dataset, OutputStream out)  
	{  
		// 声明一个工作薄  
		HSSFWorkbook workbook = new HSSFWorkbook();  
		// 生成一个表格  
		HSSFSheet sheet = workbook.createSheet(title);  
		HSSFRow row = sheet.createRow(0); 
		// 遍历集合数据，产生数据行  
		for(int i=-1;i< dataset.size();i++)
		{  
			if(i==-1){
			 HSSFRow row1 = sheet.createRow(i+1);  
			 for (int m = 0; m < dataset.get(0).size(); m++)  
			 {  
				HSSFCell cell = row1.createCell(m);  
				cell.setCellValue(dataset.get(i+1).get(m));
			 }  
			}else{
				 HSSFRow row1 = sheet.createRow(i);  
				 for (int m = 0; m < dataset.get(0).size(); m++)  
				 {  
					HSSFCell cell = row1.createCell(m);  
					cell.setCellValue(dataset.get(i).get(m));
				 }  
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

	public static void getexcel(String excelname,List<List<Double>> dataset)  
	{  
		WriteExcelCal ex = new WriteExcelCal();
		try  
		{  
			OutputStream out2 = new FileOutputStream("E://"+excelname);  
			ex.exportExcel(excelname,dataset, out2);
			out2.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}  
	
	public void exportExcelrstr( Map<String,List<List<Double>>> retmap, OutputStream out)  
	{  
		// 声明一个工作薄  
		HSSFWorkbook workbook = new HSSFWorkbook();  
		// 生成一个表格  
		for(String title : retmap.keySet()) {
			HSSFSheet sheet = workbook.createSheet(title);  
			List<List<Double>> dataset = retmap.get(title);
				
			HSSFRow row2 = sheet.createRow(0);
            for(int i=0;i<13;i++) {
            	row2.createCell(i);
			}
			CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 3); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列   
			sheet.addMergedRegion(region1); 
			
			row2.getCell(0).setCellValue("111");
			
			// 遍历集合数据，产生数据行  
			for(int i=-1;i< dataset.size();i++)
			{  
				if(i==-1){
				 HSSFRow row1 = sheet.createRow(i+2);  
				 for (int m = 0; m < dataset.get(0).size(); m++)  
				 {  
					HSSFCell cell = row1.createCell(m);  
					cell.setCellValue(dataset.get(i+1).get(m));
				 }  
				}else{
					 HSSFRow row1 = sheet.createRow(i+2);  
					 for (int m = 0; m < dataset.get(0).size(); m++)  
					 {  
						HSSFCell cell = row1.createCell(m);  
						cell.setCellValue(dataset.get(i).get(m));
					 }  
				}
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
	
	public static void getexcelstr(String excelname,Map<String,List<List<Double>>> retmap)  
	{  
		WriteExcelCal ex = new WriteExcelCal();
		try  
		{  
			OutputStream out2 = new FileOutputStream("E://"+excelname);  
			ex.exportExcelrstr(retmap, out2);
			out2.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}  
	
}
