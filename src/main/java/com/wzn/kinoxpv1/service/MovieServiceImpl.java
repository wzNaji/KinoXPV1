package com.wzn.kinoxpv1.service;

import com.wzn.kinoxpv1.entity.Movie;
import com.wzn.kinoxpv1.exception.MovieNotFoundException;
import com.wzn.kinoxpv1.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found. Movie id: " + id));
    }
}
