import com.labwork.DriverInit;
import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected DriverInit driverInit;

    protected WebDriver chromeDriver;
    protected WebDriverWait chromeWait;
    protected JavascriptExecutor chromeJs;
    protected Actions chromeActions;

    protected WebDriver firefoxDriver;
    protected WebDriverWait firefoxWait;
    protected JavascriptExecutor firefoxJs;
    protected Actions firefoxActions;

    @BeforeAll
    void globalSetUp() {
        driverInit = new DriverInit();
        driverInit.setupDrivers();

        // инициализация для Chrome
        chromeDriver = driverInit.getChromeDriver();
        chromeWait = driverInit.getChromeWait();
        chromeJs = driverInit.getChromeJs();
        chromeActions = new Actions(chromeDriver);

         //инициализация для Firefox
//        firefoxDriver = driverInit.getFirefoxDriver();
//        firefoxWait = driverInit.getFirefoxWait();
//        firefoxJs = driverInit.getFirefoxJs();
//        firefoxActions = new Actions(firefoxDriver);

//        if (chromeDriver == null || firefoxDriver == null) {
//            throw new RuntimeException("One or both drivers are null after setup!");
//        }
    }

    @AfterAll
    void globalTearDown() {
        if (chromeDriver != null) {
            chromeDriver.quit();
            chromeDriver = null;
        }
//        if (firefoxDriver != null) {
//            firefoxDriver.quit();
//            firefoxDriver = null;
//        }
    }
}
