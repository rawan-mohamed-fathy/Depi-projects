package dataReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadRegisterNegativeDataExcel {

	FileInputStream stream = null;
	
	
	public FileInputStream getExcelFile() {
		
		String filePath = System.getProperty("user.dir")+ "\\src\\test\\java\\ExcelSheetsForRegisterTestData\\registerNegativeTestData.xlsx";
		
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
	    XSSFSheet RegisterNegativeTestDataSheet1 = workBook.getSheetAt(0);
	    
	    int nRow = RegisterNegativeTestDataSheet1.getLastRowNum();

	    int nCols = 2;
	    
	    Object [][] registerNegativeDataArray = new Object[nRow][nCols];
	    
	    for(int i = 1;i<=nRow;i++) {
	    	XSSFRow row = RegisterNegativeTestDataSheet1.getRow(i);
	    	for(int j = 0;j<nCols;j++) {
	            registerNegativeDataArray[i-1][j] = row.getCell(j).toString();
	    	}
	    }
	    
	    workBook.close();
	    return registerNegativeDataArray;
		
	}
	
	
	
}
