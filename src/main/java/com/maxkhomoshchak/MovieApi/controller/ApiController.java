package com.maxkhomoshchak.MovieApi.controller;

import com.maxkhomoshchak.MovieApi.dto.Movie;
import com.maxkhomoshchak.MovieApi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ApiController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/MoviesApi")
    public List<Movie> UsersData(){

        return movieRepository.findAll();
    }
}
