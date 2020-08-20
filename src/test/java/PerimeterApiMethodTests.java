import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import objects.Triangles;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static objects.Helpers.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class PerimeterApiMethodTests {
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
    public void getPerimeterTriangle() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 1.5f, -1, 2, ";");

        String triangleID = createNewTriangle(triangle);

        given().spec(requestSpec)
                .pathParam("triangleID", triangleID)
                .when().get("/triangle/{triangleID}/perimeter").then()
                .assertThat()
                .statusCode(200)
                .body("result", equalTo(4.5f));
    }

    @Test
    public void getNonexistentTrianglePerimeter() {
        ValidatableResponse newTriangle = getTrianglePerimeter("Test");
        newTriangle.assertThat()
                .statusCode(404);
    }

    @Test
    public void getTrianglePerimeterWithWrongHTTP() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 1.5f, 1, 2, ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangle = tryUseWrongHttpMethodForPerimeter(triangleID);
        newTriangle.assertThat()
                .statusCode(405);
    }

    @Test
    public void getPreviouslyDeletedTriangle() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 1.5f, 1, 2, ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse firstResponse = deleteTriangle(triangleID);
        firstResponse.assertThat()
                .statusCode(200);

        ValidatableResponse newTriangle = getTrianglePerimeter(triangleID);
        newTriangle.assertThat()
                .statusCode(404);
    }

    @Test
    public void getInvalidCharacterPerimeter() {
        ValidatableResponse newTriangle = getTrianglePerimeter("~!@#$%^&*()?>,./<][ /*<!—«»♣☺♂");
        newTriangle.assertThat()
                .statusCode(400);
    }

    @Test
    public void getSelectSQLPerimeter() {
        ValidatableResponse firstResponse = getTrianglePerimeter("Select*");
        firstResponse.assertThat()
                .statusCode(404);  // ??
    }

    @Test
    public void getTableSQLPerimeter() {
        ValidatableResponse firstResponse = getTrianglePerimeter("DROP TABLE Triangle");
        firstResponse.assertThat()
                .statusCode(404);  // ??
    }

    @Test
    public void getXSSPerimeter() {
        ValidatableResponse firstResponse = getTrianglePerimeter("<script>alert('XSS1')</script>");
        firstResponse.assertThat()
                .statusCode(400);
    }

    @Test
    public void getHTMLPerimeter() {
        ValidatableResponse firstResponse = getTrianglePerimeter("<form%20action=»http://live.hh.ru»><input%20type=»submit»></form>");
        firstResponse.assertThat()
                .statusCode(400);
    }
}
