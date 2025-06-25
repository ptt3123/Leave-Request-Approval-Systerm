package com.example.gmail_service.service;

import com.example.gmail_service.dto.NotifyMessage;

public interface GmailService {
    void sendEmail(NotifyMessage notifyMessage);
}
