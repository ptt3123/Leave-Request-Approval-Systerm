package com.example.leave_request_service.client;

import com.example.leave_request_service.exception.DownstreamServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class RCToEmployeeService {

    private final RestClient employeeRestClient;

    @Autowired
    public RCToEmployeeService(
            @Qualifier("employeeRestClient") RestClient employeeRestClient
    ) {
        this.employeeRestClient = employeeRestClient;
    }

    public String getEmployeeEmailById(Integer employeeId) {

        try {
            return employeeRestClient.get()
                    .uri("/readEmployeeEmail/{employeeId}", employeeId)
                    .retrieve()
                    .toEntity(String.class)
                    .getBody();
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

    public List<Integer> getEmployeeIdsByManagerId(Integer managerId) {

        try {
            return employeeRestClient.get()
                    .uri("/readListEmployeeIds/{managerId}", managerId)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<Integer>>() {})
                    .getBody();
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

}
