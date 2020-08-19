import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import objects.Triangles;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static objects.Helpers.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateApiMethodTests {

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
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 1, 1, 2, ";");

        String triangleID = given()
                .spec(requestSpec)
                .body(triangle.getTriangle().toString())
                .when().post("/triangle").then()
                .statusCode(200)
                .extract().path("id");

        given().spec(requestSpec)
                .pathParam("triangleID", triangleID)
                .when().get("/triangle/{triangleID}").then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(triangleID))
                .body("firstSide", equalTo(1.0f))
                .body("secondSide", equalTo(1.0f))
                .body("thirdSide", equalTo(2.0f));
    }

    @Test
    public void createTriangleWithNegativeSideValues() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", -1, -1, -1, ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangl = getNewTriangle(triangleID);
        newTriangl.assertThat()
                .statusCode(200)
                .body("id", equalTo(triangleID))
                .body("firstSide", equalTo(1.0f))
                .body("secondSide", equalTo(1.0f))
                .body("thirdSide", equalTo(1.0f));
    }

    @Test
    public void createTriangleWithFloatSideValues() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 1.6f, -1.45f, -1.337f, ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangl = getNewTriangle(triangleID);
        newTriangl.assertThat()
                .statusCode(200)
                .body("id", equalTo(triangleID))
                .body("firstSide", equalTo(1.6f))
                .body("secondSide", equalTo(1.45f))
                .body("thirdSide", equalTo(1.337f));
    }

//    @Test
//    public void createTriangleWithLargeNumericValueSide() {
//        Triangles triangle = new Triangles();
//        triangle.setSide("separator", "input", 103657657666566336879578978789789678975f, 23637567567356568578958796989705768578f, 33576756777777777777735673575675675675f, ";");
//
//        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
//        failedTriangl.assertThat()
//                .statusCode(422);
//    }

    @Test
    public void createTriangleWithSideMoreThemSumOfTheOther() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 10, 2, 3, ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422);
    }

    @Test
    public void createTriangleWithChangingTheKeyValueForTheSides() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "~!@#$%^&*()?>,./<][ /*<!—«»♣☺♂", 2, 2, 3, ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422); // its bug: Expected status code <422> but was <500>.
    }

    @Test
    public void createTriangleWithInvalidHttpMethod() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 2, 2, 3, ";");

        ValidatableResponse failedTriangl = tryUseWrongHttpMethod(triangle);
        failedTriangl.assertThat()
                .statusCode(405);
    }

    @Test
    public void createTriangleWithNonJson() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 1, 1, 2, ";");

        given()
                .spec(requestSpec)
                .body(triangle.getTriangle())
                .when().post("/triangle").then()
                .statusCode(422);  // its bug: Expected status code <422> but was <500>.
    }

    @Test
    public void createTriangleWithInvalidSeparatorKey() {
        Triangles triangle = new Triangles();
        triangle.setSide("input", "input", 2, 2, 3, ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangl = getNewTriangle(triangleID);
        newTriangl.assertThat()
                .statusCode(200) //??
                .body("id", equalTo(triangleID))
                .body("firstSide", equalTo(2.0f))
                .body("secondSide", equalTo(2.0f))
                .body("thirdSide", equalTo(3.0f));
    }

    @Test
    public void createTriangleWithInvalidSeparator() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 2, 2, 3, "-");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422); // its bug: Expected status code <422> but was <200>.
    }

//    @Test
//    public void createTriangleWithEmptySidesValues() {
//        Triangles triangle = new Triangles();
//        triangle.setSide("input", "input", None, 2, 3, ";");
//
//        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
//        failedTriangl.assertThat()
//                .statusCode(422);
//    }

    @Test
    public void createTriangleWithoutSeparatorValue() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 2, 2, 3, "");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422);
    }

    @Test
    public void createSquare() {
        Triangles triangle = new Triangles();
        triangle.setSquareSide("separator", "input", 1, 1, 2, 3, ";");

        given()
                .spec(requestSpec)
                .body(triangle.getSquare().toString())
                .when().post("/triangle").then()
                .statusCode(422)
                .extract().path("id"); // its bug: Expected status code <422> but was <200>.
    }

    @Test
    public void createTriangleWithZeroBeforeSide() {
        Triangles triangle = new Triangles();
        triangle.setSide("input", "input", 020, 020, 030, ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangl = getNewTriangle(triangleID);
        newTriangl.assertThat()
                .statusCode(200) //??
                .body("id", equalTo(triangleID))
                .body("firstSide", equalTo(20.0f)) // its bug: Expected value 20 but actual 16.
                .body("secondSide", equalTo(20.0f))
                .body("thirdSide", equalTo(30.0f));
    }

    @Test
    public void createTriangleWithZeroOnSide() {
        Triangles triangle = new Triangles();
        triangle.setSide("input", "input", 0, 0, 0, ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422); // its bug: Expected status code <422> but was <200>.

    }

}