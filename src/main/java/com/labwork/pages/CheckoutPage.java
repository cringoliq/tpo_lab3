package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends Page{
    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
