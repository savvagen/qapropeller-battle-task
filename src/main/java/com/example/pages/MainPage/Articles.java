package com.example.pages.MainPage;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class Articles {

    public SelenideElement element;
    public SelenideElement loader = $("#loader");

    public Articles(SelenideElement element){
        this.element = element;
    }

    @Step
    public Articles clickCategory(int index){
        element.$$(".tree-main-node > button").get(index)
                .scrollIntoView(true)
                .click();
        return this;
    }

    @Step
    public Articles clickCategory(String categoryName){
        element.$$(".tree-main-node > button").filterBy(text(categoryName))
                .last()
                .scrollIntoView(true)
                .click();
        return this;
    }

    @Step
    public Articles clickArticle(int index){
        element.$$(".sub-tree-element > button").filterBy(visible)
                .get(index)
                .scrollIntoView(true)
                .click();
        loader.shouldBe(visible).should(disappear);
        return this;
    }

    @Step
    public Articles clickArticle(String articleName){
        element.$$(".sub-tree-element > button").filterBy(visible)
                .filterBy(text(articleName))
                .last().scrollTo()
                .click();
        loader.shouldBe(visible).should(disappear);
        return this;
    }

}
