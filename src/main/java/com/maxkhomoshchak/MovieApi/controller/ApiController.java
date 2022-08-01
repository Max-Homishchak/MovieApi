package com.maxkhomoshchak.MovieApi.controller;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    MovieService movieService;

    @Autowired
    public ApiController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/moviesApi")
    public List<Movie> UsersData(){

        return movieService.getMovies();
    }
}