package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.Transaction;
import com.maxkhomoshchak.MovieApi.domain.User;
import com.maxkhomoshchak.MovieApi.repository.MovieRepository;
import com.maxkhomoshchak.MovieApi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private MovieRepository movieRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, TransactionRepository transactionRepository) {

        this.movieRepository = movieRepository;
        this.transactionRepository = transactionRepository;
    }

    public boolean checkExistence(Movie movie){
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

        if(checkExistence(movie)){

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

        movieList.sort(Comparator.comparing(Movie::getVoters));

        return movieList;
    }

    @Override
    public List<Transaction> unrateAllUsersMovies(User user) {

        List<Transaction> transactions = transactionRepository.findAllTransactionByUsername(user.getUsername());
        System.out.println(transactions);

        transactions.forEach(this::unrateMovie);

        return transactions;
    }

    private void unrateMovie(Transaction transaction){

        Movie movie = movieRepository.findByName((transaction.getMovieName()).toLowerCase());

        if(movie.getVoters() == 1){
            movieRepository.delete(movie);
        }else{
            double newRate = ((movie.getRate() * movie.getVoters()) - transaction.getRate())/(movie.getVoters() - 1);

            BigDecimal fixedRate = new BigDecimal(newRate);
            fixedRate = fixedRate.setScale(2, RoundingMode.HALF_UP);
            newRate = fixedRate.doubleValue();

            movie.setVoters(movie.getVoters() - 1);
            movie.setRate(newRate);

            movieRepository.save(movie);
        }
    }
}
