package com.isa.ntsb.dto;

/**
 * Created by Karolis on 09-Aug-19.
 */
public class UserDTO {

    private String username;
    private String password;
    private String role;

    //need default constructor for JSON Parsing
    public UserDTO()
    {

    }

    public UserDTO(String username, String password, String role) {
        this.setUsername(username);
        this.setPassword(password);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
