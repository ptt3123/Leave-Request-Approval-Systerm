package com.example.leave_request_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class DownstreamServiceException extends RuntimeException {

    private final HttpStatusCode statusCode;
    private final String body;

    public DownstreamServiceException(HttpStatusCode statusCode, String body) {
        super("Downstream service error: " + statusCode);
        this.statusCode = statusCode;
        this.body = body;
    }

}
