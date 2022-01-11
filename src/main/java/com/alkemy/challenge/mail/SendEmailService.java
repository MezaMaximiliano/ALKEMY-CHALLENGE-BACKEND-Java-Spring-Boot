package com.alkemy.challenge.mail;


public interface SendEmailService {

    void sendEmailMessage(String subject,String message, String to) throws Exception;
}
