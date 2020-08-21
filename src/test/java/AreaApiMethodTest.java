import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import objects.Triangles;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static objects.Helpers.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class AreaApiMethodTest {
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
    public void getAreaTriangle() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "1.5", "-1", "2", ";");

        String triangleID = createNewTriangle(triangle);

        given().spec(requestSpec)
                .pathParam("triangleID", triangleID)
                .when().get("/triangle/{triangleID}/area").then()
                .assertThat()
                .statusCode(200)
                .body("result", equalTo(0.72618437f));
    }

    @Test
    public void getNonexistentTriangleArea() {
        ValidatableResponse newTriangle = getTriangleArea("Test");
        newTriangle.assertThat()
                .statusCode(404);
    }

    @Test
    public void getTriangleAreaWithWrongHTTP() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "1.5", "1", "2", ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangle = tryUseWrongHttpMethodForArea(triangleID);
        newTriangle.assertThat()
                .statusCode(405);
    }

    @Test
    public void getPreviouslyDeletedTriangleArea() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "1.5", "1", "2", ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse firstResponse = deleteTriangle(triangleID);
        firstResponse.assertThat()
                .statusCode(200);

        ValidatableResponse newTriangle = getTriangleArea(triangleID);
        newTriangle.assertThat()
                .statusCode(404);
    }

    @Test
    public void getInvalidCharacterArea() {
        ValidatableResponse newTriangle = getTriangleArea("~!@#$%^&*()?>,./<][ /*<!—«»♣☺♂");
        newTriangle.assertThat()
                .statusCode(400);
    }

    @Test
    public void getSelectSQLArea() {
        ValidatableResponse firstResponse = getTriangleArea("Select*");
        firstResponse.assertThat()
                .statusCode(400);   // its bug: Expected status code <400> but was <404>.
    }

    @Test
    public void getTableSQLArea() {
        ValidatableResponse firstResponse = getTriangleArea("DROP TABLE Triangle");
        firstResponse.assertThat()
                .statusCode(400);   // its bug: Expected status code <400> but was <404>.
    }

    @Test
    public void getXSSArea() {
        ValidatableResponse firstResponse = getTriangleArea("<script>alert('XSS1')</script>");
        firstResponse.assertThat()
                .statusCode(400);
    }

    @Test
    public void getHTMLArea() {
        ValidatableResponse firstResponse = getTriangleArea("<form%20action=»http://live.hh.ru»><input%20type=»submit»></form>");
        firstResponse.assertThat()
                .statusCode(400);
    }
}
