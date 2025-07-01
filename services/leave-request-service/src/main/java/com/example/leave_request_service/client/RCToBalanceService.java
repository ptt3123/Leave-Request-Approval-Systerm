package com.example.leave_request_service.client;

import com.example.leave_request_service.dto.BalanceUpdateDTO;
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
                    .uri("/read-balance")
                    .headers(headers -> {
                        headers.set("X-User-Id", employeeId.toString());
                    })
                    .retrieve()
                    .toEntity(Integer.class)
                    .getBody();
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

    public void updateBalance(Integer employeeId, Integer newBalance) {

        try {
            balanceRestClient.post()
                    .uri("/update-balance")
                    .body(new BalanceUpdateDTO(employeeId, newBalance))
                    .retrieve()
                    .toEntity(Void.class);
        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(ex.getStatusCode(), ex.getResponseBodyAsString());
        }

    }

}
