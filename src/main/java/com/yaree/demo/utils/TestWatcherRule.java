package com.yaree.demo.utils;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestWatcherRule implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        open(baseUrl);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        saveAllureScreenshot();
        getPageSourceBytes();
        open(baseUrl);
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    protected byte[] saveAllureScreenshot() {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/html")
    private byte[] getPageSourceBytes() {
        return getWebDriver().getPageSource().getBytes();
    }


}
