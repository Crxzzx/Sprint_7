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

import static constants.ErrorMessages.ERROR_ACCOUNT_NOT_FIND;
import static constants.ErrorMessages.ERROR_NOT_ENOUGH_CREDENTIALS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LoginCourierTest {
    private Courier courier;
    private CourierSteps courierSteps;
    private int courierId;

    @Before
    public void setUp() {
        courier = PatternCourier.getRandom();
        courierSteps = new CourierSteps();
        courierSteps.create(courier);
    }

    @Test
    @DisplayName("Successful authorization")
    @Description("Verification of successful authorization")
    public void courierAuthPositiveTest() {
        ValidatableResponse response = courierSteps.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        assertEquals(200, statusCode);
        courierId = response.extract().path("id");
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Authorization with an incorrect password")
    @Description("Checking the error call in case of incorrect password transmission")
    public void courierAuthIncorrectPassTest() {
        CourierCredentials data = new CourierCredentials(courier.getLogin(), "IncorrectPassword");
        ValidatableResponse response = courierSteps.login(data);
        courierId = courierSteps.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = response.extract().statusCode();
        assertEquals(404, statusCode);
        String answer = response.extract().path("message");
        assertEquals(ERROR_ACCOUNT_NOT_FIND, answer);
    }

    @Test
    @DisplayName("Authorization with an incorrect username")
    @Description("Checking the error call in case of incorrect login transfer")
    public void courierAuthIncorrectLoginTest() {
        CourierCredentials data = new CourierCredentials("IncorrectLogin", courier.getPassword());
        ValidatableResponse response = courierSteps.login(data);
        courierId = courierSteps.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = response.extract().statusCode();
        assertEquals(404, statusCode);
        String answer = response.extract().path("message");
        assertEquals(ERROR_ACCOUNT_NOT_FIND, answer);
    }

    @Test
    @DisplayName("Authorization with an empty required field")
    @Description("Checking for an error call when passing a missing required field")
    public void courierAuthEmptyPassword() {
        CourierCredentials data = new CourierCredentials(courier.getLogin(), "");
        ValidatableResponse response = courierSteps.login(data);
        courierId = courierSteps.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = response.extract().statusCode();
        assertEquals(400, statusCode);
        String answer = response.extract().path("message");
        assertEquals(ERROR_NOT_ENOUGH_CREDENTIALS, answer);
    }

    @After
    public void tearDown() {
        courierSteps.delete(courierId);
    }

}

