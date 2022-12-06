package movie.catalog.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import movie.catalog.model.CatalogItem;
import movie.catalog.model.Movie;
import movie.catalog.model.Ratings;
import movie.catalog.model.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieCatalogService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${movie.info.url}")
    private String url;
    @Value("${ratings.info.url}")
    private String ratingsurl;

    @HystrixCommand(fallbackMethod = "callFallBackMethod")
    public List<CatalogItem> getData(String userId) {
        UserRatings body = restTemplate.getForEntity(ratingsurl, UserRatings.class).getBody();
        List<Ratings> ratings = body.getRatings();

        List<CatalogItem> list = ratings.parallelStream().map(r -> {

            Movie movie = restTemplate.getForEntity(url + r.getMovieId(), Movie.class).getBody();
            return new CatalogItem(movie.getMovieId(), movie.getMovieName(), r.getRatings());
        }).collect(Collectors.toList());
        return list;
    }

    public List<CatalogItem> callFallBackMethod(String userId) {
        // This fallback method should be simple so that it again doesn't create any chaos.
        //to test this, if movie-info/ ratings-data service is down then we can this callback method will be called (that's
        // the default fallback mechanism is decided because we haven't configured the conditions for ,circuit trip).
        return Collections.singletonList(new CatalogItem("testName", "testDesc", 10));
    }
}
