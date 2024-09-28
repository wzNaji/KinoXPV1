package com.wzn.kinoxpv1.controller;

import com.wzn.kinoxpv1.entity.Movie;
import com.wzn.kinoxpv1.exception.MovieNotFoundException;
import com.wzn.kinoxpv1.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();

        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        try {
            Movie movie = movieService.getMovieById(id);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (MovieNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // Return 404 if movie not found
        }
    }

}
