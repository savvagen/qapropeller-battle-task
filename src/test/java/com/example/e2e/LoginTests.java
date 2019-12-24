package com.example.e2e;
import com.example.pages.MainPage;
import io.qameta.allure.*;
import org.testng.annotations.*;
import org.testng.reporters.jq.Main;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static org.testng.Assert.*;

import static com.codeborne.selenide.Condition.*;

@Epic("Smoke")
@Feature("Login Page")
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
       getWebDriver().manage().deleteAllCookies();
    }

    /*@Test
    public void shouldCheckFullLoginAction(){
        loginPage.shouldBeOpened();
        loginPage.header.shouldBe(visible).shouldHave(text("Welcome to Propeller Championship!"));
        loginPage.emailField.parent().click();
        loginPage.emailField.val("test");
        loginPage.passwordField.find(byXpath("./parent::div")).click();
        loginPage.passwordField.setValue("test");
        loginPage.loginButton.hover();
        loginPage.loginButton.shouldHave(text("Wait for some time...")).should(disappear);
        loginPage.signInButton.shouldBe(visible).click();
        assertEquals(switchTo().alert().getText(), "Are you sure you want to login?");
        switchTo().alert().accept();
        loginPage.preloader.shouldBe(visible);
        assertEquals(switchTo().alert().getText(), "Really sure?");
        switchTo().alert().accept();
        mainPage.userButton.shouldBe(visible);
        mainPage.header.shouldBe(visible).shouldHave(text("Articles to read"));
        assertTrue(getWebDriver().getCurrentUrl().contains(mainPage.url));
    }*/


    @Test(enabled = true)
    @Story("Non Critical Test")
    public void shouldOpenLoginPage() {
        loginPage.shouldBeOpened();
        loginPage.emailField.shouldBe(visible);
        loginPage.passwordField.shouldBe(visible);
        loginPage.header.shouldBe(visible).shouldHave(text("Welcome to Propeller Championship!"));
        loginPage.loginButton.shouldBe(exist).shouldBe(disabled).shouldHave(text("Hover me faster!\n"));
    }

    @Test
    @Story("Non Critical Test")
    public void shouldCheckLoginButton(){
        loginPage.loginWith(admin);
        loginPage.loginButton.scrollIntoView("{block: \"center\"}").hover();
        loginPage.loginButton.shouldHave(text("Wait for some time..."));
    }


    @Test
    @Story("Non Critical Test")
    public void shouldCheckSignInButtonAppears(){
        loginPage.loginWith(admin);
        loginPage.loginButton.hover();
        loginPage.loginButton.shouldHave(text("Wait for some time...")).should(disappear);
        loginPage.signInButton.shouldBe(visible);
    }

    @Test
    @Story("Non Critical Test")
    public void shouldCheckLoginAlerts(){
        loginPage.loginWith(admin).submit();
        assertEquals(switchTo().alert().getText(), "Are you sure you want to login?");
        switchTo().alert().accept();
        loginPage.preloader.shouldBe(visible);
        assertEquals(switchTo().alert().getText(), "Really sure?");
        switchTo().alert().accept();
    }

    @Test
    @Story("Business Critical Test")
    @Flaky
    @Description("Make full login action")
    public void shouldLoginToMainPage(){
        MainPage page = loginPage.loginWith(admin).submit().confirm();
        page.userButton.shouldBe(visible);
        page.header.shouldBe(visible).shouldHave(text("Articles to read"));
        assertTrue(getWebDriver().getCurrentUrl().contains(page.url));
    }
}
