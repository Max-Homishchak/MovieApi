package com.maxkhomoshchak.MovieApi.controller;

import com.maxkhomoshchak.MovieApi.dao.MovieDao;
import com.maxkhomoshchak.MovieApi.dto.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ApiController {

    private MovieDao movieDao;

    @Autowired
    public ApiController(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @GetMapping("/MoviesApi")
    public List<Movie> UsersData(){

        return movieDao.findAll();
    }
}
