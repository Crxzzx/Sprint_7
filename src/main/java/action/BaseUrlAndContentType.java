package action;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static constants.HandlesAndUrl.BASE_URL;


public class BaseUrlAndContentType {
    public RequestSpecification getBaseUrlAndContentType() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}

