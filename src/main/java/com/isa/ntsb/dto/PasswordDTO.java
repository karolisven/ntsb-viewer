package com.isa.ntsb.dto;

/**
 * Created by Karolis on 09-Aug-19.
 */
public class PasswordDTO {

    private String password;

    //need default constructor for JSON Parsing
    public PasswordDTO()
    {

    }

    public PasswordDTO(String password) {

        this.setPassword(password);
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
