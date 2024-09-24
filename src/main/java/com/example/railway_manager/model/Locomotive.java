package com.example.railway_manager.model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "locomotives")
@Data
public class Locomotive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "locomotive_id")
    private Long id;

    @Column(name = "locomotive_state")
    private Long locomotive_state;

    @Column(name = "date_of_purchase")
    private Date dateOfPurchase;

    @Column(name = "count_of_engine_hours")
    private Long countOfEngineHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "locomotive_type_id",
            referencedColumnName = "locomotive_type_id")
    private LocomotiveType locomotiveType;

    @OneToOne(mappedBy = "locomotive", optional  = true)
    private Train train;
}
