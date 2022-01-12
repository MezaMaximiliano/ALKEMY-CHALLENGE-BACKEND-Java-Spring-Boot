package com.alkemy.challenge.mail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SendGridConfig {

    @Value("${SENDGRID_KEY}")
    private String sendGridApiKey;

    @Value("${EMAIL}")
    private String senderMail;
}
