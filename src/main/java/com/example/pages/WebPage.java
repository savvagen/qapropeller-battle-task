package com.example.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.Assert.assertEquals;

public abstract class WebPage {

    public String url;
    public String title;

    public void shouldBeOpened(){
        assertEquals(getWebDriver().getTitle(), this.title,
                "Page title do not match. \nExpected title: " + this.title);
        assertEquals(getWebDriver().getCurrentUrl(), Configuration.baseUrl + this.url,
                "Page url is not correct.\nExpected URL: " + Configuration.baseUrl + this.url);
    }

//    public <T extends WebPage> T open(Class<T> tClass) {
//        T page = null;
//        try {
//            tClass.newInstance();
//            Selenide.open(page.url);
//            return page;
//        } catch(IllegalAccessError | InstantiationError | InstantiationException | IllegalAccessException error){
//            error.printStackTrace();
//        }
//        return page;
//    }

}
