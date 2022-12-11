package ratings.data.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ratings.data.model.Ratings;
import ratings.data.model.UserRatings;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsInfoController {

    @GetMapping("user/{userId}")
    public UserRatings getMessage(@PathVariable("userId") String userId) {
        List<Ratings> ratings = Arrays.asList(new Ratings("100", 10), new Ratings("101", 7));
        return new UserRatings(ratings);
    }
}
