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


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void create(User user){

        userRepository.save(User.builder()
                .userName(user.getUsername())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .eMail(user.getEMail())
                .active(true)
                .userRole(UserRole.ROLE_USER)
                .build());

    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            return userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
