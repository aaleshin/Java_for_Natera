import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import objects.Triangle;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TriangleApiTest {

    RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://qa-quiz.natera.com";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("X-User", "7ef95f96-0f68-446a-8ea1-8b6fd6de894c");
        requestSpec = builder.build();
    }

    @Test
    public void createTriangle() {
        Triangle triangle = new Triangle();
        triangle.setSide("input",3,4,5, ";");
//        triangle.setSideB(3);
//        triangle.setSideC(4);
//        triangle.setSeparator(";");

        int triangleID = given()
                .spec(requestSpec)
                .body(triangle)
                .when().post("/triangle").then()
                .statusCode(201)
                .extract().path("id");
        System.out.println(triangleID);

        given().spec(requestSpec)
                .pathParam("triangleID", triangleID)
                .when().get("/triangle").then()
                .statusCode(200);
    }
}