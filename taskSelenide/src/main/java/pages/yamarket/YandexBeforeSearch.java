package pages.yamarket;

import io.qameta.allure.Step;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;


/**
 * @author Kidrasov Fagim Fanovich
 * Класс содержит методы для выполнения выбора нужного раздела
 */

public class YandexBeforeSearch {

    SelenideElement clickCatalog;
    SelenideElement findNotebook;
    SelenideElement laptopsLink;

    /**
     * Конструктор  инициализирует переменные  кнопки каталога, поля ноутбуки и компрьютеры и поля ноутбуки
     */

    public YandexBeforeSearch() {
        this.clickCatalog = $x("//button[@id='catalogPopupButton']");
        this.findNotebook = $x("//div[@data-zone-name='catalog-content']//span[text() = 'Электроника']");
        this.laptopsLink = $x("//a[text() = 'Смартфоны']");
    }

    /**
     * Метод ожидает загрузки и выполняет нажатие на кнопку каталог
     */

    @Step("Переходим в каталог")
    public void clickCatalog() {
        clickCatalog.shouldBe(visible, Duration.ofSeconds(25));
        clickCatalog.click();
    }

    /**
     * Метод наводит курсор на раздел "ноутбуки" и ожидает подгрузки, далее кликает на раздел "ноутбуки"
     */

    @Step("Наводим курсор на раздел ноутбуков и компьютеров  и ждём прогрузки элементов, после кликаем на ноутбуки")
    public void findAndClick() {
        findNotebook.shouldBe(visible, Duration.ofSeconds(25));
        findNotebook.hover();
        laptopsLink.shouldBe(visible, Duration.ofSeconds(25));
        laptopsLink.click();
    }
}
