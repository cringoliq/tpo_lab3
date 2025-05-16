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

import java.time.Duration;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductBuyInstantTest extends BaseTest{
/*
    private HomePage homePageChrome;
    private SearchPage searchPageChrome;
    private CatalogPage catalogPageChrome;
    private ElectronicsPage electronicsPageChrome;
    private AuthPage authPageChrome;
    private CartPage cartPageChrome;
    private CheckoutPage checkoutPageChrome;

    private HomePage homePageFirefox;
    private SearchPage searchPageFirefox;
    private CatalogPage catalogPageFirefox;
    private ElectronicsPage electronicsPageFirefox;
    private AuthPage authPageFirefox;
    private CartPage cartPageFirefox;
    private CheckoutPage checkoutPageFirefox;


    @BeforeEach
    public void setUp() {


        // Инициализация страниц для Chrome
        homePageChrome = new HomePage(chromeDriver);
        searchPageChrome = new SearchPage(chromeDriver);
        catalogPageChrome = new CatalogPage(chromeDriver);
        authPageChrome = new AuthPage(chromeDriver);
        cartPageChrome = new CartPage(chromeDriver);
        electronicsPageChrome = new ElectronicsPage(chromeDriver);
        checkoutPageChrome = new CheckoutPage(chromeDriver);

        // Инициализация страниц для Firefox
        homePageFirefox = new HomePage(firefoxDriver);
        searchPageFirefox = new SearchPage(firefoxDriver);
        catalogPageFirefox = new CatalogPage(firefoxDriver);
        authPageFirefox = new AuthPage(firefoxDriver);
        cartPageFirefox = new CartPage(firefoxDriver);
        electronicsPageFirefox = new ElectronicsPage(firefoxDriver);
        checkoutPageFirefox = new CheckoutPage(firefoxDriver);

        // Открываем страницу в обоих браузерах
        chromeDriver.get("https://market.yandex.ru/");
        firefoxDriver.get("https://market.yandex.ru/");






//*[@id="passp-field-otp"]
    }


    @Test
    public void productBuyTest2() throws InterruptedException {
        //runTestInBrowser(chromeDriver, chromeWait, authPageChrome, homePageChrome, cartPageChrome, checkoutPageChrome);
        runTestInBrowser(firefoxDriver, firefoxWait, authPageFirefox, homePageFirefox, cartPageFirefox, checkoutPageFirefox);
    }
    private void runTestInBrowser(WebDriver driver,
                                  org.openqa.selenium.support.ui.WebDriverWait wait,
                                  AuthPage authPage,
                                  HomePage homePage,
                                  CartPage cartPage,
                                  CheckoutPage checkoutPage) throws InterruptedException {
        Thread.sleep(5000);
        handleCaptchaIfPresent(driver);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/header/div[1]/div/div/noindex/nav/ul/li[5]/div/div/div/div/div/a\n")
        ));
        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String login = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[0];
        authPage.enterLoginField(login);

        String password = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[1];
        authPage.enterPasswordField(password);

        System.out.println("Пожалуйста, введите код доступа вручную и нажмите Enter.");
        Scanner scanner = new Scanner(System.in);
        String accessCode = scanner.nextLine();
        authPage.enterPhoneCodeField(accessCode);
        Thread.sleep(2000);
        homePage.clickBelowMarketButton();

        WebElement firstGood = driver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));

        String originalHandle = driver.getWindowHandle();
        Set<String> oldHandles = driver.getWindowHandles();

        firstGood.click();

        wait.until(d -> d.getWindowHandles().size() > oldHandles.size());

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[1]/button[1]")));

        cartPage.clickInstantBuyButton();

        String titleText = checkoutPage.getTitle().getText();

        assertTrue(titleText.startsWith("Оформление"), titleText);

        // Закрываем дополнительное окно и возвращаемся на исходное
        driver.close();
        driver.switchTo().window(originalHandle);
    }
    private void handleCaptchaIfPresent(WebDriver driver) {

        if (isCaptchaPage(driver)) {
            System.out.println("Обнаружена капча. Пожалуйста, решите её вручную и нажмите Enter...");
            new Scanner(System.in).nextLine(); // Ждем ручного подтверждения

        }
    }

    private boolean isCaptchaPage(WebDriver driver) {
        String url = driver.getCurrentUrl().toLowerCase();
        String source = driver.getPageSource().toLowerCase();
        return url.contains("showcaptcha");
    }


/*
        @Test
        public void productBuyTest() throws InterruptedException {
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);
//
 //        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
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

        // Вводим код
        Scanner scanner = new Scanner(System.in);
        String accessCode = scanner.nextLine();  // Чтение кода из консоли

        authPage.enterPhoneCodeField(accessCode);

        homePage.clickBelowMarketButton();



        WebElement firstGood = driver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));

        String originalHandle = driver.getWindowHandle();
        Set<String> oldHandles = driver.getWindowHandles();

        firstGood.click();

        wait.until(d -> d.getWindowHandles().size() > oldHandles.size());

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[1]/button[1]")));

        cartPage.clickInstantBuyButton();

        String titleText = checkoutPage.getTitle().getText();

        assertTrue(titleText.startsWith("Оформление"), titleText);
         }
 */
}


