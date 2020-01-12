package com.example.pages.ProfilePage;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.pages.WebPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.example.models.PaymentSystems.*;

public class ProfilePage extends WebPage {

    public SelenideElement header = $("div[class*='card-header']"),
                        firstNameField = $("#firstNameInput"),
                        lastNameField = $("#lastNameInput"),
                        saveUserButton = $("button[onclick='saveUserInfo()']"),
                        savePaymentButton = $("button[onclick='savePaymentInfo()']"),
                        successAlert = $(".modal-alerts #successUserInfoSaveInfo"),
                        firstNameError = $("#firstNameInput").parent().$("div.invalid-feedback"),
                        lastNameError = $("#lastNameInput").parent().$("div.invalid-feedback"),
                        userProfileTabLink = $("#v-pills-home-tab"),
                        paymentInfoTabLink = $("#v-pills-messages-tab"),
                        cardNumberField = $("#cardNumberInput"),
                        cardError = $("#cardNumberInput").parent().$("div.invalid-feedback"),
                        paymentSystemSelect = $("#paymentSystemSelect"),
                        paymentSystemError = $("#paymentSystemSelect").parent().$("div.invalid-feedback"),
                        successPaymentAlert = $(".modal-alerts #successPaymentInfoSaveInfo"),
                        paymentRangeSlider = $("#paymentRange");


    public ElementsCollection navigationBar = $$("#v-pills-tab a[class^='nav-link']");



    public ProfilePage(){
        this.url = "/profile.html";
        this.title = "";
    }

    @Step
    public ProfilePage open(){
        return open(this);
    }

    @Step
    public ProfilePage openUserInfo(){
        userProfileTabLink.shouldBe(visible).click();
        userProfileTabLink.shouldBe(visible)
                .shouldHave(attribute("class", "nav-link active show"));
        return this;
    }

    @Step
    public ProfilePage openPaymentInfo(){
        paymentInfoTabLink.click();
        paymentInfoTabLink.shouldBe(visible)
                .shouldHave(attribute("class", "nav-link active show"));
        return this;
    }

    @Step
    public ProfilePage updateUserName(String firstName, String lastName){
        firstNameField.shouldBe(visible).val(firstName);
        lastNameField.val(lastName);
        saveUserButton.click();
        return this;
    }

    @Step
    public ProfilePage updatePaymentInfo(String cardNumber, PaymentSystem paymentSystem, int paymentDay){
        cardNumberField.shouldBe(visible).setValue(cardNumber);
        paymentSystemSelect.selectOptionContainingText(paymentSystem.name);
        int max = Integer.valueOf(paymentRangeSlider.getAttribute("max"));
        int min = Integer.valueOf(paymentRangeSlider.getAttribute("min"));
        String style = paymentRangeSlider.getAttribute("style");
        int pixs = Integer.valueOf(style.substring(6, style.length() - 3).trim());
        int pixsStep = Math.round(pixs / (max - min));
        int day = paymentDay;
        if (paymentDay > ((pixs / 2) / pixsStep)) {
            day = paymentDay - 1;
        }
        int dayPixs = ((day) * pixsStep) - (pixs / 2);
        actions().dragAndDropBy(paymentRangeSlider, dayPixs, 0).build().perform();
        savePaymentButton.click();
        return this;
    }


}
