
import com.labwork.DriverInit;
import com.labwork.pages.CatalogPage;
import com.labwork.pages.ElectronicsPage;
import com.labwork.pages.HomePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class SearchByCatalogTest {

    private DriverInit         driverInit;
    private WebDriver          driver;
    private WebDriverWait      wait;
    private Actions            actions;

    private HomePage           homePage;
    private CatalogPage        catalogPage;
    private ElectronicsPage    electronicsPage;

    @BeforeEach
    void setUp() {
        driverInit = new DriverInit();
        driverInit.setupDriver();

        driver   = driverInit.getDriver();
        wait     = driverInit.getWait();
        actions  = new Actions(driver);

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




        /* 1. открыть выпадающий каталог */
        homePage.clickCatalogButton();
        wait.until(ExpectedConditions
                .visibilityOf(catalogPage.getCatalogPanel()));

        /* 2. перейти в «Электроника» */
        wait.until(ExpectedConditions
                .elementToBeClickable(catalogPage.getElectronicsLink()));
        catalogPage.openElectronics();

        /* 3. дождаться появления карточек товаров */
        By tiles = By.cssSelector("div[data-cs-name='navigate'] a[data-auto='snippet-link']");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tiles, 0));

        /* 4. кликнуть случайный товар (откроется новая вкладка) */
        String originalHandle = driver.getWindowHandle();
        Set<String> oldHandles = driver.getWindowHandles();
        electronicsPage.chooseRandomGood();

        /* 5. переключиться на вкладку товара */
        wait.until(d -> d.getWindowHandles().size() > oldHandles.size());
        for (String h : driver.getWindowHandles()) {
            if (!h.equals(originalHandle)) {
                driver.switchTo().window(h);
                break;
            }
        }

        /* 6. убедиться, что открылась страница товара */
        wait.withTimeout(Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/product"));
        assertTrue(driver.getCurrentUrl().contains("/product"),
                "Страница товара не открылась");
    }
}
