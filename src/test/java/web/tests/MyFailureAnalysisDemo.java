package web.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import web.pages.DemoCartWebPage;
import web.pages.DemoLoginWebPage;
import web.pages.DemoShopWebPage;

public class MyFailureAnalysisDemo extends TestBase implements Runnable {
	
	/*********************************************************************/

	private String browser;
	private String version;
	private String os;
	private String testName;
	
	/*********************************************************************/

    public static final int EXCEPTION=-1, ALL_GOOD=0, BAD_LOCATOR=1, EXPIRED_PASSWORD=2, BAD_DATA=3;

    public int nFailure = ALL_GOOD;
        
	/*********************************************************************/

    private static String USERNAME = "standard_user";
    private static String PASSWD = "secret_sauce";
    

	public static final String[][] CONFIGS = {
        {IE, LATEST, WIN10},
        {EDGE, LATEST, WIN10},
        {FIREFOX, LATEST, WIN10},
        {SAFARI, LATEST, MACOS},
        {EDGE, LATEST, MACOS},
        {CHROME, LATEST, MACOS}
    };

	/*********************************************************************/

	public static final String[] TESTS = {
			"test this",
			"test that",
			"test that too",
			"check for this",
			"check for that",
			"check for that too",
			"and this one too"
	};

	/*********************************************************************/

    public static void main(String args[]) {
    	
    	for(String[] config : CONFIGS) {
    		for(String testName : TESTS) {
        		MyFailureAnalysisDemo demo = new MyFailureAnalysisDemo(config[0], config[1], config[2], testName);
        		(new Thread(demo)).start();
    		}
    	}
    	
    }
 
	/*********************************************************************/

	public MyFailureAnalysisDemo(String browser, String version, String os, String testName) {
		
		this.browser = browser;
		this.version = version;
		this.os = os;
		this.testName = testName;
		
		if(browser.equals(IE)) {
			nFailure = BAD_LOCATOR;
		} else if(testName.equals("test this")) {
			nFailure = EXPIRED_PASSWORD;
		} else if(testName.equals("test that")) {
			nFailure = BAD_DATA;
		}
		
	}
	
	/*********************************************************************/

    public void run() {
    	
        System.out.println("Running " + testName + " on " + browser + " " + version + " running on " + os);
        
        try {
        	
			this.createDriver(browser, version, os, "", testName);
	        WebDriver driver = this.getWebDriver();
	        
	        runTest(driver);
	        
	        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (nFailure == ALL_GOOD ? "passed" : "failed"));
	        
	        driver.quit();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
 
	/*********************************************************************/

    public void runTest(WebDriver driver) {
    	
        System.out.println("Running " + testName + " on " + browser + " " + version + " running on " + os);
        
        try {
        	
	        this.annotate("Visiting Demo Login page ...");
	        DemoLoginWebPage loginPage = DemoLoginWebPage.visitPage(driver, nFailure);

	        this.annotate(String.format("Login in with: " + USERNAME));
	        DemoShopWebPage shopPage = loginPage.login(USERNAME, PASSWD);
	        
	        DemoCartWebPage cartPage = shopPage.selectItemAndGotToCart();
	        
		} catch (Exception e) {
			nFailure = EXCEPTION;
			e.printStackTrace();
		}

    }
 
}
