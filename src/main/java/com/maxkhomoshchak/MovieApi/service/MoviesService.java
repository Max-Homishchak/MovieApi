package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.dto.Movie;
import com.maxkhomoshchak.MovieApi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MoviesService {

    @Autowired
    private MovieRepository movieRepository;

    public boolean checkExistance(Movie movie){
        return movieRepository.findAll().contains(movie);
    }

    public void submitMovie(Movie movie){

        List<Movie> movieList = movieRepository.findAll();

        String movieName = (movie.getName()).toLowerCase();

        movie.setName(movieName);

        if(movie.getRate() > 10){
            movie.setRate(10);
        }else if(movie.getRate() < 0){
            movie.setRate(0);
        }

        if(checkExistance(movie)){

            Movie movie1 = movieRepository.findByName(movieName);
            Movie movie2 = movieRepository.getOne(movie1.getId());

            int amountOfVoters = movie1.getVoters() + 1;

            double oldRate = movie1.getRate();

            double newRate = ((oldRate * amountOfVoters) + movie.getRate())/(amountOfVoters + 1);

            BigDecimal fixedRate = new BigDecimal(newRate);
            fixedRate = fixedRate.setScale(2, RoundingMode.HALF_UP);

            double resultRate = fixedRate.doubleValue();

            movie2.setName(movieName);
            movie2.setRate(resultRate);
            movie2.setVoters(amountOfVoters);

            movieRepository.save(movie2);
//            movieRepository.updateMovie(movieName, resultRate, amountOfVoters, movie1.getId());

        }else{
            movieRepository.save(Movie.builder()
                    .name(movieName)
                    .rate(movie.getRate())
                    .voters(1)
                    .build());
        }
    }
}
