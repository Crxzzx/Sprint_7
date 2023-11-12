package action;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;

import static constants.HandlesAndUrl.COURIER;
import static io.restassured.RestAssured.given;

public class CourierSteps extends BaseUrlAndContentType {
    @Step("Логин курьера")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseUrlAndContentType())
                .body(credentials)
                .when()
                .post(COURIER + "login")
                .then();
    }

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseUrlAndContentType())
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseUrlAndContentType())
                .when()
                .delete(COURIER + courierId)
                .then();
    }
}

