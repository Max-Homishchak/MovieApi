package com.maxkhomoshchak.MovieApi.controller;

import com.maxkhomoshchak.MovieApi.domain.Movie;
import com.maxkhomoshchak.MovieApi.domain.Transaction;
import com.maxkhomoshchak.MovieApi.domain.User;
import com.maxkhomoshchak.MovieApi.service.MovieService;
import com.maxkhomoshchak.MovieApi.service.TransactionService;
import com.maxkhomoshchak.MovieApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ViewController {

    private MovieService movieService;
    private UserService userService;
    private TransactionService transactionService;

    @Autowired
    public ViewController(MovieService movieService, UserService userService, TransactionService transactionService) {

        this.movieService = movieService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String mainView(Model model){
        return "Main";
    }

    @GetMapping("/login")
    public String loginView(){
        return "login_form";
    }

    @GetMapping("/register")
    public String registerView(Model model){
        return "registration_form";
    }

    @GetMapping("/listMovies")
    public String listStudent(Model model) {
        model.addAttribute("movies", movieService.getMovies());
        return "global_top";
    }
    @GetMapping("/myTransactions")
    public String getMyTransactions(@AuthenticationPrincipal User user, Model model) {

        List<Transaction> transactionList = transactionService.getTransactions(user);

        model.addAttribute("times", transactionService.listOfTransactionsTime(transactionList));
        model.addAttribute("transactions", transactionList);
        return "my_transactions";
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

    @PostMapping("/deleteUser")
    public String deleteUser(@AuthenticationPrincipal User user, RedirectAttributes redirectAttributes){

        userService.delete(user);

        return "redirect:/logout";
    }
}
