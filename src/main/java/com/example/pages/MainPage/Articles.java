package com.example.pages.MainPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class Articles {

    public SelenideElement element;
    public SelenideElement loader = $("#loader");
    public ElementsCollection categories;
    public ElementsCollection articles;

    public Articles(SelenideElement element){
        this.element = element;
        this.categories = element.$$(byCssSelector(".tree-main-node > button"));
        this.articles = element.$$(byCssSelector(".sub-tree-element > button"));
    }

    @Step
    public Articles clickCategory(int index){
        this.categories.get(index).scrollIntoView(true).click();
        return this;
    }

    @Step
    public Articles clickCategory(String categoryName){
        this.categories.filterBy(text(categoryName)).last().scrollIntoView(true).click();
        return this;
    }

    @Step
    public Articles clickArticle(int index){
        this.articles.filterBy(visible).get(index).scrollIntoView(true).click();
        loader.shouldBe(visible).should(disappear);
        return this;
    }

    @Step
    public Articles clickArticle(String articleName){
        this.articles.filterBy(visible).filterBy(text(articleName)).last().scrollTo().click();
        loader.shouldBe(visible).should(disappear);
        return this;
    }

}
