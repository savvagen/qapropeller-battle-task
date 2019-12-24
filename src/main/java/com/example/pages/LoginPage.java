package com.example.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

public class LoginPage extends WebPage {

    public SelenideElement emailField = $("#loginInput"),
                        passwordField = $("#passwordInput"),
                        loginHoverButton = $(byText("Hover me faster!\n"));

    public LoginPage(){
        this.url = "/";
        this.title = "Welcom to Propeller Automated Testing Championship";
    }

    public LoginPage open(){
        return Selenide.open(this.url, this.getClass());
    }

}
