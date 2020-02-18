package com.selTestng.utility;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public Object[][] readDataFromExcelXSS(String filename,String sheetname,String key)
	{
		try 
		{
			Object dataSet[][]=null;
			Object data[][]=null;
						
			FileInputStream file = new FileInputStream(new File(filename));
			XSSFWorkbook excelDataFile = new XSSFWorkbook(file);
			XSSFSheet sheet = excelDataFile.getSheet(sheetname);
			
			int rows = sheet.getLastRowNum();
			int cols = sheet.getRow(0).getLastCellNum();
			//System.out.println("NO OF ROWS: " + rows + "NO OF COL: " + cols);
			
			data = new Object[rows][cols-1];
			boolean keyDataStart=false,keyDataEnd=false;
			int keyDataRow=0;
			 
			for(int i=0 ; i<rows ;i++) 
			{
				Row row = sheet.getRow(i);
				for (int j = 0; j<cols; j++)
				{
					//System.out.println("i=" + i + "  j="+j);	
					Cell cell = row.getCell(j);
					String cellType = cell.getCellTypeEnum().name();
			        if( cell != null)
			        {		        	   	
			        	if(!keyDataStart && (cellType.equals("STRING")) && cell.getStringCellValue().equalsIgnoreCase(key))
			           	{
			        		keyDataStart=true;break;
			           	}
			           	else if(!keyDataEnd &&(cellType.equals("STRING")) && cell.getStringCellValue().equalsIgnoreCase(key+"_end"))
			           	{
			           		keyDataEnd=true;
			           		break;
			           	}
			           	else if(keyDataStart && i!=0 && j!=0)
			           	{
			           		System.out.println(cellType);
		           			switch (cellType)
		           			{
		           				case "STRING":
		           					data[keyDataRow-1][j-1] = cell.getStringCellValue();
		           					break;
		           				case "NUMERIC":
		           					data[keyDataRow-1][j-1] = cell.getNumericCellValue();
		           					break;
		           				default:
		           					System.out.println("no matching enum date type found");
		           					break;
		           			}
			           		System.out.println(data[keyDataRow-1][j-1]);
		           	   	}
			        }
				}
				if(keyDataEnd)
					break;
				else if(keyDataStart)
					keyDataRow++;
			}
			dataSet=new Object[keyDataRow-1][cols-1];
			for(int j=0 ; j < keyDataRow-1 ; j++)
				dataSet[j]=data[j];
			
			data=null;
			return dataSet;			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		//Object data1[][] = new ExcelReader().readDataFromExcelXSS("src/test/resources/testData/LoginTestData.xlsx", "login","TC1");
		Object data1[][] = new ExcelReader().readDataFromExcelXSS("src/test/resources/testData/LoginTestData.xlsx", "login","TC2");
		//Object data1[][] = new ExcelReader().readDataFromExcelXSS("src/test/resources/testData/LoginTestData.xlsx", "login","TC3");
		//Object data1[][] = new ExcelReader().readDataFromExcelXSS("src/test/resources/testData/LoginTestData.xlsx", "login","TC4");
		
		 System.out.println("\n\n");
		 System.out.println(data1[0].length);
		 
		 for(int j=0; j < 3 ; j++)
		{
			 for(Object obj: data1[j])
				 System.out.print(obj + " ");
			 
			 System.out.println();
		}
		 
	}
	
	
}
