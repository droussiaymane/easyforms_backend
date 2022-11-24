package com.stackroute.security;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stackroute.SpringApplicationContext;
import com.stackroute.dao.User;
import com.stackroute.requests.UserLoginRequest;
import com.stackroute.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private CustomAuthenticationManager authenticationManager;


    public AuthenticationFilter(CustomAuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;

    }




    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {

        try {
            req.getInputStream();
            UserLoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequest.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(creds.getMail(), creds.getPassword(), new ArrayList<>());

            return authenticationManager.authenticate(usernamePasswordAuthenticationToken
            );

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String email = ( auth.getName());

        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.Token_Expiration_Time))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET )
                .compact();

        UserService userService =(UserService) SpringApplicationContext.getBean("userServiceImpl");

        User user = userService.getUser(email);
        Integer id = user.getId();
        AtomicReference<String> roles= new AtomicReference<>("");

        user.getRole().stream().forEach(grantedAuthority -> {
            String role=grantedAuthority.getRoleName();
            roles.set(role + "," + roles);
        });
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.Token_Prefix + token);
        res.getWriter().write("{\"token\": \"" + token + "\", \"role\": \""+roles + "\", \"id\": \""+ id.toString() + "\" }");



    }


}
