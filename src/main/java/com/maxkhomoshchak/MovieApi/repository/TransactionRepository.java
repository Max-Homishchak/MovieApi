package com.maxkhomoshchak.MovieApi.repository;

import com.maxkhomoshchak.MovieApi.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transactions t WHERE t.user_name = :user_name and LOWER(t.movie_name) = LOWER(:movie_name)", nativeQuery = true)
    Optional<Transaction> findRepeatingTransaction(@Param("user_name") String user_name, @Param("movie_name") String movie_name);

    @Query(value = "SELECT * FROM transactions t WHERE t.user_name = :user_name", nativeQuery = true)
    List<Transaction> findAllTransactionByUsername(@Param("user_name") String user_name);

    void deleteById(Long id);
}
