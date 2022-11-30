package com.stackroute.service;



import com.stackroute.dao.User;
import com.stackroute.message.TokenResponse;
import com.stackroute.requests.UserRegisterRequest;



public interface UserService  {

     int MAX_FAILED_ATTEMPTS = 3;

     long LOCK_TIME_DURATION = 1000*5; // 24 hours

     User getUser(String email);
     User createUser(UserRegisterRequest user);

     String getUsername(int id);




     void updateFailedAttempts(int failAttempts, String email);
     void increaseFailedAttempts(User user) ;
     void resetFailedAttempts(String email);

     void lock(User user) ;

     boolean unlockWhenTimeExpired(User user) ;

    TokenResponse getTemporeryToken(int id);

}