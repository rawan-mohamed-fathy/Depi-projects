package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataReader.LoadRegisterPositiveDataExcel;
import pages.HomePage;
import pages.RegisterPage;

public class Register_PositiveScenario extends TestBase{
	HomePage homeObject ;
	RegisterPage registerObject ;
	
	@DataProvider(name = "RegisterPositiveData")
	public Object[][] getRegisterPositiveData() throws IOException{
		LoadRegisterPositiveDataExcel registerPositiveData = new LoadRegisterPositiveDataExcel();
		return registerPositiveData.getExcelData();
	}
	
  @Test(dataProvider = "RegisterPositiveData")
  public void testRegister_ValidFieldsWithNewEmail(String name,String email,String Password,String Day,String Month,String Year,String firstName,String lastName,String Company,String Address,String Address2,String Country,String State,String City,String Zipcode,String mobileNumber) throws InterruptedException {
	  homeObject = new HomePage(driver);
	  registerObject = new RegisterPage(driver);
	  
	  Thread.sleep(2000);
	  Assert.assertEquals(homeObject.homeBtn.getCssValue("color"), "rgba(255, 165, 0, 1)");
	  
	  homeObject.openRegisterPage();
	  Assert.assertEquals(registerObject.newUserMessage.getText(), "New User Signup!");
	  Thread.sleep(2000);
	  
	  registerObject.userCanSignUpNewUser(name,email);
	  Assert.assertEquals(registerObject.enterAccountMessage.getText(), "ENTER ACCOUNT INFORMATION");

	  Thread.sleep(2000);

	  registerObject.userCanEnterAccountInformation(Password,Day,Month,Year,firstName,lastName,Company,Address,Address2,Country,State,City,Zipcode,mobileNumber);
	  Assert.assertEquals(registerObject.successMessage.getText(), "ACCOUNT CREATED!");
	  Thread.sleep(2000);
	  
	  registerObject.userCanContinue();
	  Assert.assertTrue(registerObject.deleteAccountBtn.isDisplayed());
	  
	  Thread.sleep(2000);
	  
	  
	  registerObject.deleteAccount();
	  Assert.assertEquals(registerObject.deleteSuccessMessage.getText(), "ACCOUNT DELETED!");
	  
	  Thread.sleep(2000);
	  
	  registerObject.userCanContinue();

  }

}
