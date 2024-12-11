package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
