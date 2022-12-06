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
    private XYZService xyzService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${movie.info.url}")
    private String url;
    @Value("${ratings.info.url}")
    private String ratingsurl;


    public List<CatalogItem> getData(String userId) {
        UserRatings body = xyzService.getUserRatings();
        List<Ratings> ratings = body.getRatings();

        List<CatalogItem> list = ratings.parallelStream().map(r -> {

            Movie movie = xyzService.getMovie(r);
            return new CatalogItem(movie.getMovieId(), movie.getMovieName(), r.getRatings());
        }).collect(Collectors.toList());
        return list;
    }
}
