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


    public List<CatalogItem> getData(String userId) {
        UserRatings body = getUserRatings();
        List<Ratings> ratings = body.getRatings();

        List<CatalogItem> list = ratings.parallelStream().map(r -> {

            Movie movie = getMovie(r);
            return new CatalogItem(movie.getMovieId(), movie.getMovieName(), r.getRatings());
        }).collect(Collectors.toList());
        return list;
    }

    /*
     *NOTE : if a method of class is calling another method of same class, then Hystrix won't work.
     *       If a method calling any method of another class then it will work.
     */
    @HystrixCommand(fallbackMethod = "callUserRatingsFallBackMethod")
    private UserRatings getUserRatings() {
        return restTemplate.getForEntity(ratingsurl, UserRatings.class).getBody();
    }

    public UserRatings callUserRatingsFallBackMethod() {
        return new UserRatings(Collections.singletonList(new Ratings("101", 10)));
    }

    @HystrixCommand(fallbackMethod = "callGetMovieFallBackMethod")
    private Movie getMovie(Ratings r) {
        return restTemplate.getForEntity(url + r.getMovieId(), Movie.class).getBody();
    }

    public Movie callGetMovieFallBackMethod(Ratings r) {
        return new Movie("101", "Interstellar");
    }
}
