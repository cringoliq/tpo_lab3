package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage extends Page{

    public SearchPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//*[@id=\"/content/page/fancyPage/searchContent/searchTitleWithBreadcrumbs\"]/div/div/div/h1")
    private WebElement searchTitle;
    @FindBy(xpath = "//*[@id=\"/content/page/fancyPage/marketNotFoundEntrypoint/emptySearchPage\"]/div/div/div/h1")
    private WebElement emptySearchPage;


    public WebElement getSearchTitle() {
        return searchTitle;
    }
    public WebElement getEmptySearchPage() {
        return emptySearchPage;
    }


}
