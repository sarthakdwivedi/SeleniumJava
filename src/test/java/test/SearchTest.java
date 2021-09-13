package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import baseClasses.BasePage;
import baseClasses.BaseTest;
import pages.HomePage;
import pages.SearchResultPage;

public class SearchTest extends BaseTest {

	
	@BeforeTest
	public void navigateToHomePage() {
		BasePage page=new BasePage(getDriver());
		page.goToHomePage();
	}
	
	@Test(description="Verify error on blank Search")
	public void verifyErrorOnBlankSearch() {
		HomePage homePage=new HomePage(getDriver());
		homePage.clickSearch();
		SearchResultPage searchResultPage=new SearchResultPage(getDriver());
		assertEquals(searchResultPage.getErrorMessage(), "Please enter a search keyword","Error message for blank search is incorrect");;
	}

	@Test(dataProvider="testCaseData",description="Verify error on invalid Search")
	public void verifyErrorOnInvalidSearch(String productName) {
		HomePage homePage=new HomePage(getDriver());
		homePage.search(productName);
		SearchResultPage searchResultPage=new SearchResultPage(getDriver());
		assertEquals(searchResultPage.getErrorMessage(), "No results were found for your search \""+productName+"\"","Error message for invalid search is incorrect");;
	}

	@Test(dataProvider="testCaseData",description="Verify valid Search")
	public void verifyProductSearch(String productName) {
		HomePage homePage=new HomePage(getDriver());
		homePage.search(productName);
		SearchResultPage searchResultPage=new SearchResultPage(getDriver());
		searchResultPage.getProductNames();
		assertTrue(searchResultPage.getProductNames().contains(productName), "Product is not present in the results");
	}
}
