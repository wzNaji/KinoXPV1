package com.wzn.kinoxpv1.service;

import com.wzn.kinoxpv1.entity.Movie;
import com.wzn.kinoxpv1.entity.Seat;
import com.wzn.kinoxpv1.repository.MovieRepository;
import com.wzn.kinoxpv1.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService{

    private final MovieRepository movieRepository;
    private final SeatRepository seatRepository;

    public SeatServiceImpl(MovieRepository movieRepository, SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Seat> getAvailableSeatsForMovie(Long movieId) {
        return seatRepository.findByMovieId(movieId);
    }

    @Override
    public boolean reserveSeats(Long movieId, List<Long> seatIds) {
        // Find the seats that the user wants to reserve using the seat IDs
        List<Seat> seatsToReserve = seatRepository.findAllById(seatIds);
        // Check if all the seats are available (i.e., none of them are reserved)
        for (Seat seat : seatsToReserve) {
            if (seat.isReserved()) {
                // If any seat is already reserved, return false (reservation fails)
                return false;
            }
        }
        // If all seats are available, mark them as reserved
        for (Seat seat : seatsToReserve) {
            seat.setReserved(true);  // Set the seat as reserved
        }
        // Save the updated seat information to the database
        seatRepository.saveAll(seatsToReserve);
        //Return true to indicate the reservation was successful
        return true;
    }


}
