import com.labwork.DriverInit;
import com.labwork.pages.CatalogPage;
import com.labwork.pages.HomePage;
import com.labwork.pages.SearchPage;
import com.sun.jna.platform.mac.SystemB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchViaCatalogTest extends BaseTest {


    private static HomePage homePage;
    private static SearchPage searchPage;
    private static CatalogPage catalogPage;


    @BeforeEach
    public void setUp() {


        homePage = new HomePage(chromeDriver);
        searchPage = new SearchPage(chromeDriver);
        catalogPage = new CatalogPage(chromeDriver);
        chromeActions = new Actions(chromeDriver);
        chromeDriver.get("https://market.yandex.ru/");

    }

    @Test
    public void searchTest() throws InterruptedException {

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Войдите, и станет дешевле']\n")));
//        actions.moveByOffset(100, 100).click().perform();

        homePage.clickCatalogButton();

//*[@id="/marketfrontDynamicPopupLoader43/content"]/div
        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/catalog--muzhskaia-odezhda/54404675']")
        ));

        element.click();




        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class, 'ds-text') and contains(@class, 'ds-text_headline-4_bold')]")));


        String titleText = marketTitle.getText();

        assertTrue(titleText.startsWith("Мужская одежда"), titleText);
    }



}
