package com.example.railway_manager.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "line")
@Data
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "line_id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(
            name = "summary_weight",
            nullable = false
    )
    private Double summary_weight;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
    @OrderColumn(name = "enrollmentLineSegment_order")
    private List<EnrollmentLineSegment> enrollmentLineSegments;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
    @OrderColumn(name = "enrolmentLineStation_order")
    private List<EnrolmentLineStations> enrollmentLineStations;

    @OneToOne(mappedBy = "line", optional  = true)
    private Travel travel;
}
