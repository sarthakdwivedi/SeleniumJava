package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//**********************************************************************************************************
//Author: Sarthak Dwivedi
//Description: PropertyManager class reads global configurations and use them during test execution.
//**********************************************************************************************************
public class PropertyManager {

	private static PropertyManager instance;
	private static final Object lock = new Object();
	private static String url;
	private static String browser,excelFilePath;
	private static int waitTime;

	// Create a Singleton instance. We need only one instance of Property Manager.
	public static PropertyManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				instance = new PropertyManager();
				instance.loadData();
			}
		}
		return instance;
	}

	// Get all configuration data and assign to related fields.
	private void loadData() {
		// Declare a properties object

		Properties prop = new Properties();
		String propertyFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\configuration.properties";
		;

		// Read configuration.properties file
		try {
			prop.load(new FileInputStream(propertyFilePath));
		} catch (IOException e) {
			System.out.println("Configuration properties file cannot be found");
		}

		// Get properties from configuration.properties
		browser = prop.getProperty("browser", "chrome");
		url = prop.getProperty("applicationURL");
		waitTime = Integer.parseInt(prop.getProperty("globalWaitTime", "10"));
		excelFilePath=System.getProperty("user.dir") + prop.getProperty("excelFilePath");
	}

	public String getBaseURL() {

		return url;

	}

	public String getBrowser() {
		return browser;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public String getExcelFilePath() {
		// TODO Auto-generated method stub
		return excelFilePath;
	}

}