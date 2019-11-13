package priv.rdo.feign.parallel.client;

import priv.rdo.feign.parallel.model.Country;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class CountryClient {
    private RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<List<Country>> getCountriesByLanguage(String language) throws InterruptedException {
        String url = "https://restcountries.eu/rest/v2/lang/" + language + "?fields=name";
        Country[] response = restTemplate.getForObject(url, Country[].class);

        TimeUnit.SECONDS.sleep(3);

        return CompletableFuture.completedFuture(Arrays.asList(response));
    }

    @Async
    public CompletableFuture<List<Country>> getCountriesByRegion(String region) throws InterruptedException {
        String url = "https://restcountries.eu/rest/v2/region/" + region + "?fields=name";
        Country[] response = restTemplate.getForObject(url, Country[].class);

        TimeUnit.SECONDS.sleep(2);

        return CompletableFuture.completedFuture(Arrays.asList(response));
    }
}


