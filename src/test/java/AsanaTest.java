import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AsanaTest extends TestBase{

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
}
