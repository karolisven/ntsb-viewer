package com.isa.ntsb.persistence;

import java.util.Collection;
import java.util.List;

import com.isa.ntsb.dto.*;
import com.isa.ntsb.model.Event;
import com.isa.ntsb.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByAccNo(String accNo);

    Event findByEventId(String eventId);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "MANUAL") })
    @Query("SELECT event FROM Event event WHERE event.eventId = ?1")
    Event findByEventIdNoFlush(String eventId);

//    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "MANUAL") })
//    @Query("SELECT event FROM Event event WHERE event.accNo = ?1")
//    Event findByAccNoNoFlush(String accNo);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "MANUAL") })
    @Query("SELECT event FROM Event event WHERE event.eventId IN ?1")
    List<Event> findByEventIds(Collection<String> eventIds);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "MANUAL") })
    @Query("SELECT event FROM Event event WHERE event.eventId IN ?1")
    List<Event> findByAccNos(Collection<String> AccNos);

    @Query("SELECT new com.isa.ntsb.dto.Coordinate(event.latitude, event.longitude) " +
            "FROM Event event WHERE event.year >= ?1 AND event.year <= ?2")
    List<Coordinate> findEventsInPeriod(int year1, int year2);

    @Query("SELECT DISTINCT event.year FROM Event event ORDER BY event.year ASC")
    List<Integer> findAllYearsUsed();

    @Query("SELECT new com.isa.ntsb.dto.StateCount(state.name, count (event)) FROM Event event, State state " +
            "WHERE event.state = state AND event.year >= ?1 AND event.year <= ?2 GROUP BY event.state, state.name ")
    List<StateCount> findEventCountByState(int year1, int year2);

    @Query("SELECT new com.isa.ntsb.dto.EventDataByLatLon(event.date, event.latitude, event.longitude, finding.description, state.name)" +
            "FROM Event event LEFT JOIN State state ON event.state = state " +
            "LEFT JOIN Finding finding ON finding.event = event " +
            "WHERE event.latitude = ?1 AND event.longitude = ?2")
    List<EventDataByLatLon> findEventDataByLatLonForFinding(String latitude, String longitude);

    @Query("SELECT new com.isa.ntsb.dto.EventDataByLatLon(event.date, event.latitude, event.longitude, occurrence.description, state.name)" +
            "FROM Event event LEFT JOIN State state ON event.state = state " +
            "LEFT JOIN Occurrence occurrence ON occurrence.event = event " +
            "WHERE event.latitude = ?1 AND event.longitude = ?2")
    List<EventDataByLatLon> findEventDataByLatLonForOccurrence(String latitude, String longitude);

    @Query("SELECT new com.isa.ntsb.dto.EventDataByStateAndYear(event.date, event.city, event.accNo, event.latitude, event.longitude)" +
            "FROM Event event LEFT JOIN State state ON event.state = state " +
            "WHERE state = ?1 AND event.year >= ?2 AND event.year <= ?3")
    List<EventDataByStateAndYear> findEventDataByStateAndYear(State state, int year1, int year2);

    @Query("Select new com.isa.ntsb.dto.Description(finding.description)" +
            "FROM State state, Event event INNER JOIN Finding finding ON finding.event = event " +
            "WHERE state = ?1 AND event.year >= ?2 AND event.year <= ?3 " +
            "AND event.state = state")
    List<Description> findDescriptionByStateAndYearForFinding(State state, int year1, int year2);

    @Query("Select new com.isa.ntsb.dto.Description(occurrence.description)" +
            "FROM State state, Event event INNER JOIN Occurrence occurrence ON occurrence.event = event " +
            "WHERE state = ?1 AND event.year >= ?2 AND event.year <= ?3 " +
            "AND event.state = state")
    List<Description> findDescriptionByStateAndYearForOccurrence(State state, int year1, int year2);

    @Query("Select new com.isa.ntsb.dto.EventDataByYearAndLatLon(event.date, event.city, finding.description, event.latitude, event.longitude)" +
            "FROM Event event INNER JOIN Finding finding ON finding.event = event " +
            "WHERE event.year >= ?1 AND event.year <= ?2 " +
            "AND event.latitude = ?3 AND event.longitude = ?4")
    List<EventDataByYearAndLatLon> findEventDataByYearAndLatLonForFinding(int year1, int year2, String latitude, String longitude);

    @Query("Select new com.isa.ntsb.dto.EventDataByYearAndLatLon(event.date, event.city, occurrence.description, event.latitude, event.longitude)" +
            "FROM Event event INNER JOIN Occurrence occurrence ON occurrence.event = event " +
            "WHERE event.year >= ?1 AND event.year <= ?2 " +
            "AND event.latitude = ?3 AND event.longitude = ?4")
    List<EventDataByYearAndLatLon> findEventDataByYearAndLatLonForOccurrence(int year1, int year2, String latitude, String longitude);

    @Query("Select new com.isa.ntsb.dto.EventDataByClickedState(event.date, event.city, event.latitude, event.longitude, finding.number, finding.description)" +
            "FROM Event event JOIN State state ON event.state = state " +
            "JOIN Finding finding ON finding.event = event " +
            "WHERE state = ?1 AND event.year >= ?2 AND event.year <= ?3")
    List<EventDataByClickedState> findEventDataByClickedStateForFinding(State state, int year1, int year2);

    @Query("Select new com.isa.ntsb.dto.EventDataByClickedState(event.date, event.city, event.latitude, event.longitude, occurrence.number, occurrence.description)" +
            "FROM Event event JOIN State state ON event.state = state " +
            "JOIN Occurrence occurrence ON occurrence.event = event " +
            "WHERE state = ?1 AND event.year >= ?2 AND event.year <= ?3")
    List<EventDataByClickedState> findEventDataByClickedStateForOccurrence(State state, int year1, int year2);

    @Query("Select new com.isa.ntsb.dto.Coordinate(event.latitude, event.longitude)" +
            "FROM Event event LEFT JOIN Finding finding ON finding.event = event " +
            "WHERE lower(finding.description) like lower(concat('%', ?1,'%'))" +
            "AND event.year >= ?2 AND event.year <= ?3")
    List<Coordinate> findCoordinateByDescriptionForFinding(String description, int year1, int year2);

    @Query("Select new com.isa.ntsb.dto.Coordinate(event.latitude, event.longitude)" +
            "FROM Event event LEFT JOIN Occurrence occurrence ON occurrence.event = event " +
            "WHERE lower(occurrence.description) like lower(concat('%', ?1,'%'))" +
            "AND event.year >= ?2 AND event.year <= ?3")
    List<Coordinate> findCoordinateByDescriptionForOccurrence(String description, int year1, int year2);

    @Query("SELECT new com.isa.ntsb.dto.EventMiscData(event.latitude, event.longitude, finding.number, finding.description) " +
            "FROM Event event LEFT JOIN Finding finding ON finding.event = event " +
            "WHERE event.year >= ?1 AND event.year <= ?2 " +
            "AND finding.description NOT LIKE '%Personnel issues-Task performance%' " +
            "AND finding.description NOT LIKE '%Personnel issues-Action/decision%'" +
            "AND finding.description NOT LIKE '%Personnel issues-Psychological%'" +
            "AND finding.description NOT LIKE '%Aircraft-Aircraft oper/perf/capability%'" +
            "AND finding.description NOT LIKE '%Aircraft-Aircraft systems%'" +
            "AND finding.description NOT LIKE '%Aircraft-Aircraft power plant%'" +
            "AND finding.description NOT LIKE '%Environmental issues-Physical environment%'" +
            "AND finding.description NOT LIKE '%Environmental issues-Conditions/weather/phenomena%'" +
            "AND finding.description NOT LIKE '%Not determined%'")
    List<EventMiscData> findEventMiscDataForFinding(int year1, int year2);

    @Query("SELECT new com.isa.ntsb.dto.EventMiscData(event.latitude, event.longitude, occurrence.number, occurrence.description) " +
            "FROM Event event LEFT JOIN Occurrence occurrence ON occurrence.event = event " +
            "WHERE event.year >= ?1 AND event.year <= ?2 " +
            "AND occurrence.description NOT LIKE '%IN FLIGHT COLLISION WITH TERRAIN/WATER%' " +
            "AND occurrence.description NOT LIKE '%FORCED LANDING%'" +
            "AND occurrence.description NOT LIKE '%IN FLIGHT COLLISION WITH OBJECT%'" +
            "AND occurrence.description NOT LIKE '%LOSS OF ENGINE POWER%'" +
            "AND occurrence.description NOT LIKE '%AIRFRAME/COMPONENT/SYSTEM FAILURE/MALFUNCTION%'" +
            "AND occurrence.description NOT LIKE '%IN FLIGHT ENCOUNTER WITH WEATHER%'" +
            "AND occurrence.description NOT LIKE '%LOSS OF CONTROL - IN FLIGHT%'" +
            "AND occurrence.description NOT LIKE '%LOSS OF ENGINE POWER(PARTIAL) - NONMECHANICAL%'" +
            "AND occurrence.description NOT LIKE '%LOSS OF ENGINE POWER(PARTIAL) - MECH FAILURE/MALF%'" +
            "AND occurrence.description NOT LIKE '%LOSS OF ENGINE POWER(TOTAL) - NONMECHANICAL%'" +
            "AND occurrence.description NOT LIKE '%LOSS OF ENGINE POWER(TOTAL) - MECH FAILURE/MALF%'")
    List<EventMiscData> findEventMiscDataForOccurrence(int year1, int year2);


    @Query("SELECT new com.isa.ntsb.dto.EventDataForEngine(event.latitude, event.longitude, occurrence.description) " +
            "FROM Event event INNER JOIN Occurrence occurrence ON occurrence.event = event " +
            "WHERE event.year >= ?1 AND event.year <= ?2 " +
            "AND occurrence.description IN ('LOSS OF ENGINE POWER', 'LOSS OF ENGINE POWER(PARTIAL) - NONMECHANICAL'," +
            " 'LOSS OF ENGINE POWER(PARTIAL) - MECH FAILURE/MALF', 'LOSS OF ENGINE POWER(TOTAL) - NONMECHANICAL'," +
            " 'LOSS OF ENGINE POWER(TOTAL) - MECH FAILURE/MALF') ")
    List<EventDataForEngine> findEventDataForEngine(int year1, int year2);
}