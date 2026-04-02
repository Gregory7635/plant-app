package com.example.plantapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "identifications")
public class Identification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;
    private double probability;
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Plant plant;

    // getters/setters
}