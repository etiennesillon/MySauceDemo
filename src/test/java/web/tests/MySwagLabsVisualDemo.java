package web.tests;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.MyTestUtils;
import web.pages.SwagLabs.CartPage;
import web.pages.SwagLabs.LoginPage;
import web.pages.SwagLabs.ShopPage;

public class MySwagLabsVisualDemo extends VisualTestBase {
	
	private static final String BUILD_NAME = "My SwagLabs Visual Demo "  + MyTestUtils.getDateTime();
	
	/*********************************************************************/

    private static String USERNAME = "standard_user";
    private static String PASSWD = "secret_sauce";
    
    private static final boolean IS_FAIL = false;
    
	/*********************************************************************/

    @DataProvider(name = "Browsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
    	
        return new Object[][]{
        	
            new Object[]{IE, LATEST, WIN10, RES1280WIN, true},
            new Object[]{EDGE, LATEST, WIN10, RES1280WIN, IS_FAIL},
            new Object[]{FIREFOX, LATEST, WIN10, RES1280WIN, IS_FAIL},
            new Object[]{CHROME, LATEST, WIN10, RES1280WIN, IS_FAIL},
            new Object[]{CHROME, LATEST, MACOS, RES1280MAC, IS_FAIL},
            new Object[]{SAFARI, LATEST, MACOS, RES1280MAC, IS_FAIL},
            new Object[]{FIREFOX, LATEST, MACOS, RES1280WIN, IS_FAIL},

            new Object[]{CHROME, LATEST, MACOS, RES_IPHONE12, IS_FAIL},
            new Object[]{SAFARI, LATEST, MACOS, RES_IPHONE12, IS_FAIL},
            new Object[]{CHROME, LATEST, WIN10, RES_GALAXYS21, IS_FAIL},

        };
        
    }

	/*********************************************************************/

    @Test(dataProvider = "Browsers")
  
    public void loginTest(String browser, String version, String os, String viewPort, boolean isFail, Method method) throws MalformedURLException, InvalidElementStateException, UnexpectedException {
    	
        this.createDriver(VisualTestBase.US_DC, BUILD_NAME, "My SwagLabs Demo", browser, version, os, viewPort, false, false, true);
        WebDriver driver = this.getWebDriver();
        
        this.annotate("Visiting Demo Login page...");
        LoginPage loginPage = LoginPage.visitPage(driver, false);

        this.takeScreenshot("Login Page");

        this.annotate(String.format("Loging in with: \"%s\"", USERNAME));
        ShopPage shopPage = loginPage.login(USERNAME, PASSWD);

        if(isFail) {
//        	this.runScript("document.querySelector(\"div.inventory_item_img\").innerHTML = \"<img alt='Sauce Labs Backpack' class='inventory_item_img' src='/static/media/sauce-backpack-1200x1500.34e7aa42.jpg'>\"");
//        	this.runScript("document.querySelectorAll('.inventory_item_img').forEach(function (item) {item.src = 'https://wrongurlink/image.png'})");
        	this.runScript("document.querySelector(\"div.inventory_item_price\").innerHTML = '$30.00'");
        	this.runScript("document.querySelector(\"div.inventory_item_name\").style.textAlign = 'right'");
        	this.runScript("document.querySelector(\"div.inventory_item_desc\").style.color = 'red'");
        	this.runScript("var node = document.createElement('P');node.innerHTML = 'OOOOPS!';document.getElementById('header_container').appendChild(node);");
        }
        
        this.takeScreenshot("Shop Page");

        this.annotate(String.format("Adding item and go to cart", USERNAME));
        CartPage cart = shopPage.selectItemAndGotToCart();
        
        this.takeScreenshot("Cart Page");

    }

}