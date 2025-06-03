package com.example.employee_service.entity;

import com.example.employee_service.util.PasswordUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 15)
    private String username;

    @Column(nullable = false, length = 150)
    private String password;

    @Column(nullable = false)
    private LocalDate DOB;

    @Column(nullable = false)
    private LocalDate create_at;

    @Column(nullable = false, unique = true, length = 10)
    private String phone_number;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Float salary;

    @Column(nullable = false)
    private Boolean is_manager;

    @Column(nullable = false)
    private Integer team_id;

    @PrePersist
    private void preSave() {
        if (this.name != null) this.name = name.trim();
        if (this.username != null) this.username = username.trim();
        if (this.email != null) this.email = email.trim().toLowerCase();
        if (this.phone_number != null) this.phone_number = phone_number.trim();
        if (this.address != null) this.address = address.trim();
        if (this.password != null && !password.startsWith("$2a$")) {
            this.password = PasswordUtil.hashPassword(password);
        }
        this.create_at = LocalDate.now();
    }
}