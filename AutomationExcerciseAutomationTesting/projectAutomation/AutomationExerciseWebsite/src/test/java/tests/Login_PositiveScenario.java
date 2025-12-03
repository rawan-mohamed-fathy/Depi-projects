package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataReader.LoadLoginPositiveDataProperties;
import pages.HomePage;
import pages.LoginPage;

public class Login_PositiveScenario extends TestBase {
	HomePage homeObject ;
	LoginPage loginObject;
	
	public void initializeObjects() {
		homeObject = new HomePage(driver);
		loginObject = new LoginPage(driver);
	}
	
	String email1= LoadLoginPositiveDataProperties.loginPositiveData.getProperty("email1");
	String password1 = LoadLoginPositiveDataProperties.loginPositiveData.getProperty("password1");
	
	String email2= LoadLoginPositiveDataProperties.loginPositiveData.getProperty("email2");
	String password2 = LoadLoginPositiveDataProperties.loginPositiveData.getProperty("password2");
	
	@DataProvider(name = "loadLoginPositiveData")
    public Object[][] getLoginPositiveData() {
		Object LoginPositiveData[][] = new Object[][]
       {
			 {email1,password1},
			 {email2,password2},
        };
        return LoginPositiveData;
    }
	
	
    @Test(dataProvider = "loadLoginPositiveData")
    public void testLogin_CorrectUsernameAndMatchingPassword(String email,String password) throws InterruptedException {
    	initializeObjects();
    	
    	//Thread.sleep(3000);
    	homeObject.openLoginPage();
    	Assert.assertEquals(loginObject.loginMessage.getText(), "Login to your account");
    	
    	//Thread.sleep(3000);
    	loginObject.userCanLogin(email, password);
    	
    	Assert.assertTrue(loginObject.logoutBtn.isDisplayed());
    	//Thread.sleep(3000);
    	
    	loginObject.userCanLogout();
    }
}
