package com.wzn.kinoxpv1.repository;

import com.wzn.kinoxpv1.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByMovieId(Long movieId);

}
