package com.example.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.example.pages.MainPage.MainPage;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.Assert.assertEquals;

public abstract class WebPage {

    public String url;
    public String title;

    public void shouldBeOpened(){
        assertEquals(getWebDriver().getTitle(), this.title, "Page title do not match. \n");
        assertEquals(getWebDriver().getCurrentUrl(), Configuration.baseUrl + this.url, "Page url is not correct.\n");
    }

    public  <T extends WebPage> T open(Class<T> tClass) {
        T page = null;
        try {
            page = Selenide.open(tClass.newInstance().url, tClass);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return page;
    }

    public <T extends WebPage> T open(T page) {
        Class<T> tClass = (Class<T>) page.getClass();
        page = Selenide.open(page.url, tClass);
        return page;
    }

    public void scrollDownElement(SelenideElement element){
        element.scrollIntoView(true);
        Selenide.executeJavaScript("$(arguments[0]).animate({scrollTop: document.body.scrollHeight},\"fast\");", element.getSearchCriteria());
    }


}
