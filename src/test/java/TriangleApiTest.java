import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import objects.Triangle;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static objects.Helpers.deleteAllTriangles;
import static objects.Helpers.requestSpecification;
import static org.hamcrest.CoreMatchers.equalTo;

public class TriangleApiTest {

    RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = requestSpecification();
    }

    @AfterTest
    public void clear() {
        deleteAllTriangles();
    }

    @Test
    public void createTriangle() {
        Triangle triangle = new Triangle();
        triangle.setSide("separator", "input", 3, 4, 5, ";");

        String triangleID = given()
                .spec(requestSpec)
                .body(triangle.getTriangle().toString())
                .when().post("/triangle").then()
                .statusCode(200)
                .extract().path("id");

        given().spec(requestSpec)
                .pathParam("triangleID", triangleID)
                .when().get("/triangle/{triangleID}").then()
                .statusCode(200);
    }
}