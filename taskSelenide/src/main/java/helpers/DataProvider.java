package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * @author Kidrasov Fagim Fanovich
 * Класс для входных данных, используемых в тестах
 */
public class DataProvider {
    /**
     * Метод для первого теста Задания 2.1
     * @return значения для теста
     */
    public static Stream<Arguments> testCheckArgument(){

        return Stream.of(
                Arguments.of("Смартфоны", "iPhone")
        );
    }
}
