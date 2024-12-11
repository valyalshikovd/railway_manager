package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.Locomotive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocomotiveRepository extends JpaRepository<Locomotive, Long> {
}
