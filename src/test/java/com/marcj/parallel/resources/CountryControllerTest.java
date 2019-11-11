package com.marcj.parallel.resources;

import com.marcj.parallel.client.CountryClient;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.internal.stubbing.answers.Returns;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.marcj.parallel.helper.CountryHelper.countriesByLanguageResponse;
import static com.marcj.parallel.helper.CountryHelper.countriesByRegionResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryControllerTest {
    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryClient countryClient = mock(CountryClient.class);

    private MockMvc mockMvc;

    @Test
    public void getAllEuropeanFrenchSpokenCountries() throws Throwable {
        //WHEN
        long startTime = System.currentTimeMillis();
        ResultActions result = mockMvc.perform(get("/country")).andDo(print());
        long endTime = System.currentTimeMillis();

        //THEN
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]", Matchers.is("France")));

        System.out.println("The test took " + (endTime - startTime) + " ms.");
        assertThat(endTime - startTime).isBetween(5000L, 5500L);
    }

    @Before
    public void startService() throws Exception {
        doAnswer(new AnswersWithDelay(3000, new Returns(countriesByLanguageResponse())))
                .when(countryClient)
                .getCountriesByLanguage(anyString());

        doAnswer(new AnswersWithDelay(2000, new Returns(countriesByRegionResponse())))
                .when(countryClient)
                .getCountriesByRegion(anyString());

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }
}
