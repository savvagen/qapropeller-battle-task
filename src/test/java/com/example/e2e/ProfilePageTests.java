package com.example.e2e;

import com.codeborne.selenide.SelenideElement;
import com.example.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.example.models.PaymentSystems.*;
import static org.testng.Assert.*;

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
    public void shouldCheckProfilePage(){
        profilePage.firstNameField.shouldBe(visible);
        profilePage.lastNameField.shouldBe(visible);
        profilePage.saveUserButton.shouldHave(text("Save user info"));
        profilePage.header.shouldBe(visible).shouldHave(text("User profile settings"));
        assertTrue(getWebDriver().getCurrentUrl().contains(profilePage.url));
    }

    @Test
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
    public void shouldUpdateUserNameWithInvalidData(String firstName, String lastName, SelenideElement errorElement, String errorMessage){
        profilePage.updateUserName(firstName, lastName);
        errorElement.shouldBe(visible).shouldHave(text(errorMessage));
        assertNull(getWebDriver().manage().getCookieNamed("firstName"));
        assertNull(getWebDriver().manage().getCookieNamed("lastName"));
        profilePage.shouldBeOpened();
    }

    @Test
    public void shouldUpdatePaymentInfo(){
        profilePage.openPaymentInfo();
        profilePage.updatePaymentInfo("4444444444444444", PaymentSystem.MASTER_CARD, 17);
        profilePage.successPaymentAlert.shouldBe(visible).shouldHave(text("Payment info successfully saved"));
        assertEquals(getWebDriver().manage().getCookieNamed("cardNumber").getValue(), "4444444444444444");
        assertEquals(getWebDriver().manage().getCookieNamed("paymentSystem").getValue(), PaymentSystem.MASTER_CARD.value);
        assertEquals(getWebDriver().manage().getCookieNamed("paymentDay").getValue(), String.valueOf(17));
    }


    @Test(dataProvider = "profilePageInvalidCardData", dataProviderClass = TestData.class)
    public void shouldNotUpdatePaymentInfoWithInvalidData(String card, PaymentSystem paymentSystem, SelenideElement error, String message){
        profilePage.openPaymentInfo();
        profilePage.updatePaymentInfo(card, paymentSystem, 17);
        error.shouldBe(visible).shouldHave(text(message));
        assertNull(getWebDriver().manage().getCookieNamed("cardNumber"));
        assertNull(getWebDriver().manage().getCookieNamed("paymentSystem"));
        assertNull(getWebDriver().manage().getCookieNamed("paymentDay"));
    }

}
