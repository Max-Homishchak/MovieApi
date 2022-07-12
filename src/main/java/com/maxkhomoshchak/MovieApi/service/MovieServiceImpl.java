package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean checkExistance(Movie movie){
        return movieRepository.findAll().contains(movie);
    }

    public void submitMovie(Movie movie){

        String movieName = (movie.getName()).toLowerCase();

        movie.setName(movieName);

        if(movie.getRate() > 10){
            movie.setRate(10);
        }else if(movie.getRate() < 0){
            movie.setRate(0);
        }

        if(checkExistance(movie)){

            Movie movie1 = movieRepository.findByName(movieName);

            int amountOfVoters = movie1.getVoters() + 1;


            double oldRate = movie1.getRate();

            double newRate = ((oldRate * (amountOfVoters - 1)) + movie.getRate())/(amountOfVoters);

            BigDecimal fixedRate = new BigDecimal(newRate);
            fixedRate = fixedRate.setScale(2, RoundingMode.HALF_UP);

            newRate = fixedRate.doubleValue();

            movie1.setName(movieName);
            movie1.setRate(newRate);
            movie1.setVoters(amountOfVoters);

            movieRepository.save(movie1);

        }else{
            movieRepository.save(Movie.builder()
                    .name(movieName)
                    .rate(movie.getRate())
                    .voters(1)
                    .build());
        }
    }

    @Override
    public List<Movie> getMovies() {

        List<Movie> movieList = movieRepository.findAll();

        Collections.sort(movieList);

        return movieList;
    }
}
