package com.example.e2e;


import com.codeborne.selenide.CollectionCondition;
import com.example.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;



public class MainPageTests extends BaseTest {

    @BeforeMethod
    public void setUpTest(){
        open("/");
        getWebDriver().manage().deleteAllCookies();
        // getWebDriver().manage().addCookie(secretCookie); # Is not working on this website
        // executeJavaScript("document.cookie = 'secret=IAmSuperSeleniumMaster;domain=localhost;path=/;'");
        executeJavaScript(String.format("document.cookie = '%s=%s;domain=%s;path=%s;'",
                secretCookie.getName(),
                secretCookie.getValue(),
                secretCookie.getDomain(), secretCookie.getPath())
        );
        refresh();
    }

    @Test
    public void shouldCheckMainPage(){
        mainPage.open();
        mainPage.userButton.shouldBe(visible);
        mainPage.categories.forEach(category -> category.shouldBe(visible));
        mainPage.categories.shouldHave(CollectionCondition.size(3));
        mainPage.logoutButton.shouldBe(visible);
    }


    @Test
    public void shouldOpenFirstArticle(){
        mainPage.clickCategory("Advertisers")
                .clickArticle(0);
        mainPage.cardTitle.shouldBe(visible).shouldHave(text("Test Advertiser"));
        mainPage.categories.filterBy(visible).shouldHaveSize(3);
        mainPage.articles.filterBy(visible).shouldHaveSize(2);
    }

    @Test
    public void shouldOpenFirstArticleByName(){
        mainPage.clickCategory("Advertisers")
                .clickArticle("Test Advertiser");
        mainPage.cardTitle.shouldBe(visible).shouldHave(text("Test Advertiser"));
        mainPage.categories.filterBy(visible).shouldHaveSize(3);
        mainPage.articles.filterBy(visible).shouldHaveSize(2);
    }


    @Test
    public void openAllCategoriesAndArticles(){
        mainPage.clickCategory(0).clickCategory(1).clickCategory(2);
        mainPage.clickArticle("Darth Vader");
        mainPage.cardTitle.shouldBe(visible).shouldHave(text("Darth Vader"));
        mainPage.categories.filterBy(visible).shouldHaveSize(3);
        mainPage.articles.filterBy(visible).shouldHaveSize(14);
    }

}
