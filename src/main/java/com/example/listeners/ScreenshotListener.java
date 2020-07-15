package com.example.listeners;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.reporters.ExitCodeListener;
import java.io.IOException;

public class ScreenshotListener extends ExitCodeListener {

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        try {
            saveScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
        try {
            saveScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
        try {
            saveScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*@Attachment(type = "image/png")
    public byte[] screenshot(String testName) throws IOException {
        return screenshot(testName);
        // return screenshot == null ? null : Files.toByteArray(screenshot);
    }*/

    @Attachment(type = "image/png")
    public byte[] saveScreenshot() throws IOException {
        Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(Screenshots.getLastScreenshot());
    }


}


