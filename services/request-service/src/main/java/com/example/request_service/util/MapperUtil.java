package com.example.request_service.util;

import com.example.request_service.dto.RequestSubmitDTO;
import com.example.request_service.entity.Request;

public class MapperUtil {
    public static Request requestSubmitDTOToRequest(RequestSubmitDTO requestSubmitDTO){
        return Request.builder()
                .start_at(requestSubmitDTO.getStart_at())
                .end_at(requestSubmitDTO.getEnd_at())
                .employee_id(requestSubmitDTO.getEmployee_id())
                .type(requestSubmitDTO.getType())
                .detail(requestSubmitDTO.getDetail())
                .build();
    }
}
