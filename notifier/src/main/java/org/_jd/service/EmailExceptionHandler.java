package org._jd.service;

import org._jd.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class EmailExceptionHandler {
    @Autowired
    Logger logger;

    @Autowired
    MailGateway gateway;

    public void sendEmail(String from, String to, String subject, String payload){
        try {
            gateway.sendEmail(from, to, subject, payload);
        }catch (EmailException e){
            logger.warning(e.getMessage());
        }
    }
}
