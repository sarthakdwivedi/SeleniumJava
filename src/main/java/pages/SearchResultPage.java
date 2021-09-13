package pages;

import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseClasses.BasePage;

public class SearchResultPage extends BasePage {

	public SearchResultPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "p.alert")
	WebElement errorMessage;

	By productName = By.cssSelector("ul.product_list a.product-name");

	public String getErrorMessage() {
		return errorMessage.getText().trim();
	}

	public String getProductNames() {
		return driver.findElements(productName).stream().parallel().map(e -> e.getText().trim())
				.collect(Collectors.joining(""));
	}
}
