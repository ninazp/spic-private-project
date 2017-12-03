package com.jeeplus.modules.fea.pub.util;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
public class ReadExcelCal {
	public static Double getirrnpvvalue(Double[] flowfinanceVals,
			Double rate1,Double rate2,
			String returntype){
		try {
			FileInputStream fis = new FileInputStream("E:/my_work/testexcel/11.xls");
			Workbook wb1=new HSSFWorkbook(fis);
			Sheet sheet = wb1.getSheetAt(0);
			
			for(int i = 1 ;i<flowfinanceVals.length;i++){
				sheet.getRow(0).getCell(i-1).setCellValue(flowfinanceVals[i]);
			}
			sheet.getRow(1).getCell(0).setCellValue(rate1);
			sheet.getRow(1).getCell(1).setCellValue(rate2);
			
			FormulaEvaluator evaluator = wb1.getCreationHelper().createFormulaEvaluator();  
			
			CellReference cellReference = null;
			if(returntype.equalsIgnoreCase("1")){
			    cellReference = new CellReference("C2"); //税前回收值
			}else if(returntype.equalsIgnoreCase("2")){
				cellReference = new CellReference("D2"); //税后回收值
			}
			else if(returntype.equalsIgnoreCase("3")){
				cellReference = new CellReference("E2"); //税前内部收益率
			}
			else if(returntype.equalsIgnoreCase("4")){
				cellReference = new CellReference("F2"); //税后内部收益率
			}
			Row row = sheet.getRow(cellReference.getRow());  
			Cell cell = row.getCell(cellReference.getCol()); 
			
			CellValue cellValue = evaluator.evaluate(cell); 
			
			if(null!=cellValue){
			    return cellValue.getNumberValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0.0;
	}
	
	public static Double getreturnperiod(Double[] flowmoney){
		int num = 0;
		for(int i = 1 ;i<flowmoney.length;i++){
			if(flowmoney[i]>0 && num==0){
				num = i;
			}
		}
		if(num>=1){
		  Double val = num-1+(Math.abs(flowmoney[num-1])/flowmoney[num]);
		  return val;
		}
		
		return 0.0;
	}
}
