package com.stackroute.security;


public class SecurityConstants {

    public static final long Token_Expiration_Time=1000*3600*24*30;/** 1 month */
    public static final long Token_Temporery_Expiration_Time=1000*60;/**  1 minute */

    public static final String Token_Prefix="Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/register";
    public static final String TOKEN_SECRET = "dfg893hdc475zwerop4tghg4ddfdfgdsdfeqaas?=-0ljznm0-9";

    public static String getTokenSecret() {
        return TOKEN_SECRET;
    }
}