package com.isa.ntsb.security;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Karolis on 12-Aug-19.
 */
public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/authenticate";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
//    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    // JWT token defaults

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 24; //1 hour

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}