package helpers;

import org.aeonbits.owner.ConfigFactory;

/**
 * @author Kidrasov Fagim Fanovich
 * Класс для предоставления данных из properties
 */
public class Properties {
    public static TestsProperties testsProperties = ConfigFactory.create(TestsProperties.class);
}
