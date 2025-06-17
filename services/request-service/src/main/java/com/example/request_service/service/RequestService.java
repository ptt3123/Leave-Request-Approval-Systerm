package com.example.request_service.service;

import com.example.request_service.dto.RequestSubmitDTO;
import com.example.request_service.dto.UpdateStatusDTO;
import com.example.request_service.entity.Request;

import java.util.List;

public interface RequestService {

    void create(RequestSubmitDTO requestSubmitDTO);

    List<Request> readRequestListByEmployeeIdList(List<Integer> employeeIds);

    List<Request> readRequestListByEmployeeId(Integer employeeId);

    void updateRequestStatusById(UpdateStatusDTO updateStatusDTO);

}
