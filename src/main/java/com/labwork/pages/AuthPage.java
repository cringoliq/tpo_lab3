package com.labwork.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthPage extends Page {
    public AuthPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }



    @FindBy(xpath = "//*[@id=\"passp:exp-register\"]")
    private WebElement moreWaysButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[2]/div[3]/div/div/div/div/form/div/div[3]/div[2]/div/div[2]/div/div/ul/li[3]/button")
    private WebElement byLoginButton;


    @FindBy(xpath = "//*[@id=\"passp-field-login\"]")
    private WebElement loginField;

    @FindBy(xpath = "//*[@id=\"passp-field-passwd\"]")
    private WebElement passwordField;
    @FindBy(xpath = "//*[@id=\"passp:sign-in\"]")
    private WebElement signInButton;
    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[2]/div[3]/div/div/div/div/div[4]/button")
    private WebElement nextButton;

    @FindBy(xpath = "//*[@id=\"passp-field-otp\"]")
    private WebElement phoneCodeField;


    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[2]/div[3]/div/div/div/div/div[4]/div/button")
    private WebElement codeVariationsButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[2]/div[3]/div/div/div/div/div[4]/div/div/ul/li[2]/button")
    private WebElement codeBySmsButton;

    public void clickCodeBySmsButton() {
        codeBySmsButton.click();
    }

    public void clickMoreWaysButton() {
        moreWaysButton.click();
    }
    public void clickByLoginButton() {
        byLoginButton.click();
    }

    public void enterLoginField(String login) {
        loginField.sendKeys(login);
        signInButton.click();
    }
    public void enterPasswordField(String password)  {
        passwordField.sendKeys(password);
        signInButton.click();
    }

    public void enterLongPasswordField(String password) throws InterruptedException {
        passwordField.sendKeys(password);
        Thread.sleep(3000);
        signInButton.click();
    }
    public void enterPhoneCodeField(String phone) {
        phoneCodeField.sendKeys(phone);
    }

    public void clickByCodeVariationsButton() {
        codeVariationsButton.click();
    }

//*[@id="root"]/div/div[2]/div[2]/div/div/div[2]/div[3]/div/div/div/div/div[4]/div/button
}
