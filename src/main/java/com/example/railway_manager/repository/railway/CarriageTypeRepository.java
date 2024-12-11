package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.CarriageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarriageTypeRepository extends JpaRepository<CarriageType, Long> {
    CarriageType findByNameType(String name);
}
