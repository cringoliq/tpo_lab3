package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Page{
    public HomePage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }


    @FindBy(xpath = "//*[@id=\"HeaderSearchLightId\"]/div[1]/button")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"header-search\"]")
    private WebElement searchInput;

    @FindBy(xpath = "//*[@id=\"main_tab\"]")
    private WebElement forYouButton;

    @FindBy(xpath = "//*[@id=\"profitable_goods_tab\"]")
    private WebElement belowMarketButton;

    @FindBy(xpath = "//*[@id=\"/content/header/header/ordersButton\"]/a/div")
    private WebElement ordersButton;


    @FindBy(xpath = "//*[@id=\"CART_ENTRY_POINT_ANCHOR\"]/a/div")
    private WebElement cartButton;

    public void clickOrdersButton(){
        ordersButton.click();
    }


    public void clickForYouButton() {
        forYouButton.click();
    }
    public void clickBelowMarketButton() {
        belowMarketButton.click();
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public void enterSearchInput(String text) {
        searchInput.click();
        searchInput.sendKeys(text);
    }
}
