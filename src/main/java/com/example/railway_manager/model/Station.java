package com.example.railway_manager.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "stations")
@Data
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "station_id")
    private Long id;

    @Column(name = "station_name")
    private String name;

    @OneToMany(mappedBy = "stationOne")
    private Set<Segment> segmentsOne = new HashSet<>();

    @OneToMany(mappedBy = "stationTwo")
    private Set<Segment> segmentsTwo = new HashSet<>();

    @OneToMany(mappedBy = "station", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<EnrolmentLineStations> enrollments;
}
