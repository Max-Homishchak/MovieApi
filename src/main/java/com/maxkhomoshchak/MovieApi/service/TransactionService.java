package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.Transaction;
import com.maxkhomoshchak.MovieApi.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    public boolean saveTransaction(User user, Movie movie);

    public String submitTransaction(User user, Movie movie);

    public void deleteTransactions(User user);

    public List<Transaction> getTransactions(User user);

    public List<String> listOfTransactionsTime(List<Transaction> transactions);

}
