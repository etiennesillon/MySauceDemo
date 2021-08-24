package web.tests;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

import web.pages.DemoCartWebPage;
import web.pages.DemoLoginWebPage;
import web.pages.DemoShopWebPage;

public class MyEnd2EndTest extends TestBase {
	
	/*********************************************************************/

    private static String USERNAME = "standard_user";
    private static String PASSWD = "secret_sauce";
    
	/*********************************************************************/

    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
    	
        return new Object[][]{
        	
            new Object[]{CHROME, LATEST, ANDROID, "Google Pixel .*"},
            new Object[]{SAFARI, LATEST, IOS, "iPhone 12.*"},

            new Object[]{PERF, LATEST, MACOS, ""},
            new Object[]{PERF, LATEST, WIN10, ""},	

            new Object[]{EDGE, LATEST, WIN10, ""},
            new Object[]{IE, LATEST, WIN10, ""},
            new Object[]{FIREFOX, LATEST, WIN10, ""},

            new Object[]{SAFARI, LATEST, MACOS, ""},
            new Object[]{EDGE, LATEST, MACOS, ""},
            new Object[]{CHROME, LATEST, MACOS, ""}

        };
        
    }

	/*********************************************************************/

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void loginTest(String browser, String version, String os, String deviceName, Method method) throws MalformedURLException, InvalidElementStateException, UnexpectedException {
    	
        this.createDriver(browser, version, os, deviceName, method.getName());
        WebDriver driver = this.getWebDriver();
        
        this.annotate("Visiting Demo Login page...");
        DemoLoginWebPage loginPage = DemoLoginWebPage.visitPage(driver, 0);

        this.annotate(String.format("Login in with: \"%s\"", USERNAME));
        DemoShopWebPage shopPage = loginPage.login(USERNAME, PASSWD);

        this.annotate(String.format("Add item and go to cart", USERNAME));
        DemoCartWebPage cart = shopPage.selectItemAndGotToCart();
        
    }

}