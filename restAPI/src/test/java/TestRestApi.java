import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * Test which check if JsonPlaceHolder Comments endpoint is up
 * and response contain in email "Jayne_Kuhic@sydney.com" entry
 * and comments number is greater than 0
 * Created by wblachut on 2017-05-11.
 */

public class TestRestApi {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
    }

    @Test
    public void makeSureThatCommentsEndpointIsUpAndCheckResponse() {
        given().when().get("/comments").then()
                .statusCode(200)
                .body("email", CoreMatchers.hasItem("Jayne_Kuhic@sydney.com"))
                .body("list.size()", Matchers.greaterThan(0));
    }
}
