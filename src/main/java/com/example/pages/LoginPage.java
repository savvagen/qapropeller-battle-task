package com.example.pages;

import com.codeborne.selenide.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import sun.applet.Main;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static org.testng.Assert.*;

public class LoginPage extends WebPage {

    public SelenideElement header = $("div[class*='card-header'] h4"),
                        emailField = $("#loginInput"),
                        passwordField = $("#passwordInput"),
                        loginButton = $("div[class*='card-footer']").$$("button").filter(visible).last(),
                        signInButton = $("div[class*='card-footer'] img"),
                        preloader = $("#loader");

    public LoginPage(){
        this.url = "/";
        this.title = "Welcom to Propeller Automated Testing Championship";
    }

    public LoginPage open(){
        return Selenide.open(this.url, this.getClass());
    }

    public LoginPage shouldBeOpened(){
        assertEquals(WebDriverRunner.getWebDriver().getTitle(), this.title);
        return this;
    }

    public LoginPage submitLogin(String username,String password){
        emailField.parent().click();
        emailField.val("test");
        passwordField.find(byXpath("./parent::div")).click();
        passwordField.setValue("test");
        loginButton.hover();
        loginButton.should(disappear);
        signInButton.shouldBe(visible).click();
        return this;
    }

    public MainPage confirmLogin(){
        switchTo().alert().accept();
        preloader.shouldBe(visible);
        switchTo().alert().accept();
        return new MainPage();
    }

    public MainPage login(String username, String pass){
        return submitLogin(username, pass).confirmLogin();
    }

}
