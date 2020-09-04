package com.isa.ntsb.controllers;

import com.isa.ntsb.service.DownloadService;
import com.isa.ntsb.service.UnzipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
import java.net.URISyntaxException;


/**
 * Created by Karolis on 23-Sep-19.
 */

@RestController
@RequestMapping("/avall")
public class AvallController {

    @Autowired
    DownloadService downloadService;

    @Autowired
    UnzipService unzipService;

    @Autowired
    ImportController importController;

    @GetMapping("/download")
    public void downloadAvall() {
        downloadService.downloadAvall();
    }

    @GetMapping("/download/nio")
    public void downloadWithJavaNIO() {
        try {
            downloadService.downloadWithJavaNIO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download/resumable")
    public void downloadResumable(){
        try {
            downloadService.downloadFileWithResume();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/unzip")
    public void unzipAvall(){
        try {
            unzipService.unzipAvall();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.expression}")
    @GetMapping("/download+unzip+import")
    public void downloadAndUnzip() {
        downloadAvall();
        unzipAvall();
        importController.deleteAllData();
        importController.importData();

    }

}
