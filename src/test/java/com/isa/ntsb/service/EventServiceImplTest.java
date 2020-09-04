package com.isa.ntsb.service;

import com.isa.ntsb.NtsbApplication;
import com.isa.ntsb.model.Event;
import com.isa.ntsb.model.User;
import com.isa.ntsb.persistence.EventRepository;
import com.isa.ntsb.persistence.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Karolis on 22-Aug-19.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
// (classes = NtsbApplication.class)
public class EventServiceImplTest {
    /**
     * @verifies return all distinct years
     * @see EventServiceImpl#findAllYearsUsed()
     */

    @TestConfiguration
    static class EventServiceImplTestContextConfiguration{

        @Bean
        public EventService eventService(){
            return new EventServiceImpl();
        }
    }

    @Autowired
    EventService eventService;

    @MockBean
    private EventRepository eventRepository;


    @Before
    public void setUp(){
        Event event = new Event();
        event.setEventId("testEventId");

        Mockito.when(eventRepository.findByEventId(event.getEventId()))
                .thenReturn(event);
    }

    @Test
    public void findByEventIdTest(){
        String eventId = "testEventId";
        Event found = eventService.findByEventId(eventId);

        assertThat(found.getEventId()).isEqualTo(eventId);
    }
}
