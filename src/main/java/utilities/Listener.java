package utilities;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Listener implements ITestListener, IReporter, ISuiteListener {
	static String folderPath = "/errorScreenshots/";
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	String lastRun;
	String systemName;

	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();

	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	public static WebDriver driver;

	public Listener() throws IOException {
		super();

		// TODO Auto-generated constructor stub
	}

	public void onTestStart(ITestResult result) {

		ExtentTest child = ((ExtentTest) parentTest.get()).createNode(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		test.set(child);
	}

	public void onTestSuccess(ITestResult result) {
		// logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED
		// ", ExtentColor.GREEN));
		test.get().pass("Test passed");

	}

	public void onTestFailure(ITestResult result) {

		// draw a border around the found element if (driver instanceof

		try {

			test.get().fail(result.getThrowable().getMessage())
					.addScreenCaptureFromPath(capture(driver, result.getMethod().getMethodName()))
					.error(result.getThrowable());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String capture(WebDriver driver, String testName) throws IOException {
		// File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Screenshot fpScreenshot;
		if (driver.findElements(By.cssSelector("[role=dialog]")).size() == 0) {
			fpScreenshot = new AShot()
					.shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.simple(), 100))
					.takeScreenshot(driver);
		} else {
			fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.simple()).takeScreenshot(driver);
		}
		File Dest = new File(System.getProperty("user.dir") + folderPath + testName + ".png");
		try {
			ImageIO.write(fpScreenshot.getImage(), "PNG", Dest);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String errflpath = Dest.getAbsolutePath();
		// FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}

	public void cleanFolders() throws IOException {
		File currentFolder = new File(System.getProperty("user.dir") + "testResults" + "\\");

		// String destination =System.getProperty("user.dir") + "/PreviousTestResults/";
		File OutputFolder = null;
		if (currentFolder.list()!=null && currentFolder.list().length > 0) {
			for (String f : currentFolder.list()) {
				// System.out.println(f);
				OutputFolder = new File("./PreviousTestResults" + "\\" + f.replace("_ExtentReport.html", ""));
				OutputFolder.mkdir();
				File source = new File(currentFolder + "\\" + f);
				File dest = new File(OutputFolder + "\\" + f);
				Files.move(source, dest);

			}
			currentFolder = new File(System.getProperty("user.dir") + "\\errorScreenshots" + "\\");
			for (String f : currentFolder.list()) {
				// System.out.println(f);
				File source = new File(currentFolder + "\\" + f);
				File dest = new File(OutputFolder + "\\" + f);
				Files.move(source, dest);

			}
		}

		/*
		 * if (!OutputFolder.exists()) { if (OutputFolder.mkdir()) {
		 * 
		 * 
		 * 
		 * System.out.println("Directory is created!"); } else {
		 * System.out.println("Failed to create Directory"); } } else {
		 * System.out.println("Diretory already exists"); Files.move(currentFolder,
		 * OutputFolder);
		 * 
		 * }
		 */
	}

	public void onTestSkipped(ITestResult result) {

		test.get().skip("skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public static void loginfo(String text) {
		test.get().info(text);
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		logger = extent.createTest(context.getName());
		parentTest.set(logger);
	}

	public static String timestamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		try {
			cleanFolders();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			systemName = InetAddress.getLocalHost().getHostName();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/testResults/" + timestamp() + "_ExtentReport.html");// specify

		String PATH =System.getProperty("user.dir") + "/testResults/";

		File directory = new File(PATH);
		if (!directory.exists()) {
			directory.mkdir();
		}
		extent = new ExtentReports();
		htmlReporter.config().setDocumentTitle("Selenium Example");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setReportName("Test Report");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
		extent.attachReporter(htmlReporter); // of
		extent.setSystemInfo("os", System.getProperty("os.name"));
		extent.setSystemInfo("Browser", PropertyManager.getInstance().getBrowser());
		extent.setSystemInfo("System Name", systemName);

	}

	@Override
	public void onFinish(ISuite suite) {
		extent.flush();
	}
}
