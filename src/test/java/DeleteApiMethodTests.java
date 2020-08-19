import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import objects.Triangles;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static objects.Helpers.*;

public class DeleteApiMethodTests {
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
    public void deleteNonexistentTriangle() {
        given().spec(requestSpec)
                .pathParam("triangleID", "triangleID")
                .when().get("/triangle/{triangleID}").then()
                .assertThat()
                .statusCode(404);  //- ?
    }

    @Test
    public void deleteWithEmptyID() {
        ValidatableResponse firstResponce = tryDeleteTriangle("");
        firstResponce.assertThat()
                .statusCode(404); // its bug: Expected status code <404> but was <405>.
    }

    @Test
    public void deleteDeletedTriangleBefore() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 1, 1, 2, ";");

        String triangleID = given()
                .spec(requestSpec)
                .body(triangle.getTriangle().toString())
                .when().post("/triangle").then()
                .statusCode(200)
                .extract().path("id");


        ValidatableResponse firstResponce = deleteTriangle(triangleID);
        firstResponce.assertThat()
                .statusCode(200);

        ValidatableResponse secondResponce = deleteTriangle(triangleID);
        secondResponce.assertThat()
                .statusCode(404); // its bug: Expected status code <404> but was <200>.
    }

    @Test
    public void wrongHttpMethodForDelete() {
        Triangles triangle = new Triangles();
        triangle.setSide("separator", "input", 1, 1, 2, ";");

        String triangleID = createNewTriangle(triangle);

        ValidatableResponse failedTriangl = tryUseWrongHttpMethodWithId(triangleID);
        failedTriangl.assertThat()
                .statusCode(405);
    }

    @Test
    public void deleteSomeStringTriangle() {
        ValidatableResponse firstResponce = tryDeleteTriangle("»‘~!@#$%^&*()?>,./<][ /*<!—«»♣☺♂");
        firstResponce.assertThat()
                .statusCode(400);
    }

    @Test
    public void deleteSelectSQL() {
        ValidatableResponse firstResponce = deleteTriangle("Select*");
        firstResponce.assertThat()
                .statusCode(400);  // its bug: Expected status code <400> but was <200>.
    }

    @Test
    public void deleteTableSQL() {
        ValidatableResponse firstResponce = deleteTriangle("DROP TABLE Triangle");
        firstResponce.assertThat()
                .statusCode(400);  // its bug: Expected status code <400> but was <200>.
    }

    @Test
    public void deleteXSS() {
        ValidatableResponse firstResponce = tryDeleteTriangle("<script>alert('XSS1')</script>");
        firstResponce.assertThat()
                .statusCode(400);
    }

    @Test
    public void deleteHTML() {
        ValidatableResponse firstResponce = tryDeleteTriangle("<form%20action=»http://live.hh.ru»><input%20type=»submit»></form>");
        firstResponce.assertThat()
                .statusCode(400);
    }
}


