package com.wzn.kinoxpv1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class MovieController {

    @GetMapping("/movies")
    public String showListOfMovies() {
        return "forward:/movies.html";
    }

    @GetMapping("/movies/{id}")
    public String showMovieDetails(@PathVariable Long id) {
        return "forward:/movieDetails.html";  // Serve static movieDetails.html
    }
}
