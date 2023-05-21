package pages.yamarket;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.HashSet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.*;


/**
 * @author Kidrasov Fagim Fanovich
 * Класс содержит методы для выполнения поиска и получения результатов
 */

public class YandexCheckAllResults {
    SelenideElement spinner;
    SelenideElement pageMore;
    SelenideElement nextButton;
    SelenideElement positionOfModel;
    SelenideElement nextPageButton;
    ElementsCollection pageMoreBoolean;
    ElementsCollection positionOfModels;

    HashSet<WebElement> list = new HashSet<>();
    HashSet<String> stringList = new HashSet<>();


    /**
     * Конструктор  инициализирует поля, используемые для проверки соответствия фильтра на всех страницах
     */

    public YandexCheckAllResults() {
        this.nextPageButton = $x("//button[@data-auto='pager-more']");
        this.pageMore = $x("//button[@data-auto='pager-more']");
        this.pageMoreBoolean = $$x("//button[@data-auto='pager-more']");
        this.nextButton = $x("//div[@data-auto='pagination-next']");
        this.spinner = $x("//span[@data-auto='spinner']");
        this.positionOfModels = $$x("//h3[@data-zone-name='title']");
        this.positionOfModel = $x("//h3[@data-zone-name='title']");
    }

    /**
     * Метод проверяет есть на странице кнопка показать больше
     * @return значение не пустой ли элемент
     */
    public boolean checkNextButton() {
        return !pageMoreBoolean.isEmpty();
    }

    /**
     * Метод нажимает на кпопку "вперёд" и ждёт прогрузки сайта, ожидая изменения значения текущей адресной строки на 1
     */
    public void clickNextButton() {
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        String[] parts = currentUrl.split("page=");

        if (parts.length > 1) {
            int numberPage = Integer.parseInt(parts[1]);
            int expectedPageNumber;
            String expectedUrl;
            expectedPageNumber = numberPage + 1;
            expectedUrl = currentUrl.replace("page=" + numberPage, "page=" + expectedPageNumber);
            nextButton.click();
            Selenide.Wait().until(urlToBe(expectedUrl));

        } else {
            nextButton.click();
            nextButton.shouldBe(visible);
        }
    }

    /**
     * Метод сохраняет в список названия элеменов на всех страницах
     */
    @Step("Загрузка всех элементов")
    public void checkPage() {
        int iterationCheck = 0;

        while (checkNextButton() && iterationCheck < 100) {
            iterationCheck++;
            actions().moveToElement(pageMore).perform();
            getListOfProducts();
            list.clear();
            clickNextButton();
            ElementsCollection checkPageMore = pageMoreBoolean;
            if (checkPageMore.size() > 0) {
                pageMore.shouldBe(enabled, Duration.ofSeconds(25));
            }
            positionOfModel.shouldBe(enabled, Duration.ofSeconds(25));
        }
    }


    /**
     * Метод помещает содержимое полей в список названий позиций, элементы хранятся в формате WebElement,
     * затем поэлментно записывает в список в формате String
     */
    public void getListOfProducts() {
        Selenide.Wait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h3[@data-zone-name='title']")));
        list.addAll($$x("//h3[@data-zone-name='title']"));

        for (WebElement product : list) {
            stringList.add(product.getText());
        }
    }

    /**
     * Метод для получения значений списка в классе для проведения тестов
     * @return возвращает список названий всех элементов
     */

    public HashSet<String> getList() {
        return stringList;
    }

}
