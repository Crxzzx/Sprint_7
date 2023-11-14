package action;

import model.Order;
import net.datafaker.Faker;

public class PatternOrder {
    public static Faker faker = new Faker();

    public static Order getOrder(String[] color) {
        String firstName = faker.name().toString();
        String lastName = faker.name().lastName();
        String address = faker.address().toString();
        String metroStation = faker.number().toString();
        String phone = faker.phoneNumber().toString();
        int rentTime = 10;
        String deliveryDate = "2023-11-15";
        String comment = "Привезите крутой самокат пж";
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}

