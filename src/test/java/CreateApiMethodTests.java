import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import objects.Square;
import objects.Triangles;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static objects.Helpers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertEquals;

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
        triangle.setSide("separator", "input", "1", "1", "2", ";");

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
        triangle.setSide("separator", "input", "-1", "-1", "-1", ";");

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
        triangle.setSide("separator", "input", "1.6", "-1.45", "-1.337", ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangl = getNewTriangle(triangleID);
        newTriangl.assertThat()
                .statusCode(200)
                .body("id", equalTo(triangleID))
                .body("firstSide", equalTo(1.6f))
                .body("secondSide", equalTo(1.45f))
                .body("thirdSide", equalTo(1.337f));
    }

    @Test
    public void createTriangleWithLargeNumericValueSide() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "1036576576665663368795789787897896789751111111111111111", "1036576576665663368795789787897896789751111111111111111", "1036576576665663368795789787897896789751111111111111111", ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(200);
    }

    @Test
    public void createTriangleWithSideMoreThemSumOfTheOther() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "10", "2", "3", ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422);
    }

    @Test
    public void createTriangleWithChangingTheKeyValueForTheSides() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "~!@#$%^&*()?>,./<][ /*<!—«»♣☺♂", "2", "2", "3", ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422); // its bug: Expected status code <422> but was <500>.
    }

    @Test
    public void createTriangleWithInvalidHttpMethod() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "2", "2", "3", ";");

        ValidatableResponse failedTriangl = tryUseWrongHttpMethod(triangle);
        failedTriangl.assertThat()
                .statusCode(405);
    }

    @Test
    public void createTriangleWithNonJson() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "1", "1", "2", ";");

        given()
                .spec(requestSpec)
                .body(triangle.getTriangle())
                .when().post("/triangle").then()
                .statusCode(422);  // its bug: Expected status code <422> but was <500>.
    }

    @Test
    public void createTriangleWithInvalidSeparatorKey() {
        Triangles triangle = new Triangles();
        triangle.setSide("Test", "input", "2", "2", "3", ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangl = getNewTriangle(triangleID);
        newTriangl.assertThat()
                .statusCode(200)
                .body("id", equalTo(triangleID))
                .body("firstSide", equalTo(2.0f))
                .body("secondSide", equalTo(2.0f))
                .body("thirdSide", equalTo(3.0f));
    }

    @Test
    public void createTriangleWithInvalidSeparator() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "22", "22", "32", "-");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422); // its bug: Expected status code <422> but was <200>.
    }

    @Test
    public void createTriangleWithEmptySidesValues() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", null, null, null, ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422);
    }

    @Test
    public void createTriangleWithoutSeparatorValue() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "22", "22", "32", "");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422); // its bug: Expected status code <422> but was <200>.
    }

    @Test
    public void createSquare() {
        Square square = new Square();
        square.setSquareSide("separator", "input", "1", "1", "2", "3", ";");

        given()
                .spec(requestSpec)
                .body(square.getSquare().toString())
                .when().post("/triangle").then()
                .statusCode(422)
                .extract().path("id"); // its bug: Expected status code <422> but was <200>.
    }

    @Test
    public void createTriangleWithZeroBeforeSide() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "020", "020", "030", ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse newTriangl = getNewTriangle(triangleID);
        newTriangl.assertThat()
                .statusCode(200)
                .body("id", equalTo(triangleID))
                .body("firstSide", equalTo(20.0f))
                .body("secondSide", equalTo(20.0f))
                .body("thirdSide", equalTo(30.0f));
    }

    @Test
    public void createTriangleWithZeroOnSide() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "0", "0", "0", ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422); // its bug: Expected status code <422> but was <200>.

    }

    @Test
    public void createTenTriangle() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "1.6", "-1.45", "-1.337", ";");

        List<String> AllTriangles = getAllTriangleValues(getAllTriangles(), "id");
        assertEquals(AllTriangles.size(), 0);

        for (int i = 0; i < 10; i++) {
            String triangleID = createNewTriangle(triangle);
            ValidatableResponse newTriangl = getNewTriangle(triangleID);
            newTriangl.assertThat()
                    .statusCode(200)
                    .body("id", equalTo(triangleID));
        }

        AllTriangles = getAllTriangleValues(getAllTriangles(), "id");
        assertEquals(AllTriangles.size(), 10);
    }

    @Test
    public void createElevenTriangle() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "1.6", "-1.45", "-1.337", ";");

        List<String> AllTriangles = getAllTriangleValues(getAllTriangles(), "id");
        assertEquals(AllTriangles.size(), 0);

        for (int i = 0; i < 10; i++) {
            String triangleID = createNewTriangle(triangle);
            ValidatableResponse newTriangl = getNewTriangle(triangleID);
            newTriangl.assertThat()
                    .statusCode(200)
                    .body("id", equalTo(triangleID));
        }
        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422); // its bug: Expected status code <422> but was <200>.

        AllTriangles = getAllTriangleValues(getAllTriangles(), "id");
        assertEquals(AllTriangles.size(), 10);
    }

    @Test
    public void createTriangleWithTextOnSide() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", "text", "text", "text", ";");

        ValidatableResponse failedTriangl = tryCreateTriangle(triangle);
        failedTriangl.assertThat()
                .statusCode(422);
    }
}