package com.shiv.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shiv.moviecatalogservice.models.CatalogItem;
import com.shiv.moviecatalogservice.models.Movie;
import com.shiv.moviecatalogservice.models.Rating;
import com.shiv.moviecatalogservice.models.UserRating;
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
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder builder;
    @RequestMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        List<Rating> ratings = restTemplate.getForObject("http://ratings-data-service:9003/ratingsdata/users/"+userId, UserRating.class).getUserRating();
        return ratings.stream().map( rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service:9002/movies/100", Movie.class);
//                    Movie movie = builder.build()
//                            .get()
//                            .uri("http://localhost:9002/movies/id")
//                            .retrieve()
//                            .bodyToMono(Movie.class)
//                            .block();
                    return new CatalogItem(movie.getName(),"part 1", rating.getRating());
                }).
                collect(Collectors.toList());

//        return Collections.singletonList(
//                new CatalogItem("Transformers","part 1", 4)
//        );

    }
    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
        return Arrays.asList(
                new CatalogItem("No Movie","Response from fallback",0)
        );
    }
}
