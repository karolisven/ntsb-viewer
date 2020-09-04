package com.isa.ntsb.controllers;

import com.isa.ntsb.dto.*;
import com.isa.ntsb.model.User;
import com.isa.ntsb.security.JwtTokenUtil;
import com.isa.ntsb.service.JwtUserDetailsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;


@Api(value = "Security_Controller",
        description = "Mapping to all the functions related to security logic ")
@RestController
@CrossOrigin
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){

        return userDetailsService.getAllUsers();
    }

    /**
     * @should create new user
     */
    @PostMapping("/createNewUser")
    public void createNewUser(@RequestBody UserDTO userDetailsProvided){

        String username = userDetailsProvided.getUsername();
        String password = userDetailsProvided.getPassword();
        String role = userDetailsProvided.getRole();

        if(role.equals("admin") || role.equals("user")) {
            userDetailsService.createNewUser(username, password, role);
        }
    }

    @PutMapping("/changeAnyPassword")
    public void changeAnyPassword(@RequestBody JwtCredsRequest userCredentials){

        userDetailsService.changePassword(userCredentials.getUsername(),
                                            userCredentials.getPassword());
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestBody UsernameDTO usernameDTO){

        userDetailsService.deleteUser(usernameDTO.getUsername());
    }


//    @PostMapping("/parse")
//    public String parsedCredentials(@RequestParam String username, @RequestParam String password)
//            throws Exception
//    {
//        String parsed;
//        parsed = " { \"username\" : \""+username+"\", \"pasword\" : \""+password+"\" } ";
//
//        createAuthenticationToken(parsed);
//    }

    /**
     * @should return token
     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestParam String username,
                                                       @RequestParam String password,
                                                       HttpServletResponse res)
            throws Exception {

        authenticate(username, password);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        final String token = jwtTokenUtil.generateToken(userDetails);

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
//        cookie.setDomain("localhost");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.addCookie(cookie);

//        return ResponseEntity.ok(new JwtResponse(token));
        res.sendRedirect("/");
    }

    @PutMapping("/changeMyPassword")
    public void changeMyPassword(Principal principal, @RequestBody PasswordDTO newPassword) {

        userDetailsService.changePassword(principal.getName(), newPassword.getPassword());
    }

    @PostMapping("/clear")
    public void clearToken(HttpServletResponse res)
            throws Exception {

        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        res.sendRedirect("/");
    }
//    @GetMapping("/logout")
//    public String logout() {
//
//        return "Logout successful";
//    }

//    @GetMapping("/login")
//    public String login() {
//
//        return "/login.jsp";
//}

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}