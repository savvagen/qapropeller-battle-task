package com.example.listeners;

import com.codeborne.selenide.logevents.SimpleReport;
import org.testng.*;


/**
 * Allready implemented in {GlobalTextReport} listener
 */

public class TextReportListener extends TestListenerAdapter {


    protected SimpleReport report = new SimpleReport();

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
        report.start();
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        report.finish(tr.getName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        report.finish(tr.getName());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        report.finish(tr.getName());
    }

}
