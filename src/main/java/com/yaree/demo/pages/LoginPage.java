package com.yaree.demo.pages;

import com.yaree.demo.common.BasePage;
import com.yaree.demo.utils.User;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {

    private final By emailField = By.id("input-email");
    private final By passwordField = By.id("input-password");
    private final By loginButton = By.cssSelector("input.btn.btn-primary");

    public MyAccountPage login(User user) {
        return setLogin(user.getLogin())
                .setPassword(user.getPassword())
                .clickLoginButton();
    }

    @Step("Set login field to {login}")
    public LoginPage setLogin(String login) {
        $(emailField).shouldBe(enabled).setValue(login);
        return this;
    }

    @Step("Set password field to {password}")
    public LoginPage setPassword(String password) {
        $(passwordField).shouldBe(enabled).setValue(password);
        return this;
    }

    @Step("Pressed login button")
    public MyAccountPage clickLoginButton() {
        $(loginButton).shouldBe(enabled).click();
        return new MyAccountPage();
    }

}
