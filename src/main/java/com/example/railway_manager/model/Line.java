package com.example.railway_manager.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "line")
@Data
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "line_id")
    private Long id;

    @Column(name = "name_line")
    private String name;

    @Column(
            name = "summary_weight",
            nullable = false
    )
    private Double summary_weight;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "line_segment",
            joinColumns = @JoinColumn(name = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id")
    )
    private Set<Segment> segments = new HashSet<Segment>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "line_stantion",
            joinColumns = @JoinColumn(name = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id")
    )
    private Set<Station> stations = new HashSet<Station>();

    @OneToOne(mappedBy = "line", optional  = true)
    private Travel travel;
}
