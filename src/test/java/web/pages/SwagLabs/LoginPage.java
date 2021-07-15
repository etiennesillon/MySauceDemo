package web.pages.SwagLabs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import web.pages.PageBase;

public class LoginPage extends PageBase {
	
    /***************************************************************/
    
    @FindBy(xpath = "//*[@id=\"user-name\"]")
    private WebElement userName;
    
    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement password;
    
    @FindBy(xpath = "//*[@id=\"login-button\"]")
    private WebElement submitButton;
    
    @FindBy(xpath = "//input[@placeholder='Username']") // enable to have tests pass
    private WebElement usernameTextBox;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement lockedOutMessage;

    @FindBy(xpath = "//pre[@id='login_credentials']")
    private WebElement loginCredentials;

        
    /***************************************************************/
    
    public static String url = "http://www.saucedemo.com";
    
    public boolean isFail;

    /***************************************************************/
    
    public static LoginPage visitPage(WebDriver driver, boolean isFail) {
    	LoginPage page = new LoginPage(driver, isFail);
        page.visitPage();
        return page;
    }

    /***************************************************************/
    
    public LoginPage(WebDriver driver, boolean isFail) {
        super(driver);
    	this.isFail = isFail;
        PageFactory.initElements(driver, this);
    }

    /***************************************************************/
    
    public void visitPage() {
        this.driver.get(url);
    }

    /***************************************************************/
    
    public ShopPage login(String userName, String password) {
    	
    	if(isFail) {
    		password = "failed";
    	}
    	
    	this.userName.sendKeys(userName);
    	this.password.sendKeys(password);
    	
    	submitButton.click();
    	
    	return new ShopPage(driver);
    	
    }
    
}