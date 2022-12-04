package movie.info.controller;

import movie.info.model.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieInfoController {

    @RequestMapping("/{movieId}")
    public Movie getMessage(@PathVariable String movieId) {
        return new Movie(movieId, "Avengers - Age of Ultron");
    }
}
