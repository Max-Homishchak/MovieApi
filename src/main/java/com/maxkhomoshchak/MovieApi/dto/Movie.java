package com.maxkhomoshchak.MovieApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Movie {

    private String name;

    private double rate;

    private int voters = 1;

    @Override
    public boolean equals(Object object) {
        return this.name.equals(((Movie)object).name);
    }

}
