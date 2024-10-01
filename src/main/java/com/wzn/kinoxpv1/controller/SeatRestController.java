package com.wzn.kinoxpv1.controller;

import com.wzn.kinoxpv1.entity.Seat;
import com.wzn.kinoxpv1.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class SeatRestController {

    private final SeatService seatService;

    public SeatRestController(SeatService seatService) {
        this.seatService = seatService;
    }

    // Get available seats for a movie
    @GetMapping("/{movieId}/seats")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Long movieId) {
        List<Seat> seats = seatService.getAvailableSeatsForMovie(movieId);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    // Reserve selected seats
    @PostMapping("/{movieId}/reserve")
    public ResponseEntity<String> reserveSeats(@PathVariable Long movieId, @RequestBody List<Long> seatIds) {
        boolean success = seatService.reserveSeats(movieId, seatIds);
        if (success) {
            return new ResponseEntity<>("Reservation successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reservation failed", HttpStatus.BAD_REQUEST);
        }
    }
}

