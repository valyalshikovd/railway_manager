package com.example.railway_manager.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "travel")
public class Travel {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "travel_id")
    @Setter
    private Long id;

    @Getter
    @Column(name = "dateDeparture")
    @Setter
    private Instant dateDeparture;

    @Getter
    @Column(name = "state")
    @Setter
    private String state;

    @Getter
    @Column(name = "travelTime", nullable = true)
    @Setter
    private Instant travelTime;

    @Getter
    @Column(name = "arrivalTime", nullable = true)
    @Setter
    private Instant arrivalTime;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "train_id",
            referencedColumnName = "train_id")
    private Train train;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "line_id",
            nullable = false)
    private Line line;
}
