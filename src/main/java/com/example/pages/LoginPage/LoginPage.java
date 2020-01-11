package com.example.pages.LoginPage;

import com.codeborne.selenide.*;
import com.example.models.User;
import com.example.pages.MainPage.MainPage;
import com.example.pages.WebPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

public class LoginPage extends WebPage {

    public SelenideElement header = $("div[class*='card-header'] h4"),
                        loginForm = $(".card-body"),
                        registerContainer = $("#registrationContainer"),
                        emailField = $("#loginInput"),
                        passwordField = $("#passwordInput"),
                        loginButton = $("div[class*='card-footer']").$$("button").filter(visible).last(),
                        signInButton = $("div[class*='card-footer'] img"),
                        loader = $("#loader");

    public LoginPage(){
        this.url = "/index.html";
        this.title = "Welcom to Propeller Automated Testing Championship";
    }


    @Step("Open Login page")
    public LoginPage open(){
        return Selenide.open(this.url, this.getClass());
    }


    @Step("Login With credentials: user = ${user.username}; pass = ${user.password}")
    public LoginPage loginWith(User user){
        emailField.parent().click();
        emailField.setValue(user.getUsername());
        passwordField.find(byXpath("./parent::div")).click();
        passwordField.setValue(user.getPassword());
        return this;
    }

    @Step("Submit login")
    public LoginPage submit(){
        loginButton.hover();
        loginButton.should(disappear);
        signInButton.shouldBe(visible).click();
        return this;
    }

    @Step("Confirm login alerts")
    public MainPage confirm(){
        switchTo().alert().accept();
        loader.shouldBe(visible);
        switchTo().alert().accept();
        return new MainPage();
    }

}
