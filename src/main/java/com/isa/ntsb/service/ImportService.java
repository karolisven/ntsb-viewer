package com.isa.ntsb.service;

import com.isa.ntsb.persistence.EventRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Karolis on 6/24/2019.
 */
@Service
public interface ImportService {

    void importEventsData();

    void deleteAllData();

    void importStatesData();

    void importFindingsData();

    void importOccurrencesData();

//    void importUsersData();
}
