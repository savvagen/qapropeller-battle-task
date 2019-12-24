package com.example.e2e;
import com.codeborne.selenide.WebDriverRunner;
import com.example.pages.MainPage;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static org.testng.Assert.*;

import static com.codeborne.selenide.Condition.*;

public class LoginTests extends TestBase {


    @BeforeClass
    public static void setUpClass(){
    }

    @AfterClass
    public static void terDownClass(){
    }

    @BeforeMethod
    public void setUp(){
        loginPage.open();
    }

    @AfterMethod
    public void tearDown(){
        switchTo().window(0).manage().deleteAllCookies();
    }


    @Test
    public void shouldOpenLoginPage() {
        loginPage.emailField.shouldBe(visible);
        loginPage.passwordField.shouldBe(visible);
        loginPage.loginButton.shouldBe(exist).shouldBe(disabled).shouldHave(text("Hover me faster!\n"));
    }

    @Test
    public void shouldCheckLoginButton(){
        loginPage.emailField.parent().click();
        loginPage.emailField.val("test");
        loginPage.passwordField.find(byXpath("./parent::div")).click();
        loginPage.passwordField.setValue("test");
        loginPage.loginButton.scrollIntoView("{block: \"center\"}").hover();
        loginPage.loginButton.shouldHave(text("Wait for some time..."));
    }


    @Test
    public void shouldCheckSignInButtonAppears(){
        loginPage.shouldBeOpened()
                .header.shouldBe(visible)
                .shouldHave(text("Welcome to Propeller Championship!"));
        loginPage.emailField.parent().click();
        loginPage.emailField.val("test");
        loginPage.passwordField.find(byXpath("./parent::div")).click();
        loginPage.passwordField.setValue("test");
        loginPage.loginButton.hover();
        loginPage.loginButton.shouldHave(text("Wait for some time...")).should(disappear);
        loginPage.signInButton.shouldBe(visible);
    }

    @Test
    public void shouldCheckLoginAlert(){
        loginPage.submitLogin("test", "test");
        Alert firstAlert = switchTo().alert();
        Assert.assertEquals(firstAlert.getText(), "Are you sure you want to login?");
        firstAlert.accept();
        loginPage.preloader.shouldBe(visible);
        Alert secondAlert = switchTo().alert();
        Assert.assertEquals(secondAlert.getText(), "Really sure?");
    }

    @Test
    public void shouldLoginToMainPage(){
        loginPage.login("test", "test");
        mainPage.userButton.shouldBe(visible);
        mainPage.header.shouldBe(visible).shouldHave(text("Articles to read"));
        assertTrue(getWebDriver().getCurrentUrl().contains(mainPage.url));
    }
}
