package com.example.e2e;



import com.codeborne.selenide.testng.GlobalTextReport;
import com.example.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static org.testng.Assert.*;

@Listeners({GlobalTextReport.class})
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
        mainPage.readArticles.categories.forEach(category -> category.shouldBe(visible));
        mainPage.readArticles.categories.shouldHave(size(3));
    }

    @Test
    public void shouldOpenUserProfile(){
        mainPage.userButton.click();
        profilePage.header.shouldBe(visible).shouldHave(text("User profile settings"));
        assertTrue(getWebDriver().getCurrentUrl().contains(profilePage.url));
    }

    @Test
    public void shouldOpenFirstArticle(){
        mainPage.readArticles.clickCategory("Advertisers").clickArticle(0);
        mainPage.cardTitle.shouldBe(visible).shouldHave(text("Test Advertiser"));
        mainPage.readArticles.categories.filterBy(visible).shouldHaveSize(3);
        mainPage.readArticles.articles.filterBy(visible).shouldHaveSize(2);
    }

    @Test
    public void shouldOpenFirstArticleByName(){
        mainPage.readArticles.clickCategory("Advertisers").clickArticle("Test Advertiser");
        mainPage.cardTitle.shouldBe(visible).shouldHave(text("Test Advertiser"));
        mainPage.readArticles.categories.filterBy(visible).shouldHaveSize(3);
        mainPage.readArticles.articles.filterBy(visible).shouldHaveSize(2);
    }


    @Test
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
    public void shouldEnableSaveButtonAfterArticleReading(){
        mainPage.shouldBeOpened();
        mainPage.readArticles.clickCategory("Top level clients").clickArticle("Jon Snow");
        mainPage.cardSaveButton.shouldBe(disabled);
        mainPage.scrollDownArticle().cardSaveButton.shouldBe(enabled);
    }

    @Test
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
    public void shouldDownloadArticleData() throws IOException, InterruptedException {
        mainPage.readArticles.clickCategory("Top level clients").clickArticle("Darth Vader");
        var downloadedFile = mainPage.downloadArticle();
        var actualText = new String(Files.readAllBytes(Paths.get(downloadedFile.getAbsolutePath())));
        var expectedText = new String(Files.readAllBytes(Paths.get(descriptionFilePath)));
        assertEquals(expectedText, actualText);
    }


    @Test
    public void shouldCheckArticleZooming() {
        mainPage.readArticles.clickCategory(0).clickArticle(0);
        mainPage.cardSlider.scrollIntoView(true).shouldBe(visible);
        actions().dragAndDropBy(mainPage.cardSlider, 295, 0).build().perform();
        mainPage.heroImage.shouldHave(attribute("style", "width: 399px; height: 399px;"));
        actions().dragAndDropBy(mainPage.cardSlider, 350, 0).build().perform();
        mainPage.heroImage.shouldHave(attribute("style", "width: 500px; height: 500px;"));
    }
}
