package com.example.pages.MainPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.pages.WebPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;

import static com.codeborne.selenide.Selenide.*;

public class MainPage extends WebPage {

    public SelenideElement userButton = $("#avatar"),
                            header = $("div[class*='card-header']"),
                            loader = $("#loader"),
                            cardBody = $(".card-body"),
                            cardTitle = $(".card-body h5.card-title"),
                            cardDescription = $(".card-body .card-text"),
                            cardText = $(".card-body textarea"),
                            downloadButton = $(byText("Download info")),
                            logoutButton = $("*[src*='logout']");

    public ElementsCollection categories = $$(".tree-main-node > button"),
                            articles = $$(".sub-tree .sub-tree-element > button");

    public MainPage() {
        this.title = "";
        this.url = "/main.html";
    }

    @Step
    public MainPage open(){
        return Selenide.open(this.url, this.getClass());
    }

    @Step
    public MainPage clickCategory(int index){
        categories.get(index).scrollIntoView(true).click();
        return this;
    }

    @Step
    public MainPage clickCategory(String categoryName){
        categories.filterBy(text(categoryName)).last().scrollIntoView(true).click();
        return this;
    }

    @Step
    public MainPage clickArticle(int index){
        articles.filterBy(visible).get(index).scrollIntoView(true).click();
        loader.shouldBe(visible).should(disappear);
        return this;
    }

    @Step
    public MainPage clickArticle(String articleName){
        articles.filterBy(visible).filterBy(text(articleName)).last().scrollTo().click();
        loader.shouldBe(visible).should(disappear);
        return this;
    }



}
