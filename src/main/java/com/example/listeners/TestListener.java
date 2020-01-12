package com.example.listeners;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.reporters.ExitCodeListener;

public class TestListener extends ExitCodeListener {

    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
    }

    @Override
    public void onStart(ITestContext context) {
        super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        super.onFinish(context);
    }

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
    }
}
