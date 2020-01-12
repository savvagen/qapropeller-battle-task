package com.example.listeners;


import com.codeborne.selenide.logevents.EventsCollector;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.logevents.SimpleReport;
import com.google.common.base.Joiner;
import io.qameta.allure.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class CustomReport extends SimpleReport {

    private static final Logger log= LoggerFactory.getLogger("appLogger");
    private EventsCollector logEventListener;


    public void start(){
        SelenideLogger.addListener("AllureListener",  logEventListener = new EventsCollector());
    }

    public void finish(String title){
        SelenideLogger.removeListener("AllureListener");

        StringBuilder sb = new StringBuilder();
        sb.append("Report for ").append(title).append("\n");
        String delimeter = "+" + Joiner.on("+").join(line(20), line(70), line(10), line(10) + "+\n");
        sb.append(delimeter);
        sb.append(String.format("|%-20s|%-70s|%-10s|%-10s|%n", "Element", "Subject", "Status", "ms."));
        sb.append(delimeter);

        for (LogEvent e: logEventListener.events() ){
            sb.append(String.format("|%-20s|%-70s|%-10s|%-10s|%n", e.getElement(), e.getSubject(), e.getStatus(), e.getDuration()));
        }
        sb.append(delimeter);
        log.info(sb.toString());
        attacheText(sb.toString());
    }



    private String line(int count){ return Joiner.on("").join(Collections.nCopies(count, "-")); }


    @Attachment(value = "Test log")
    public static String attacheText(String text){
        log.info("Taking test log to allure report");
        return text;
    }




}