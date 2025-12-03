package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataReader.LoadRegisterNegativeDataExcel;
import pages.HomePage;
import pages.RegisterPage;

public class Register_NegativeScenario extends TestBase {
	HomePage homeObject ;
	RegisterPage registerObject ;
	
	@DataProvider(name = "RegisterNegativeData")
	public Object[][] getRegisterNegativeData() throws IOException{
		LoadRegisterNegativeDataExcel registerNegativeData = new LoadRegisterNegativeDataExcel();
		return registerNegativeData.getExcelData();
	}
	
	
  @Test(dataProvider = "RegisterNegativeData")
  public void testRegister_ValidFieldsWithExistEmail(String name, String email) throws InterruptedException {
	  homeObject = new HomePage(driver);
	  registerObject = new RegisterPage(driver);
	  
	  Thread.sleep(3000);
	  homeObject.openRegisterPage();
	  Assert.assertEquals(registerObject.newUserMessage.getText(), "New User Signup!");

	  registerObject.userCanSignUpNewUser(name,email);
	  Assert.assertEquals(registerObject.failedMessage.getText(), "Email Address already exist!");
	  Thread.sleep(3000);

  }
}
