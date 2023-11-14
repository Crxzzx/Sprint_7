package action;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;

import static constants.HandlesAndUrl.ORDERS;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseUrlAndContentType {
    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseUrlAndContentType())
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseUrlAndContentType())
                .when()
                .get(ORDERS)
                .then();
    }
}

