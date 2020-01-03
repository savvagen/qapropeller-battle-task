package com.example;


import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.driver_profiders.ChromeDriverProvider;
import com.example.models.User;
import com.example.pages.LoginPage.LoginPage;
import com.example.pages.MainPage.MainPage;
import com.example.pages.ProfilePage.ProfilePage;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import static com.codeborne.selenide.Configuration.*;


public class BaseTest {


    private static final Logger log = LoggerFactory.getLogger("appLogger");
    public final String descriptionFilePath = "src/test/resources/data/darth_vader.txt";
    public User admin;
    public Cookie secretCookie;

    public LoginPage loginPage;
    public MainPage mainPage;
    public ProfilePage profilePage;


    public BaseTest(){
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (Exception e){
            e.printStackTrace();
        }
        assert inetAddress != null;
        log.info("Running Tests on host: " + inetAddress);

        baseUrl = "http://localhost:8080"; // String.format("http://%s:8080", inetAddress.getHostAddress());
        browserSize = "1280x1024"; // 12024x768
        timeout = 11000;
        startMaximized = true;
        // fileDownload = FileDownloadMode.HTTPGET;
        //browser = ChromeDriverProvider.class.getName();
        browser = "chrome";
        admin = new User("test", "test");
        secretCookie = new Cookie.Builder("secret", "IAmSuperSeleniumMaster")
                //.domain(inetAddress.getHostAddress())
                .domain("localhost")
                .path("/")
                .build();
        loginPage = new LoginPage();
        mainPage = new MainPage();
        profilePage = new ProfilePage();
    }


    @BeforeSuite
    public static void setUpSuite(){
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true)
                .enableLogs(LogType.BROWSER, Level.ALL));
    }

    @AfterSuite
    public static void tearDownSuite() {
        videoAttachment();
        SelenideLogger.removeListener("allure");
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
            return Files.readAllBytes(Paths.get(video.getAbsolutePath()));
        } catch (IOException e) {
            return new byte[0];
        }
    }


}
