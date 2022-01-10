import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    @BeforeEach
    public void setUp(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.authentication = RestAssured.oauth2("1/1201634783355717:fb1e7a86e2398a08ee1658d7a9c7beb0\n");
    }
}
