package priv.rdo.feign.parallel.resources;

import priv.rdo.feign.parallel.helper.CountryHelper;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryControllerIntegrationFeignTest {
    private static final Logger log = LoggerFactory.getLogger(CountryControllerIntegrationFeignTest.class);

    @LocalServerPort
    private int port;

    /**
     * two sleeps combined = 6000 ms
     * checking if the response got returned in 5000 ms, because there needs to be some buffer over 3k for:
     * - serialization/deserialization
     * - calling the external system
     */
    @Test
    public void getAllEuropeanFrenchSpokenCountries() throws Throwable {
        //WHEN
        long startTime = System.currentTimeMillis();
        RestAssured.given()
                .port(port)
                .when()
                .get("/feign-country")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("[1]", Matchers.is(CountryHelper.France.getName()));

        long endTime = System.currentTimeMillis();

        //THEN
        log.warn("The test took " + (endTime - startTime) + " ms.");
        assertThat(endTime - startTime).isLessThan(5000);

    }
}
