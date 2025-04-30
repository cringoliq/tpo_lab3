package com.labwork;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.processing.Generated;
import java.time.Duration;

@Getter
public class DriverInit {

    public WebDriver driver;
    public JavascriptExecutor js;
    public WebDriverWait wait;

    public void setupDriver() {
        WebDriverManager.chromedriver().driverVersion("135.0.7049.114").setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

        driver.manage().window().setSize(new Dimension(1050, 716));
        js = (JavascriptExecutor) driver;
        driver.get("https://market.yandex.ru/");
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
//package com.labwork;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import lombok.Getter;
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//@Getter
//public class DriverInit {
//
//    public WebDriver driver;
//    public JavascriptExecutor js;
//    public WebDriverWait wait;
//
//    public void setupDriver() {
//        WebDriverManager.firefoxdriver().setup(); // Подключаем Firefox driver
//        driver = new FirefoxDriver();             // Инициализируем Firefox
//        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
//
//        driver.manage().window().setSize(new Dimension(1050, 716));
//        js = (JavascriptExecutor) driver;
//        driver.get("https://market.yandex.ru/");
//    }
//
//    public void quitDriver() {
//        if (driver != null) {
//            driver.quit();
//            driver = null;
//        }
//    }
//}
