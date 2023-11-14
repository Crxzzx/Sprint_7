import action.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.apache.http.HttpStatus.*;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("The list of orders is not empty")
    @Description("Checking the list of orders for emptiness")
    public void orderListIsNotEmptyTest() {
        ValidatableResponse response = orderClient.getOrderList();

        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);

        List<String> bodyAnswer = response.extract().path("orders");
        assertFalse(bodyAnswer.isEmpty());
    }
}

