package priv.rdo.feign.parallel.resources;

import priv.rdo.feign.parallel.client.CountryService;
import priv.rdo.feign.parallel.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class CountryControllerFeign {
    private static final Logger log = LoggerFactory.getLogger(CountryControllerFeign.class);

    private final CountryService countryService;

    public CountryControllerFeign(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("feign-country")
    public List<String> getAllEuropeanFrenchSpeakingCountries() throws Throwable {
        log.warn("launch completable futures Thread.currentThread(): " + Thread.currentThread());

        CompletableFuture<List<Country>> countriesByLanguageFuture = countryService.getCountriesByLanguage("fr");
        CompletableFuture<List<Country>> countriesByRegionFuture = countryService.getCountriesByRegion("europe");

        log.warn("collect results Thread.currentThread(): " + Thread.currentThread());
        List<String> europeanFrenchSpeakingCountries;
        europeanFrenchSpeakingCountries = countriesByLanguageFuture.get().stream().map(Country::getName).collect(Collectors.toList());
        europeanFrenchSpeakingCountries.retainAll(countriesByRegionFuture.get().stream().map(Country::getName).collect(Collectors.toList()));

        return europeanFrenchSpeakingCountries;
    }
}
