package com.shiv.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableCircuitBreaker
public class MovieCatalogServiceApplication {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate()
	{
//		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =new HttpComponentsClientHttpRequestFactory();
//		httpComponentsClientHttpRequestFactory.setConnectTimeout(1000);
//		httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(1000);
//		return new RestTemplate(httpComponentsClientHttpRequestFactory);
		return new RestTemplate();
	}
	@Bean
	public WebClient.Builder getWebClientBuilder()
	{
		return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
