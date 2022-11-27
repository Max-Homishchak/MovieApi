package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.Transaction;
import com.maxkhomoshchak.MovieApi.domain.User;
import com.maxkhomoshchak.MovieApi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;
    private MovieService movieService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, MovieService movieService) {
        this.transactionRepository = transactionRepository;
        this.movieService = movieService;
    }

    @Override
    public boolean saveTransaction(User user, Movie movie) {

        AtomicBoolean flag = new AtomicBoolean(true);

        Calendar calendar = Calendar.getInstance();

        Date date = calendar.getTime();

        Optional<Transaction> validatingTransaction = transactionRepository.findRepeatingTransaction(user.getUsername(), movie.getName());

        validatingTransaction.ifPresentOrElse((value) -> {
            flag.set(true); }, () -> {
            flag.set(false);} );

        boolean result = flag.get();

        if(movie.getRate() > 10) {
            movie.setRate(10);
        }else if(movie.getRate() < 0){
            movie.setRate(0);
        }

        if(!result){
            Transaction transaction = Transaction.builder()
                    .userName(user.getUsername())
                    .movieName(movie.getName())
                    .rate(movie.getRate())
                    .creationTime(date.getTime())
                    .build();


            transactionRepository.save(transaction);
        }

        return result;
    }

    @Override
    public String submitTransaction(User user, Movie movie) {

        String result = "";

        if(saveTransaction(user, movie)){
            result += "You have already rated this movie";
        }else{
            result += "You have successfully rated this movie";
            movieService.submitMovie(movie);
        }

        return result;
    }

    public void deleteTransactions(User user){

        List<Transaction> transactions = movieService.unrateAllUsersMovies(user);

        transactions.forEach(t -> transactionRepository.deleteById(t.getId()));
    }

    @Override
    public List<Transaction> getTransactions(User user) {

        return transactionRepository.findAllTransactionByUsername(user.getUsername());
    }

    public List<String> listOfTransactionsTime(List<Transaction> transactions){

        List<String> result = new ArrayList<>();

        transactions.forEach(t -> result.add(getDifferenceOfTime(t.getCreationTime())));

        return result;
    }

    private String getDifferenceOfTime(long creationTime){

        String result = "";

        Calendar calendar = Calendar.getInstance();

        Date date = calendar.getTime();

        int s = ((int)(date.getTime() - creationTime))/1000;

        int m = s / 60;
        int h = m / 60;
        int d = h / 24;
        int mn = d / 30;
        int y = mn / 12;

        if(y >= 1){
            result += y + " years ago";
        }else if(mn >= 1){
            result += mn + " months ago";
        }else if(d >= 1){
            result += d + " days ago";
        }else if(h >= 1){
            result += h + " hours ago";
        }else if(m >= 1){
            result += m + " minutes ago";
        }else if(s >= 1){
            result += s + " seconds ago";
        }else{
            result += "just now";
        }
        return result;
    }
}
