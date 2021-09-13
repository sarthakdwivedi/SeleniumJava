package pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import baseClasses.BasePage;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	WebElement email;
	WebElement passwd;
	WebElement SubmitLogin;

	@FindAll(@FindBy(css = "div.alert li"))
	List<WebElement> errorMessages;

	public void login(String userName, String password) {
		writeUserName(userName);
		writePassword(password);
		clickSignIn();
		
	}

	public void clickSignIn() {
		click(SubmitLogin);
	}

	public void writeUserName(String userName) {
		sendText(email, userName);
	}

	public void writePassword(String password) {
		sendText(passwd, password);
	}

	public String getErrorMessages() {
		return errorMessages.stream().map(e -> e.getText().trim()).collect(Collectors.joining(" "));
	}

	public void waitForLogin() {
		// TODO Auto-generated method stub
		getWait().until(ExpectedConditions.urlContains("controller=my-account"));
	}
}
