package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
}
