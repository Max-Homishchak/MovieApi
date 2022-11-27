package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.Transaction;
import com.maxkhomoshchak.MovieApi.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    public boolean checkExistence(Movie movie);

    public void submitMovie(Movie movie);

    public List<Movie> getMovies();

    public List<Transaction> unrateAllUsersMovies(User user);
}
