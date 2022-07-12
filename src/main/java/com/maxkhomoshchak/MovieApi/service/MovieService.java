package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    public boolean checkExistance(Movie movie);

    public void submitMovie(Movie movie);

    public List<Movie> getMovies();
}
