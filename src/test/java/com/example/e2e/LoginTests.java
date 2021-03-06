package com.example.e2e;

import com.codeborne.selenide.testng.GlobalTextReport;
import com.codeborne.selenide.testng.ScreenShooter;
import com.example.BaseTest;
import com.example.listeners.AllureTextReport;
import com.example.listeners.ScreenshotListener;
import com.example.models.User;
import com.example.pages.MainPage.MainPage;
import io.qameta.allure.*;
import org.testng.annotations.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static org.testng.Assert.*;
import static com.codeborne.selenide.Condition.*;
import static org.fest.assertions.api.Assertions.*;

@Epic("Smoke")
@Feature("Login Page")
@Link(name = "video", type = "selenoid")
@Listeners({GlobalTextReport.class,
        AllureTextReport.class,
        ScreenShooter.class, ScreenshotListener.class})
public class LoginTests extends BaseTest {


    @BeforeMethod
    public void setUp(){
        clearBrowserCache();
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
    @Story("Non Critical Tests")
    @Description("Should open and verify Login Page")
    public void shouldOpenLoginPage() {
        loginPage.shouldBeOpened();
        loginPage.emailField.shouldBe(visible);
        loginPage.passwordField.shouldBe(visible);
        loginPage.header.shouldBe(visible).shouldHave(text("Welcome to Propeller Championship!"));
        loginPage.loginButton.shouldBe(exist).shouldBe(disabled).shouldHave(text("Hover me faster!\n"));
    }

    @Test
    @Story("Non Critical Tests")
    @Description("Should open and check Login Button")
    public void shouldCheckLoginButton(){
        loginPage.loginWith(admin);
        loginPage.loginButton.scrollIntoView("{block: \"center\"}").hover();
        loginPage.loginButton.shouldHave(text("Wait for some time..."));
    }


    @Test
    @Story("Non Critical Tests")
    @Description("Should check Sign In button appears")
    public void shouldCheckSignInButtonAppears(){
        loginPage.loginWith(admin);
        loginPage.loginButton.hover();
        loginPage.loginButton.shouldHave(text("Wait for some time...")).should(disappear);
        loginPage.signInButton.shouldBe(visible);
    }

    @Test
    @Story("Non Critical Tests")
    @Description("Should check alerts on Login Page")
    public void shouldCheckLoginAlerts(){
        loginPage.loginWith(admin).submit();
        assertEquals(switchTo().alert().getText(), "Are you sure you want to login?");
        switchTo().alert().accept();
        loginPage.loader.shouldBe(visible);
        assertEquals(switchTo().alert().getText(), "Really sure?");
        switchTo().alert().accept();
    }

    @Test
    @Story("Business Critical Tests")
    @Flaky
    @Description("Make full login action")
    public void shouldLoginToMainPage(){
        MainPage page = loginPage.loginWith(admin).submit().confirm();
        page.userButton.shouldBe(visible);
        page.header.shouldBe(visible).shouldHave(text("Articles to read"));
        assertTrue(getWebDriver().getCurrentUrl().contains(page.url));
    }

    @Test
    @Story("Business Critical Tests")
    @Flaky
    @Description("Failed test")
    public void failedLoginTest(){
        MainPage page = loginPage.loginWith(new User("test", "invalid")).submit().confirm();
        page.userButton.shouldBe(visible);
        page.header.shouldBe(visible).shouldHave(text("Articles to read"));
        assertTrue(getWebDriver().getCurrentUrl().contains(page.url));
    }

    @Test(enabled = false)
    @Story("Business Critical Tests")
    @Description("Skipped tests")
    public void skippedLoginTest(){
        MainPage page = loginPage.loginWith(new User("test", "invalid")).submit().confirm();
        page.userButton.shouldBe(visible);
        page.header.shouldBe(visible).shouldHave(text("Articles to read"));
        assertTrue(getWebDriver().getCurrentUrl().contains(page.url));
    }


    @Test
    @Story("Non Critical Tests")
    @Description("Check user navigated to empty page in case of invalid login")
    public void shouldNotLoginWithInvalidPassword(){
        User invalidUser = new User("test", "invalid");
        loginPage.open().loginWith(invalidUser).submit().confirm();
        loginPage.loginForm.shouldNot(be(visible));
        assertThat(getWebDriver().getTitle()).isEqualTo(loginPage.title);
        assertThat(loginPage.registerContainer.getAttribute("style")).contains("display: none;");
    }

    @Test
    @Story("Non Critical Tests")
    @Description("Check user navigated to empty page in case of invalid login")
    public void shouldNotLoginWithInvalidLogin() {
        User invalidUser = new User("invalid", "test");
        loginPage.open().loginWith(invalidUser).submit().confirm();
        loginPage.loginForm.shouldNot(be(visible));
        assertEquals(getWebDriver().getTitle(), loginPage.title);
        assertTrue(loginPage.registerContainer.getAttribute("style").contains("display: none;"));
    }
}
