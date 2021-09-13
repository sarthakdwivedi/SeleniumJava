package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import baseClasses.BasePage;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindAll(@FindBy(css = "#block_top_menu>ul>li>a"))
	List<WebElement> mainCategory;

	WebElement search_query;

	WebElement submit_search;

	@FindBy(css = "a.login")
	WebElement login;

	By logout = By.cssSelector("a.logout");

	public void search(String searchText) {
		sendText(search_query, searchText);
		clickSearch();
		getWait().until(ExpectedConditions.urlContains("controller=search"));
	}

	public void clickSearch() {
		click(submit_search);

	}

	public void goToLoginPage() {

		if (driver.findElements(logout).size() > 0) {
			driver.findElements(logout).get(0).click();
		} else if (driver.getTitle().contains("Login")) {
			return;
		}

		click(login);
		getWait().until(ExpectedConditions.urlContains("controller=authentication&back=my-account"));
	}


}
