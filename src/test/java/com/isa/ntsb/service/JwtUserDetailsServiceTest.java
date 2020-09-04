package com.isa.ntsb.service;

import com.isa.ntsb.model.User;
import com.isa.ntsb.persistence.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sun.java2d.pipe.SpanShapeRenderer;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Karolis on 27-Aug-19.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
//        (classes = NtsbApplication.class)
public class JwtUserDetailsServiceTest {
    /**
     * @verifies return user by username
     * @see JwtUserDetailsService#loadUserByUsername(String)
     */

    @MockBean
    private UserRepository userRepository;

    @Autowired
    JwtUserDetailsService userDetailsService;

    @MockBean
    private PasswordEncoder encoder;

    @Captor
    ArgumentCaptor<User> acUser;

    private User alex;
    private String alexName = "alex";

    @Before
    public void setUp(){
        String username = "alex";
        String password = "alexpass";
        String role = "user";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        alex = new User(username, password, authority);
        Mockito.reset(userRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsername_shouldReturnUserByUsername() throws Exception {
        //given
        GrantedAuthority role = new SimpleGrantedAuthority("user");

        User alex = new User("alex", "alexpass", role);

        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(
                        alex.getUsername(),
                        alex.getPassword(),
                        singletonList(alex.getRole()));

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(alex);

        //when
        org.springframework.security.core.userdetails.UserDetails found =
                userDetailsService.loadUserByUsername(alex.getUsername());

        //then
        assertEquals(found, user);
    }


    /**
     * @verifies create admin user
     * @see JwtUserDetailsService#createAdminUser()
     */

    @Test
    public void createAdminUser_shouldCreateAdminUser() throws Exception {

//        User user = new User("ntsb", encoder.encode("admin"), new SimpleGrantedAuthority("admin"));

//        when(userRepository.findByUsername("ntsb"))
//                .thenReturn(null);

//        when(userRepository.save(user))
//                .thenReturn(user);

        userDetailsService.createAdminUser();

        verify(userRepository).save(acUser.capture());
        assertEquals("ntsb", acUser.getValue().getUsername());

    }

    /**
     * @verifies create a custom user
     * @see JwtUserDetailsService#createNewUser(String, String, String)
     */

    @Test
    public void createNewUser_shouldCreateACustomUser() throws Exception {

        String username = "alex";
        String password = "alexpass";
        String role = "user";
//        User alex = new User(username, password, new SimpleGrantedAuthority(role));

        //given no user of that username exists
//        when(userRepository.findByUsername(username))
//                .thenReturn(null);
        userDetailsService.createNewUser(username, password, role);

        verify(userRepository).save(acUser.capture());
        assertEquals("alex", acUser.getValue().getUsername());

    }

    /**
     * @verifies change password
     * @see JwtUserDetailsService#changePassword(String, String)
     */
    @Test
    public void changePassword_shouldChangePassword() throws Exception {

        String newPassword = "newPassword";
        String newPasswordEncoded = "newPasswordEncoded";
        //given a user with this username exists
        when(userRepository.findByUsername(alexName))
                .thenReturn(alex);

        when(encoder.encode(newPassword))
                .thenReturn(newPasswordEncoded);

        userDetailsService.changePassword(alexName, newPassword);
        assertEquals(newPasswordEncoded, alex.getPassword());
    }

    /**
     * @verifies delete user
     * @see JwtUserDetailsService#deleteUser(String)
     */
    @Test
    public void deleteUser_shouldDeleteUser() throws Exception {

        when(userRepository.findByUsername(alexName))
                .thenReturn(alex);

        userDetailsService.deleteUser(alexName);

        verify(userRepository).delete(acUser.capture());
        assertEquals("alex", acUser.getValue().getUsername());
    }
}
