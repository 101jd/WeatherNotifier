package org._jd.conf;

import org._jd.exceptions.EmailException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class IntegrationConfig {

    @Bean
    public DirectChannel inputChannel(){
        return new DirectChannel();
    }

    @Bean
    DirectChannel outputChannel(){
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public org.springframework.integration.transformer.Transformer transformer(){
        return t -> t;
    }

    @Bean
    @ServiceActivator(inputChannel = "outputChannel")
    public MailSendingMessageHandler handler(JavaMailSender sender) throws EmailException {
        try {
            return new MailSendingMessageHandler(sender);
        }catch (Exception e){
            throw new EmailException();
        }
    }

    @Bean
    JavaMailSender mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(2525);
        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return mailSender;
    }
}
