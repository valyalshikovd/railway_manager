package com.example.railway_manager.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "segment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "station1_id",
            referencedColumnName = "station_id")
    public Station stationOne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
          name = "station2_id",
            referencedColumnName = "station_id")
    private Station stationTwo;

    @Column(name = "complexity")
    private Integer complexity;

    @Column(name = "level")
    private Integer level;

    @ManyToMany(mappedBy = "segments")
    private Set<Line> lines =  new HashSet<>();
}
