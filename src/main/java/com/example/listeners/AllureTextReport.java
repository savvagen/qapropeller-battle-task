package com.example.listeners;

import org.testng.ITestResult;
import org.testng.reporters.ExitCodeListener;

public class AllureTextReport extends ExitCodeListener {

    private CustomReport report = new CustomReport();

    @Override
    public void onTestStart(ITestResult testResult){
        report.start();
    }

    @Override
    public void onTestFailure(ITestResult testResult){
        report.finish(testResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult testResult){
        report.finish(testResult.getName());
    }


}
