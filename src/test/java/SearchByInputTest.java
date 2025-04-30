
import com.labwork.DriverInit;
import com.labwork.pages.HomePage;
import com.labwork.pages.SearchPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchByInputTest {


    private static DriverInit driverInit;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;
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
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchFoundTest() {
        homePage.enterSearchInput("Iphone 15 Pro");
        homePage.clickSearchButton();

        String titleText = searchPage.getSearchTitle().getText();

        assertTrue(titleText.startsWith("Iphone 15 Pro"), titleText);
    }
    @Test
    public void searchNotFoundTest() {
        homePage.enterSearchInput("sdfksflwoelrijwer");
        homePage.clickSearchButton();

        String titleText = searchPage.getEmptySearchPage().getText();

        assertTrue(titleText.startsWith("Такого у нас нет"), titleText);
    }
}
