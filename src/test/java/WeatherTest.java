import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WeatherTest extends TestBase {

    @Test
    public void getWeatherFromLondon() {
        given().
                get("?q=London,uk&appid=2b1fd2d7f77ccf1b7de9b441571b39b8").
        then().
                assertThat().
                body("wind.speed", equalTo(4.1f));
    }
}
