import action.CourierSteps;
import action.PatternCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.ErrorMessages.ERROR_NOT_ENOUGH_CREDENTIALS_FOR_CREATE;
import static constants.ErrorMessages.ERROR_THIS_LOGIN_IS_ALREADY_IN_USE;
import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

public class CreateCourierTest {
    private Courier courier;
    private CourierSteps courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierSteps();
    }

    @Test
    @DisplayName("Successful creation of a courier")
    @Description("Checking the successful creation of a courier /api/v1/courier")
    public void courierCreatedTest() {
        courier = PatternCourier.getRandom();
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals(SC_OK, loginStatusCode);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
        courierId = loginResponse.extract().path("id");
        assertNotNull(courierId);
        boolean isCreated = response.extract().path("ok");
        assertTrue(isCreated);
    }

    @Test
    @DisplayName("Creating an existing courier")
    @Description("Checking for an error call when creating an existing courier /api/v1/courier")
    public void courierCreatedExitingTest() {
        Courier courier = new Courier(
                PatternCourier.getRandom().getLogin(),
                PatternCourier.getRandom().getPassword(),
                PatternCourier.getRandom().getFirstName());
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);
        String responseBody = response.extract().path("message");
        assertEquals(ERROR_THIS_LOGIN_IS_ALREADY_IN_USE, responseBody);
    }

    @Test
    @DisplayName("Creating a courier without a required field")
    @Description("Checking for an error call in the absence of a required field /api/v1/courier")
    public void courierWithoutRequiredField() {
        Courier courier = new Courier(
                PatternCourier.getRandom().getLogin(),
                null,
                PatternCourier.getRandom().getFirstName());
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
        String responseBody = response.extract().path("message");
        assertEquals(ERROR_NOT_ENOUGH_CREDENTIALS_FOR_CREATE, responseBody);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }
}
