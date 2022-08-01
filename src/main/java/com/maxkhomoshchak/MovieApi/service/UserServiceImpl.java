package com.maxkhomoshchak.MovieApi.service;

import com.maxkhomoshchak.MovieApi.domain.User;
import com.maxkhomoshchak.MovieApi.domain.UserRole;
import com.maxkhomoshchak.MovieApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String create(User user){

        String message = "";

        if(userRepository.findByUserName(user.getUsername()).isEmpty() && userRepository.findByEmail(user.getEmail()).isEmpty() && user.getPassword().length() >= 6 && checkEmail(user.getEmail())){
            userRepository.save(User.builder()
                    .userName(user.getUsername())
                    .password(bCryptPasswordEncoder.encode(user.getPassword()))
                    .email(user.getEmail())
                    .active(true)
                    .userRole(UserRole.ROLE_USER)
                    .build());

        }else if(user.getPassword().length() < 6){
            message += "Password is too short";
        }else if(!checkEmail(user.getEmail())){
            message += "Email is not valid";
        }else if(userRepository.findByUserName(user.getUsername()).isPresent() && userRepository.findByEmail(user.getEmail()).isEmpty()){
            message += "This username is already used";
        }else if(userRepository.findByUserName(user.getUsername()).isEmpty() && userRepository.findByEmail(user.getEmail()).isPresent()){
            message += "This email is already used";
        }else{
            message += "This email and username are already used";
        }

        return message;

    }

    private boolean checkEmail(String email){

        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            return userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
