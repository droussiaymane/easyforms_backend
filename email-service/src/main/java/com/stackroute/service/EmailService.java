package com.stackroute.service;

import com.stackroute.model.EmailRequest;

public interface EmailService {

    boolean sendEmail(EmailRequest emailRequest);
}
