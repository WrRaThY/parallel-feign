package priv.rdo.feign.parallel.client;

import priv.rdo.feign.parallel.model.Country;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "countries", url = "https://restcountries.eu")
public interface CountryClientFeign {

    @GetMapping(value = "/rest/v2/lang/{language}/?fields=name")
    List<Country> getCountriesByLanguage(@PathVariable String language);

    @GetMapping(value = "/rest/v2/region/{region}/?fields=name")
    List<Country> getCountriesByRegion(@PathVariable String region);
}


