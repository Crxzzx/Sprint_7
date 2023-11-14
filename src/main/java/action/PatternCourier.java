package action;

import model.Courier;
import net.datafaker.Faker;

public class PatternCourier {
    public static Faker faker = new Faker();

    public static Courier getRandom() {
        String login = faker.name().fullName();
        String password = String.valueOf(faker.password());
        String firstName = faker.name().fullName();

        return new Courier(firstName, login, password);
    }
}

