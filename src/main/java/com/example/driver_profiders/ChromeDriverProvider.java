package com.example.driver_profiders;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;

public class ChromeDriverProvider implements WebDriverProvider {

    private static final Logger log = LoggerFactory.getLogger("appLogger");

    @Override
    public WebDriver createDriver(DesiredCapabilities dc) {
        dc.setBrowserName("chrome");
        dc.setVersion("79.0");
        dc.setCapability("enableVNC", true);
        dc.setCapability("enableVideo", true);
        dc.setCapability("screenResolution", "1960x1280x24");
        dc.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        dc.setCapability("videoName", "selenoid_recording_chrome.mp4");
        dc.setCapability("videoScreenSize", "1960x1280");
        dc.setPlatform(Platform.LINUX);
        dc.setJavascriptEnabled(true);
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(URI.create("http://127.0.0.1:4444/wd/hub").toURL(), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert driver != null;
        log.info("Running browser with session: " + driver.getSessionId().toString());
        return driver;
    }


}
