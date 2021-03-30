package com.yaree.demo.tests;

import com.yaree.demo.TestBase;
import com.yaree.demo.pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPageTest extends TestBase {

    private final String MY_ACCOUNT_PAGE_URL = "http://heroku-opencart-mysql.herokuapp.com/index.php?route=account/account";

    @Test
    public void positiveScenarioTest() {
        String actualUrl = new HomePage()
                .openLoginPage()
                .login(user)
                .getPageUrl();
        assertEquals(MY_ACCOUNT_PAGE_URL, actualUrl);
    }

    @Test
    public void negativeScenarioTest() {
        String actualUrl = new HomePage()
                .openLoginPage()
                .setLogin("qwe")
                .setPassword("123")
                .clickLoginButton()
                .getPageUrl();
        assertEquals(MY_ACCOUNT_PAGE_URL, actualUrl);
    }

}
