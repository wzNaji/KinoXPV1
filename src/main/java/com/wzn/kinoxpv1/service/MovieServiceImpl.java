package com.wzn.kinoxpv1.service;

import com.wzn.kinoxpv1.entity.Movie;
import com.wzn.kinoxpv1.entity.Seat;
import com.wzn.kinoxpv1.exception.MovieNotFoundException;
import com.wzn.kinoxpv1.repository.MovieRepository;
import com.wzn.kinoxpv1.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final SeatRepository seatRepository;
    private final MovieRepository movieRepository;

    public MovieServiceImpl(SeatRepository seatRepository, MovieRepository movieRepository) {
        this.seatRepository = seatRepository;
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
    // Create a new movie and generate seats based on theater type
    public Movie createMovie(Movie movie) {
        // Save the movie
        Movie savedMovie = movieRepository.save(movie);

        // Generate seats based on theater type
        if ("large".equalsIgnoreCase(movie.getTheaterType())) {
            generateSeatsForMovie(savedMovie, 25, 16);  // Large theater: 25 rows, 16 seats per row
        } else if ("small".equalsIgnoreCase(movie.getTheaterType())) {
            generateSeatsForMovie(savedMovie, 20, 12);  // Small theater: 20 rows, 12 seats per row
        } else { // Safety :))
            generateSeatsForMovie(savedMovie, 20, 12);  // Default to small if unknown theater type
        }

        return savedMovie;
    }

    // Generate seats for the movie based on rows and seats per row
    private void generateSeatsForMovie(Movie movie, int rows, int seatsPerRow) {
        for (int row = 1; row <= rows; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                Seat seat = new Seat();
                seat.setRowNumber(row);
                seat.setSeatNumber(seatNum);
                seat.setReserved(false);
                seat.setMovie(movie);  // Link the seat to the movie

                // Save the seat to the database
                seatRepository.save(seat);
            }
        }
    }
}
