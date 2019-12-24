package com.example.pages;

import com.codeborne.selenide.Selenide;

public abstract class WebPage {

    public String url;
    public String title;

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
