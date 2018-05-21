package com.jeeplus.modules.fea.pub.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
public class WriteExcelMGFX {
	public void exportExcel(String projectname,
			Map<String,List<List<Double>>> retmap, OutputStream out)  
	{  
		// 声明一个工作薄  
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		// 生成一个表格  
		HSSFSheet sheet = workbook.createSheet("敏感性分析报表");  
		HSSFRow row1 = sheet.createRow(0);
		for(int j=0;j<6;j++) {
			HSSFCell cell = row1.createCell(j);
			if(j==0) cell.setCellValue("序号");
			if(j==1) cell.setCellValue("不确定性因素");
			if(j==2) cell.setCellValue("变化率");
			if(j==3) cell.setCellValue("变化值");
			if(j==4) cell.setCellValue("内部收益率");
			if(j==5) cell.setCellValue("敏感系数");
		}
		Map<String,String> namemap = new HashMap<String,String>();
		namemap.put("investamt", "初投资");namemap.put("person", "人工费");
		namemap.put("powercost", "电费");namemap.put("price", "取暖费");

		int onerownum = 0;
		int row = 0;
		for(String mgitem : retmap.keySet()) {
			List<List<Double>> changell = retmap.get(mgitem);
			onerownum = changell.get(0).size();
			for(int i=0;i<changell.get(0).size();i++) {
				HSSFRow row2 = sheet.createRow(i+1+(row*onerownum));
				for(int j=0;j<6;j++) {
					HSSFCell cell2 = row2.createCell(j);
					if(j==0) cell2.setCellValue(""+(row+1));
					if(j==1) cell2.setCellValue(namemap.get(mgitem));
					if(j==2) cell2.setCellValue(changell.get(3).get(i));
					if(j==3) cell2.setCellValue(changell.get(2).get(i));
					if(j==4) cell2.setCellValue(changell.get(1).get(i));
					if(j==5) cell2.setCellValue(changell.get(0).get(i));
				}
			}
			row++;
		}
		for(int i=0;i<retmap.size();i++) {
			sheet.addMergedRegion(new Region((i+1), (short) 0, (i+onerownum*(i+1)),(short)0));
			sheet.addMergedRegion(new Region((i+1), (short)1,  (i+onerownum*(i+1)), (short)(1)));
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

	public static void exportmgexcel(String path,String projectname,
			Map<String,List<List<Double>>> retmap)  
	{  
		WriteExcelMGFX ex = new WriteExcelMGFX();
		try  
		{  
			OutputStream out2 = new FileOutputStream(path);
			ex.exportExcel(projectname,retmap, out2);
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
