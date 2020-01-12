package com.example.pages.MainPage;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.example.models.Article;
import com.example.pages.WebPage;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.*;


public class MainPage extends WebPage {

    public SelenideElement userButton = $("#avatar"),
                            header = $("div[class*='card-header']"),
                            loader = $("#loader"),
                            cardTitle = $("h5.card-title"),
                            cardDescription = $("p.card-text"),
                            cardText = $("textarea.form-control"),
                            downloadButton = $(byText("Download info")),
                            cardSaveButton = $(byText("Move to saved")),
                            cardRemoveButton = $(byText("Removed from saved")),
                            cardSlider = $("div[class*='ui-slider'] > span"),
                            heroImage = $("#heroImage"),
                            readArticlesContainer = $(byText("Articles to read")).parent(),
                            savedArticlesContainer = $(byText("Saved articles")).parent();

    public Articles readArticles = new Articles(readArticlesContainer);
    public Articles savedArticles = new Articles(savedArticlesContainer);

    public MainPage() {
        this.title = "";
        this.url = "/main.html";
    }

    public MainPage open(){
        return open(this);
    }

    public MainPage saveArticle(){
        cardSaveButton.scrollTo().shouldBe(visible);
        // Click disabled button
        // executeJavaScript(String.format("$(\".card-body button:contains('%s')\").click()", cardSaveButton.text()));
        // Make button enabled
        executeJavaScript(String.format(
                "let button = $(\".card-body button:contains('%s')\")[0];" +
                "button.removeAttribute(\"disabled\")",
                cardSaveButton.text())
        );
        cardSaveButton.shouldBe(enabled).click();
        return this;
    }

    public MainPage removeArticle(){
        cardRemoveButton.scrollTo().shouldBe(visible).click();
        return this;
    }

    public File downloadArticle() throws IOException {
        List<Article> articles = given().when().get(baseUrl + "/articles.json")
                .then()
                .extract()
                .body()
                .jsonPath().getList("articles", Article.class);
        String articleDataFileTextLink = articles.get(2).subArticles.get(9).articleDataFileTextLink;
        executeJavaScript(String.format(
                "arguments[0].setAttribute('href', \"%s/%s\");", Configuration.baseUrl, articleDataFileTextLink),
                downloadButton);
        return downloadButton.shouldBe(enabled).download();
    }

    public MainPage scrollDownArticle(){
        cardText.scrollIntoView(true);
        executeJavaScript("$(arguments[0]).animate({scrollTop: document.body.scrollHeight},\"fast\");", cardText.getSearchCriteria());
        return this;
    }



}
