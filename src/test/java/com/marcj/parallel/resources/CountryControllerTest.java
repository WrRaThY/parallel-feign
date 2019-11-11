package com.marcj.parallel.resources;

import com.marcj.parallel.client.CountryClient;
import com.marcj.parallel.client.output.Country;
import com.marcj.parallel.helper.CountryHelper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.internal.stubbing.answers.Returns;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.marcj.parallel.helper.CountryHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CountryControllerTest {
    @InjectMocks
    private CountryController countryController;

    private CountryClient countryClient;

    @Before
    public void setup() {
        this.countryClient = mock(CountryClient.class);
        this.countryController = new CountryController(countryClient);
    }

    @Test
    public void getAllEuropeanFrenchSpokenCountries() throws Throwable {
        //GIVEN
        doAnswer(new AnswersWithDelay(3000, new Returns(countriesByLanguageResponse())))
                .when(countryClient)
                .getCountriesByLanguage(anyString());

        doAnswer(new AnswersWithDelay(2000, new Returns(countriesByRegionResponse())))
                .when(countryClient)
                .getCountriesByRegion(anyString());

        //WHEN
        long startTime = System.currentTimeMillis();
        List<String> result = countryController.getAllEuropeanFrenchSpeakingCountries();
        long endTime = System.currentTimeMillis();

        //THEN
        assertThat(result).containsExactly(France.getName());
        System.out.println("The test took " + (endTime - startTime) + " ms.");
        assertThat(endTime - startTime).isBetween(5000L, 5500L);
    }
}
