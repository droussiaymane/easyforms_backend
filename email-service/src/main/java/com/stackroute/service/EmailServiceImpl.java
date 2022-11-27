package com.stackroute.service;

import com.stackroute.model.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Validated
public class EmailServiceImpl implements EmailService{


    @Value("${spring.mail.username}")
    private String senderMail;

    @Value("${spring.mail.password}")
    private String senderPass;


    @Override
    public boolean sendEmail(EmailRequest emailRequest){

        boolean isSend = false;


        String sender = senderMail;

        String host="smtp.office365.com";
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES "+properties);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");

        Session session=Session.getInstance(properties,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender,senderPass);
            }
        });

        session.setDebug(true);

        MimeMessage m = new MimeMessage(session);

        try {

                m.setFrom(sender);

                m.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getReceiver()));

                m.setSubject(emailRequest.getSubject());

                m.setText(emailRequest.getMessage());

                Transport.send(m);

                isSend = true;

        }catch (Exception e) {
            e.printStackTrace();
        }

        return isSend;

    }
}
