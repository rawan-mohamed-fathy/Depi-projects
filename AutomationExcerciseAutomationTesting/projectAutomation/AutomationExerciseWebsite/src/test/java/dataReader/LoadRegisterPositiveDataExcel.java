package dataReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadRegisterPositiveDataExcel {

	FileInputStream stream = null;
	
	
	public FileInputStream getExcelFile() {
		
		String filePath = System.getProperty("user.dir")+ "\\src\\test\\java\\ExcelSheetsForRegisterTestData\\registerPositiveTestData.xlsx";
		
		try {
			stream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			
			System.out.println("File Not Found");
		}
		
		return stream;
		 
	}
	
	
	public Object[][] getExcelData() throws IOException {
		stream = getExcelFile();
		
		XSSFWorkbook workBook = new XSSFWorkbook(stream);
	    XSSFSheet RegisterPositiveTestDataSheet1 = workBook.getSheetAt(0);
	    XSSFSheet RegisterPositiveTestDataSheet2 = workBook.getSheetAt(1);
	 
	    int nRowSheet1 = RegisterPositiveTestDataSheet1.getLastRowNum();

	    int nColsSheet1 = 2;
	    int nColsSheet2 = 14;
	    int totalCols = nColsSheet1 + nColsSheet2; // 16 column
	    
	    
	    Object [][] registerPositiveDataArray = new Object[nRowSheet1][totalCols];
	    DataFormatter formatter = new DataFormatter();
	    
	    
	    for(int i = 1;i<=nRowSheet1;i++) {
	    	XSSFRow row1 = RegisterPositiveTestDataSheet1.getRow(i);
    		XSSFRow row2 = RegisterPositiveTestDataSheet2.getRow(i);
	    	for(int j = 0;j<nColsSheet1;j++) {
	    		
	    		String cellValue = formatter.formatCellValue(row1.getCell(j));
	            registerPositiveDataArray[i-1][j] = cellValue;
	    	}
	    	
	    	for(int j = 0;j<nColsSheet2;j++) {
	    		
	    		String cellValue = formatter.formatCellValue(row2.getCell(j));
	            registerPositiveDataArray[i-1][nColsSheet1+j] = cellValue;	    	}
	    }
	    
	    workBook.close();
	    return registerPositiveDataArray;
		
	}
	
	
	
}
