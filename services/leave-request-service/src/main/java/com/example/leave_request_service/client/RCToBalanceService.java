package com.example.leave_request_service.client;

import com.example.leave_request_service.dto.UpdateBalanceDTO;
import com.example.leave_request_service.exception.DownstreamServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class RCToBalanceService {

    private final RestClient balanceRestClient;

    @Autowired
    public RCToBalanceService(
            @Qualifier("balanceRestClient") RestClient balanceRestClient
    ) {
        this.balanceRestClient = balanceRestClient;
    }

    public Integer getBalance(Integer employeeId) {

        try {
            return balanceRestClient.get()
                    .uri("/readBalance/{employeeId}", employeeId)
                    .retrieve()
                    .body(Integer.class);
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

    public Integer updateBalance(Integer employeeId, Integer newBalance) {

        try {
            return balanceRestClient.post()
                    .uri("/updateBalance")
                    .body(new UpdateBalanceDTO(employeeId, newBalance))
                    .retrieve()
                    .body(Integer.class);
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

}
