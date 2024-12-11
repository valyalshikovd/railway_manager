package com.example.railway_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locomotive_types")
@Getter
@Setter
public class LocomotiveType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "locomotive_type_id")
    private Long id;

    @Column(name = "power")
    private Long power;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(
            mappedBy = "locomotiveType",
            fetch = FetchType.LAZY)
    private Set<Locomotive> locomotives = new HashSet<>();
}
