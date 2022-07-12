package com.maxkhomoshchak.MovieApi.repository;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByName(String name);
}
