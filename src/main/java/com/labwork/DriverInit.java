package com.labwork;

import lombok.Getter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;


import javax.annotation.processing.Generated;
import java.time.Duration;

@Getter
public class DriverInit {

    private WebDriver chromeDriver;
    private JavascriptExecutor chromeJs;
    private WebDriverWait chromeWait;

    private WebDriver firefoxDriver;
    private JavascriptExecutor firefoxJs;
    private WebDriverWait firefoxWait;

    public void setupDrivers() {
        // Настройка Chrome
        WebDriverManager.chromedriver().driverVersion("136.0.7103.113").setup();
        chromeDriver = new ChromeDriver();
        chromeWait = new WebDriverWait(chromeDriver, Duration.ofSeconds(25));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        chromeDriver.manage().window().setSize(new Dimension(1050, 716));
        chromeJs = (JavascriptExecutor) chromeDriver;
        chromeDriver.get("https://market.yandex.ru/");

        // Настройка Firefox
//        WebDriverManager.firefoxdriver().setup();
//        firefoxDriver = new FirefoxDriver();
//        firefoxWait = new WebDriverWait(firefoxDriver, Duration.ofSeconds(25));
//        firefoxDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        firefoxDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
//        firefoxDriver.manage().window().setSize(new Dimension(1050, 716));
//        firefoxJs = (JavascriptExecutor) firefoxDriver;
//        firefoxDriver.get("https://market.yandex.ru/");
    }

    public void quitDrivers() {
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

