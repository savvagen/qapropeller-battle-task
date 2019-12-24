package com.example.e2e;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.pages.LoginPage;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.logging.Level;

import static com.codeborne.selenide.Configuration.*;

public class TestBase {

    public LoginPage loginPage;

    public TestBase(){
        baseUrl = "http://localhost:8080";
        browserSize = "1280x1024"; // 12024x768
        timeout = 8000;
        browser = "chrome";
        loginPage = new LoginPage();
    }


    @BeforeSuite
    public static void setUpSuite(){
        SelenideLogger.addListener("allure", new AllureSelenide().savePageSource(true).screenshots(true)
                .enableLogs(LogType.BROWSER, Level.ALL)
        );
    }

    @AfterSuite
    public static void tearDownSuite(){
        SelenideLogger.removeListener("allure");
    }


}
