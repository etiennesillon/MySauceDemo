package web.pages.SwagLabs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import web.pages.PageBase;

public class ShopPage extends PageBase {

    /***************************************************************/
    
	private WebDriver driver;
	
    /***************************************************************/
    
    @FindBy(xpath = "//*[@id=\"inventory_container\"]/div/div[1]/div[3]/button")
    private WebElement addButton;

    @FindBy(xpath = "//*[@id=\"shopping_cart_container\"]/a")
    private WebElement cartButton;
    
    @FindBy(className = "product_label")
    private WebElement productInventory;  

    @FindBy(css = "#inventory_container > div > div:nth-child(1) > div.pricebar > button")
    private WebElement addToCartBackpackButton;
    
    @FindBy(css = "#inventory_container > div > div:nth-child(3) > div.pricebar > button")
    private WebElement addToCartBoltTshirtButton;  
    
    @FindBy(css = "#inventory_container > div > div:nth-child(5) > div.pricebar > button")
    private WebElement addToCartOnesieButton;  
    
    @FindBy(xpath = "//div//div[@class='inventory_list']//div[6]//div[3]//button[1]")
    private WebElement addToCartTshirtRedButton;
    
    @FindBy(css = "#inventory_container > div > div:nth-child(4) > div.pricebar > button")
    private WebElement addToCartFleeceJacketButton;
    
    @FindBy(xpath = "//div//div[@class='inventory_list']//div[2]//div[3]//button[1]")
    private WebElement addToCartBikeLightButton;
    
    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    private WebElement logoutLink; 
    
    @FindBy(xpath = "//button[contains(text(),'Open Menu')]")
    private WebElement menuButton; 

	/***************************************************************/

	public ShopPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
	}
	
    /***************************************************************/
    
    public CartPage selectItemAndGotToCart() {
    	
    	this.addButton.click();
    	
    	this.cartButton.click();
    	
    	return new CartPage(driver);
    	
    }
    
}
