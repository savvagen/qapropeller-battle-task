package com.example.e2e;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.testng.GlobalTextReport;
import com.codeborne.selenide.testng.ScreenShooter;
import com.example.BaseTest;
import com.example.listeners.AllureTextReport;
import com.example.listeners.ScreenshotListener;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.example.models.PaymentSystems.*;
import static org.testng.Assert.*;


@Epic("Smoke")
@Feature("Profile Page")
@Listeners({GlobalTextReport.class,
        AllureTextReport.class,
        ScreenShooter.class, ScreenshotListener.class})
public class ProfilePageTests extends BaseTest {

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
        profilePage.open();
    }

    @Test
    @Story("Non Critical Tests")
    @Description("Should open and verify Profile Page")
    public void shouldCheckProfilePage(){
        profilePage.firstNameField.shouldBe(visible);
        profilePage.lastNameField.shouldBe(visible);
        profilePage.saveUserButton.shouldHave(text("Save user info"));
        profilePage.header.shouldBe(visible).shouldHave(text("User profile settings"));
        assertTrue(getWebDriver().getCurrentUrl().contains(profilePage.url));
    }

    @Test
    @Story("Business Critical Tests")
    @Description("Should update profile name")
    public void shouldUpdateUserName(){
        profilePage.updateUserName("Sava", "Henchevskiy");
        profilePage.successAlert.shouldBe(visible).shouldHave(text("User info successfully saved"));
        profilePage.firstNameField.shouldBe(visible).shouldHave(value("Sava"));
        profilePage.lastNameField.shouldBe(visible).shouldHave(value("Henchevskiy"));
        assertEquals(getWebDriver().manage().getCookieNamed("firstName").getValue(), "Sava");
        assertEquals(getWebDriver().manage().getCookieNamed("lastName").getValue(), "Henchevskiy");
        profilePage.shouldBeOpened();
    }

    @Test
    @Story("Business Critical Tests")
    @Description("Should update profile name and save results")
    public void shouldUpdateUserNameAndSaveResults(){
        profilePage.updateUserName("Sava", "Henchevskiy");
        profilePage.successAlert.shouldBe(visible).shouldHave(text("User info successfully saved"));
        mainPage.open().userButton.click();
        profilePage.firstNameField.shouldBe(visible).shouldHave(value("Sava"));
        profilePage.lastNameField.shouldBe(visible).shouldHave(value("Henchevskiy"));
        assertEquals(getWebDriver().manage().getCookieNamed("firstName").getValue(), "Sava");
        assertEquals(getWebDriver().manage().getCookieNamed("lastName").getValue(), "Henchevskiy");
        profilePage.shouldBeOpened();
    }


    @Test
    @Story("Business Critical Tests")
    @Description("Should update profile and payment info")
    public void shouldUpdateUserNameAndPaymentInfo(){
        profilePage.updateUserName("Sava", "Henchevskiy");
        profilePage.successAlert.shouldBe(visible).shouldHave(text("User info successfully saved"));
        profilePage.openPaymentInfo();
        profilePage.updatePaymentInfo("4444", PaymentSystem.MASTER_CARD, 7);
        profilePage.successPaymentAlert.shouldBe(visible).shouldHave(text("Payment info successfully saved"));
        mainPage.open().userButton.click();
        profilePage.firstNameField.shouldBe(visible).shouldHave(value("Sava"));
        profilePage.lastNameField.shouldBe(visible).shouldHave(value("Henchevskiy"));
        profilePage.openPaymentInfo();
        profilePage.cardNumberField.shouldBe(visible).shouldHave(value("4444"));
        profilePage.paymentSystemSelect.shouldBe(visible).shouldHave(value(PaymentSystem.MASTER_CARD.value));
        profilePage.paymentRangeSlider.shouldBe(visible).shouldHave(value("7"));
        assertEquals(getWebDriver().manage().getCookieNamed("firstName").getValue(), "Sava");
        assertEquals(getWebDriver().manage().getCookieNamed("lastName").getValue(), "Henchevskiy");
        assertEquals(getWebDriver().manage().getCookieNamed("cardNumber").getValue(), "4444");
        assertEquals(getWebDriver().manage().getCookieNamed("paymentSystem").getValue(), PaymentSystem.MASTER_CARD.value);
        assertEquals(getWebDriver().manage().getCookieNamed("paymentDay").getValue(), String.valueOf(7));
    }

    @Test(dataProvider = "profilePageInvalidData", dataProviderClass = TestData.class)
    @Story("Non Critical Tests")
    @Description("Should update profile with invalid creds")
    public void shouldUpdateUserNameWithInvalidData(String firstName, String lastName, SelenideElement errorElement, String errorMessage){
        profilePage.updateUserName(firstName, lastName);
        errorElement.shouldBe(visible).shouldHave(text(errorMessage));
        assertNull(getWebDriver().manage().getCookieNamed("firstName"));
        assertNull(getWebDriver().manage().getCookieNamed("lastName"));
        profilePage.shouldBeOpened();
    }

    @Test
    @Story("Business Critical Tests")
    @Description("Should update payment info")
    public void shouldUpdatePaymentInfo(){
        profilePage.openPaymentInfo();
        profilePage.updatePaymentInfo("4444444444444444", PaymentSystem.MASTER_CARD, 17);
        profilePage.successPaymentAlert.shouldBe(visible).shouldHave(text("Payment info successfully saved"));
        assertEquals(getWebDriver().manage().getCookieNamed("cardNumber").getValue(), "4444444444444444");
        assertEquals(getWebDriver().manage().getCookieNamed("paymentSystem").getValue(), PaymentSystem.MASTER_CARD.value);
        assertEquals(getWebDriver().manage().getCookieNamed("paymentDay").getValue(), String.valueOf(17));
    }


    @Test(dataProvider = "profilePageInvalidCardData", dataProviderClass = TestData.class)
    @Story("Non Critical Tests")
    @Description("Should update payment info with invalid payment creds")
    public void shouldNotUpdatePaymentInfoWithInvalidData(String card, PaymentSystem paymentSystem, SelenideElement error, String message){
        profilePage.openPaymentInfo();
        profilePage.updatePaymentInfo(card, paymentSystem, 17);
        error.shouldBe(visible).shouldHave(text(message));
        assertNull(getWebDriver().manage().getCookieNamed("cardNumber"));
        assertNull(getWebDriver().manage().getCookieNamed("paymentSystem"));
        assertNull(getWebDriver().manage().getCookieNamed("paymentDay"));
    }

}
