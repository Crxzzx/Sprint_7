import action.OrderClient;
import action.PatternOrder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String[] color;
    private Order order;
    private OrderClient orderClient;

    public CreateOrderTest(String color) {
        this.color = new String[]{color};
    }

    @Parameterized.Parameters
    public static Object[][] setColor() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {""},
                {"BLACK , GREY"}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Creating an order with different colors")
    @Description("Checking the creation of an order with the transfer of the color of the scooter /api/v1/orders")
    public void orderCreatedTest() {
        order = PatternOrder.getOrder(color);
        ValidatableResponse response = orderClient.createOrder(order);

        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
        int track = response.extract().path("track");
        assertNotNull(track);
    }
}
