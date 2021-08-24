package web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import web.tests.MyFailureAnalysisDemo;

public class DemoLoginWebPage extends WebPageBase {
	
    /***************************************************************/
    
    @FindBy(how = How.XPATH, using = "//*[@id=\"user-name\"]")
    private WebElement userName;
    
    @FindBy(how = How.XPATH, using = "//*[@id=\"password\"]")
    private WebElement password;
    
    @FindBy(how = How.XPATH, using = "//*[@id=\"login-button\"]") 
    private WebElement submitButton;
        
    /***************************************************************/
    
    @FindBy(how = How.XPATH, using = "//*[@id=\"user.name\"]")
    private WebElement userNameFailed;
    
    @FindBy(how = How.XPATH, using = "//*[@id=\"pass.word\"]")
    private WebElement passwordFailed;
    
    @FindBy(how = How.XPATH, using = "//*[@id=\"login.button\"]") 
    private WebElement submitButtonFailed;
        
    /***************************************************************/
    
    private static String USERNAME = "standard_user";
    private static String PASSWD = "secret_sauce";
    
	/*********************************************************************/

    public static String url = "http://www.saucedemo.com/v1";
    
    public int nFailure;
    
    /***************************************************************/
    
    public static DemoLoginWebPage visitPage(WebDriver driver, int nFailure) {
    	DemoLoginWebPage page = new DemoLoginWebPage(driver, nFailure);
        page.visitPage();
        return page;
    }

    /***************************************************************/
    
    public DemoLoginWebPage(WebDriver driver, int nFailure) {
        super(driver);
    	this.nFailure = nFailure;
        PageFactory.initElements(driver, this);
    }

    /***************************************************************/
    
    public void visitPage() {
        this.driver.get(url);
    }

    /***************************************************************/
    
    public DemoShopWebPage login() {
    	return login(USERNAME, PASSWD);
    }
    
    /***************************************************************/
    
    public DemoShopWebPage login(String userName, String password) {
    	
    	switch(nFailure) {
    	
	    	case MyFailureAnalysisDemo.ALL_GOOD:
	        	this.userName.sendKeys(userName);
	        	this.password.sendKeys(password);
	        	submitButton.click();
	    		break;
	    		
	    	case MyFailureAnalysisDemo.BAD_LOCATOR:
	        	this.userNameFailed.sendKeys(userName);
	        	this.passwordFailed.sendKeys(password);
	        	submitButtonFailed.click();
	    		break;
	    	
	    	case MyFailureAnalysisDemo.EXPIRED_PASSWORD:
	        	this.userName.sendKeys(userName);
	        	this.password.sendKeys("exipred password");
	        	submitButton.click();
	    		break;

    	}
    	
    	return new DemoShopWebPage(driver, nFailure);
    	
    }
    
}