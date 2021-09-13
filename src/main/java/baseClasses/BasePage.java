package baseClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.PropertyManager;

public class BasePage {
	protected WebDriver driver;
	private WebDriverWait wait;

	public BasePage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.setWait(new WebDriverWait(this.driver, PropertyManager.getInstance().getWaitTime()));
	}

	public void click(WebElement e) {
		try {
			getWait().until(ExpectedConditions.elementToBeClickable(e));
		} catch (TimeoutException timeout) {
			System.out.println("Element: " + e + " is not clicable");
		}
		System.out.println("Clicking element" + e);
		e.click();
	}

	public void sendText(WebElement e, String t) {
		e.clear();
		System.out.println("writing in to element" + e);
		e.sendKeys(t);
	}

	public void goToHomePage() {

		if (!driver.getCurrentUrl().equals(PropertyManager.getInstance().getBaseURL())) {
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.HOME);
			driver.findElement(By.cssSelector("#header_logo a")).click();
			getWait().until(ExpectedConditions.urlToBe("http://automationpractice.com/index.php"));
		}
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}
}
