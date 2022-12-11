package movie.catalog.facade;

import movie.catalog.model.UserRatings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Ratings-Data-Service")
public interface RatingsDataProxy {

    @GetMapping("ratings/user/{userId}")    //==>this line should match with the controller API, which is being called from here.
    public UserRatings getUserRatingsViaFeign(@PathVariable("userId") String userId);
}
