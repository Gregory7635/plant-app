package com.example.plantapp.foundation;

import com.example.plantapp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface IdentificationRepository extends JpaRepository<Identification, Long> {
    List<Identification> findByUserOrderByCreatedAtDesc(User user);

    List<Identification> findByUserId(Long id);
}