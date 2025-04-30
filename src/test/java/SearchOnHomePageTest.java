import com.labwork.DriverInit;
import com.labwork.pages.CatalogPage;
import com.labwork.pages.ElectronicsPage;
import com.labwork.pages.HomePage;
import com.labwork.pages.SearchPage;
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
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchOnHomePageTest {
    private static DriverInit driverInit;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;
    private static Actions actions;

    private static HomePage homePage;
    private static SearchPage searchPage;
    private static CatalogPage catalogPage;
    private static ElectronicsPage electronicsPage;
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
        electronicsPage = new ElectronicsPage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchTest(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Войдите, и станет дешевле']\n")));
        actions.moveByOffset(100, 100).click().perform();


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

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/productTitle\"]/div/div/h1")));
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Текущий URL: " + currentUrl);

        assertTrue(currentUrl.contains("product--"), "URL не содержит 'product--'. Текущий URL: " + currentUrl);


    }
}
