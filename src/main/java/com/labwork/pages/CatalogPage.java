package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CatalogPage extends Page {

    public CatalogPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /* корневая панель каталога */
    @FindBy(xpath = "//*[@id=\"/MarketNodeHeaderCatalog42\"]/div")
    private WebElement catalogPanel;

    /* пункт «Электроника» */
    @FindBy(xpath = "//*[@id=\"/MarketNodeHeaderCatalog42\"]/div/div[1]/div/ul/li[5]/a")
    private WebElement electronicsLink;

    public WebElement getCatalogPanel()    { return catalogPanel; }
    public WebElement getElectronicsLink() { return electronicsLink; }

    public void openElectronics() {
        electronicsLink.click();
    }
}
