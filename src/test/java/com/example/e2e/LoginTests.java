package com.example.e2e;
import com.codeborne.selenide.Condition;
import com.example.pages.LoginPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;

public class LoginTests extends TestBase {


    @BeforeClass
    public static void setUpClass(){

    }

    @AfterClass
    public static void terDownClass(){

    }

    @Test(testName = "should open login page")
    public void shouldOpenLoginPage(){
        loginPage.open();
        loginPage.emailField.shouldBe(visible);
        loginPage.passwordField.shouldBe(visible);
        loginPage.loginHoverButton.shouldBe(visible).shouldBe(disabled);
    }

}
