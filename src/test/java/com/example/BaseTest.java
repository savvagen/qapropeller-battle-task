package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.driver_profiders.ChromeDriverProvider;
import com.example.models.User;
import com.example.pages.LoginPage.LoginPage;
import com.example.pages.MainPage.MainPage;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.InetAddress;
import java.util.logging.Level;
import static com.codeborne.selenide.Configuration.*;


public class BaseTest {

    public LoginPage loginPage;
    public MainPage mainPage;
    public User admin;
    public Cookie secretCookie;
    private static final Logger log = LoggerFactory.getLogger("appLogger");

    public BaseTest(){
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (Exception e){
            e.printStackTrace();
        }
        assert inetAddress != null;
        log.info("Running Tests on host: " + inetAddress);

        baseUrl = "http://" + inetAddress.getHostAddress() + ":8080";
        browserSize = "1280x1024"; // 12024x768
        timeout = 11000;
        browser = ChromeDriverProvider.class.getName();

        admin = new User("test", "test");
        secretCookie = new Cookie.Builder("secret", "IAmSuperSeleniumMaster").domain("localhost").path("/").build();

        loginPage = new LoginPage();
        mainPage = new MainPage();
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
