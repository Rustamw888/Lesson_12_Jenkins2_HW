package tests.homework;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CredentialsConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {

    @BeforeAll
    public static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
        String login = config.login();
        String password = config.password();

        String propertyBrowser = System.getProperty("browser", "chrome");
        String propertyVersion = System.getProperty("version", "100");

        String propertyMainPageUrl = System.getProperty("propertyMainPageUrl", "https://demoqa.com");
        String propertyBrowserSize = System.getProperty("propertyBrowserSize", "1980x1080");
        String propertySelenoidUrl = System.getProperty("propertySelenoidUrl", "selenoid.autotests.cloud");

        Configuration.baseUrl = propertyMainPageUrl;
        Configuration.browserSize = propertyBrowserSize;
        Configuration.remote = "https://" + login + ":" + password + "@" + propertySelenoidUrl + "/wd/hub";
        Configuration.browser = propertyBrowser;
        Configuration.browserVersion = propertyVersion;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    public void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.attachAsText("Text", "test text");
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
