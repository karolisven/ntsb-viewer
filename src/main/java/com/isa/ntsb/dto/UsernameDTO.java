package com.isa.ntsb.dto;

/**
 * Created by Karolis on 09-Aug-19.
 */
public class UsernameDTO {

    String username;

    public UsernameDTO()
    {

    }

    public UsernameDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
