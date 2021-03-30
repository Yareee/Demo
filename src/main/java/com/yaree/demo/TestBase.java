package com.yaree.demo;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.yaree.demo.utils.AllureSelenide;
import com.yaree.demo.utils.TestWatcherRule;
import com.yaree.demo.utils.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@ExtendWith(TestWatcherRule.class)
public abstract class TestBase {

    public static User user;
    private static boolean isHeadless;

    @BeforeAll
    public static void setUp() {
        initializeCredentials();
        selenideConfigurations();

        open(Configuration.baseUrl);

        if (Configuration.headless) {
            getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
        }
    }

    @AfterEach
    public void tearDownMethod() {
        close();
    }

    @AfterAll
    public static void tearDown() {
        addEnvironmentPropertiesToReport();
    }

    private static void initializeCredentials() {
        Properties properties = new Properties();
        try (InputStream inputStream =
                     TestBase.class.getClassLoader().getResourceAsStream("config.properties")) {

            properties.load(inputStream);
            user = User.builder()
                    .setLogin(properties.getProperty("login"))
                    .setPassword(properties.getProperty("password"))
                    .setTimeout(Integer.parseInt(properties.getProperty("timeout")))
                    .setUrl(properties.getProperty("url"))
                    .build();

            isHeadless = properties.getProperty("headless.mode").equals("true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void selenideConfigurations() {
        Configuration.baseUrl = user.getUrl();
        Configuration.timeout = user.getTimeout();
        Configuration.startMaximized = true;
        Configuration.clickViaJs = false;
        Configuration.screenshots = false;
        Configuration.savePageSource = false;
        SelenideLogger.addListener("AllureListener", new AllureSelenide());

        if (isHeadless) {
            Configuration.headless = true;
            Configuration.browserSize = "1920x1080";
        }
    }

    public static void addEnvironmentPropertiesToReport() {
        Properties properties = new Properties();
        Capabilities caps = ((RemoteWebDriver) getWebDriver()).getCapabilities();
        properties.setProperty("BrowserVersion", caps.getBrowserName() + " " + caps.getVersion());
        properties.setProperty("User", user.getLogin());
        properties.setProperty("Platform", caps.getPlatform().name());

        try {
            InetAddress address = InetAddress.getLocalHost();
            properties.setProperty("Host", address.getHostName());
        } catch (UnknownHostException e) {
            System.out.println("Hostname can't be resolved");
        }
        try (OutputStream outputStream = new FileOutputStream("environment.properties")) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
