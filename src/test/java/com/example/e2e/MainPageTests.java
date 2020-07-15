package com.example.e2e;



import com.codeborne.selenide.testng.GlobalTextReport;
import com.codeborne.selenide.testng.ScreenShooter;
import com.example.BaseTest;
import com.example.listeners.AllureTextReport;
import com.example.listeners.ScreenshotListener;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static org.testng.Assert.*;


@Epic("Smoke")
@Feature("Main Page")
@Listeners({GlobalTextReport.class,
        AllureTextReport.class,
        ScreenShooter.class, ScreenshotListener.class})
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
    @Story("Non Critical Tests")
    @Description("Should open and verify Main Page")
    public void shouldCheckMainPage(){
        mainPage.open();
        mainPage.userButton.shouldBe(visible);
        mainPage.readArticles.categories.forEach(category -> category.shouldBe(visible));
        mainPage.readArticles.categories.shouldHave(size(3));
    }

    @Test
    @Story("Non Critical Tests")
    @Description("Should open and verify User Page")
    public void shouldOpenUserProfile(){
        mainPage.userButton.click();
        profilePage.header.shouldBe(visible).shouldHave(text("User profile settings"));
        assertTrue(getWebDriver().getCurrentUrl().contains(profilePage.url));
    }

    @Test
    @Story("Business Critical Tests")
    @Description("Should open article")
    public void shouldOpenFirstArticle(){
        mainPage.readArticles.clickCategory("Advertisers").clickArticle(0);
        mainPage.cardTitle.shouldBe(visible).shouldHave(text("Test Advertiser"));
        mainPage.readArticles.categories.filterBy(visible).shouldHaveSize(3);
        mainPage.readArticles.articles.filterBy(visible).shouldHaveSize(2);
    }

    @Test
    @Story("Business Critical Tests")
    @Description("Should open article by name")
    public void shouldOpenFirstArticleByName(){
        mainPage.readArticles.clickCategory("Advertisers").clickArticle("Test Advertiser");
        mainPage.cardTitle.shouldBe(visible).shouldHave(text("Test Advertiser"));
        mainPage.readArticles.categories.filterBy(visible).shouldHaveSize(3);
        mainPage.readArticles.articles.filterBy(visible).shouldHaveSize(2);
    }


    @Test
    @Story("Business Critical Tests")
    @Description("Should open categories and articles")
    public void shouldOpenAllCategoriesAndArticles(){
        mainPage.readArticles.clickCategory(0).clickCategory(1).clickCategory(2);
        mainPage.readArticles.clickArticle("Darth Vader");
        mainPage.cardTitle.shouldBe(visible).shouldHave(text("Darth Vader"));
        mainPage.readArticles.categories.filterBy(visible).shouldHaveSize(3);
        mainPage.readArticles.articles.filterBy(visible).shouldHaveSize(14);
        assertEquals(getWebDriver().manage().getCookieNamed("notSavedOpened").getValue(),
                "Advertisers,Publishers,Top level clients");
    }

    @Test
    @Story("Non Critical Tests")
    @Description("Should check Save button is enabled")
    public void shouldEnableSaveButtonAfterArticleReading(){
        mainPage.shouldBeOpened();
        mainPage.readArticles.clickCategory("Top level clients").clickArticle("Jon Snow");
        mainPage.cardSaveButton.shouldBe(disabled);
        mainPage.scrollDownArticle().cardSaveButton.shouldBe(enabled);
    }

    @Test
    @Story("Business Critical Tests")
    @Description("Should save article")
    public void shouldAddArticleToSaved(){
        mainPage.readArticles.clickCategory("Top level clients").clickArticle("Darth Vader");
        mainPage.saveArticle();
        mainPage.savedArticlesContainer.shouldBe(visible);
        mainPage.savedArticles.categories.shouldHave(sizeGreaterThanOrEqual(1));
        mainPage.savedArticles.articles.shouldHave(sizeGreaterThanOrEqual(1));
        mainPage.readArticles.articles.filterBy(visible).shouldHaveSize(9).filterBy(text("Darth Vader")).shouldHaveSize(0);
        assertEquals(getWebDriver().manage().getCookieNamed("notSavedOpened").getValue(), "Top level clients");
        assertEquals(getWebDriver().manage().getCookieNamed("saved").getValue(), "Darth Vader");
    }

    @Test
    @Flaky
    @Story("Business Critical Tests")
    @Description("Should remove article from saved")
    public void shouldRemoveArticleFromSavedList(){
        String articleName = "Darth Vader";
        mainPage.readArticles.clickCategory("Top level clients").clickArticle(articleName);
        mainPage.saveArticle();
        mainPage.savedArticlesContainer.shouldBe(visible);
        mainPage.savedArticles.clickCategory(0);
        mainPage.savedArticles.articles.filterBy(text(articleName)).shouldHaveSize(1);
        mainPage.readArticles.articles.filterBy(visible).filterBy(text(articleName)).shouldHaveSize(0);
        mainPage.removeArticle();
        mainPage.savedArticlesContainer.should(disappear);
        mainPage.savedArticlesContainer.should(not(be(visible)));
        mainPage.readArticles.articles.filterBy(visible).filterBy(text(articleName)).shouldHaveSize(1);
        assertNull(getWebDriver().manage().getCookieNamed("saved"));

    }

    @Test
    @Story("Business Critical Tests")
    @Description("Should download article attachment")
    public void shouldDownloadArticleData() throws IOException {
        mainPage.readArticles.clickCategory("Top level clients").clickArticle("Darth Vader");
        File downloadedFile = mainPage.downloadArticle();
        String actualText = new String(Files.readAllBytes(Paths.get(downloadedFile.getAbsolutePath())));
        String expectedText = new String(Files.readAllBytes(Paths.get(descriptionFilePath)));
        assertEquals(expectedText, actualText);
    }


    @Test
    @Story("Non Critical Tests")
    @Description("Should zoom article icon")
    public void shouldCheckArticleZooming() {
        mainPage.readArticles.clickCategory(0).clickArticle(0);
        mainPage.cardSlider.scrollIntoView(true).shouldBe(visible);
        actions().dragAndDropBy(mainPage.cardSlider, 295, 0).build().perform();
        mainPage.heroImage.shouldHave(attribute("style", "width: 399px; height: 399px;"));
        actions().dragAndDropBy(mainPage.cardSlider, 350, 0).build().perform();
        mainPage.heroImage.shouldHave(attribute("style", "width: 500px; height: 500px;"));
    }
}
