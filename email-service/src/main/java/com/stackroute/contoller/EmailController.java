package com.stackroute.contoller;

import com.stackroute.exceptionHandler.CatchException;
import com.stackroute.model.EmailRequest;
import com.stackroute.service.EmailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class EmailController {

    private final EmailServiceImpl emailService;

    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }


    //sending mail
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest emailRequest) throws CatchException {


        if (emailRequest.getSubject().isEmpty() || emailRequest.getSubject().length()==0) {
            throw new CatchException("Subject is missing from given data");
        }else if(emailRequest.getReceiver().isEmpty() || emailRequest.getReceiver().length()==0) {
            throw new CatchException("Receiver MailID is missing from given data");
        }else if(emailRequest.getMessage().isEmpty() || emailRequest.getMessage().length()==0) {
            throw new CatchException("Message content is missing from given data");
        }else {
            boolean isSend = this.emailService.sendEmail(emailRequest);
            if (isSend) {
                return ResponseEntity.ok("Success");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
            }
        }

    }

}
