package priv.rdo.feign.parallel.resources;

import priv.rdo.feign.parallel.client.CountryClient;
import priv.rdo.feign.parallel.model.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class CountryController {

    private final CountryClient countryClient;

    public CountryController(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    @GetMapping("country")
    public List<String> getAllEuropeanFrenchSpeakingCountries() throws Throwable {
        CompletableFuture<List<Country>> countriesByLanguageFuture = countryClient.getCountriesByLanguage("fr");
        CompletableFuture<List<Country>> countriesByRegionFuture = countryClient.getCountriesByRegion("europe");

        List<String> europeanFrenchSpeakingCountries;
        europeanFrenchSpeakingCountries = countriesByLanguageFuture.get().stream().map(Country::getName).collect(Collectors.toList());
        europeanFrenchSpeakingCountries.retainAll(countriesByRegionFuture.get().stream().map(Country::getName).collect(Collectors.toList()));

        return europeanFrenchSpeakingCountries;
    }
}
