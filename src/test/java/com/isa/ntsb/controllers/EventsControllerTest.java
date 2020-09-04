package com.isa.ntsb.controllers;

import com.isa.ntsb.model.Event;
import com.isa.ntsb.persistence.EventRepository;
import com.isa.ntsb.security.JwtTokenUtil;
import com.isa.ntsb.service.EventService;
import com.isa.ntsb.service.JwtUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;


/**
 * Created by Karolis on 22-Aug-19.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {EventsController.class}, secure = false)
//@SpringBootTest
//@ContextConfiguration(classes=NtsbApplication.class)
//@WebMvcTest(EventsController.class)
//@ContextConfiguration(classes = {EventServiceImpl.class})
//@AutoConfigureMockMvc
public class EventsControllerTest {
    /**
     * @verifies return all distinct years
     * @see EventsController#allYearsUsed()
     */

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void getAllEvents() throws Exception {

        //given
        Event event = new Event();
        event.setEventId("testEventId");
        List<Event> allEvents = Arrays.asList(event);

        //when
        when(eventService.getAllEvents())
                .thenReturn(allEvents);

        //then
        mvc.perform(
                get("/events/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].eventId").value(event.getEventId()));
    }
}
