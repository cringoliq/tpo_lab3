package com.labwork;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class DriverInit {

    // Создаёт и возвращает настроенный новый ChromeDriver
    public WebDriver initChromeDriver() {
        WebDriverManager.chromedriver().driverVersion("136.0.7103.113").setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().window().setSize(new Dimension(1050, 716));
        driver.get("https://market.yandex.ru/");
        return driver;
    }

    // Возвращает WebDriverWait для данного драйвера
    public WebDriverWait getChromeWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // Возвращает JavascriptExecutor для данного драйвера
    public JavascriptExecutor getChromeJs(WebDriver driver) {
        return (JavascriptExecutor) driver;
    }


    // Аналогично для Firefox
    public WebDriver initFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().window().setSize(new Dimension(1050, 716));
        driver.get("https://market.yandex.ru/");
        return driver;
    }

    public WebDriverWait getFirefoxWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public JavascriptExecutor getFirefoxJs(WebDriver driver) {
        return (JavascriptExecutor) driver;
    }
}
