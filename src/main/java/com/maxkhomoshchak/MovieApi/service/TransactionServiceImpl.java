package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.Transaction;
import com.maxkhomoshchak.MovieApi.domain.User;
import com.maxkhomoshchak.MovieApi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

        for(Transaction t: transactions){

            transactionRepository.deleteById(t.getId());
        }
    }
}
