import com.labwork.DriverInit;
import com.labwork.pages.CatalogPage;
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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchViaCatalogTest {
    private static DriverInit driverInit;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;
    private static Actions actions;

    private static HomePage homePage;
    private static SearchPage searchPage;
    private static CatalogPage catalogPage;


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
        actions = new Actions(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchTest() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Войдите, и станет дешевле']\n")));
        actions.moveByOffset(100, 100).click().perform();

        homePage.clickCatalogButton();

//*[@id="/marketfrontDynamicPopupLoader43/content"]/div
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/catalog--muzhskaia-odezhda/54404675']")
        ));

        element.click();




        WebElement marketTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class, 'ds-text') and contains(@class, 'ds-text_headline-4_bold')]")));


        String titleText = marketTitle.getText();

        assertTrue(titleText.startsWith("Мужская одежда"), titleText);
    }

}
