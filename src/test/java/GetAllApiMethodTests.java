import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

    @Test
    public void UseWrongHTTPForAll() {
        ValidatableResponse AllTriangles = tryUseWrongHttpMethodForAll();
        AllTriangles.assertThat()
                .statusCode(405);
    }
}
