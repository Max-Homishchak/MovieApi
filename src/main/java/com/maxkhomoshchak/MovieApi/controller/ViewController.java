package com.maxkhomoshchak.MovieApi.controller;

import com.maxkhomoshchak.MovieApi.dao.MovieDao;
import com.maxkhomoshchak.MovieApi.dto.Movie;
import com.maxkhomoshchak.MovieApi.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    private MovieDao movieDao;
    private MoviesService moviesService;

    @Autowired
    public ViewController(MovieDao movieDao, MoviesService moviesService) {

        this.movieDao = movieDao;
        this.moviesService = moviesService;
    }

    @GetMapping
    public String mainView(){
        return "Main";
    }

    @PostMapping
    public String submit(Movie movie){

        moviesService.submitMovie(movie);

        return "redirect:/";
    }

    @PostMapping("/getMovies")
    public String viewMovies(){

        return "redirect:/MoviesApi";
    }
}
