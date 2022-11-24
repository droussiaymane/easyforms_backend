package com.stackroute.security;

import com.stackroute.SpringApplicationContext;
import com.stackroute.dao.User;
import com.stackroute.service.UserService;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        PasswordEncoder encoder = (PasswordEncoder) SpringApplicationContext.getBean("passwordEncoder");
        User user = userService.getUser(email);

        if (user != null) {
            if(!user.isActive()){
                throw new LockedException("Your account has been blocked. Please contact the Support.");
            }
            if (user.isAccountNonLocked()) {
                if (!encoder.matches(password, user.getPassword())) {
                    if (user.getFailedAttempt() < userService.MAX_FAILED_ATTEMPTS - 1) {
                        userService.increaseFailedAttempts(user);
                    } else {
                        userService.lock(user);
                        throw new LockedException("Your account has been locked due to 3 failed attempts."
                                + " It will be unlocked after 24 hours.");
                    }
                    throw new BadCredentialsException("Password incorrect");
                }
                else {
                    return new UsernamePasswordAuthenticationToken(email, password, user.getRole().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList()));

                }

            }
            else {
                if (userService.unlockWhenTimeExpired(user)) {
                    throw new LockedException("Your account has been unlocked. Please try to login again.");
                }
                else{
                    throw new LockedException("Your account has been locked due to 3 failed attempts."
                            + " It will be unlocked after 24 hours.");
                }


            }
        }

        else{
            throw new BadCredentialsException("user not found exception");
        }

    }

}
