package com.yaree.demo.pages;

import com.codeborne.selenide.WebDriverRunner;
import com.yaree.demo.common.BasePage;
import io.qameta.allure.Step;

public class MyAccountPage extends BasePage {

    @Step("Get url of currently opened page")
    public String getPageUrl() {
        return WebDriverRunner.url();
    }

}
