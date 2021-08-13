package web.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MySimpleVisualDemo {

	public static void main(String[] args) throws MalformedURLException {
		
	    String username = System.getenv("SAUCE_USERNAME");
	    String accesskey = System.getenv("SAUCE_ACCESS_KEY");
	    String screenerApiKey = System.getenv("SCREENER_API_KEY");

	    MutableCapabilities sauceOptions = new MutableCapabilities();
	    sauceOptions.setCapability("username", username);
	    sauceOptions.setCapability("accesskey", accesskey);
	    sauceOptions.setCapability("name", "MySimpleVisualDemo");
	    
	    MutableCapabilities screenerOptions = new MutableCapabilities();
	    screenerOptions.setCapability("structure", new Boolean(true));
	    screenerOptions.setCapability("layout", new Boolean(true));
	    screenerOptions.setCapability("style", new Boolean(true));
	    screenerOptions.setCapability("content", new Boolean(true));

	    MutableCapabilities sauceVisual = new MutableCapabilities();
	    sauceVisual.setCapability("apiKey", screenerApiKey);
	    sauceVisual.setCapability("projectName", "MySimpleVisualDemo");
	    sauceVisual.setCapability("viewportSize", "1280x1024");
	    sauceVisual.setCapability("diffOptions", screenerOptions);
	    sauceVisual.setCapability("ignore", "#some-id, .some-selector");
	    
	    DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");
        capabilities.setCapability(CapabilityType.VERSION, "latest");
        capabilities.setCapability("name", "MySimpleVisualDemo");
	    capabilities.setCapability("sauce:options", sauceOptions);
	    capabilities.setCapability("sauce:visual", sauceVisual);
	    
	    WebDriver driver = new RemoteWebDriver(new URL("https://hub.screener.io:443/wd/hub"), capabilities);
	    
	    driver.get("https://www.google.com/");
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("/*@visual.init*/", "My Visual Test");
	    js.executeScript("/*@visual.snapshot*/", "Home");
	    
	    driver.quit();
	    
	}

}
