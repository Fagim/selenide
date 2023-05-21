package pages.yamarket;


import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import java.time.Duration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.*;


/**
 * @author Kidrasov Fagim Fanovich
 * Класс содержит методы для устанвки нужного фильтра
 */

public class YandexAfterSearch {

    SelenideElement checkText;
    SelenideElement moreSmartphone;
    SelenideElement scroller;
    SelenideElement spinner;
    SelenideElement pageMore;
    ElementsCollection checkListPhone;
    ElementsCollection positionOfModels;

    /**
     * Конструктор  инициализирует поля, используемые на сайте после выбора каталога
     */

    public YandexAfterSearch() {
        this.checkText = $x("//h1[text() = 'Смартфоны']");
        this.moreSmartphone = $x("//div[contains(@data-zone-data, 'filterName\":\"Производитель')]//div[@data-zone-name='LoadFilterValues']");
        this.scroller = $x("//div[contains(@data-zone-data, 'filterName\":\"Производитель')]//div[@data-test-id='virtuoso-scroller']");
        this.checkListPhone = $$x("//div[contains(@data-zone-data, 'filterName\":\"Производитель')]//span[text() = 'Apple']");
        this.spinner = $x("//span[@data-auto='spinner']");
        this.positionOfModels = $$x("//h3[@data-zone-name='title']");
        this.pageMore = $x("//button[@data-auto='pager-more']");
    }

    /**
     * Метод принимает во входном параметре переменную для сравнения с текущим состояниям поля
     * @param checkWord содержит переменную для сравнения
     * @return результат сравнения текущего поля и входного параметра
     */
    @Step("Проверка, что перешли на нужную страницу")
    public boolean checkOpenPage(String checkWord) {
        checkText.should(visible, Duration.ofSeconds(25));
        return checkText.getText().equals(checkWord);
    }

    /**
     * Метод нажимает на кнопку для разворачивания полного списка моеделй
     * затем производит скролл до момента появления нужной модели
     */
    public void clickModel() {
        moreSmartphone.click();
        scroller.shouldBe(visible, Duration.ofSeconds(15));
        scrollModel();
        scroller.shouldBe(visible, Duration.ofSeconds(15));
        pageMore.shouldBe(enabled, Duration.ofSeconds(25));
    }

    /**
     * Метод  путём передачи в поле со скроллером кнопки PAGE_DOWN
     * происходит проверка есть ли в подргуженных элементах нужные и выбирает их
     * Цикл повторяется пока не выберется нужная модель или пока значение переменной iterationControl < 20
     */

    @Step("Скролл до нужной модели")
    public void scrollModel() {

        boolean checkModel1 = true;
        int iterationControl = 0;
        while (checkModel1 && iterationControl < 20) {

            scroller.shouldBe(visible, Duration.ofSeconds(15));
            checkListPhone = $$x("//div[contains(@data-zone-data, 'filterName\":\"Производитель')]//span[text() = 'Apple']");
            iterationControl++;

            if (!checkListPhone.isEmpty()) {
                actions().moveToElement(checkListPhone.get(0)).click();
                checkListPhone.get(0).click();
                checkModel1 = false;
                spinner.should(visible);
                positionOfModels.shouldBe(sizeGreaterThan(1));
            }
            if (checkModel1) {
                actions().sendKeys(scroller, Keys.PAGE_DOWN).perform();
            }
        }
    }
}
