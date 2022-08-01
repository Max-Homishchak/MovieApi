package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    public boolean saveTransaction(User user, Movie movie);

    public String submitTransaction(User user, Movie movie);

}
