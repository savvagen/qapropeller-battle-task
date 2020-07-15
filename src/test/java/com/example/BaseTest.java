package com.example;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.testng.ScreenShooter;
import com.example.driver_profiders.ChromeDriverProvider;
import com.example.models.User;
import com.example.pages.LoginPage.LoginPage;
import com.example.pages.MainPage.MainPage;
import com.example.pages.ProfilePage.ProfilePage;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.junit.After;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Paths;
import java.util.logging.Level;
import static com.codeborne.selenide.Configuration.*;


public class BaseTest {


    private static final Logger log = LoggerFactory.getLogger("appLogger");
    public final String descriptionFilePath = "src/test/resources/data/darth_vader.txt";
    public static User admin;
    public static Cookie secretCookie;

    public static LoginPage loginPage;
    public static MainPage mainPage;
    public static ProfilePage profilePage;


    @BeforeClass
    public static void setUpClass(){
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (Exception e){
            e.printStackTrace();
        }
        assert inetAddress != null;
        log.info("Running Tests on host: " + inetAddress);

        //browser = ChromeDriverProvider.class.getName();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x1024"; // 12024x768
        //Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.baseUrl = String.format("http://%s:8080", inetAddress.getHostAddress()); // "http://localhost:8080";
        Configuration.timeout = 11000;
        Configuration.startMaximized = true;
        Configuration.reportsFolder = "test-result/reports";


        admin = new User("test", "test");
        secretCookie = new Cookie.Builder("secret", "IAmSuperSeleniumMaster")
                .domain(inetAddress.getHostAddress())
                // .domain("localhost") // put if baseUrl = "http://localhost:8080";
                .path("/")
                .build();
        loginPage = new LoginPage();
        mainPage = new MainPage();
        profilePage = new ProfilePage();

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true)
                .enableLogs(LogType.BROWSER, Level.ALL));
    }

    @BeforeMethod
    public void setUpMethod(){
        ScreenShooter.captureSuccessfulTests = true;
    }

    @AfterMethod
    public void tearDown() throws IOException {
        screenshot();
    }

    @AfterClass
    public static void tearDownClass() {
        videoAttachment();
        SelenideLogger.removeListener("AllureSelenide");
    }


    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        Screenshots.takeScreenShotAsFile();
        File screenshot = Screenshots.getLastScreenshot();
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }

    @Attachment(value = "video", type = "video/mp4")
    public static byte[] videoAttachment() {
        log.info("Attaching Video to report.");
        try {
            File video = new File("selenoid/video/selenoid_recording_chrome.mp4");
            // If video recorder is installed
            //
            // File video = VideoRecorder.getLastRecording();
            // await().atMost(5, TimeUnit.SECONDS)
            //        .pollDelay(1, TimeUnit.SECONDS)
            //        .ignoreExceptions()
            //        .until(() -> video != null);
            //return Files.readAllBytes(Paths.get(video.getAbsolutePath()));
            return Files.toByteArray(video);
        } catch (IOException e) {
            return new byte[0];
        }
    }


}
