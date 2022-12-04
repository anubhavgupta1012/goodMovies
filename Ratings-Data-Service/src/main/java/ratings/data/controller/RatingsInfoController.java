package ratings.data.controller;

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

    @RequestMapping("user/{userId}")
    public UserRatings getMessage(@PathVariable String userId) {
        List<Ratings> ratings = Arrays.asList(new Ratings("1234", 10), new Ratings("2928", 7));
        return new UserRatings(ratings);
    }
}
