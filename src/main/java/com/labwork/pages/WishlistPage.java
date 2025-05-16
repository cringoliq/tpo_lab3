package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WishlistPage extends Page{
    public WishlistPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"searchSection\"]")
    private WebElement wishListDiv;
//*[@id="/content/page/fancyPage/layout/content/content/lazyGenerator/initialContent/103654322705_918506-1-wishlist-item"]/div/div[1]/div/div[2]/div[1]/div/a/span


    public WebElement getWishListDiv() {
        return wishListDiv;
    }
}
