package com.yaree.demo.common;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import com.yaree.demo.pages.HomePage;
import com.yaree.demo.pages.LoginPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BasePage {

    private final By myAccountLocator = By.cssSelector(".list-inline > li > a.dropdown-toggle");
    private final By myAccountDropdownOptions = By.cssSelector(".dropdown-menu-right li");
    private final By logoLocator = By.cssSelector("#logo img");

    @Step("Go to home page")
    public HomePage openHomePage() {
        $(logoLocator).shouldBe(enabled).click();
        return new HomePage();
    }

    @Step("Go to Login Page")
    public LoginPage openLoginPage() {
        $(myAccountLocator).shouldBe(exist).click();
        //TODO: implement generatePicklistOptions method or class to avoid code duplicate for similar logic
        for (SelenideElement element : $$(myAccountDropdownOptions).shouldHave(CollectionCondition.sizeGreaterThan(1))) {
            //TODO: available picklist params should be stored in enum
            if (element.getText().equals("Login")) {
                element.click();
                return new LoginPage();
            }
        }
        throw new RuntimeException("Unable to reach Login Page");
    }

}
