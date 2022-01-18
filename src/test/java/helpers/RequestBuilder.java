package helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class RequestBuilder {

    public static RequestSpecBuilder builder;
    public static RequestSpecification reqSpec;

    @BeforeAll
    public static void requestSpecBuilder() {
        builder = new RequestSpecBuilder();

        builder.setBaseUri("https://samples.openweathermap.org/data/2.5/weather");
        builder.addParam("q", "London,uk");
        builder.addParam("appid", "b1b15e88fa797225412429c1c50c122a1");

        reqSpec = builder.build();
    }
}
