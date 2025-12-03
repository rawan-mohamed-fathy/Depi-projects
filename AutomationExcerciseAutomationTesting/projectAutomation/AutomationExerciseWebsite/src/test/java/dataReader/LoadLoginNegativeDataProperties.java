package dataReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadLoginNegativeDataProperties {
	
	public static Properties loginNegativeData = loadLoginNegativeData(System.getProperty("user.dir")+"\\src\\test\\java\\properties\\LoginNegativeTestData.properties");
	
	private static Properties loadLoginNegativeData(String filePath) {
		
		Properties pro = new Properties();
		
		try 
		{
			FileInputStream stream =  new FileInputStream(filePath);
			try {
				pro.load(stream);
			} catch (IOException e) {
				System.out.println("Can not Read From File");
			}
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File Not Found");
		}
		
		return pro;
	}
	
}
