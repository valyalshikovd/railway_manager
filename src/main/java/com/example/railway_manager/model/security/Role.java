package com.example.railway_manager.model.security;


import com.example.railway_manager.model.Line;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @Column(name = "role_name", unique = true, nullable = false)
    private String rolename;
    @ManyToMany(mappedBy = "roles")
    private Set<Users> users =  new HashSet<>();
}
