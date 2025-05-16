//import com.labwork.DriverInit;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class BaseTest {
//
//
//    private static WebDriver chromeDriver;
//    private static WebDriverWait chromeWait;
//    private static JavascriptExecutor chromeJs;
//
//    private static WebDriver firefoxDriver;
//    private static WebDriverWait firefoxWait;
//    private static JavascriptExecutor firefoxJs;
//
//    private static DriverInit driverInit = new DriverInit();
//
//    @BeforeAll
//    public static void setUp() {
//        WebDriver cDriver = driverInit.initChromeDriver();
//        if (cDriver == null) {
//            throw new RuntimeException("Failed to initialize ChromeDriver!");
//        }
//        chromeDriver = cDriver;
//        chromeWait = driverInit.getChromeWait(cDriver);
//        chromeJs = driverInit.getChromeJs(cDriver);
//
//        WebDriver fDriver = driverInit.initFirefoxDriver();
//        if (fDriver == null) {
//            throw new RuntimeException("Failed to initialize FirefoxDriver!");
//        }
//        firefoxDriver = fDriver;
//        firefoxWait = driverInit.getFirefoxWait(fDriver);
//        firefoxJs = driverInit.getFirefoxJs(fDriver);
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        if (chromeDriver != null) {
//            chromeDriver.quit();
//            chromeDriver = null;
//            chromeWait= null;
//            chromeJs= null;
//        }
//        if (firefoxDriver != null) {
//            firefoxDriver.quit();
//            firefoxDriver= null;
//            firefoxWait= null;
//            firefoxJs= null;
//        }
//    }
//
//    protected WebDriver getChromeDriver() {
//        return chromeDriver;
//    }
//    protected WebDriverWait getChromeWait() {
//        return chromeWait;
//    }
//    protected JavascriptExecutor getChromeJs() {
//        return chromeJs;
//    }
//
//    protected WebDriver getFirefoxDriver() {
//        return firefoxDriver;
//    }
//    protected WebDriverWait getFirefoxWait() {
//        return firefoxWait;
//    }
//    protected JavascriptExecutor getFirefoxJs() {
//        return firefoxJs;
//    }
//}
import com.labwork.DriverInit;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {

    private static final ThreadLocal<WebDriver> chromeDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> chromeWait = new ThreadLocal<>();
    private static final ThreadLocal<JavascriptExecutor> chromeJs = new ThreadLocal<>();

    private static final ThreadLocal<WebDriver> firefoxDriver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> firefoxWait = new ThreadLocal<>();
    private static final ThreadLocal<JavascriptExecutor> firefoxJs = new ThreadLocal<>();

    private static final DriverInit driverInit = new DriverInit();

    protected void setUpDrivers() {
        if (chromeDriver.get() == null) {
            WebDriver cDriver = driverInit.initChromeDriver();
            if (cDriver == null) throw new RuntimeException("Failed to initialize ChromeDriver!");
            chromeDriver.set(cDriver);
            chromeWait.set(driverInit.getChromeWait(cDriver));
            chromeJs.set(driverInit.getChromeJs(cDriver));
        }

        if (firefoxDriver.get() == null) {
            WebDriver fDriver = driverInit.initFirefoxDriver();
            if (fDriver == null) throw new RuntimeException("Failed to initialize FirefoxDriver!");
            firefoxDriver.set(fDriver);
            firefoxWait.set(driverInit.getFirefoxWait(fDriver));
            firefoxJs.set(driverInit.getFirefoxJs(fDriver));
        }
    }

    @AfterAll
    public static void cleanUpAll() {
        if (chromeDriver.get() != null) {
            chromeDriver.get().quit();
            chromeDriver.remove();
            chromeWait.remove();
            chromeJs.remove();
        }
        if (firefoxDriver.get() != null) {
            firefoxDriver.get().quit();
            firefoxDriver.remove();
            firefoxWait.remove();
            firefoxJs.remove();
        }
    }

    protected WebDriver getChromeDriver() { return chromeDriver.get(); }
    protected WebDriverWait getChromeWait() { return chromeWait.get(); }
    protected JavascriptExecutor getChromeJs() { return chromeJs.get(); }

    protected WebDriver getFirefoxDriver() { return firefoxDriver.get(); }
    protected WebDriverWait getFirefoxWait() { return firefoxWait.get(); }
    protected JavascriptExecutor getFirefoxJs() { return firefoxJs.get(); }
}
