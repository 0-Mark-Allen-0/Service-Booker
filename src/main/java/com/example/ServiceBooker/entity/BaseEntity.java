package com.example.ServiceBooker.entity;

//Defining all common fields within a base entity - reducing repeating code
//Base Entity becomes a Super Entity
//The other entities will draw from Base

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //PrePersist actions must be performed before saving to DB
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
