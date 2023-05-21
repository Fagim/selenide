package com.yamarket.tests;

import allure.yamarket.CustomAllureSelenide;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.lang.reflect.Method;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;


/**
 * @author Kidrasov Fagim Fanovich
 * Класс для работы настройки работы с браузером и задания необходимых конфигураций до тестов и после их выполнения
 */

public class BaseTest {

    /**
     * Метод добавляет Listener к SelenideLogger в зависимости от наличия аннотации @Step над тестом
     * @param testInfo объект, содержащий информацию о текущем тесте
     */
    @BeforeEach
    public void screenshotCondition(TestInfo testInfo){
        if (isStep(testInfo)) {
            SelenideLogger.addListener("AllureSelenide", new CustomAllureSelenide()
                    .screenshots(true));
        } else {
            SelenideLogger.addListener("AllureSelenide", new CustomAllureSelenide()
                    .screenshots(false));
        }
    }

    /**
     * Метод возвращает содержит ли тестовый метод аннтоацию @Step
     * @param testInfo объект, содержащий информацию о текущем тесте
     * @return содержит значение в зависимости от присутствия аннтоации @Step
     */
    public static boolean isStep(TestInfo testInfo) {
        Method testMethod = testInfo.getTestMethod().orElse(null);
        return testMethod != null && testMethod.isAnnotationPresent(Step.class);
    }


    /**
     * Метод предназначен для установки конфигурации браузера перед тестами, а также настройки работы Selenide
     */

    @BeforeAll
    public static void setup(){
        Configuration.timeout=30000;
        Configuration.browser="chrome";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-blink-features");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        Configuration.browserCapabilities = capabilities;

        System.setProperty("webdriver.chrome.driver",System.getenv("CHROME_DRIVER"));
        System.setProperty("selenide.versatile_report.enabled", "true");

        WebDriver driver = new ChromeDriver(options);
        setWebDriver(driver);
        WebDriverRunner.setWebDriver(driver);

    }

    /**
     * Метод удаляет Listener после каждого теста
     */

    @AfterEach
    public void afterEach() {
        SelenideLogger.removeListener("AllureSelenide");
    }
}
