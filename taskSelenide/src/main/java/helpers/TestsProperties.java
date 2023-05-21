package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/test/resources/tests.properties"
})

/**
 * @author Kidrasov Fagim Fanovich
 * Интерфейс для файла tests.properties
 */
public interface TestsProperties extends Config{

    @Key("yandex.url")
    String yandexUrl();

}
