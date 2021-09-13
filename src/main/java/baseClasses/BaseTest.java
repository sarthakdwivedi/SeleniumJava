package baseClasses;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import utilities.ExcelUtil;
import utilities.PropertyManager;

public class BaseTest {
	public WebDriver driver;

	@BeforeTest
	public void setup() {
		String browser = PropertyManager.getInstance().getBrowser();
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");

			setDriver((WebDriver) new ChromeDriver());
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
			
			setDriver((WebDriver) new FirefoxDriver());
		} else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/drivers/IEDriverServer.exe");

			setDriver((WebDriver) new InternetExplorerDriver());
		} else {

			System.out.println("Browser not handled. Browser should be Chrome,Firefox or IE");
			return;
		}
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		getDriver().get(PropertyManager.getInstance().getBaseURL());
		
	}

	@DataProvider(name="testCaseData")
	public Object[][] getDataForTestCase(ITestContext context, Method method) {
	 
		//System.out.println(method.getName()+":"+ context.getName());
		return ExcelUtil.getData(method.getName(), context.getName());

	}

	@AfterTest
	public void cleanup() {
		getDriver().quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
