package com.example.plantapp.control.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PlantDto {

    private Long id;

    @NotBlank(message = "Название обязательно")
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 5000)
    private String description;

    private String wikipediaUrl;

    public PlantDto() {
    }

    public PlantDto(Long id, String name,
                    String description,
                    String wikipediaUrl) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.wikipediaUrl = wikipediaUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Название обязательно") @Size(min = 2, max = 100) String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Название обязательно") @Size(min = 2, max = 100) String name) {
        this.name = name;
    }

    public @Size(max = 5000) String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 5000) String description) {
        this.description = description;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }
}