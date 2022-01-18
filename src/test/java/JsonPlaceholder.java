import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class JsonPlaceholder extends TestBase {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String USERS = "users";

    @Test
    public void shouldGetUsers() {
        when()
                .get(BASE_URL + USERS)
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldGetFirstUserUsingPathParam() {
        Response response =
                given()
                        .pathParam("userId", 1)
                        .when()
                        .get(BASE_URL + USERS + "/{userId}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));
    }

    @Test
    public void shouldGetFirstUserUsingQueryParam() {
        given()
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + USERS)
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldDeleteFirstUser() {
        given()
                .when()
                .delete(BASE_URL + USERS + "/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldCreateNewUser() {
        String json = "{\n" +
                "        \"name\": \"Adrian Pawlak\",\n" +
                "        \"username\": \"Bret\",\n" +
                "        \"email\": \"adrianp@april.biz\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Kulas Light\",\n" +
                "            \"suite\": \"Apt. 556\",\n" +
                "            \"city\": \"Gwenborough\",\n" +
                "            \"zipcode\": \"92998-3874\",\n" +
                "            \"geo\": {\n" +
                "                \"lat\": \"-37.3159\",\n" +
                "                \"lng\": \"81.1496\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"phone\": \"1-770-736-8031 x56442\",\n" +
                "        \"website\": \"hildegard.org\",\n" +
                "        \"company\": {\n" +
                "            \"name\": \"Romaguera-Crona\",\n" +
                "            \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "            \"bs\": \"harness real-time e-markets\"\n" +
                "        }\n" +
                "    }";

        Response response = given()
                .contentType("application/json")
                .body(json)
                .when()
                .post(BASE_URL + USERS)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals("Adrian Pawlak", jsonPath.get("name"));
        Assertions.assertEquals("adrianp@april.biz", jsonPath.get("email"));
    }

    @Test
    public void shouldUpdateUser() {
        String user = "{\n" +
                "        \"name\": \"Adrian Pawlak\",\n" +
                "        \"username\": \"Bret\",\n" +
                "        \"email\": \"adrianp@april.biz\",\n" +
                "        \"address\": {\n" +
                "            \"street\": \"Kulas Light\",\n" +
                "            \"suite\": \"Apt. 556\",\n" +
                "            \"city\": \"Gwenborough\",\n" +
                "            \"zipcode\": \"92998-3874\",\n" +
                "            \"geo\": {\n" +
                "                \"lat\": \"-37.3159\",\n" +
                "                \"lng\": \"81.1496\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"phone\": \"1-770-736-8031 x56442\",\n" +
                "        \"website\": \"hildegard.org\",\n" +
                "        \"company\": {\n" +
                "            \"name\": \"Romaguera-Crona\",\n" +
                "            \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "            \"bs\": \"harness real-time e-markets\"\n" +
                "        }\n" +
                "    }";

        Response response = given()
                .contentType("application/json")
                .body(user)
                .when()
                .put(BASE_URL + USERS + "/1")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals("adrianp@april.biz", jsonPath.get("email"));
    }

    @Test
    public void shouldPathUser() {
//        String user = "{\n" +
//                "        \"email\": \"newMail@april.biz\"\n" +
//                "    }";

        JSONObject user = new JSONObject();
        user.put("email", "newMail@april.biz");

        Response response = given()
                .contentType("application/json")
                .body(user)
                .when()
                .patch(BASE_URL + USERS + "/1")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals("newMail@april.biz", jsonPath.get("email"));
    }
}
