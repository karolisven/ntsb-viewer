package com.isa.ntsb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.ntsb.NtsbApplication;
import com.isa.ntsb.dto.JwtCredsRequest;
import com.isa.ntsb.dto.JwtResponse;
import com.isa.ntsb.dto.UserDTO;
import com.isa.ntsb.model.User;
import com.isa.ntsb.persistence.UserRepository;
import com.isa.ntsb.security.JwtTokenUtil;
import com.isa.ntsb.security.WebSecurityConfig;
import com.isa.ntsb.service.JwtUserDetailsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Karolis on 29-Aug-19.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {SecurityController.class}, secure = false)
//@SpringBootTest
//@AutoConfigureMockMvc
public class SecurityControllerTest {
    /**
     * @verifies create new user
     * @see SecurityController#createNewUser(com.isa.ntsb.dto.UserDTO)
     */

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    JwtUserDetailsService userDetailsService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    private User alex;

    @Before
    public void setUp(){
        String username = "alex";
        String password = "alexpass";
        String role = "user";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        alex = new User(username, password, authority);
        Mockito.reset(userRepository);
    }


    @Test
    public void createNewUser_shouldCreateNewUser() throws Exception {

        String username = "alex";
        String password = "alexpass";
        String role = "user";

        UserDTO userDTO = new UserDTO(username, password, role);

        mvc.perform(
                post("/createNewUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isOk());

//        verify(userService, times(1)).findById(user.getId());
//        verify(userService, times(1)).update(user);
//        verifyNoMoreInteractions(userService);


    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @verifies authenticate user
     * @see SecurityController#createAuthenticationToken(com.isa.ntsb.dto.JwtCredsRequest, javax.servlet.http.HttpServletResponse)
     */
    @Test
    public void createAuthenticationToken_shouldReturnToken() throws Exception {

        JwtCredsRequest jwtCreds = new JwtCredsRequest(alex.getUsername(), alex.getPassword());
        Authentication authToken = new UsernamePasswordAuthenticationToken
                (alex.getUsername(), alex.getPassword(), Collections.singletonList(alex.getRole()));

        UserDetails alexDetails = new org.springframework.security.core.userdetails.User
                (alex.getUsername(), alex.getPassword(), Collections.singletonList(alex.getRole()));

        String token = "generatedToken";

        when(authenticationManager.authenticate(authToken))
                .thenReturn(authToken);

        when(userDetailsService.loadUserByUsername(alex.getUsername()))
                .thenReturn(alexDetails);

        when(jwtTokenUtil.generateToken(alexDetails))
                .thenReturn("generatedToken");

        //when creds sent as params
        mvc.perform(
                post("/authenticate")
                        .param("username", alex.getUsername())
                        .param("password", alex.getPassword()))
                .andExpect(status().is(302));

        //when creds sent as json
//        mvc.perform(
//                post("/authenticate")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(jwtCreds)))
//                .andExpect(status().isOk());
    }
}
