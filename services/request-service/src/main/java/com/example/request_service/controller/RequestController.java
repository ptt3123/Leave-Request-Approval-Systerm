package com.example.request_service.controller;

import com.example.request_service.dto.RequestSubmitDTO;
import com.example.request_service.dto.UpdateStatusDTO;
import com.example.request_service.entity.Request;
import com.example.request_service.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/r")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/read/{requestId}")
    public ResponseEntity<Request> read(@PathVariable Integer requestId){
        return ResponseEntity.ok(requestService.read(requestId));
    }

    @PostMapping("create-request")
    public ResponseEntity<Void> create(@RequestBody @Valid RequestSubmitDTO requestSubmitDTO){

        requestService.create(requestSubmitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("my-requests")
    public ResponseEntity<List<Request>> readRequestListByEmployeeId(
            @RequestHeader("X-User-Id") String userId){

        Integer uid = Integer.parseInt(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(requestService.readRequestListByEmployeeId(uid));
    }

    @PostMapping("/read-my-employees-pending-request")
    public ResponseEntity<List<Request>> readRequestListByEmployeeIdList(
            @RequestBody List<Integer> employeeIdList){

        return ResponseEntity.status(HttpStatus.OK)
                .body(requestService.readRequestListByEmployeeIdList(employeeIdList));
    }

    @PostMapping("update-request")
    public ResponseEntity<Void> updateRequest(
            @RequestBody @Valid UpdateStatusDTO updateStatusDTO){

        requestService.updateRequestStatusById(updateStatusDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
