import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.DataStore;
import models.Student;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class AsanaTest {
    HashMap<String, String> studentMap = new HashMap<>();

    Student student;

    @Test
    public void shouldGetWorkspaces() {
        when()
                .get("https://app.asana.com/api/1.0/workspaces/").
                then()
                .statusCode(200);
    }

    @Test
    public void shouldGetListOfProjects() {
        when()
                .get("https://app.asana.com/api/1.0/projects/").
                then()
                .statusCode(200);
    }

    @Test
    public void postNewStudent() {
        String requestBody =
                "{\n" +
                        "    \"first_name\": \"Angelina\",\n" +
                        "    \"middle_name\": \"Jolie\",\n" +
                        "    \"last_name\": \"Camila\",\n" +
                        "    \"date_of_birth\": \"01/03/1977\"\n" +
                        "}";

        given()
                .baseUri("http://www.thetestingworldapi.com/")
                .basePath("api/studentsDetails")
                .contentType(ContentType.JSON)
                .log().body().

                when()
                .body(requestBody)
                .post()
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void postNewStudent1() {
        studentMap.put("first_name", "Brad");
        studentMap.put("middle_name", "Janusz");
        studentMap.put("last_name", "Pitt");
        studentMap.put("date_of_birth", "01/03/1977");

        given()
                .baseUri("http://www.thetestingworldapi.com/")
                .basePath("api/studentsDetails")
                .contentType(ContentType.JSON)
                .log().body().

                when()
                .body(studentMap)
                .post()
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void postNewStudent2() {
        student = new Student("Salma", "Joanna", "Hayek", "01/03/1977");

        given()
                .baseUri("http://www.thetestingworldapi.com/")
                .basePath("api/studentsDetails")
                .contentType(ContentType.JSON)
                .log().body().

                when()
                .body(student)
                .post()
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void postNewStudent3() {
        given()
                .baseUri("http://www.thetestingworldapi.com/")
                .basePath("api/studentsDetails")
                .contentType(ContentType.JSON)
                .log().body().

                when()
                .body(new File("src/test/resources/student.json"))
                .post()
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void requestSpec() {
        given()
                .param("q", "London,uk")
                .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                .log().all()
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Test
    public void requestSpecObj() {

        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();
        given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Test
    public void responseSpecObj() {

        ResponseSpecification responseSpec = RestAssured.expect();

        responseSpec
                .log().all()
                .time(Matchers.lessThan(5000L))
                .contentType(ContentType.JSON)
                .statusCode(200);
        given()
                .param("q", "London,uk")
                .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                .log().all().
                when()
                .get("https://samples.openweathermap.org/data/2.5/weather").
                then()
                .spec(responseSpec);
    }

    @Test
    public void getWindSpeed() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        DataStore.WINDSPEED = given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
                .then()
                .log()
                .all()
                .extract().path("wind.speed");
        System.out.println("speedWind " + DataStore.WINDSPEED);
    }

    @Test
    public void getCountry() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        DataStore.COUNTRY = given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
//                .post("https://samples.openweathermap.org/data/2.5/weather" + DataStore.COUNTRY + "/country")
                .then()
                .log()
                .all()
                .extract().path("sys.country");
        System.out.println("Country: " + DataStore.COUNTRY);
    }

    @Test
    public void isSpeedWindIsCorrect() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
//                .post("https://samples.openweathermap.org/data/2.5/weather" + DataStore.COUNTRY + "/country")
                .then()
                .log()
                .all()
                .body("wind.speed", is(4.1f));
    }

    @Test
    public void isCityName() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
//                .post("https://samples.openweathermap.org/data/2.5/weather" + DataStore.COUNTRY + "/country")
                .then()
                .log()
                .all()
                .body("name", equalTo("London"));
    }

    @Test
    public void windSpeed() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
                .then()
                .log()
                .all()
                .body("wind.speed", greaterThan(5.0f));
    }

    @Test
    public void compareWithMap() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
                .then()
                .log()
                .all()
                .body("sys", empty());
    }

    @Test
    public void compareWithMap2() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
                .then()
                .log()
                .all()
                .body("sys", hasValue("GB"));
    }

    @Test
    public void compareWithMap3() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
                .then()
                .log()
                .all();
//                .body(matchesFromFile(student.json)); // znaleźć narzędzie, które porówna jsona z responsem
    }

    @Test
    public void compareWithMap4() {
        RequestSpecification requestSpec =
                given()
                        .header("name", "Adrian")
                        .param("q", "London,uk")
                        .param("appid", "b1b15e88fa797225412429c1c50c122a1")
                        .log().all();


        given()
                .spec(requestSpec)
                .when()
                .get("https://samples.openweathermap.org/data/2.5/weather")
                .then()
                .log()
                .all()
                .body("sys", hasValue("GB")) // znaleźć narzędzie, które porówna jsona z responsem
                .body("name", equalTo("London")); // znaleźć narzędzie, które porówna jsona z responsem
    }
}
