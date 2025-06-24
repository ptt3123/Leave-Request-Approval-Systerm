package com.example.leave_request_service.client;

import com.example.leave_request_service.dto.Request;
import com.example.leave_request_service.dto.RequestSubmitDTO;
import com.example.leave_request_service.dto.UpdateStatusDTO;
import com.example.leave_request_service.exception.DownstreamServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class RCToRequestService {

    private final RestClient requestRestClient;

    @Autowired
    public RCToRequestService(
            @Qualifier("requestRestClient") RestClient requestRestClient
    ) {
        this.requestRestClient = requestRestClient;
    }

    public Request read(Integer requestId){

        try {
            return requestRestClient.get()
                    .uri("/read")
                    .retrieve()
                    .toEntity(Request.class)
                    .getBody();
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

    public List<Request> readMyEmployeesRequest(List<Integer> list){

        try {
            return requestRestClient.post()
                    .uri("/readMyEmployeesRequest")
                    .body(list)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<Request>>() {})
                    .getBody();
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

    public void submitRequest(RequestSubmitDTO requestSubmitDTO){

        try {
            requestRestClient.post()
                    .uri("createRequest")
                    .body(requestSubmitDTO)
                    .retrieve()
                    .toEntity(Void.class);
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

    public void updateRequest(Integer requestId, UpdateStatusDTO updateStatusDTO){

        try {
            requestRestClient.post()
                    .uri("/updatePendingLeaveRequest/{requestId}", requestId)
                    .body(updateStatusDTO)
                    .retrieve()
                    .toEntity(Void.class);
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

}
