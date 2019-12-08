package com.example.e2e;

import com.example.TestBase;
import org.junit.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends TestBase {


    @BeforeClass
    public void setupClass(){
        System.out.println("Setting Up Class.");
    }

    @BeforeMethod
    public void setupTest(){
        System.out.println("Setting Up Test.");
    }

    @Test(testName = "Test 1")
    public void test1(){
        assert true;
    }

}
