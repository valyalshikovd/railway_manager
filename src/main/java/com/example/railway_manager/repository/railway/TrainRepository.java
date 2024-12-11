package com.example.railway_manager.repository.railway;

import com.example.railway_manager.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Long> {

}
