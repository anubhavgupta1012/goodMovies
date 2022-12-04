package movie.catalog.controller;

import movie.catalog.model.CatalogItem;
import movie.catalog.services.MovieCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private MovieCatalogService movieCatalogService;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getData(@PathVariable String userId) {
        return movieCatalogService.getData(userId);
        //return Collections.singletonList(new CatalogItem("Avengers", "Marvel", 10));
    }
}
