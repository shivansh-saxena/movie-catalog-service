package com.shiv.moviecatalogservice.resources;

import com.shiv.moviecatalogservice.models.CatalogItem;
import com.shiv.moviecatalogservice.models.Movie;
import com.shiv.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        List<Rating> ratings = Arrays.asList(
                new Rating("mov 1",4),
                new Rating("mov 2",3)
        );
        return ratings.stream().map( rating -> {
                    Movie movie = restTemplate.getForObject("http://localhost:9002/movies/id", Movie.class);
                    return new CatalogItem(movie.getName(),"part 1", rating.getRating());
                }).
                collect(Collectors.toList());

//        return Collections.singletonList(
//                new CatalogItem("Transformers","part 1", 4)
//        );

    }
}
