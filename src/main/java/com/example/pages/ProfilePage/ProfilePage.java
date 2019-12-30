package com.example.pages.ProfilePage;

import com.codeborne.selenide.SelenideElement;
import com.example.pages.WebPage;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage extends WebPage {

    public SelenideElement header = $("div[class*='card-header']");

    public ProfilePage(){
        this.url = "/profile.html";
        this.title = "";
    }

}
