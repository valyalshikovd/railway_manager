package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.LocomotiveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocomotiveTypeRepository extends JpaRepository<LocomotiveType, Long> {

    LocomotiveType findByName(String name);
}
