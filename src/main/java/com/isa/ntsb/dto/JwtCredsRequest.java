package com.isa.ntsb.dto;

import java.io.Serializable;

public class JwtCredsRequest{

    private String username;
    private String password;

    //need default constructor for JSON Parsing
    public JwtCredsRequest()
    {

    }

    public JwtCredsRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}