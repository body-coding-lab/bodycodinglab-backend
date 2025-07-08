package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendResetPasswordEmail(String email, String token) throws MessagingException;
    void sendTrainerApprovalResultEmail(SendTrainerApprovalResultEmailRequestDto dto) throws MessagingException;
}
