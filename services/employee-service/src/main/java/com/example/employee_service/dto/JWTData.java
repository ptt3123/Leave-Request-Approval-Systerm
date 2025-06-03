package com.example.employee_service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JWTData {
    Integer id;
    String name;
    Boolean is_manager;
}