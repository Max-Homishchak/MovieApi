package com.maxkhomoshchak.MovieApi.dao;

import com.maxkhomoshchak.MovieApi.dto.Movie;
import com.maxkhomoshchak.MovieApi.mapper.MovieMapper;
import com.maxkhomoshchak.MovieApi.provider.ConnectionProvider;
import com.maxkhomoshchak.MovieApi.provider.SimpleConnectionProvider;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.List;

@Component
public class MovieDao {

    private MovieMapper movieMapper = new MovieMapper();
    private ConnectionProvider provider = new SimpleConnectionProvider();

    public Movie create(Movie movie){
        try(Connection connection = provider.getConnection();
            PreparedStatement st = connection.prepareStatement("INSERT INTO moviestable(movie, rate, voters)" +
                    "VALUES(?, ?, 1)")
        ){

            BigDecimal fixedRate = new BigDecimal(movie.getRate());
            fixedRate = fixedRate.setScale(2, RoundingMode.HALF_UP);

            double resultRate = fixedRate.doubleValue();

            st.setString(1, movie.getName());
            st.setDouble(2, resultRate);

            st.executeUpdate();

            ResultSet generatedKey = st.getGeneratedKeys();
            generatedKey.next();

            return movie;

        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }

    public List<Movie> findAll(){

        try(Connection connection = provider.getConnection();
            Statement st = connection.createStatement()
        ){

            return movieMapper.mapList(st.executeQuery("SELECT movie, rate, voters FROM moviestable"));

        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }

    public Movie findByName(String name){
        try(Connection connection = provider.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT movie, rate, voters FROM moviestable WHERE movie=?");
        ){
            st.setString(1, name);
            return movieMapper.mapMovie(st.executeQuery());

        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }

    public Movie update(Movie newMovie, String movieName){
        try(Connection connection = provider.getConnection();
            PreparedStatement st = connection.prepareStatement("UPDATE moviestable " +
                    "SET movie = ?, rate = ?, voters = ?" +
                    "WHERE movie = ?;")
        ){


            st.setString(1, newMovie.getName());
            st.setDouble(2, newMovie.getRate());
            st.setInt(3, newMovie.getVoters());
            st.setString(4, movieName);

            System.out.println(newMovie.toString());

            st.executeUpdate();

            return newMovie;

        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }
}
