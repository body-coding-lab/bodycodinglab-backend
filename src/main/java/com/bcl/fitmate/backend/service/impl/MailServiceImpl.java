package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.bcl.fitmate.backend.service.MailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendResetPasswordEmail(String email, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(sender);
        message.setRecipients(Message.RecipientType.TO, email);
        message.setSubject("[Fit-Mage] 비밀번호 재설정 링크 발송");

        String body = """
                <p>안녕하세요, Fit-Mate 입니다.</p>
                <p>아래 비밀번호 재설정 링크에 접속하여 인증을 완료해 주세요.</p>
                <a href="http://localhost:5173/password/reset?token=%s">여기를 클릭하여 설정 페이지에 접속해 주세요.</a>
                <p>감사합니다.</p>
                """.formatted(token);

        message.setText(body, "utf-8", "html");
        javaMailSender.send(message);
    }

    @Override
    public void sendTrainerApprovalResultEmail(SendTrainerApprovalResultEmailRequestDto dto) throws MessagingException {
        MimeMessage message;

        if (dto.getStatus().equals(TrainerStatus.APPROVED)) {
            message = createTrainerApproveMail(dto.getEmail());
        } else if (dto.getStatus().equals(TrainerStatus.REJECTED)) {
            message = createTrainerReapplyMail(dto.getEmail(), dto.getChangeReason());
        } else {
            message = createTrainerPendingMail(dto.getEmail());
        }

        javaMailSender.send(message);
    }

    private MimeMessage createTrainerReapplyMail(String email, String changeReason) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(sender);
        message.setRecipients(Message.RecipientType.TO, email);
        message.setSubject("[Fit-Mage] 트레이너 재신청 요청");

        String body = """
                <p>안녕하세요, Fit-Mate입니다.</p>
                <p>저희 서비스를 이용해 주셔서 감사드립니다.</p>
                <p>트레이너 가입이 거부되어 재신청 절차를 안내드립니다.</p>
                
                <ol>
                    <li><strong>거부 사유 확인</strong></li>
                    <li><strong>서류 보완</strong></li>
                    <li><strong>재신청</strong></li>
                    <li><strong>가입 승인 대기</strong></li>
                </ol>
                
                <p>
                    [ 거부 사유 ] <strong style="color: red;">%s</strong>
                </p>
                
                <p>홈페이지에 방문하여 재신청 해주시길 바랍니다.</p>
                <p>감사합니다.</p>
                """.formatted(changeReason);

        message.setText(body, "utf-8", "html");
        return message;
    }

    private MimeMessage createTrainerApproveMail(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(sender);
        message.setRecipients(Message.RecipientType.TO, email);
        message.setSubject("[Fit-Mage] 트레이너 승인 안내");

        String body = """
                <p>안녕하세요, Fit-Mate입니다.</p>
                <p>저희 서비스를 이용해 주셔서 감사드립니다.</p>
                
                <p>
                    트레이너 가입이 승인되었습니다. <br />
                    홈페이지에 방문하시면, 서비스 이용이 가능합니다. <br />
                </p>

                <p>감사합니다.</p>
                """;

        message.setText(body, "utf-8", "html");
        return message;
    }

    private MimeMessage createTrainerPendingMail(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(sender);
        message.setRecipients(Message.RecipientType.TO, email);
        message.setSubject("[Fit-Mage] 트레이너 서비스 안내");

        String body = """
                <p>안녕하세요, Fit-Mate입니다.</p>
                <p>저희 서비스를 이용해 주셔서 감사드립니다.</p>
                
                <p>
                    트레이너 신청이 완료되었습니다. <br />
                    현재 관리자 승인 검토 단계입니다. <br />
                    관리자의 승인 이후, 서비스 이용이 가능하오니 참고 바랍니다. <br />
                </p>

                <p>감사합니다.</p>
                """;

        message.setText(body, "utf-8", "html");
        return message;
    }
}
