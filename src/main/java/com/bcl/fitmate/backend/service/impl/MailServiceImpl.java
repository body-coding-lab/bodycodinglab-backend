package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.bcl.fitmate.backend.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {
    @Override
    public void sendResetPasswordEmail(String email, String token) throws MessagingException {

    }

    @Override
    public void sendTrainerApprovalResultEmail(SendTrainerApprovalResultEmailRequestDto dto) throws MessagingException {

    }
}
