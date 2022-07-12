package com.maxkhomoshchak.MovieApi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "Movies")
public class Movie implements Comparable<Movie>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double rate;
    private int voters;

    @Override
    public boolean equals(Object object) {
        return this.name.equals(((Movie)object).name);
    }

    public int compareTo(Movie movie) {

        return (int)((this.id).compareTo(movie.getId()));
    }

}
