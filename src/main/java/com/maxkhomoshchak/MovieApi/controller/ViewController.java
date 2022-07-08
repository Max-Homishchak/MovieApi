package com.maxkhomoshchak.MovieApi.controller;

import com.maxkhomoshchak.MovieApi.dao.UserDao;
import com.maxkhomoshchak.MovieApi.dto.Movie;
import com.maxkhomoshchak.MovieApi.dto.User;
import com.maxkhomoshchak.MovieApi.repository.UserRepository;
import com.maxkhomoshchak.MovieApi.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    private MoviesService moviesService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ViewController(MoviesService moviesService) {
        this.moviesService  = moviesService;
    }

    @GetMapping("/")
    public String mainView(){
        return "Main";
    }

    @GetMapping("/Success")
    public String success(){
        return "Success";
    }

    @PostMapping
    public String submit(Movie movie, User user){

        moviesService.submitMovie(movie);

        return "redirect:/Success";
    }

    @PostMapping("/toHomePage")
    public String toHomePage(){

        return "redirect:/";
    }

    @PostMapping("/getMovies")
    public String viewMovies(){

        return "redirect:/MoviesApi";
    }
}
