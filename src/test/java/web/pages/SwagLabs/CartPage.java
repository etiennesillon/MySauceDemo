package web.pages.SwagLabs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import web.pages.PageBase;

public class CartPage extends PageBase {

    /***************************************************************/
    
	private WebDriver driver;
	
    /***************************************************************/
    
    @FindBy(xpath = "//div[contains(text(),'Sauce Labs Backpack')]")
    private WebElement backpackLabel;

    @FindBy(xpath = "//div[contains(text(),'Sauce Labs Bolt T-Shirt')]")
    private WebElement boltTshirtLabel;

    @FindBy(xpath = "//div[contains(text(),'Sauce Labs Onesie')]")
    private WebElement onesieLabel;

//  @FindBy(how = How.XPATH, using = "//*[@id=\"inventory_container\"]/div/div[1]/div[3]/button")
    @FindBy(xpath =  "//*[@id=\"inventory_container\"]/div/div[1]/div[3]/button")
    private WebElement addButton;

    @FindBy(xpath =  "//*[@id=\"shopping_cart_container\"]/a")
    private WebElement cartButton;
    
	/***************************************************************/

	public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
	}
	
    /***************************************************************/
    
    public CartPage login(String userName, String password) {
    	
    	this.addButton.click();
    	
    	this.cartButton.click();
    	
    	return new CartPage(driver);
    	
    }
    
}
