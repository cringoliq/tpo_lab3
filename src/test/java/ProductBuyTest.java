import com.labwork.AuthUtils;
import com.labwork.DriverInit;
import com.labwork.pages.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.*;

import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductBuyTest extends BaseTest {


    private static HomePage homePage;
    private static SearchPage searchPage;
    private static CatalogPage catalogPage;
    private static ElectronicsPage electronicsPage;
    private static AuthPage authPage;
    private static CartPage cartPage;
    private static CheckoutPage checkoutPage;

    @BeforeEach
    public void setUp() {


        homePage = new HomePage(chromeDriver);
        searchPage = new SearchPage(chromeDriver);
        catalogPage = new CatalogPage(chromeDriver);
        authPage = new AuthPage(chromeDriver);
        cartPage = new CartPage(chromeDriver);
        electronicsPage = new ElectronicsPage(chromeDriver);
        checkoutPage = new CheckoutPage(chromeDriver);
        chromeDriver.get("https://market.yandex.ru/");

    }


    @Test
    @Order(1)
    public void testLoginWithEmptyCredentials() {
        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("");


        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        assertTrue(titleText.startsWith("Логин"), titleText);
    }



    @Test
    @Order(2)
    public void testLoginWithInvalidLoginCredentials() {
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String incorrectLogin = "~~~~~~~~~~~~~KFSKDF";
        authPage.enterLoginField(incorrectLogin);



        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);
        assertTrue(titleText.startsWith("Такой"), titleText);
    }

    @Test
    @Order(3)
    public void testLoginWithInvalidPasswordCredentials() {
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String incorrectLogin = "lomaniinoss";
        authPage.enterLoginField(incorrectLogin);

        authPage.enterPasswordField("90000000000");


        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-passwd:hint\"]\n")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);
        assertTrue(titleText.startsWith("Неверный"), titleText);
    }
    @Test
    @Order(4)
    public void productBuyTest() throws InterruptedException {
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();
        String login = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[0];

        authPage.enterLoginField(login);
        String password = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[1];
        authPage.enterPasswordField(password);

        // Ждем, пока пользователь введет код доступа вручную
        System.out.println("Пожалуйста, введите код доступа вручную и нажмите Enter.");

        // Ожидаем, что пользователь введет код и нажмет Enter


        Scanner scanner = new Scanner(System.in);
        String accessCode = scanner.nextLine();  // Чтение кода из консоли

        authPage.enterPhoneCodeField(accessCode);

        homePage.clickBelowMarketButton();

        ((JavascriptExecutor) chromeDriver).executeScript("document.body.style.zoom='50%'");


        WebElement firstGood = chromeDriver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));

        String originalHandle = chromeDriver.getWindowHandle();
        Set<String> oldHandles = chromeDriver.getWindowHandles();

        firstGood.click();

        chromeWait.until(d -> d.getWindowHandles().size() > oldHandles.size());

        for (String handle : chromeDriver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                chromeDriver.switchTo().window(handle);
                break;
            }
        }
        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[2]/div/button")));

        cartPage.clickInCardButton();

        String originalHandleCart = chromeDriver.getWindowHandle();
        Set<String> oldHandlesCart = chromeDriver.getWindowHandles();

        Thread.sleep(2000);
        homePage.clickCartButton();

        chromeWait.until(d -> d.getWindowHandles().size() > oldHandles.size());

        for (String handle : chromeDriver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                chromeDriver.switchTo().window(handle);
                break;
            }
        }
        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cartCheckoutButton\"]")));
        cartPage.clickCheckoutButton();

        String titleText = checkoutPage.getTitle().getText();

        assertTrue(titleText.startsWith("Оформление"), titleText);

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/notification\"]/div")));
//        cartPage.clickInCardButton();

//        cartPage.clickCheckoutButton();

//        wait.until(d -> d.getWindowHandles().size() > oldHandles.size());
//
//        for (String handle : driver.getWindowHandles()) {
//            if (!handle.equals(originalHandle)) {
//                driver.switchTo().window(handle);
//                break;
//            }
//        }


    }


}
