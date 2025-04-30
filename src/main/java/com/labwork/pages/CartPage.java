package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends Page{
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[2]/div/button")
    private WebElement inCardButton;
    @FindBy(xpath = "//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[1]/button[1]")
    private WebElement instantBuyButton;
    @FindBy(xpath= "//*[@id=\"cartCheckoutButton\"]")
    private WebElement checkoutButton;
    public void clickInCardButton(){
        inCardButton.click();
    }
    public void clickCheckoutButton(){
        checkoutButton.click();
    }
    public void clickInstantBuyButton(){
        instantBuyButton.click();
    }
}
