package com.example.balance_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "balance")
@Data
@Builder
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer balance;

    @Column(nullable = false)
    private Integer employee_id;

    @Column(nullable = false)
    private LocalDate create_at;

    @Column(nullable = true)
    private LocalDate update_at;

    @PrePersist
    private void preSave() {
        this.create_at = LocalDate.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.update_at = LocalDate.now();
    }

}
