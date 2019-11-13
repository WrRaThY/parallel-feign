package priv.rdo.feign.parallel.helper;

import priv.rdo.feign.parallel.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CountryHelper {
    public static final Country France = Country.builder().name("France").build();
    public static final Country Belgium = Country.builder().name("Belgium").build();
    public static final Country Germany = Country.builder().name("Germany").build();

    public static CompletableFuture<List<Country>> countriesByLanguageResponse() {
        List<Country> countriesByLanguage = new ArrayList<>();
        countriesByLanguage.add(France);
        countriesByLanguage.add(Belgium);

        return CompletableFuture.completedFuture(countriesByLanguage);
    }

    public static CompletableFuture<List<Country>> countriesByRegionResponse() {
        List<Country> countriesByRegion = new ArrayList<>();
        countriesByRegion.add(France);
        countriesByRegion.add(Germany);

        return CompletableFuture.completedFuture(countriesByRegion);
    }
}
