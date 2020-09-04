package com.isa.ntsb.service;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.isa.ntsb.model.*;
import com.isa.ntsb.persistence.EventRepository;
import com.isa.ntsb.persistence.FindingRepository;
import com.isa.ntsb.persistence.OccurrenceRepository;
import com.isa.ntsb.persistence.StateRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Karolis on 30-Aug-19.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ImportServiceImplTest {

//    @TestConfiguration
//    static class ImportServiceImplTestContextConfiguration{
//
//        @Bean
//        public ImportService importService(){
//            return new ImportServiceImpl();
//        }
//    }

    @Autowired
    ImportService importService;

    @MockBean
    private StateRepository stateRepository;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private FindingRepository findingRepository;

    @MockBean
    private OccurrenceRepository occurrenceRepository;

    @MockBean
    private EntityManager entityManager;

    @Value("${access.location:aDefaultUrl}")
    private String databaseFileURL;

    @Captor
    private ArgumentCaptor<Event> acEvent;

    @Captor
    private ArgumentCaptor<State> acState;

    @Captor
    private ArgumentCaptor<Finding> acFinding;

    @Captor
    private ArgumentCaptor<Occurrence> acOccurrence;

    /**
     * @verifies import events to DB
     * @see ImportServiceImpl#importEventsData()
     */
    @Test
    public void importEventsData_shouldImportEventsToDB() throws Exception {

        State testState = new State("WY", "WYOMING");

        List<State> testStateList = Collections.singletonList(testState);

        when(stateRepository.findAll())
                .thenReturn(testStateList);

        importService.importEventsData();

        verify(eventRepository).save(acEvent.capture());

        assertEquals("testEventId", acEvent.getValue().getEventId());
//        assertEquals(testState.getName(), acEvent.getValue().getState().getName());

    }

    /**
     * @verifies import states to DB
     * @see ImportServiceImpl#importStatesData()
     */
    @Test
    public void importStatesData_shouldImportStatesToDB() throws Exception {

//        State testState = new State("WY", "WYOMING");

        importService.importStatesData();

        verify(stateRepository).save(acState.capture());

//        State importedFound = stateRepository.findByName(testState.getName());

        assertEquals("testState", acState.getValue().getName());
    }

    /**
     * @verifies import findings to DB
     * @see ImportServiceImpl#importFindingsData()
     */
    @Test
    public void importFindingsData_shouldImportFindingsToDB() throws Exception {

        importService.importFindingsData();

        verify(findingRepository).save(acFinding.capture());

        assertEquals(1, acFinding.getValue().getNumber());
    }

    /**
     * @verifies import occurrences to DB
     * @see ImportServiceImpl#importOccurrencesData()
     */
    @Test
    public void importOccurrencesData_shouldImportOccurrencesToDB() throws Exception {

        importService.importOccurrencesData();

        verify(occurrenceRepository).save(acOccurrence.capture());

        assertEquals("testMeaning", acOccurrence.getValue().getDescription());
    }
}
