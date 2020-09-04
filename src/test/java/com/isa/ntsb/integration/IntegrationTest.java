package com.isa.ntsb.integration;

import com.isa.ntsb.controllers.EventsController;
import com.isa.ntsb.model.Event;
import com.isa.ntsb.persistence.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = {EventsController.class}, secure = false)
@SpringBootTest
//        (webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//        classes = NtsbApplication.class,
//                properties = { "security.basic.enabled=false"}
//
//                )
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@WithMockUser
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void eventIntegrationTest()
            throws Exception {

        Event event = new Event();
        String eventId = "testEventId";
        event.setEventId(eventId);
        eventRepository.save(event);

        mvc.perform(
                get("/events/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventId").value(eventId));
    }
}
