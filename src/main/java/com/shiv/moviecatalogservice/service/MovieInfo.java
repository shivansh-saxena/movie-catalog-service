package com.shiv.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.shiv.moviecatalogservice.models.CatalogItem;
import com.shiv.moviecatalogservice.models.Movie;
import com.shiv.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    public RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
    threadPoolKey = "movieInfoPool",
    threadPoolProperties = {
            @HystrixProperty(name = "coreSize",value = "20"),
            @HystrixProperty(name = "maxQueueSize",value = "10")
    })
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service:9002/movies/100", Movie.class);
//                    Movie movie = builder.build()
//                            .get()
//                            .uri("http://localhost:9002/movies/id")
//                            .retrieve()
//                            .bodyToMono(Movie.class)
//                            .block();
        return new CatalogItem(movie.getName(), "part 1", rating.getRating());
    }
    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("No Movie getFallbackCatalogItem","Response from fallback",0);
    }
}
