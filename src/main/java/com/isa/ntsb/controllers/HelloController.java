package com.isa.ntsb.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Hello_Controller", description = "Controller for testing user authorization")
@RestController
@RequestMapping("/hello")
public class HelloController {

    @ApiOperation(value = "returns Hello, only accessible to 'admin' users")
    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @ApiOperation(value = "Hello, only accessible to 'user' users")
    @GetMapping("/user")
    public String helloUser() {
        return "Hello User";
    }

}