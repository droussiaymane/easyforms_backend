package com.stackroute.service;


import com.stackroute.dao.User;
import com.stackroute.message.TokenResponse;
import com.stackroute.repository.UserRepository;
import com.stackroute.requests.UserRegisterRequest;
import com.stackroute.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService{




    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;




    @Override
    public void updateFailedAttempts(int failAttempts, String email){
        User user=userRepository.findByMail(email);
        user.setFailedAttempt(failAttempts);
        userRepository.save(user);

    }
    @Override
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        updateFailedAttempts(newFailAttempts, user.getMail());
    }

    @Override
    public void resetFailedAttempts(String email) {
        updateFailedAttempts(0, email);
    }

    @Override
    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
    }

    @Override
    public boolean unlockWhenTimeExpired(User user) {

        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public TokenResponse getTemporeryToken(int id) {
        User user=userRepository.findById(id).get();

        String token = Jwts.builder()
                .setSubject(user.getMail())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.Token_Temporery_Expiration_Time))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET )
                .compact();
        TokenResponse tokenResponse=new TokenResponse();
        tokenResponse.setId(user.getId());
        tokenResponse.setToken(token);
        return tokenResponse;
    }


    @Override
    public User getUser(String email) throws UsernameNotFoundException {
        User user = userRepository.findByMail(email);
        if(user==null){
            throw new UsernameNotFoundException("Sorry, User Not exist");
        }
        return user;
    }


    @Override
    public User createUser(UserRegisterRequest user) {
        User myuser = userRepository.findByMail(user.getMail());
        if(myuser!=null){
            throw new RuntimeException("User exist already");
        }

        ModelMapper modelMapper = new ModelMapper();
        User userCreated = modelMapper.map(user, User.class);
        userCreated.setPassword(passwordEncoder.encode(user.getPassword()));
        userCreated.setAccountNonLocked(true);
        userCreated.setRegistrationTime(String.valueOf(new Date()));
        userCreated.setActive(true);
        User userRegistred=userRepository.save(userCreated);
        // check the type of the user
        return userRegistred;
    }

    @Override
    public String getUsername(int id) {
        User user = userRepository.findById(id).get();
        if(user==null){
            throw new RuntimeException("No user");
        }
        return user.getUsername();
    }
}
