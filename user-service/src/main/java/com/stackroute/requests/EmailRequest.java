package com.stackroute.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {

    private String subject;

    @Email
    private String receiver;

    private String message;
}

