package com.maxkhomoshchak.MovieApi.controller;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.Transaction;
import com.maxkhomoshchak.MovieApi.domain.User;
import com.maxkhomoshchak.MovieApi.service.MovieService;
import com.maxkhomoshchak.MovieApi.service.TransactionService;
import com.maxkhomoshchak.MovieApi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewController {

//    private MovieService movieService;
    private UserService userService;
    private TransactionService transactionService;

    @Autowired
    public ViewController(/*MovieService movieService,*/ UserService userService, TransactionService transactionService) {

//        this.movieService = movieService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String mainView(Model model){
        return "Main";
    }

    @GetMapping("/register")
    public String registerView(Model model){
        return "registration_form";
    }

    @PostMapping
    public String submit(@AuthenticationPrincipal User user, Movie movie, RedirectAttributes redirectAttributes){

        String s = transactionService.submitTransaction(user, movie);

        redirectAttributes.addFlashAttribute("result", s);

        return "redirect:/";
    }

    @PostMapping("/createUser")
    public String createUser(User user, RedirectAttributes redirectAttributes){

         String s = userService.create(user);

         if(s.equals("")){

             return "redirect:/login";
         }else{

             redirectAttributes.addFlashAttribute("message", s);

             return "redirect:/register";
         }
    }
}
