package com.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MainPage extends WebPage {

    public SelenideElement userButton = $("#avatar"),
                            header = $("div[class*='card-header']");

    public MainPage() {
        this.title = "";
        this.url = "/main.html";
    }
}
