package com.shiv.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shiv.moviecatalogservice.models.CatalogItem;
import com.shiv.moviecatalogservice.models.Movie;
import com.shiv.moviecatalogservice.models.Rating;
import com.shiv.moviecatalogservice.models.UserRating;
import com.shiv.moviecatalogservice.service.MovieInfo;
import com.shiv.moviecatalogservice.service.RatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private WebClient.Builder builder;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private RatingInfo ratingInfo;

    @RequestMapping("/{userId}")
//    @HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        List<Rating> ratings = ratingInfo.getUserRating(userId);
        return ratings.stream().map( rating ->  movieInfo.getCatalogItem(rating))
                        .collect(Collectors.toList());

//        return Collections.singletonList(
//                new CatalogItem("Transformers","part 1", 4)
//        );

    }


//    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
//        return Arrays.asList(
//                new CatalogItem("No Movie","Response from fallback",0)
//        );
//    }
}
