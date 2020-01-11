package com.example.pages.MainPage;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.example.pages.WebPage;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


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

    public File downloadArticle() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder().GET().uri(URI.create(baseUrl + "/articles.json")).build();
        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        var articles = new Gson().fromJson(response.body(), com.example.models.Articles.class);
        var articleDataFileTextLink = articles.articles.get(2).subArticles.get(9).articleDataFileTextLink;
        executeJavaScript(
                String.format("$(\"button:contains('Download info')\")[0].setAttribute('href', \"%s/%s\");",
                        Configuration.baseUrl, articleDataFileTextLink));
        return downloadButton.shouldBe(enabled).download();
    }

}
