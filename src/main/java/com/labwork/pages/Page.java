package com.labwork.pages;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.openqa.selenium.WebDriver;

@Getter
@Setter
@AllArgsConstructor
public abstract class Page {
    protected WebDriver webDriver;
}
