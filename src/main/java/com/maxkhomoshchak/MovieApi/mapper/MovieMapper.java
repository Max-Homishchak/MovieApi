package com.maxkhomoshchak.MovieApi.mapper;

import com.maxkhomoshchak.MovieApi.dto.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieMapper {

    public List<Movie> mapList(ResultSet resultSet){

        List<Movie> movieList = new ArrayList<>();

        try(resultSet){
            while(resultSet.next()){

                Movie selectedUser = getBuild(resultSet);

                movieList.add(selectedUser);
            }
        }catch (SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
        return movieList;
    }

    public Movie mapMovie(ResultSet resultSet){

        try(resultSet){
            resultSet.next();
            return getBuild(resultSet);
        }catch (SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
    }

    private Movie getBuild(ResultSet resultSet) throws SQLException {
        return Movie.builder()
                .name(resultSet.getString("movie"))
                .rate(resultSet.getDouble("rate"))
                .voters(resultSet.getInt("voters"))
                .build();
    }
}
