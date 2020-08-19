import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import objects.Triangles;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static objects.Helpers.*;

public class GetAllApiMethodTests {


    RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = requestSpecification();
    }

    @Test
    public void GetAnswerIfNoTriangles() {
        ValidatableResponse AllTriangles = getAllTriangles();
        AllTriangles.assertThat()
                .statusCode(200);
    }
}
