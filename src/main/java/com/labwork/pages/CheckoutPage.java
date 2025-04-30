package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends Page{
    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //*[@id="/content/page/title"]/h1
    @FindBy(xpath = "//*[@id=\"/content/page/title\"]/h1")
    private WebElement title;

    public WebElement getTitle() {
        return title;
    }
}
