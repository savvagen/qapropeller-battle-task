package com.example;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.logging.Level;


public class TestBase {

    public final String baseUrl = "http://localhost:8080";
    public LoginPage loginPage;


    public TestBase(){
        Configuration.baseUrl = this.baseUrl;
        Configuration.browserSize = "1280x1024"; // 12024x768
        Configuration.timeout = 8000;
        // Configuration.browser = "chrome";


        loginPage = new LoginPage();

    }


    @BeforeSuite
    public static void setUpSuite(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true)
                .enableLogs(LogType.BROWSER, Level.ALL)
        );
    }

    @AfterSuite
    public static void tearDownSuite(){
        SelenideLogger.removeListener("AllureSelenide");
    }



}


