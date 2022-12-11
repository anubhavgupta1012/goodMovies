package movie.catalog.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import movie.catalog.facade.MovieInfoProxy;
import movie.catalog.facade.RatingsDataProxy;
import movie.catalog.model.Movie;
import movie.catalog.model.Ratings;
import movie.catalog.model.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class XYZService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${movie.info.url}")
    private String url;
    @Value("${ratings.info.url}")
    private String ratingsurl;
    @Autowired
    private RatingsDataProxy ratingsDataProxy;
    @Autowired
    private MovieInfoProxy movieInfoProxy;

    /*
     * Hystrix will only work if we are getting calls from another class.
     */

    @HystrixCommand(fallbackMethod = "callUserRatingsFallBackMethod")
    public UserRatings getUserRatings() {
        //return restTemplate.getForEntity(ratingsurl, UserRatings.class).getBody();
        UserRatings userRatings = ratingsDataProxy.getUserRatingsViaFeign("testUserId");
        return userRatings;
    }

    public UserRatings callUserRatingsFallBackMethod() {
        return new UserRatings(Collections.singletonList(new Ratings("101", 10)));
    }

    @HystrixCommand(fallbackMethod = "callGetMovieFallBackMethod")
    public Movie getMovie(Ratings r) {
        //return restTemplate.getForEntity(url + r.getMovieId(), Movie.class).getBody();
        Movie movieViaFeign = movieInfoProxy.getMovieViaFeign(r.getMovieId());
        return movieViaFeign;
    }

    public Movie callGetMovieFallBackMethod(Ratings r) {
        return new Movie("101", "Interstellar");
    }
}
