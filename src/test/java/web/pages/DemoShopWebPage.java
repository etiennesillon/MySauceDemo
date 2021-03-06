package web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import web.tests.MyFailureAnalysisDemo;

public class DemoShopWebPage extends WebPageBase {

    /***************************************************************/
    
	private WebDriver driver;
	
    /***************************************************************/
    
    @FindBy(how = How.XPATH, using = "//*[@id=\"inventory_container\"]/div/div[1]/div[3]/button")
    private WebElement addButton;

    @FindBy(how = How.XPATH, using = "//*[@id=\"inventory_container\"]/div/div[1]/div[3]/addtocart")
    private WebElement addButtonFailed;

    @FindBy(how = How.XPATH, using = "//*[@id=\"shopping_cart_container\"]/a")
    private WebElement cartButton;

    /***************************************************************/
    
    public int nFailure;
    
	/***************************************************************/

	public DemoShopWebPage(WebDriver driver, int nFailure) {
        super(driver);
    	this.nFailure = nFailure;
        PageFactory.initElements(driver, this);
	}
	
    /***************************************************************/
    
    public DemoCartWebPage selectItemAndGotToCart() {
    	
    	if(nFailure == MyFailureAnalysisDemo.BAD_DATA) {
        	this.addButtonFailed.click();
    	} else {
        	this.addButton.click();
    	}
    	
    	this.cartButton.click();
    	
    	return new DemoCartWebPage(driver);
    	
    }
    
}
