package com.infinityCoder.movie_streaming_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieCatelogService {

    @Autowired
    private RestTemplate restTemplate;

//    using this url and load balancer we are able to connect to the any avaiable instance of the movie-catelogy-service
    private static final String CATELOG_SERVICE="http://movie-catelog-service";

    public String getMoviePath(Long movieInfoId){
//        here we are connecting to the another service
//        RestTemplate enable to give the http request to the another service
        var response= restTemplate.getForEntity(CATELOG_SERVICE+"/movie-info/find-movie-by-id/{movieInfoId}",String.class,movieInfoId);
        return  response.getBody();
    }
}
