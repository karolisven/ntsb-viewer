package com.isa.ntsb.controllers;

import com.isa.ntsb.dto.*;
import com.isa.ntsb.model.Event;
import com.isa.ntsb.persistence.EventRepository;
import com.isa.ntsb.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Karolis on 24-Jul-19.
 */

@Api(value = "Events_Controller",
        description = "Mapping for all the incoming event data request by calling" +
                "the appropriate services")
@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private EventService eventService;

    @ApiOperation(value = "All the distinct years of the events", response = List.class)
    @GetMapping("/getAllYearsUsed")
    public List<Integer> allYearsUsed(){
        return eventService.findAllYearsUsed();
    }

    @ApiOperation(value = "All the events from the databse, supports pagination",
                    response = List.class)
    @GetMapping("/getAll")
    public List<Event> getAll(@RequestParam int page, @RequestParam int size){
        return eventService.findAll(page, size);
    }

    @ApiOperation(value = "Event by a unique AccNo (accident number)", response = Event.class)
    @GetMapping("/getEventByAccNo")
    public Event getEventByAccNo(@RequestParam String accNo){
        return eventService.findByAccNo(accNo);
    }

    @ApiOperation(value = "Event by a unique eventId", response = Event.class)
    @GetMapping("/getEventByEventId")
    public Event getEventByEventId(@RequestParam String eventId){
        return eventService.findByEventId(eventId);
    }

    @ApiOperation(value = "Events by unique Accident Numbers", response = List.class)
    @GetMapping("/getEventsByAccNos")
    public List<Event> getEventsByAccNos(@RequestParam(value="List<String> accNos") List<String> accNos){
        return eventService.findByAccNos(accNos);
    }

    @ApiOperation(value = "List of events by a list of eventId", response = List.class)
    @GetMapping("/getEventsByEventIds")
    public List<Event> getEventsByEventIds(@RequestParam(value="List<String> eventId") List<String> eventIds){
        return eventService.findByEventIds(eventIds);
    }

    @ApiOperation(value = "The states with their count", response = List.class)
    @GetMapping("/getCountByState")
    public List<StateCount> countByState(@RequestParam int year1, @RequestParam int year2){
        return eventService.findEventCountByState(year1, year2);
    }

    @ApiOperation(value = "Coordinates of the events for a time period provided", response = List.class)
    @GetMapping("/getCoordinatesByYear")
    public List<Coordinate> eventsCoordinates(@RequestParam int year1, @RequestParam int year2){
        return eventService.findEventsInPeriod(year1, year2);
    }

    @ApiOperation(value = "Finding data for a particular coordinate", response = List.class)
    @GetMapping("/finding/getDataByLatLon")
    public List<EventDataByLatLon> eventDataByLatLonForFinding(@RequestParam String lat, @RequestParam String lon){
        return eventService.findEventDataByLatLonForFinding(lat, lon);
    }

    @ApiOperation(value = "Occurrence data for a particular coordinate", response = List.class)
    @GetMapping("/occurrence/getDataByLatLon")
    public List<EventDataByLatLon> eventDataByLatLonForOccurrence(@RequestParam String lat, @RequestParam String lon){
        return eventService.findEventDataByLatLonForOccurrence(lat, lon);
    }

    @ApiOperation(value = "Events data for a particular state and time period", response = List.class)
    @GetMapping("/getDataByStateAndYear")
    public List<EventDataByStateAndYear> eventDataByStateAndYear (@RequestParam String stateName,
                                                                  @RequestParam int year1, @RequestParam int year2){
        return eventService.findEventDataByStateAndYear(stateName, year1, year2);
    }

    @ApiOperation(value = "Finding Description for a particular state and time period", response = List.class)
    @GetMapping("/finding/getDescriptionByStateAndYear")
    public List<Description> descriptionByStateAndYearForFinding (@RequestParam String stateName, @RequestParam int year1,
                                                                  @RequestParam int year2){
        return eventService.findDescriptionByStateAndYearForFinding(stateName, year1, year2);
    }

    @ApiOperation(value = "Occurrence for a particular coordinate", response = List.class)
    @GetMapping("/occurrence/getDescriptionByStateAndYear")
    public List<Description> descriptionByStateAndYearForOccurrence (@RequestParam String stateName, @RequestParam int year1,
                                                                     @RequestParam int year2){
        return eventService.findDescriptionByStateAndYearForOccurrence(stateName, year1, year2);
    }

    @ApiOperation(value = "Finding data for a particular coordinate and time period", response = List.class)
    @GetMapping("/finding/getDataByYearAndLatLon")
    public List<EventDataByYearAndLatLon> eventDataByYearAndLatLonForFinding(@RequestParam int year1, @RequestParam int year2,
                                                                             @RequestParam String lat,  @RequestParam String lon){
        return eventService.findEventDataByYearAndLatLonForFinding(year1, year2, lat, lon);
    }

    @ApiOperation(value = "Occurrence data for a particular coordinate and time period", response = List.class)
    @GetMapping("/occurrence/getDataByYearAndLatLon")
    public List<EventDataByYearAndLatLon> eventDataByYearAndLatLonForOccurrence(@RequestParam int year1, @RequestParam int year2,
                                                                                @RequestParam String lat,  @RequestParam String lon){
        return eventService.findEventDataByYearAndLatLonForOccurrence(year1, year2, lat, lon);
    }

    @ApiOperation(value = "Finding data for the particular coordinate", response = List.class)
    @GetMapping("/finding/getDataByClickedState")
    public List<EventDataByClickedState> eventDataByClickedStateForFinding(@RequestParam String stateName, @RequestParam int year1,
                                                                           @RequestParam int year2){
        return eventService.findEventDataByClickedStateForFinding(stateName, year1, year2);
    }

    @ApiOperation(value = "Event data for the state clicked", response = List.class)
    @GetMapping("/occurrence/getDataByClickedState")
    public List<EventDataByClickedState> eventDataByClickedStateForOccurrence(@RequestParam String stateName, @RequestParam int year1,
                                                                              @RequestParam int year2){
        return eventService.findEventDataByClickedStateForOccurrence(stateName, year1, year2);
    }

    @ApiOperation(value = "Coordinates of the Findings with the description provided", response = List.class)
    @GetMapping("/finding/getCoordinateByDescription")
    public List<Coordinate> coordinateByDescriptionForFinding (@RequestParam String description, @RequestParam int year1, @RequestParam int year2){
        return eventService.findCoordinateByDescriptionForFinding(description, year1, year2);
    }

    @ApiOperation(value = "Coordinates of the Occurrences with the description provided", response = List.class)
    @GetMapping("/occurrence/getCoordinateByDescription")
    public List<Coordinate> coordinateByDescriptionForOccurrence (@RequestParam String description, @RequestParam int year1, @RequestParam int year2){
        return eventService.findCoordinateByDescriptionForOccurrence(description, year1, year2);
    }

    @ApiOperation(value = "Other data for Findings in the time period provided ", response = List.class)
    @GetMapping("/finding/getMiscData")
    public List<EventMiscData> eventMiscDataForFinding(@RequestParam int year1, @RequestParam int year2){
        return eventService.findEventMiscDataForFinding(year1, year2);
    }

    @ApiOperation(value = "Other data for Occurrences in the time period provided", response = List.class)
    @GetMapping("/occurrence/getMiscData")
    public List<EventMiscData> eventMiscDataForOccurrence(@RequestParam int year1, @RequestParam int year2){
        return eventService.findEventMiscDataForOccurrence(year1, year2);
    }

    @ApiOperation(value = "Event data for engine fault in the time period provided", response = List.class)
    @GetMapping("/occurrence/getDataForEngine")
    public List<EventDataForEngine> eventDataForEngine(@RequestParam int year1, @RequestParam int year2){
        return eventService.findEventDataForEngine(year1, year2);
    }

    @GetMapping("/all")
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }
}
