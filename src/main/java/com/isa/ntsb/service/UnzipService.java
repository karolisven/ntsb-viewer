package com.isa.ntsb.service;

import com.isa.ntsb.NtsbApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Karolis on 24-Sep-19.
 */

@Service
public class UnzipService {

    private static final Logger logger = LoggerFactory.getLogger(NtsbApplication.class);

    @Value("${access.zip:aDefaultUrl}")
    private String accessZipProperty;

    @Value("${access.directory:aDefaultUrl}")
    private String accessDirProperty;

    private static String fileZip;
    private static String destDirPath;

    @PostConstruct
    private void init(){
        fileZip = accessZipProperty;
        destDirPath = accessDirProperty;
    }

    public static void unzipAvall() throws IOException {
//        String fileZip = "D:/Karolis/ntsb-viewer/data/avall.zip";

        logger.info("Unzippping avall.zip");

        File destDir = new File(destDirPath);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

        logger.info("Unzipped avall.zip successfully");
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
