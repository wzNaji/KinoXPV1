package com.wzn.kinoxpv1.service;

import com.wzn.kinoxpv1.entity.Movie;
import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
}

