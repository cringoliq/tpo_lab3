
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

public class SearchByInputTest extends BaseTest {
/*
    private static HomePage homePage;
    private static SearchPage searchPage;

    @BeforeEach
    public void setUp() {

        homePage = new HomePage(chromeDriver);
        searchPage = new SearchPage(chromeDriver);
        chromeDriver.get("https://market.yandex.ru/");

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


 */

}
