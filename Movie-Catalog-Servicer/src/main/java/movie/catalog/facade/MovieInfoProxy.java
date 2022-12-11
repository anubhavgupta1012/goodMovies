package movie.catalog.facade;

import movie.catalog.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Movie-Info-Service")
public interface MovieInfoProxy {

    @RequestMapping(value = "movie/{movieId}", method = RequestMethod.GET)
    Movie getMovieViaFeign(@PathVariable String movieId);

}
