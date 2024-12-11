package com.example.railway_manager.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "carriages")
@Getter
@Setter
public class Carriage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "carriage_id")
    private Long id;

    @Column(name = "carriage_state")
    private Long carriage_state;

    @Column(name = "date_of_purchase")
    private LocalDate dateOfPurchase;

    @Column(name = "count_of_engine_hours")
    private Long countOfEngineHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "carriage_type_id",
            referencedColumnName = "carriage_type_id")
    private CarriageType carriageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "train_id",
            referencedColumnName = "train_id")
    private Train train;
}