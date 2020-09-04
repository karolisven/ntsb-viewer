package com.isa.ntsb.service;

import com.isa.ntsb.dto.*;
import com.isa.ntsb.model.Event;
import com.isa.ntsb.model.State;
import com.isa.ntsb.persistence.EventRepository;
import com.isa.ntsb.persistence.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Karolis on 6/19/2019.
 */

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventRepository repository;

    @Autowired
    private StateRepository stateRepository;

//    @Autowired
//    private Greeting greeting;

    @Override
    public List<Coordinate> findEventsInPeriod(int year1, int year2) {
        return repository.findEventsInPeriod(year1, year2);
        //return eventDAO.findEventsInPeriod(year1, year2);
    }

    /**
     * @should return all distinct years
     */
    @Override
    public List<Integer> findAllYearsUsed() {
        return repository.findAllYearsUsed();
    }

    @Override
    public List<Event> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).get().collect(Collectors.toList());
    }

    @Override
    public Event findByAccNo(String accNo) {
        return repository.findByAccNo(accNo);
    }

    @Override
    public List<Event> findByAccNos(List<String> accNos) {
        return repository.findByAccNos(accNos);
    }

    @Override
    public Event findByEventId(String eventId) {
        return repository.findByEventId(eventId);
    }

    @Override
    public List<Event> findByEventIds(List<String> eventIds) {
        return repository.findByEventIds(eventIds);
    }

    @Override
    public List<StateCount> findEventCountByState(int year1, int year2){
        Optional<Event> byId = repository.findById((long) 52);
        return repository.findEventCountByState(year1, year2);
    }

    @Override
    public List<EventDataByLatLon> findEventDataByLatLonForFinding(String lat, String lon) {
        return repository.findEventDataByLatLonForFinding(lat, lon);
    }

    @Override
    public List<EventDataByLatLon> findEventDataByLatLonForOccurrence(String lat, String lon) {
        return repository.findEventDataByLatLonForOccurrence(lat, lon);
    }


    @Override
    public List<EventDataByStateAndYear> findEventDataByStateAndYear(String stateName, int year1, int year2){
        State state = stateRepository.findByName(stateName);
        return repository.findEventDataByStateAndYear(state, year1, year2);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Description> findDescriptionByStateAndYearForFinding(String stateName, int year1, int year2){
        State state = stateRepository.findByName(stateName);
        return repository.findDescriptionByStateAndYearForFinding(state, year1, year2);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Description> findDescriptionByStateAndYearForOccurrence(String stateName, int year1, int year2){
        State state = stateRepository.findByName(stateName);
        return repository.findDescriptionByStateAndYearForOccurrence(state, year1, year2);
    }

    @Override
    public List<EventDataByYearAndLatLon> findEventDataByYearAndLatLonForFinding(int year1, int year2, String lat, String lon){
        return repository.findEventDataByYearAndLatLonForFinding(year1, year2, lat, lon);
    }

    @Override
    public List<EventDataByYearAndLatLon> findEventDataByYearAndLatLonForOccurrence(int year1, int year2, String lat, String lon){
        return repository.findEventDataByYearAndLatLonForOccurrence(year1, year2, lat, lon);
    }

    @Override
    public List<EventDataByClickedState> findEventDataByClickedStateForFinding(String stateName, int year1, int year2){
        State state = stateRepository.findByName(stateName);
        return repository.findEventDataByClickedStateForFinding(state, year1, year2);
    }

    @Override
    public List<EventDataByClickedState> findEventDataByClickedStateForOccurrence(String stateName, int year1, int year2){
        State state = stateRepository.findByName(stateName);
        return repository.findEventDataByClickedStateForOccurrence(state, year1, year2);
    }

    @Override
    public List<Coordinate> findCoordinateByDescriptionForFinding(String description, int year1, int year2){
        return repository.findCoordinateByDescriptionForFinding(description, year1, year2);
    }

    @Override
    public List<Coordinate> findCoordinateByDescriptionForOccurrence(String description, int year1, int year2){
        return repository.findCoordinateByDescriptionForOccurrence(description, year1, year2);
    }

    @Override
    public List<EventMiscData> findEventMiscDataForFinding(int year1, int year2){
        return repository.findEventMiscDataForFinding(year1, year2);
    }

    @Override
    public List<EventMiscData> findEventMiscDataForOccurrence(int year1, int year2){
        return repository.findEventMiscDataForOccurrence(year1, year2);
    }

    public List<EventDataForEngine> findEventDataForEngine(int year1, int year2){
        return repository.findEventDataForEngine(year1, year2);
    }

    public List<Event> getAllEvents(){
        return repository.findAll();
    }
}
