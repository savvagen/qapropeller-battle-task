package com.example.pages;

import com.codeborne.selenide.*;
import com.example.models.User;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.WebDriverRunner.*;
import static org.testng.Assert.*;

public class LoginPage extends WebPage {

    public SelenideElement header = $("div[class*='card-header'] h4"),
                        emailField = $("#loginInput"),
                        passwordField = $("#passwordInput"),
                        loginButton = $("div[class*='card-footer']").$$("button").filter(visible).last(),
                        signInButton = $("div[class*='card-footer'] img"),
                        preloader = $("#loader");

    public LoginPage(){
        this.url = "";
        this.title = "Welcome to Propeller Automated Testing Championship";
    }

    public LoginPage open(){
        return Selenide.open(this.url, this.getClass());
    }


    @Step
    public LoginPage loginWith(User user){
        emailField.parent().click();
        emailField.val(user.getUsername());
        passwordField.find(byXpath("./parent::div")).click();
        passwordField.val(user.getPassword());
        return this;
    }

    @Step
    public LoginPage submit(){
        loginButton.hover();
        loginButton.should(disappear);
        signInButton.shouldBe(visible).click();
        return this;
    }

    @Step
    public MainPage confirm(){
        switchTo().alert().accept();
        preloader.shouldBe(visible);
        switchTo().alert().accept();
        return new MainPage();
    }

}
