package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.print.DocFlavor;
import java.util.List;

public class HomePage extends Page{
    public HomePage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//*[@id=\"/content/header/header/catalogEntrypoint\"]")
    private WebElement catalogButton;

    @FindBy(xpath = "//*[@id=\"/content/page/fancyPage/recommendationRollTabs/recommendationRolls/remix_desktop_width_6-RecommendationRoll/content/lazyGenerator/initialContent/recomLayoutItem_21#1:7274212_2/content/content/toggleWishlist\"]/div/button")
    private WebElement addToWishlistButton;

    @FindBy(xpath = "//*[@id=\"/content/header/header/wishlistButton\"]/a")
    private WebElement whishlistButton;

    @FindBy(xpath = "//*[@id=\"HeaderSearchLightId\"]/div[1]/button")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"header-search\"]")
    private WebElement searchInput;

    @FindBy(xpath = "//*[@id=\"main_tab\"]")
    private WebElement forYouButton;

    @FindBy(xpath = "//*[@id=\"profitable_goods_tab\"]\n")
    private WebElement belowMarketButton;

    @FindBy(xpath = "//*[@id=\"/content/header/header/ordersButton\"]/a/div")
    private WebElement ordersButton;


    @FindBy(xpath = "//*[@id=\"CART_ENTRY_POINT_ANCHOR\"]/a/div")
    private WebElement cartButton;


    @FindBy(xpath = "//*[@id=\"/MarketNodeHeaderCatalog42\"]/div/div[1]/div/ul/li[5]/a/img")
    private WebElement nodeHeader;

    @FindBy(xpath = "//*[@id=\"/MarketNodeHeaderCatalog42/97014022/97014029\"]/div/div/div/div/div/div/div[2]/div[1]/div/div/a")
    private WebElement marketHeader;
//a[@href='/catalog--muzhskaia-odezhda/54404675']


    @FindBy(css = "div[data-cs-name='navigate'] a[data-auto='snippet-link']")
    private List<WebElement> goods;


    public void clickFirstGood(){
        goods.get(0).click();
    }

    public void clickMarketHeader(){
        marketHeader.click();
    }
    public void clickOrdersButton(){
        ordersButton.click();
    }

    public void clickWishlistButton(){
        whishlistButton.click();
    }
    public void clickAddToWishlistButton(){
        addToWishlistButton.click();
    }
    public void clickNodeHeader(){
        nodeHeader.click();
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

    public void clickCatalogButton(){
        catalogButton.click();
    }
    public void clickCartButton(){
        cartButton.click();
    }
}
