package com.example.leave_request_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyMessage {

    private String to;

    private String subject;

    private String content;

}
