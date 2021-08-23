package web.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

public class VisualTestBase  {
	
	/*********************************************************************/

    public static String username = System.getenv("SAUCE_USERNAME");

    public static String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    
    public static String screenerkey = System.getenv("SCREENER_API_KEY");
    
	/*********************************************************************/

	public static final int US_DC=0, APAC_DC=1;
	public static final String[] SAUCE_URLS = {"https://ondemand.saucelabs.com/wd/hub", "https://ondemand.apac-southeast-1.saucelabs.com/wd/hub"};

	public static final String SAUCE_VISUAL_URL = 	"https://hub.screener.io:443/wd/hub";

	/*********************************************************************/

	public static final String  EDGE = "MicrosoftEdge", 
								IE = "internet explorer",
								FIREFOX = "firefox",
								CHROME = "chrome",
								SAFARI = "safari";
	
	public static final String  MACOS = "macOS 10.15",
								WIN10 = "Windows 10";

	public static final String  LATEST = "latest",
								PERF = "perf",
								DEBUG = "debug";
	
	public static final String  RES1280WIN = "1280x1024",
								RES1280MAC = "1280x960",
								RES1024 = "1024x768",
								RES_IPHONE12 = "390x844",
								RES_IPHONEX = "375x812",
								RES_GALAXYS21 = "384x854";
	 
	/*********************************************************************/

    public static final String DEFAULT_SELENIUM_VERSION = "3.141.59";
	public static final String DEFAULT_BROWSER = CHROME;
	public static final String DEFAULT_BROWSER_VERSION = LATEST;
	public static final String DEFAULT_OS = WIN10;
	public static final String DEFAULT_VIEWPORT = RES1280WIN;

	/*********************************************************************/

	private static final long SLEEP = 3000;
	
	/*********************************************************************/

	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    private ThreadLocal<String> config = new ThreadLocal<String>();

	/*********************************************************************/

    public WebDriver getWebDriver() {
        return webDriver.get();
    }

	/*********************************************************************/
    
    public String getSessionId() {
        return sessionId.get();
    }

	/*********************************************************************/

    protected void createDriver(int dcId, String buildName, String testName) throws MalformedURLException, UnexpectedException {
    	createDriver(dcId, buildName, testName, DEFAULT_BROWSER, DEFAULT_BROWSER_VERSION, DEFAULT_OS, DEFAULT_VIEWPORT, false, false, false);
    }
    	
	/*********************************************************************/

