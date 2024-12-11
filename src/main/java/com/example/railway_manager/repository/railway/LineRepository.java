package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
    Line findByName(String name);
}
