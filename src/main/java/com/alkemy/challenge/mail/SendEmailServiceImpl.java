package com.alkemy.challenge.mail;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;




@Service
public class SendEmailServiceImpl implements SendEmailService{

    private final SendGridConfig sendGridConfig;

    SendEmailServiceImpl(final SendGridConfig sendGridConfig){
        this.sendGridConfig=sendGridConfig;
    }

    @Override
    public void sendEmailMessage(String subject, String message, String to) throws Exception {
        Email fromEmail = new Email(sendGridConfig.getSenderMail());
        Email toEmail = new Email(to);

        Content content = new Content("text/plain", message);

        Mail mail = new Mail(fromEmail,subject,toEmail,content);

        SendGrid sg = new SendGrid(sendGridConfig.getSendGridApiKey());
        Request request = new Request();
        Response response = null;

        try{

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            response = sg.api(request);

        }catch (Exception err){
            System.out.println(err.getMessage());
        }

        if(response.getStatusCode() != HttpStatus.ACCEPTED.value()){
            throw new Exception(response.getBody());
        }

    }
}
