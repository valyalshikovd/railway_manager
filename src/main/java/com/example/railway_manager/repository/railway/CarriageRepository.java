package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.Carriage;
import com.example.railway_manager.model.CarriageType;
import com.example.railway_manager.model.LocomotiveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarriageRepository extends JpaRepository<Carriage, Long> {
}
