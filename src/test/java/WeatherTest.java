import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class WeatherTest {
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .addParam("q", "London,uk")
            .addParam("appid", "b1b15e88fa797225412429c1c50c122a1")
            .build();


    ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType("application/json")
            .expectBody("wind.speed", is(4.1F))
            .expectBody("sys.id", is(5091))
            .build();

    @Test
    public void getWeatherFromLondon() {
        given()
            .spec(requestSpec)
            .log().all()
        .when()
            .get("https://samples.openweathermap.org/data/2.5/weather")
        .then()
            .spec(responseSpec)
            .log().all();
    }
}
