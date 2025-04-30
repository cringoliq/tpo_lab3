package com.labwork.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ElectronicsPage extends Page {

    public ElectronicsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /* все реальные карточки товара в разделе */
    @FindBy(css = "div[data-cs-name='navigate'] a[data-auto='snippet-link']")
    private List<WebElement> goods;

    public List<WebElement> getGoods() { return goods; }

    /** кликает случайный видимый товар */
    public void chooseRandomGood() {
        List<WebElement> visibles = goods.stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());

        if (visibles.isEmpty())
            throw new NoSuchElementException("Карточки товара не прогрузились");

        WebElement random = visibles.get(new Random().nextInt(visibles.size()));
        ((JavascriptExecutor) webDriver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", random);
        random.click();
    }
}
