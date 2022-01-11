package com.alkemy.challenge.mail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SendGridConfig {

    @Value("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")
    private String sendGridApiKey;

    @Value("meza.maximiliano@hotmail.com")
    private String senderMail;
}
