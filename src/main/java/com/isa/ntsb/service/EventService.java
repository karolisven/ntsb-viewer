package com.isa.ntsb.service;

import com.isa.ntsb.dto.*;
import com.isa.ntsb.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Karolis on 6/19/2019.
 */
@Service
public interface EventService {
    List<Coordinate> findEventsInPeriod(int year1, int year2);

    List<Integer> findAllYearsUsed();

    List<Event> findAll(int page, int size);

    Event findByAccNo(String accNo);

    Event findByEventId(String eventId);

    List<Event> findByAccNos(List<String> accNos);

    List<Event> findByEventIds(List<String> eventIds);

    List<StateCount> findEventCountByState(int year1, int year2);

    List<EventDataByLatLon> findEventDataByLatLonForFinding(String lat, String lon);

    List<EventDataByLatLon> findEventDataByLatLonForOccurrence(String lat, String lon);

    List<EventDataByStateAndYear> findEventDataByStateAndYear(String stateName, int year1, int year2);

    List<Description> findDescriptionByStateAndYearForFinding(String stateName, int year1, int year2);

    List<Description> findDescriptionByStateAndYearForOccurrence(String stateName, int year1, int year2);

    List<EventDataByYearAndLatLon> findEventDataByYearAndLatLonForFinding(int year1, int year2, String lat, String lon);

    List<EventDataByYearAndLatLon> findEventDataByYearAndLatLonForOccurrence(int year1, int year2, String lat, String lon);

    List<EventDataByClickedState> findEventDataByClickedStateForFinding(String stateName, int year1, int year2);

    List<EventDataByClickedState> findEventDataByClickedStateForOccurrence(String stateName, int year1, int year2);

    List<Coordinate> findCoordinateByDescriptionForFinding(String description, int year1, int year2);

    List<Coordinate> findCoordinateByDescriptionForOccurrence(String description, int year1, int year2);

    List<EventMiscData> findEventMiscDataForFinding(int year1, int year2);

    List<EventMiscData> findEventMiscDataForOccurrence(int year1, int year2);

    List<EventDataForEngine> findEventDataForEngine(int year1, int year2);

    List<Event> getAllEvents();
}
