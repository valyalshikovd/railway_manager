package com.example.railway_manager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carriage_types")
@Getter
@Setter
public class CarriageType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "carriage_type_id")
    private Long id;

    @Column(name = "capacity")
    private Long capacity; //

    @Column(name = "nameType", unique = true)
    private String nameType; //

    @OneToMany(mappedBy = "carriageType",
            fetch = FetchType.LAZY)
    private Set<Carriage> carriages = new HashSet<>();
}
