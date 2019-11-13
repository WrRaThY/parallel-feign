package priv.rdo.feign.parallel.client;

import priv.rdo.feign.parallel.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class CountryService {
    private static final Logger log = LoggerFactory.getLogger(CountryService.class);

    private final CountryClientFeign countryClient;

    public CountryService(CountryClientFeign countryClient) {
        this.countryClient = countryClient;
    }

    @Async
    public CompletableFuture<List<Country>> getCountriesByLanguage(String language) throws InterruptedException {
        log.warn("getCountriesByLanguage sleep start Thread.currentThread(): " + Thread.currentThread());
        TimeUnit.SECONDS.sleep(3);
        log.warn("getCountriesByLanguage sleep end Thread.currentThread(): " + Thread.currentThread());

//        return CompletableFuture.completedFuture(
//                List.of(
//                        Country.builder().name("Belgium").build(),
//                        Country.builder().name("France").build()
//                )
//        );
        return CompletableFuture.completedFuture(countryClient.getCountriesByLanguage(language));

    }

    @Async
    public CompletableFuture<List<Country>> getCountriesByRegion(String region) throws InterruptedException {
        log.warn("getCountriesByRegion sleep start Thread.currentThread(): " + Thread.currentThread());
        TimeUnit.SECONDS.sleep(3);
        log.warn("getCountriesByLanguage sleep end Thread.currentThread(): " + Thread.currentThread());

//        return CompletableFuture.completedFuture(
//                List.of(
//                        Country.builder().name("Germany").build(),
//                        Country.builder().name("Belgium").build(),
//                        Country.builder().name("France").build()
//                )
//        );
        return CompletableFuture.completedFuture(countryClient.getCountriesByRegion(region));
    }
}
