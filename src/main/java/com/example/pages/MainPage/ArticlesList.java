package com.example.pages.MainPage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ArticlesList {

    public SelenideElement element;
    public SelenideElement loader = $("#loader");
    public ElementsCollection articles;
    public ElementsCollection categories;


    public ArticlesList(SelenideElement element){
        this.element = element;
        this.categories = element.$$(".tree-main-node > button");
        this.articles = element.$$(".sub-tree-element > button");
    }

    @Step
    public ArticlesList clickCategory(int index){
        categories.get(index).scrollIntoView(true).click();
        return this;
    }

    @Step
    public ArticlesList clickCategory(String categoryName){
        categories.filterBy(text(categoryName)).last().scrollIntoView(true).click();
        return this;
    }

    @Step
    public ArticlesList clickArticle(int index){
        articles.filterBy(visible).get(index).scrollIntoView(true).click();
        loader.shouldBe(visible).should(disappear);
        return this;
    }

    @Step
    public ArticlesList clickArticle(String articleName){
        articles.filterBy(visible).filterBy(text(articleName)).last().scrollTo().click();
        loader.shouldBe(visible).should(disappear);
        return this;
    }

}
