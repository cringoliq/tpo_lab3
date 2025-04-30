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

import java.util.Scanner;
import java.util.Set;

public class ProductBuyTest {
    private static DriverInit driverInit;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;
    private static Actions actions;

    private static HomePage homePage;
    private static SearchPage searchPage;
    private static CatalogPage catalogPage;
    private static ElectronicsPage electronicsPage;
    private static AuthPage authPage;
    private static CartPage cartPage;

    @BeforeEach
    public void setUp() {
        driverInit = new DriverInit();
        driverInit.setupDriver();
        driver = driverInit.getDriver();
        wait = driverInit.getWait();
        js = driverInit.getJs();

        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        catalogPage = new CatalogPage(driver);
        authPage = new AuthPage(driver);
        cartPage = new CartPage(driver);
        electronicsPage = new ElectronicsPage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void productBuyTest() throws InterruptedException {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", loginButton);

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("lomaniinoss");
        String password = AuthUtils.getPasswordFromFile("/home/extrzz/stuff/verySecretPassword");
        authPage.enterPasswordField(password);

        // Ждем, пока пользователь введет код доступа вручную
        System.out.println("Пожалуйста, введите код доступа вручную и нажмите Enter.");

        // Ожидаем, что пользователь введет код и нажмет Enter

        // Вводим код
        authPage.clickByCodeVariationsButton();
        authPage.clickCodeBySmsButton();
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

        cartPage.clickInCardButton();
        Thread.sleep(2000);
        String originalHandleCart = driver.getWindowHandle();
        Set<String> oldHandlesCart = driver.getWindowHandles();
        cartPage.clickInCardButton();


        wait.until(d -> d.getWindowHandles().size() > oldHandlesCart.size());

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandleCart)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        cartPage.clickCheckoutButton();


        Thread.sleep(10000);
    }
}
