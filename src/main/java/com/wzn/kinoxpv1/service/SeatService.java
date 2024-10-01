package com.wzn.kinoxpv1.service;

import com.wzn.kinoxpv1.entity.Seat;

import java.util.List;

public interface SeatService {


    List<Seat> getAvailableSeatsForMovie(Long movieId);

    boolean reserveSeats(Long movieId, List<Long> seatIds);

}
