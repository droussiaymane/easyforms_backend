package com.stackroute.service;



import com.stackroute.dao.User;
import com.stackroute.message.TokenResponse;
import com.stackroute.requests.UserRegisterRequest;

import java.util.Date;

public interface UserService  {

    public static final int MAX_FAILED_ATTEMPTS = 10;

    static final long LOCK_TIME_DURATION = 1000*3600*24; // 24 hours

    public User getUser(String email);
    public User createUser(UserRegisterRequest user);

    public String getUsername(int id);




    public void updateFailedAttempts(int failAttempts, String email);
    public void increaseFailedAttempts(User user) ;
    public void resetFailedAttempts(String email);

    public void lock(User user) ;

    public boolean unlockWhenTimeExpired(User user) ;

    TokenResponse getTemporeryToken(int id);

}