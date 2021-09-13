package test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import baseClasses.BaseTest;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest{

	@BeforeMethod
	public void goToLoginPage() {
		HomePage homePage=new HomePage(getDriver());
		homePage.goToLoginPage();
	}
	@Test(description="Verify Blank Error Message")
	public void verifyErrorMessageOnBlank() {
		LoginPage loginPage=new LoginPage(getDriver());
		loginPage.clickSignIn();
		assertEquals(loginPage.getErrorMessages(), "An email address required", "Error Message for Blank is incorrect");
	}
	
	@Test(dataProvider="testCaseData",description="Verify Invalid Credentials error Message")
	public void VerifyErrorMessageOnInvalidCredentials(String userName,String password) {
		LoginPage loginPage=new LoginPage(getDriver());
		loginPage.login(userName, password);
		assertEquals(loginPage.getErrorMessages(), "Authentication failed", "Error Message for incorrect credentials are incorrect");
	}
	
	@Test(dataProvider="testCaseData",description="Verify valid login")
	public void VerifyValidLogin(String userName,String password) {
		LoginPage loginPage=new LoginPage(getDriver());
		loginPage.login(userName, password);
		loginPage.waitForLogin();
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(driver.getTitle().trim(), "My account - My Store", "Page title is incorrect");
		soft.assertEquals(driver.getCurrentUrl().trim(), "http://automationpractice.com/index.php?controller=my-account", "Url is incorrect");
		soft.assertAll();
	}
	
	
}
