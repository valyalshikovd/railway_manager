package com.example.railway_manager.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "travel")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "travel_id")
    private Long id;

    @Column(name = "dateDeparture")
    private Date dateDeparture;

    @Column(name = "state")
    private String state;

    @Column(name = "travelTime", nullable = true)
    private Long travelTime;

    @Column(name = "arrivalTime", nullable = true)
    private Long arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "train_id",
            referencedColumnName = "train_id")
    private Train train;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "line_id",
            nullable = false)
    private Line line;
}
