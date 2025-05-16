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

    @FindBy(xpath = "//*[@id=\"/content/page/fancyPage/searchContent/searchSerpStatic/content\"]/div/div")
    private WebElement searchResultsDiv;

    @FindBy(xpath = "//*[@id=\"range-filter-field-glprice_25563_max\"]")
    private WebElement priceRangeInput;

    @FindBy(xpath = "//*[@id=\"/content/page/fancyPage/searchContent/searchControls/viewTypeSelector\"]/div/div[2]/button")
    private WebElement viewTypeSelector;

    public void clickViewTypeSelector() {
        viewTypeSelector.click();
    }
    public WebElement getSearchResultsDiv(){
        return searchResultsDiv;
    }

    public void inputPriceRange(String priceRange){
        priceRangeInput.sendKeys(priceRange);
    }
    //*[@id="/marketfrontSerpLayout/serpLayoutItem_606807"]
    //*[@id="61yf5izqk9t"]/div[1]/div/div[3]/div/div[1]/div/a/div/div/span/span/span[4]

    public WebElement getSearchTitle() {
        return searchTitle;
    }
    public WebElement getEmptySearchPage() {
        return emptySearchPage;
    }


}
