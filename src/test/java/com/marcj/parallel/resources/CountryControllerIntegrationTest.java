package com.marcj.parallel.resources;

import com.marcj.parallel.helper.CountryHelper;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Test
    public void getAllEuropeanFrenchSpokenCountries() throws Throwable {
        //WHEN
        long startTime = System.currentTimeMillis();
        RestAssured.given()
                .port(port)
            .when()
                .get("/country")
            .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("[1]", Matchers.is(CountryHelper.France.getName()));

        long endTime = System.currentTimeMillis();

        //THEN
        System.out.println("The test took " + (endTime - startTime) + " ms.");
        assertThat(endTime - startTime).isBetween(3000L, 3500L);
    }
}
