package com.isa.ntsb.controllers;

import com.isa.ntsb.service.ImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Karolis on 24-Jul-19.
 */

@Api(value = "Import_Controller",
        description = "Mapping data import requests from Access DB to Postgres DB which" +
                "this application is working on")
@RestController
@RequestMapping("/import")
public class ImportController {

    @Autowired
    ImportService importService;

    @ApiOperation(value = "Import Events table")
    @PostMapping("/eventsData")
    public void importEventsData(){
        importService.importEventsData();
    }

    @ApiOperation(value = "Delete all the tables from Postgres DB")
    @DeleteMapping("/deleteAllData")
    public void deleteAllData(){
        importService.deleteAllData();
    }

    @ApiOperation(value = "Import States table")
    @PostMapping("/statesData")
    public void importStatesData(){
        importService.importStatesData();
    }

    @ApiOperation(value = "Import Findings table")
    @PostMapping("/findingsData")
    public void importFindingsData(){
        importService.importFindingsData();
    }

    @ApiOperation(value = "Import Occurrences table")
    @PostMapping("/occurrencesData")
    public void importOccurencesData(){
        importService.importOccurrencesData(); //Occurences get merged into findings table
    }

    @ApiOperation(value = "Import all the tables needed at for this application at once")
    @PostMapping("/allData")
    public void importData(){
        importService.importStatesData();
        importService.importEventsData();
        importService.importFindingsData();
        importService.importOccurrencesData();
    }
}
