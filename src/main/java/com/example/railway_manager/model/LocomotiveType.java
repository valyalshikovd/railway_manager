package com.example.railway_manager.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locomotive_types")
public class LocomotiveType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "locomotive_type_id")
    private Long id;

    @Column(name = "power")
    private Long power;

    @OneToMany(
            mappedBy = "locomotiveType",
            fetch = FetchType.LAZY)
    private Set<Locomotive> locomotives = new HashSet<>();
}
