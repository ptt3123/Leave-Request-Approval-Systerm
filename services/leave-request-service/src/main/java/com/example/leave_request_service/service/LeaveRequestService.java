package com.example.leave_request_service.service;

import com.example.leave_request_service.dto.Request;
import com.example.leave_request_service.dto.RequestSubmitDTO;
import com.example.leave_request_service.dto.UpdateStatusDTO;

import java.util.List;

public interface LeaveRequestService {

    List<Request> readListByManagerId(Integer managerId);

    void submitRequest(Integer employeeId, RequestSubmitDTO requestSubmitDTO);

    void updateRequest(UpdateStatusDTO updateStatusDTO);

}
