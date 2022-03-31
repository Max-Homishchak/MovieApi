package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.dao.MovieDao;
import com.maxkhomoshchak.MovieApi.dto.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MoviesService {

    private MovieDao movieDao;

    @Autowired
    public MoviesService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public boolean checkExistance(Movie movie){
        return movieDao.findAll().contains(movie);
    }

    public void submitMovie(Movie movie){

        List<Movie> movieList = movieDao.findAll();

        String movieName = (movie.getName()).toLowerCase();

        movie.setName(movieName);

        if(movie.getRate() > 10){
            movie.setRate(10);
        }else if(movie.getRate() < 0){
            movie.setRate(0);
        }

        if(checkExistance(movie)){

            int index = movieList.indexOf(movie);

            int amountOfVoters = movieList.get(index).getVoters();;

            double oldRate = movieList.get(index).getRate();

            double newRate = ((oldRate * amountOfVoters) + movie.getRate())/(amountOfVoters + 1);

            BigDecimal fixedRate = new BigDecimal(newRate);
            fixedRate = fixedRate.setScale(2, RoundingMode.HALF_UP);

            double resultRate = fixedRate.doubleValue();

            movie.setRate(resultRate);
            movie.setVoters(++amountOfVoters);

            movieDao.update(movie, movieName);

        }else{
            movieDao.create(Movie.builder()
                    .name(movieName)
                    .rate(movie.getRate())
                    .build());
        }
    }
}
