package com.example.request_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "request")
@Data
@Builder
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate create_at;

    @Column(nullable = false)
    private LocalDate start_at;

    @Column(nullable = false)
    private LocalDate end_at;

    @Column(nullable = false)
    private Integer employee_id;

    @Column(nullable = true)
    private LocalDate update_at;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @PrePersist
    private void preSave() {
        this.create_at = LocalDate.now();
        this.status = Status.PENDING;
    }

    @PreUpdate
    private void preUpdate() {
        this.update_at = LocalDate.now();
    }
}
