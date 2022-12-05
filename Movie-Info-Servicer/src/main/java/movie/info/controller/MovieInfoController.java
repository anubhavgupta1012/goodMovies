package movie.info.controller;

import movie.info.model.Movie;
import movie.info.model.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movie")
public class MovieInfoController {

    @Value("${movieDB.url}")
    private String movieUrl;
    @Value("${api_key}")
    private String api_key;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{movieId}")
    public Movie getMessage(@PathVariable String movieId) {
        String url = movieUrl + movieId + "?api_key=" + api_key;
        MovieSummary body = restTemplate.getForEntity(url, MovieSummary.class).getBody();
        return new Movie(body.getTitle(), body.getOverview());
    }
}
