package com.example.leave_request_service.controller;

import com.example.leave_request_service.dto.Request;
import com.example.leave_request_service.dto.RequestSubmitDTO;
import com.example.leave_request_service.dto.UpdateStatusDTO;
import com.example.leave_request_service.service.LeaveRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/l")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/get-my-employee-pending-requests")
    public ResponseEntity<List<Request>> readMyEmployeePendingRequests(
            @RequestHeader("X-User-Id") String userId){

        Integer mangerId = Integer.parseInt(userId);
        return ResponseEntity.ok(leaveRequestService.readListByManagerId(mangerId));
    }

    @PostMapping("/submit-request")
    public ResponseEntity<Void> submitRequest(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid RequestSubmitDTO requestSubmitDTO){

        Integer employeeId = Integer.parseInt(userId);
        leaveRequestService.submitRequest(employeeId, requestSubmitDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-request-status")
    public ResponseEntity<Void> updateRequestStatus(
            @RequestBody @Valid UpdateStatusDTO updateStatusDTO) {

        leaveRequestService.updateRequest(updateStatusDTO);
        return ResponseEntity.ok().build();
    }
}