    protected void createDriver(int dcId, String buildName, String testName, String browser, String version, String os, String viewportSize, boolean isPerf, boolean isSauceConnect, boolean isVisual) throws MalformedURLException, UnexpectedException {
    	
        config.set(browser + "/" +  version+ "/" +  os+ "/" +  viewportSize);
        
        MutableCapabilities sauceOpts = new MutableCapabilities();
        
        if(!isVisual && !isPerf) {
        	sauceOpts.setCapability("screenResolution", viewportSize);
        	testName += " " + viewportSize;
        }
        sauceOpts.setCapability("username", username);
        sauceOpts.setCapability("accessKey", accesskey);
        sauceOpts.setCapability("seleniumVersion", DEFAULT_SELENIUM_VERSION);
        sauceOpts.setCapability("build", buildName);
        sauceOpts.setCapability("name", testName);

        
        if (isPerf && browser.equals(CHROME)) {
        	sauceOpts.setCapability("capturePerformance", true);
        	sauceOpts.setCapability("extendedDebugging", true);
        } 
        
        if(isSauceConnect) {
        	sauceOpts.setCapability("tunnelIdentifier", "etiennesil_tunnel_id");
        }

        Capabilities options = null;

		MutableCapabilities sauceVisual = null;
        if(isVisual) {
    		sauceVisual = new MutableCapabilities();
    		sauceVisual.setCapability("apiKey", screenerkey); //"1e8eb185-94ca-4b16-be53-70a51191161d");
    		sauceVisual.setCapability("projectName", testName);

    		sauceVisual.setCapability("viewportSize", viewportSize);
    		
        }
        
        if(browser.equals(CHROME)) {

        	ChromeOptions specificOpts = new ChromeOptions();
        	
            specificOpts.setCapability("browserName", browser);
            specificOpts.setCapability("browserVersion", version);
            specificOpts.setCapability("platformName", os);
            specificOpts.setCapability("sauce:options", sauceOpts);
            
            if(sauceVisual != null) {
        		specificOpts.setCapability("sauce:visual", sauceVisual);
            }
            options = specificOpts;
            
        } else if(browser.equals(SAFARI)) {

        	SafariOptions specificOpts = new SafariOptions();
        	
            specificOpts.setCapability("browserName", browser);
            specificOpts.setCapability("browserVersion", version);
            specificOpts.setCapability("platformName", os);
            specificOpts.setCapability("sauce:options", sauceOpts);
            if(sauceVisual != null) {
        		specificOpts.setCapability("sauce:visual", sauceVisual);
            }
            options = specificOpts;
            
        } else if(browser.equals(FIREFOX)) {

        	FirefoxOptions specificOpts = new FirefoxOptions();
        	
            specificOpts.setCapability("browserName", browser);
            specificOpts.setCapability("browserVersion", version);
            specificOpts.setCapability("platformName", os);
            specificOpts.setCapability("sauce:options", sauceOpts);
            if(sauceVisual != null) {
        		specificOpts.setCapability("sauce:visual", sauceVisual);
            }
            options = specificOpts;
            
        } else if(browser.equals(IE)) {

        	InternetExplorerOptions specificOpts = new InternetExplorerOptions();
        	
            specificOpts.setCapability("browserName", browser);
            specificOpts.setCapability("browserVersion", version);
            specificOpts.setCapability("platformName", os);
            specificOpts.setCapability("sauce:options", sauceOpts);
            if(sauceVisual != null) {
        		specificOpts.setCapability("sauce:visual", sauceVisual);
            }
            options = specificOpts;
            
        } else if(browser.equals(EDGE)) {

        	FirefoxOptions specificOpts = new FirefoxOptions();
        	
            specificOpts.setCapability("browserName", browser);
            specificOpts.setCapability("browserVersion", version);
            specificOpts.setCapability("platformName", os);
            specificOpts.setCapability("sauce:options", sauceOpts);
            if(sauceVisual != null) {
        		specificOpts.setCapability("sauce:visual", sauceVisual);
            }
            options = specificOpts;
            
        }
        
        if(isVisual) {
    		webDriver.set(new RemoteWebDriver(new URL(SAUCE_VISUAL_URL), options));
        	startVisualTest(testName);
        } else {
            URL url = new URL(SAUCE_URLS[dcId]);
            webDriver.set(new RemoteWebDriver(url, options));
        }

        String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        sessionId.set(id);
        
    }

	/*********************************************************************/

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
    	
    	WebDriver driver = webDriver.get();
    	String sessId = sessionId.get();
    	String confId = config.get();
    	
    	System.out.println("tearDown(): driver="+driver + " / sessId="+sessId + " / confId="+confId);
    	
    	try {
            ((JavascriptExecutor) webDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            webDriver.get().quit();
    	} catch(Exception ex) {
        	System.out.println("Caught exception in tearDown(): driver="+driver + " / sessId="+sessId + " / confId="+confId);
        	System.out.println(ex.getMessage());
    	}
    }

	/*********************************************************************/

    protected void annotate(String text) {
        ((JavascriptExecutor) webDriver.get()).executeScript("sauce:context=" + text);
    }
    
	/*********************************************************************/

    protected void startVisualTest(String label) {
        ((JavascriptExecutor) webDriver.get()).executeScript("/*@visual.init*/", label);
    }
    
	/*********************************************************************/

    protected void takeScreenshot(String label, Map<String, Object> arguments) {
        try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
		}
        if(arguments == null) {
            ((JavascriptExecutor) webDriver.get()).executeScript("/*@visual.snapshot*/", label);
        } else {
            ((JavascriptExecutor) webDriver.get()).executeScript("/*@visual.snapshot*/", label, arguments);
        }
    }    
	
    protected void takeScreenshot(String label) { takeScreenshot(label, null); }
    
	/*********************************************************************/

    protected void runScript(String script) {
        try {
			Thread.sleep(SLEEP);
		} catch (InterruptedException e) {
		}
        ((JavascriptExecutor) webDriver.get()).executeScript(script);
    }    
	
	/*********************************************************************/

    protected void stopLog() {
        ((JavascriptExecutor) webDriver.get()).executeScript("sauce: disable log" );
    }

	/*********************************************************************/

    protected void startLog() {
        ((JavascriptExecutor) webDriver.get()).executeScript("sauce: enable log" );
    }
}
