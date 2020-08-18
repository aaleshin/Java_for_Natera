package objects;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Helpers {

    public static RequestSpecification requestSpecification() {
        RestAssured.baseURI = "https://qa-quiz.natera.com";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("X-User", "7ef95f96-0f68-446a-8ea1-8b6fd6de894c");
        return builder.build();
    }

    public static List<String> getAllTriangleValues(ValidatableResponse response, String key) {
        return response.extract()
                .jsonPath()
                .getList(key);
    }

    public static ValidatableResponse getAllTriangles() {
        return given().spec(requestSpecification()).get("/triangle/all").then().log().all();
    }

    public static void deleteAllTriangles() {
        List<String> values = getAllTriangleValues(getAllTriangles(), "id");
        if (values.size() > 0) {
            values.forEach(value -> given().spec(requestSpecification())
                    .pathParam("triangleIDs", value)
                    .delete("/triangle/{triangleIDs}")
                    .then()
                    .log()
                    .all());
        }
    }

    public static String createNewTriangle(Triangles triangle) {
        return given()
                .spec(requestSpecification())
                .body(triangle.getTriangle().toString())
                .when().post("/triangle").then()
                .statusCode(200)
                .extract().path("id");
    }

    public static ValidatableResponse getNewTriangle(String triangleID) {
        return given().spec(requestSpecification())
                .pathParam("triangleID", triangleID)
                .when().get("/triangle/{triangleID}").then()
                .statusCode(200);
    }

    public static ValidatableResponse tryCreateTriangle(Triangles triangle) {
        return given()
                .spec(requestSpecification())
                .body(triangle.getTriangle().toString())
                .when().post("/triangle").then()
                .statusCode(422);
    }

    public static ValidatableResponse tryUseWrongHttpMethod(Triangles triangle) {
        return given()
                .spec(requestSpecification())
                .body(triangle.getTriangle().toString())
                .when().put("/triangle").then()
                .statusCode(405);
    }
}

