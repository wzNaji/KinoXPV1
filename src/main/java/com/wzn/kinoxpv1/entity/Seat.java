package com.wzn.kinoxpv1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int rowNumber;
    private int seatNumber;
    private boolean reserved;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

}
