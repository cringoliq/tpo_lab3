import com.labwork.DriverInit;
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

public class SearchViaCatalog {
    private static DriverInit driverInit;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;
    private static Actions actions;

    private static HomePage homePage;
    private static SearchPage searchPage;


    @BeforeEach
    public void setUp() {
        driverInit = new DriverInit();
        driverInit.setupDriver();
        driver = driverInit.getDriver();
        wait = driverInit.getWait();
        js = driverInit.getJs();

        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        actions = new Actions(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchTest() throws InterruptedException {

        homePage.clickCatalogButton();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/marketfrontDynamicPopupLoader44/content\"]/div/div/a\n")));

        actions.moveByOffset(100, 100).click().perform();


        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/catalog--muzhskaia-odezhda/54404675']")
        ));

        element.click();


        WebElement marketTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class, 'ds-text') and contains(@class, 'ds-text_headline-4_bold')]")));


        String titleText = marketTitle.getText();

        assertTrue(titleText.startsWith("Мужская одежда"), titleText);
    }

}
