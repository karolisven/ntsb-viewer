package com.isa.ntsb.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Karolis on 05-Sep-19.
 */

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping(value = {"/", "/index"})
    public String index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
        /* The user is logged in :) */
            return "index";
        } else {
            return "login";
        }
    }

    @GetMapping(value = {"/login"})
    public String login(){ return "login"; }

}
