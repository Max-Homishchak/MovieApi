package com.maxkhomoshchak.MovieApi.controller;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.User;
import com.maxkhomoshchak.MovieApi.service.MovieService;
import com.maxkhomoshchak.MovieApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    private MovieService movieService;
    private UserService userService;

    @Autowired
    public ViewController(MovieService movieService, UserService userService) {

        this.movieService = movieService;
        this.userService = userService;
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
    public String submit(Movie movie){

        movieService.submitMovie(movie);

        return "redirect:/Success";
    }

    @PostMapping("/createUser")
    public String createUser(User user){

        userService.create(user);

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

    @PostMapping("/logIn")
    public String login(){

        return "redirect:/login";
    }
}
