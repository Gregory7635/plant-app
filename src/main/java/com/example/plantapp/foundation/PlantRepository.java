package com.example.plantapp.foundation;

import com.example.plantapp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    Plant findByName(String name);

    List<Plant> findTop10ByOrderByCreatedAtDesc();
}
