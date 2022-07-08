package com.maxkhomoshchak.MovieApi.repository;

import com.maxkhomoshchak.MovieApi.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
