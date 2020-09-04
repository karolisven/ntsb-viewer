package com.isa.ntsb.service;

import com.healthmarketscience.jackcess.*;
import com.isa.ntsb.NtsbApplication;
import com.isa.ntsb.model.*;
import com.isa.ntsb.persistence.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Karolis on 6/24/2019.
 */
@Service
public class ImportServiceImpl implements ImportService {

    private static final Logger logger = LoggerFactory.getLogger(NtsbApplication.class);

    @Value("${access.location:aDefaultUrl}")
    private String databaseFileURL;

    @Autowired
    private EventRepository repository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FindingRepository findingRepository;

    @Autowired
    private OccurrenceRepository occurrenceRepository;

    @Autowired
    EntityManager entityManager;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    /**
     * @should import findings to DB
     */
    @Override
    @Transactional
    public void importFindingsData(){

        try(Database db = DatabaseBuilder.open(new File(databaseFileURL))){

            //importing findings table
            logger.info("Importing 'findings' table");
            Table table = db.getTable("findings");

            int i = 0;

            String findingDescription;
            String accNo;
            String eventId;
            int findingNumber;
            int k = 0;
            List<Finding> findings = new ArrayList<>();
            Map<Finding, String> eventIds = new HashMap<>();

            for(Row row : table){
                Finding finding = new Finding();

                eventId = row.getString("ev_id");

                eventIds.put(finding, eventId);

                try {
                    findingDescription = row.getString("finding_description");
                    finding.setDescription(findingDescription);
                } catch(Exception e){
                    logger.info("Row 'ev_id' = {} has value in 'finding_description' = {}", row.getString("ev_id"), e.getMessage());
                }

                try {
                    findingNumber = row.getInt("finding_no");
                    finding.setNumber(findingNumber);
                } catch(Exception e){
                    logger.info("Row 'ev_id' = {} has value in 'finding_no' = {}", row.getString("ev_id"), e.getMessage());
                }

                findings.add(finding);
                findingRepository.save(finding);


                if (++k % 1500 == 0) {
                    setEventToFindings(findings, eventIds);
                }

                if (++i % 10000 == 0) {
                    logger.info("Committing findings i={}", i);
                    entityManager.flush();
                    entityManager.clear();
                }
                //if(++j % 4000 == 0) break; //break after 4k imported findings to see statistics
            }

            if(k % 1500 != 0){
                setEventToFindings(findings, eventIds);
            }

            if (++i % 10000 != 0) {
                logger.info("Committing findings i={}", i);
                entityManager.flush();
                entityManager.clear();
            }

            logger.info("table 'findings' import successful");

        } catch (Exception e) {
            logger.error("DB access error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    private void setEventToFindings(List<Finding> findings, Map<Finding, String> eventIds) {
        List<Event> events = repository.findByEventIds(eventIds.values());
        Map<String, Event> eventMap = events.stream()
                .collect(Collectors.toMap(e -> e.getEventId(), e -> e));

        for (Finding f : findings){
            String fEventId = eventIds.get(f);
            f.setEvent(eventMap.get(fEventId));
        }

        //clear all the lists
        eventIds.clear();
        findings.clear();
    }

    /**
     * @should import occurrences to DB
     */
    @Override
    @Transactional
    public void importOccurrencesData(){

        try(Database db = DatabaseBuilder.open(new File(databaseFileURL))){

            //importing occurrences table
            logger.info("Importing 'Occurrences + ct_seqevt' tables into 'Occurrence'");
            Table occurrenceTable = db.getTable("Occurrences");
            Table ct_seqevtTable = db.getTable("ct_seqevt");

            String eventId;
            String occurrenceMeaning; //findingDescription to become
            int occurrenceNumber; //findingNumber to become
            int occurrenceCode; //to map the OccurrenceMeaning
            int i = 0; //for batching
            int k = 0;
            //int j = 0;
            List<Occurrence> occurrences = new ArrayList<>();
            Map<Occurrence, String> eventIds = new HashMap<>();

            for(Row row : occurrenceTable) {
                Occurrence occurrence = new Occurrence();

                eventId = row.getString("ev_id");
                eventIds.put(occurrence, eventId);

                try {
                    occurrenceCode = row.getInt("Occurrence_Code");
                    Row ct_seqevtRow = CursorBuilder.findRow(ct_seqevtTable, Collections.singletonMap("code", occurrenceCode));
                    occurrenceMeaning = ct_seqevtRow.getString("meaning");
                    occurrence.setDescription(occurrenceMeaning);
                } catch (Exception e) {
                    logger.info("Row 'ev_id' = {} has value in 'Occurrence_Code' = {}", row.getString("ev_id"), e.getMessage());
                }

                try {
                    occurrenceNumber = row.getInt("Occurrence_No");
                    occurrence.setNumber(occurrenceNumber);
                } catch (Exception e) {
                    logger.info("Row 'ev_id' = {} has value in 'Occurrence_No' = {}", row.getString("ev_id"), e.getMessage());
                }

                occurrences.add(occurrence);
                occurrenceRepository.save(occurrence);

                if(++k % 1500 == 0){
                    setEventToOccurrences(occurrences, eventIds);
                }

                if (++i % 10000 == 0) {
                    logger.info("Committing occurrences i={}", i);
                    entityManager.flush();
                    entityManager.clear();
                }

                //if(++j % 40000 == 0) break;
            }

            if(k % 1500 != 0){
                setEventToOccurrences(occurrences, eventIds);
            }

            if (i % 10000 != 0) {
                logger.info("Committing occurrences i={}", i);
                entityManager.flush();
                entityManager.clear();
            }

            logger.info("table 'Occurrences' import successful");

        } catch (Exception e) {
            logger.error("DB access error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    private void setEventToOccurrences(List<Occurrence> occurrences, Map<Occurrence, String> eventIds) {
        List<Event> events = repository.findByEventIds(eventIds.values());
        Map<String, Event> eventMap = events.stream()
                .collect(Collectors.toMap(e -> e.getEventId(), e -> e));

        for (Occurrence o : occurrences){
            String oEventId = eventIds.get(o);
            o.setEvent(eventMap.get(oEventId));
        }

        //clear all the lists
        eventIds.clear();
        occurrences.clear();
    }

    /**
     * @should import states to DB
     */
    @Override
    @Transactional
    public void importStatesData(){
        try(Database db = DatabaseBuilder.open(new File(databaseFileURL))){

            //importing states table
            Table table = db.getTable("states");
            String abbrev;
            String name;

            logger.info("Importing 'states' table");
            for(Row row : table){
                abbrev = row.getString("state");
                name = row.getString("name").trim();
                State state = new State(abbrev, name);
                stateRepository.save(state);
            }
            logger.info("table 'states' import successful");

        } catch (Exception e) {
            logger.error("DB access error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }

/*    @Override
    @Transactional
    public void importUsersData(){
        try(Database db = DatabaseBuilder.open(new File(databaseFileURL))){

            //importing users table
            Table table = db.getTable("users");
            String username;
            String password;

            logger.info("Importing 'users' table");
            for(Row row : table){
                username = row.getString("username");
                password = passwordEncoder.encode(row.getString("password"));
                User user = new User(username, password);
                if(userRepository.findByUsername(username) == null)
                    userRepository.save(user);
            }
            logger.info("table 'user' import successful");

        } catch (Exception e) {
            logger.error("DB access error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }*/

    @Override
    @Transactional
    public void deleteAllData(){

        logger.info("Deleting 'occurrence' table");
        String sqlDrop = "TRUNCATE occurrence;";
        entityManager.createNativeQuery(sqlDrop).executeUpdate();

//        logger.info("Deleting 'occurrence' table");
//        occurrenceRepository.deleteAllInBatch();
//        occurrenceRepository.deleteAll();
        logger.info("'occurrence' table deleted");

        logger.info("Deleting 'finding' table");
        sqlDrop = "TRUNCATE finding;";
        entityManager.createNativeQuery(sqlDrop).executeUpdate();
//        findingRepository.deleteAll();
        logger.info("'finding' table deleted");

        logger.info("Deleting 'events' table");
        sqlDrop = "TRUNCATE event CASCADE;";
        entityManager.createNativeQuery(sqlDrop).executeUpdate();
//        repository.deleteAll();
        logger.info("'event' table deleted");

        logger.info("Deleting 'states' table");
        sqlDrop = "TRUNCATE state CASCADE;";
        entityManager.createNativeQuery(sqlDrop).executeUpdate();
//        stateRepository.deleteAll();
        logger.info("'state' table deleted");
    }

    /**
     * @should import events to DB
     */
    @Override
    @Transactional
    public void importEventsData(){

        try(Database db = DatabaseBuilder.open(new File(databaseFileURL))){

            //importing events table
            logger.info("Importing 'events' table");
            Table table = db.getTable("events");

            String eventId;
            String accNo;
            Date date;
            String dateString;
//            int timeInt;
            String timeZoneStr = null;
            int hour = 12;
            int minute = 30;
            int timeNumber;
            TimeZone timeZone = null;
            String city;
            String stateString;
            int year;
            String latitude;
            String longitude;
            Date lchgdate;

            int i = 0; //for batching
            State state;
            Map<String, State> stateMap;

            List<State> states = (List<State>) stateRepository.findAll();
            Map<String, State> statesMap = states.stream()
                    .collect(Collectors.toMap(s -> s.getAbbreviation(), s -> s ));

            //SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");

            List<String> availableIds = Arrays.asList(TimeZone.getAvailableIDs());

            Calendar cal = Calendar.getInstance();
//                logger.info("cal = {} ", cal);
//            cal.setTimeZone(TimeZone.getTimeZone("UTC"));
//                logger.info("cal after setting to UTC= {} ", cal);



            for(Row row : table){
                Event event = new Event();

                eventId = row.getString("ev_id");
                event.setEventId(eventId);

                accNo = row.getString("ntsb_no");
                event.setAccNo(accNo);



//                try {
////                    try{
////                        timeZoneStr = row.getString("ev_tmzn");
////                        TimeZone.setDefault(TimeZone.getTimeZone(timeZoneStr));
////
////                        cal.setTimeZone(TimeZone.getTimeZone(timeZoneStr));
//////                        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
////
////                    } catch (Exception e){
////                        logger.info("Row 'ntsb_no' = {} has value in 'ev_tmzn' = {}, so setting to UTC", row.getString("ntsb_no"), e.getMessage());
////                        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
////                        timeZoneStr = "UTC";
////                    }
//
//                    date = row.getDate("ev_date");
////                    cal.setTime(date);
//
//                    cal.set(Calendar.YEAR, date.getYear());
//                    cal.set(Calendar.MONTH, date.getMonth());
//                    cal.set(Calendar.DAY_OF_MONTH , date.getDay());
//
//                    try{
//                        timeNumber = row.getShort("ev_time");
//
//                        hour = timeNumber / 100;
//                        minute = timeNumber % 100;
//
//                        cal.set(Calendar.HOUR_OF_DAY, hour);
//                        cal.set(Calendar.MINUTE, minute);
//                        cal.set(Calendar.SECOND, 0);
//                        cal.set(Calendar.MILLISECOND, 0);
//
//
//
//                    } catch(Exception e){
//
//                        logger.info("Row 'ntsb_no' = {} has value in 'ev_time' = {}, so setting to default 12:00", row.getString("ntsb_no"), e.getMessage());
//                        cal.set(Calendar.HOUR_OF_DAY, 12);
//                        cal.set(Calendar.MINUTE, 0);
//                        cal.set(Calendar.SECOND, 0);
//                        cal.set(Calendar.MILLISECOND, 0);
//                    }
//
////
//
//
////                TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
////                cal.setTimeZone(TimeZone.getTimeZone(timeZoneStr));
//
//                date = cal.getTime();
//                event.setDate(date);
//
//                } catch(Exception e){
//                    logger.info("Row 'ntsb_no' = {} has value in 'ev_date' = {}, so setting to 1900-01-01", row.getString("ntsb_no"), e.getMessage());
//                    cal.set(Calendar.YEAR, 1900);
//                    cal.set(Calendar.MONTH, 1);
//                    cal.set(Calendar.DAY_OF_MONTH, 1);
//                }

                try {
//                    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                    date = row.getDate("ev_date");
                    cal.setTime(date);
                    cal.set(Calendar.HOUR_OF_DAY, date.getHours());
                    cal.setTimeZone(TimeZone.getTimeZone("UTC"));
                    date = cal.getTime();
                    event.setDate(date);

                } catch(Exception e){
                    logger.info("Row 'ntsb_no' = {} has value in 'lchg_date' = {}", row.getString("ntsb_no"), e.getMessage());
                }

                try {
//                    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                    lchgdate = row.getDate("lchg_date");
                    event.setLchgdate(lchgdate);
                } catch(Exception e){
                    logger.info("Row 'ntsb_no' = {} has value in 'lchg_date' = {}", row.getString("ntsb_no"), e.getMessage());
                }

                try {
                    city = row.getString("ev_city");
                    event.setCity(city);
                } catch(Exception e){
                    logger.info("Row 'ntsb_no' = {} has value in 'ev_city' = {}", row.getString("ntsb_no"), e.getMessage());
                }

                try {
                    stateString = row.getString("ev_state");
                    //State state = stateRepository.findByAbbreviationNoFlush(stateString);
                    state = statesMap.get(stateString);
                    event.setState(state);
                } catch(Exception e){
                    logger.info("Row 'ntsb_no' = {} has value in 'ev_state' = {}", row.getString("ntsb_no"), e.getMessage());
                }

                try {
                    year = row.getShort("ev_year");
                    event.setYear(year);
                } catch(Exception e){
                    logger.info("Row 'ntsb_no' = {} has value in 'ev_year' = {}", row.getString("ntsb_no"), e.getMessage());
                }

                try {
                    latitude = row.getString("latitude");
                    event.setLatitude(latitude);
                } catch(Exception e){
                    logger.info("Row 'ntsb_no' = {} has value in 'latitude' = {}", row.getString("ntsb_no"), e.getMessage());
                }

                try {
                    longitude = row.getString("longitude");
                    event.setLongitude(longitude);
                } catch(Exception e){
                    logger.info("Row 'ntsb_no' = {} has value in 'latitude' = {}", row.getString("ntsb_no"), e.getMessage());
                }

                repository.save(event);
                if (++i % 10000 == 0) {
                    logger.info("Committing events i={}", i);
                    entityManager.flush();
                    entityManager.clear();
                }
            }
            logger.info("table 'events' import successful");

        } catch (Exception e) {
            logger.error("DB access error occurred: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
