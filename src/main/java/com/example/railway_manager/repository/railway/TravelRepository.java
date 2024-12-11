package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> {
}
