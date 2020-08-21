import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import objects.Triangles;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static objects.Helpers.*;

public class GetApiMethodTests {

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
    public void GetNonexistentTriangle() {
        given().spec(requestSpec)
                .pathParam("triangleID", "triangleID")
                .when().get("/triangle/{triangleID}").then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void GetPreviouslyDeletedID() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "-1", "-1", "-1", ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse firstResponce = deleteTriangle(triangleID);
        firstResponce.assertThat()
                .statusCode(200);

        ValidatableResponse newTriangl = getNewTriangle(triangleID);
        newTriangl.assertThat()
                .statusCode(404);
    }

    @Test
    public void getSomeStringTriangle() {
        ValidatableResponse firstResponce = getNewTriangle("»‘~!@#$%^&*()?>,./<][ /*<!—«»♣☺♂");
        firstResponce.assertThat()
                .statusCode(400);
    }

    @Test
    public void getSelectSQL() {
        ValidatableResponse firstResponce = getNewTriangle("Select*");
        firstResponce.assertThat()
                .statusCode(400);  // its bug: Expected status code <400> but was <404>.
    }

    @Test
    public void getTableSQL() {
        ValidatableResponse firstResponce = getNewTriangle("DROP TABLE Triangle");
        firstResponce.assertThat()
                .statusCode(400);  // its bug: Expected status code <400> but was <404>.
    }

    @Test
    public void getXSS() {
        ValidatableResponse firstResponce = getNewTriangle("<script>alert('XSS1')</script>");
        firstResponce.assertThat()
                .statusCode(400);
    }

    @Test
    public void getHTML() {
        ValidatableResponse firstResponce = getNewTriangle("<form%20action=»http://live.hh.ru»><input%20type=»submit»></form>");
        firstResponce.assertThat()
                .statusCode(400);
    }
}
