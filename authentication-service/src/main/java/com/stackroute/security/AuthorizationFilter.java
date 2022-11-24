package com.stackroute.security;


import com.stackroute.SpringApplicationContext;
import com.stackroute.dao.Role;
import com.stackroute.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.Token_Prefix)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {

            token = token.replace(SecurityConstants.Token_Prefix, "");

            String user = Jwts.parser()
                    .setSigningKey( SecurityConstants.getTokenSecret() )
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();

            if (user != null) {
                UserService userService =(UserService) SpringApplicationContext.getBean("userServiceImpl");
                Set<Role> roles = userService.getUser(user).getRole();

                return new UsernamePasswordAuthenticationToken(user, null, roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList()));
            }

            return null;
        }

        return null;
    }
}
