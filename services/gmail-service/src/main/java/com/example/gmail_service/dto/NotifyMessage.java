package com.example.gmail_service.dto;

import lombok.Value;

@Value
public class NotifyMessage {
    String to;
    String subject;
    String content;
}
