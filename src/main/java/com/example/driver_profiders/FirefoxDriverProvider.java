package com.example.driver_profiders;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

import static org.openqa.selenium.firefox.FirefoxDriver.PROFILE;

public class FirefoxDriverProvider implements WebDriverProvider {
    @Override
    public WebDriver createDriver(DesiredCapabilities dc) {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("browser.fullscreen.autohide",true);
        firefoxProfile.setPreference("browser.fullscreen.animateUp",0);
        dc.setCapability(PROFILE, firefoxProfile);
        dc.setBrowserName("firefox");
        dc.setVersion("70.0");
        dc.setCapability("enableVNC", true);
        dc.setCapability("enableVideo", true);
        dc.setCapability("screenResolution", "1960x1280x24");
        dc.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        dc.setCapability("videoName", "selenoid_recording_firefox.mp4");
        dc.setCapability("videoScreenSize", "1960x1280");
        dc.setPlatform(Platform.LINUX);
        dc.setJavascriptEnabled(true);
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(URI.create("http://selenoid:4444/wd/hub").toURL(), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
