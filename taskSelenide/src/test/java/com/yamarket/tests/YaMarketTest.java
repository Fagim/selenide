package com.yamarket.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Properties;
import io.qameta.allure.Feature;

import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.yamarket.YandexAfterSearch;
import pages.yamarket.YandexBeforeSearch;
import pages.yamarket.YandexCheckAllResults;

import java.util.stream.Collectors;
import static com.codeborne.selenide.Selenide.*;

/**
 * @author Kidrasov Fagim Fanovich
 * Класс для выполнения тестового сценария
 */

public class YaMarketTest extends BaseTest{

    @Feature("Задание 2.1")
    @DisplayName("Проверка задания 2.1")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#testCheckArgument")
    public void firstSelenide(String checkWord, String modelOfPhone) {

        openYandexCatalogPage();
        clickCatalogLink();
        findAndClickCategory();
        checkPageIsOpen(checkWord);
        clickModel();
        checkResults(modelOfPhone);
    }

    @Step("Открытие страницы яндекс каталог")
    private void openYandexCatalogPage() {
        open(Properties.testsProperties.yandexUrl());
    }

    @Step("Нажатие на ссылку 'Каталог' на главной странице")
    private void clickCatalogLink() {
        YandexBeforeSearch yandexFirstPage = new YandexBeforeSearch();
        yandexFirstPage.clickCatalog();
    }

    @Step("Поиск и клик на категорию товара")
    private void findAndClickCategory() {
        YandexBeforeSearch yandexFirstPage = new YandexBeforeSearch();
        yandexFirstPage.findAndClick();
    }

    @Step("Проверка открытия страницы с текстом '{checkWord}'")
    private void checkPageIsOpen(String checkWord) {
        YandexAfterSearch yandexAfterClick = new YandexAfterSearch();
        Assertions.assertTrue(yandexAfterClick.checkOpenPage(checkWord), "Страница не содержит " + checkWord);
    }

    @Step("Клик на модель")
    private void clickModel() {
        YandexAfterSearch yandexAfterClick = new YandexAfterSearch();
        yandexAfterClick.clickModel();
    }

    @Step("Проверка соответствия предложений фильтру по модели '{modelOfPhone}'")
    private void checkResults(String modelOfPhone) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true));
        YandexCheckAllResults yandexCheckAllResults = new YandexCheckAllResults();
        yandexCheckAllResults.checkPage();
        Assertions.assertTrue(yandexCheckAllResults.getList().stream().allMatch(s -> s.contains(modelOfPhone)),
                "Предложения, которые не соответствуют фильтру" + yandexCheckAllResults.getList().stream().
                        filter(s -> !s.contains(modelOfPhone)).collect(Collectors.toList()));
    }

}
