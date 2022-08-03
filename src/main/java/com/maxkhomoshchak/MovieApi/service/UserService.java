package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.User;

public interface UserService {

    public String create(User user);

    public void delete(User user);
}
