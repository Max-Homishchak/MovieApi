package com.maxkhomoshchak.MovieApi.repository;

import com.maxkhomoshchak.MovieApi.dto.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByName(String name);
}
