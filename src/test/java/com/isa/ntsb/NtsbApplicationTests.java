package com.isa.ntsb;

import com.isa.ntsb.controllers.EventsController;
import com.isa.ntsb.controllers.HelloController;
import com.isa.ntsb.model.Event;
import com.isa.ntsb.model.User;
import com.isa.ntsb.persistence.EventRepository;
import com.isa.ntsb.persistence.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
//@DataJpaTest
//@WebMvcTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class NtsbApplicationTests {

    @Autowired
    private UserRepository userRepository;

	@Test
	public void findByUsernameTest() throws Exception {

	    //given
        GrantedAuthority role = new SimpleGrantedAuthority("user");

        User alex = new User("Alex", "alexpass", role);
        userRepository.save(alex);

        //when
        User found = userRepository.findByUsername(alex.getUsername());

        //then
        assertThat(found.getUsername()).isEqualTo(alex.getUsername());
    }
}
