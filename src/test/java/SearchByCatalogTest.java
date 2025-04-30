
import com.labwork.DriverInit;
import com.labwork.pages.CatalogPage;
import com.labwork.pages.ElectronicsPage;
import com.labwork.pages.HomePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchByCatalogTest {

    private DriverInit driverInit;
    private WebDriver      driver;
    private WebDriverWait  wait;
    private JavascriptExecutor js;

    private HomePage       homePage;
    private CatalogPage    catalogPage;
    private ElectronicsPage electronicsPage;

    @BeforeEach
    void setUp() {
        driverInit = new DriverInit();
        driverInit.setupDriver();

        driver = driverInit.getDriver();
        wait   = driverInit.getWait();
        js     = driverInit.getJs();

        homePage        = new HomePage(driver);
        catalogPage     = new CatalogPage(driver);
        electronicsPage = new ElectronicsPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void openCatalogAndChooseRandomGoodTest() {

        // открыть выпадающий каталог
        homePage.clickCatalogButton();
        wait.until(ExpectedConditions
                .visibilityOf(catalogPage.getCatalogPanel()));

        // перейти в «Электроника»
        wait.until(ExpectedConditions
                .elementToBeClickable(catalogPage.getElectronicsLink()));
        catalogPage.openElectronics();

        // дождаться появления карточек
        By tiles = By.cssSelector(
                "div[data-cs-name='navigate'] a[data-auto='snippet-link']");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tiles, 0));

        // клик по случайному товару  откроется НОВАЯ вкладка
        String originalHandle = driver.getWindowHandle();
        Set<String> oldHandles = driver.getWindowHandles();
        electronicsPage.chooseRandomGood();

        // переключаемся на появившуюся вкладку
        wait.until(d -> d.getWindowHandles().size() > oldHandles.size());
        for (String h : driver.getWindowHandles()) {
            if (!h.equals(originalHandle)) {
                driver.switchTo().window(h);
                break;
            }
        }

        // ждём загрузки страницы товара и проверяем URL
        wait.withTimeout(Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/product"));
        assertTrue(driver.getCurrentUrl().contains("/product"),
                "Страница товара не открылась");
    }
}
