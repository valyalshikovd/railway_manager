package com.example.railway_manager.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trains")
@Data
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "train_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "locomotive_id",
            nullable = true)
    private Locomotive locomotive;

    @OneToMany(mappedBy = "train", fetch = FetchType.LAZY)
    private Set<Carriage> carriages = new HashSet<>();

    @OneToMany(mappedBy = "train")
    private Set<Travel> travels = new HashSet<>();
}
