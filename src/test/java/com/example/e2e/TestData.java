package com.example.e2e;

import com.example.BaseTest;
import org.testng.annotations.DataProvider;
import static com.example.models.PaymentSystems.*;

public class TestData extends BaseTest {

    @DataProvider(name = "profilePageInvalidData")
    public Object[][] invalidNames(){
        return new Object[][]{
                { "", "test", profilePage.firstNameError, "\n" +
                        "                                            Please set your first name.\n" +
                        "                                        " },
                { "test", "", profilePage.lastNameError, "\n" +
                        "                                            Please set your last name.\n" +
                        "                                        " }
        };
    }

    @DataProvider(name = "profilePageInvalidCardData")
    public Object[][] invalidCards(){
        return new Object[][]{
                {"", PaymentSystem.VISA, profilePage.cardError, "\n" +
                        "                                            Please set your card number.\n" +
                        "                                        "},
                {"111", PaymentSystem.NONE, profilePage.paymentSystemError, "\n" +
                        "                                            Please select your payment system.\n" +
                        "                                        "}
        };
    }

}
