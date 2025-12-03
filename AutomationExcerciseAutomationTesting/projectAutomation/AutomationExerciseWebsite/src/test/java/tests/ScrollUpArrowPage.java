package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.HomePage;

public class ScrollUpArrowPage extends TestBase {
	HomePage homeObject ;
	
	public void initializeObjects() {
		homeObject = new HomePage(driver);
	}
	
    @Test
    public void testScrollUpArrow() throws InterruptedException {
    	initializeObjects();
    	
    	Thread.sleep(3000);
    	homeObject.openHomePage();
    	Assert.assertEquals(homeObject.homeBtn.getCssValue("color"), "rgba(255, 165, 0, 1)");
    	//Thread.sleep(3000);
    	//homeObject.scrollToBottom();
    	//Thread.sleep(3000);
//    	homeObject.ScrollUpPage();
//        Thread.sleep(2000);
    	
    }
}
