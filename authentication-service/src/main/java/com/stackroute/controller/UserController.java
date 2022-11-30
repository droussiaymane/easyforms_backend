package com.stackroute.controller;




import com.stackroute.message.ResponseMessage;
import com.stackroute.message.TokenResponse;
import com.stackroute.requests.UserRegisterRequest;
import com.stackroute.responses.UsernameResponse;
import com.stackroute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserService userService;

        @PostMapping("/register")
    public ResponseEntity<ResponseMessage> createStudent(@RequestBody UserRegisterRequest user) {
        String message ="";
        try{

            message="User is created successfully";
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
        }
        catch (Exception e){
            message = "Error ! Try again ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }



    }



    @GetMapping("logintemporery/{id}")
    public ResponseEntity getTempororyToken(@PathVariable int id) {

        TokenResponse tokenResponse = userService.getTemporeryToken(id);
        return ResponseEntity.ok(tokenResponse);

    }

    @GetMapping("/username/{id}")
    public ResponseEntity getUsername(@PathVariable int id) {
        String message ="";
        try{
            String username = userService.getUsername(id);
            message="Done";
            UsernameResponse usernameResponse=new UsernameResponse();
            usernameResponse.setUsername(username);
            return ResponseEntity.status(HttpStatus.CREATED).body(usernameResponse);
        }
        catch (Exception e){
            message = "Error ! Try again ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }


    }

}
