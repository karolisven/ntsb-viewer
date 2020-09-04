package com.isa.ntsb.service;

import java.util.Collections;
import java.util.List;

import com.isa.ntsb.model.User;
import com.isa.ntsb.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * @should return user by username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User 404");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singletonList(user.getRole()));
    }

    /**
     * @should create admin user
     */
    @EventListener(ApplicationReadyEvent.class)
    public void createAdminUser() {
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("admin");

        User user = new User("ntsb", encoder.encode("admin"), adminAuthority);

        if (userRepository.findByUsername(user.getUsername()) == null)
            userRepository.save(user);
    }

    /**
     * @should create a custom user
     */
    public void createNewUser(String username, String password, String role) {

        GrantedAuthority userAuthority = new SimpleGrantedAuthority(role);

        User user = new User(username, encoder.encode(password), userAuthority);

        if(userRepository.findByUsername(user.getUsername()) == null) {
            userRepository.save(user);
        }
    }


    /**
     * @should change password
     */
    public void changePassword(String username, String newPassword) {

        User user = userRepository.findByUsername(username);

        if(user != null) {
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    /**
     * @should delete user
     */
    public void deleteUser(String username) {

        User user = userRepository.findByUsername(username);

        if(user != null)
            userRepository.delete(user);
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public User getUserModelByUsername(String username){
        return userRepository.findByUsername(username);
    }
}