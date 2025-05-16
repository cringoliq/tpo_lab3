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

public class SearchOnHomePageTest extends BaseTest {


    private static HomePage homePage;
    private static SearchPage searchPage;
    private static CatalogPage catalogPage;
    private static ElectronicsPage electronicsPage;
    @BeforeEach
    public void setUp() {

        homePage = new HomePage(chromeDriver);
        searchPage = new SearchPage(chromeDriver);
        catalogPage = new CatalogPage(chromeDriver);
        electronicsPage = new ElectronicsPage(chromeDriver);
        chromeActions = new Actions(chromeDriver);
        chromeDriver.get("https://market.yandex.ru/");

    }


    @Test
    public void searchTest(){

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Войдите, и станет дешевле']\n")));
//        actions.moveByOffset(100, 100).click().perform();


        homePage.clickBelowMarketButton();



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

        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/productTitle\"]/div/div/h1")));
        String currentUrl = chromeDriver.getCurrentUrl();
        System.out.println("Текущий URL: " + currentUrl);

        assertTrue(currentUrl.contains("product--"), "URL не содержит 'product--'. Текущий URL: " + currentUrl);


    }


}
