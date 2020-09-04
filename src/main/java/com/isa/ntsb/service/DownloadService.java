package com.isa.ntsb.service;

import com.isa.ntsb.NtsbApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by Karolis on 24-Sep-19.
 */

@Service
public class DownloadService {

    private static final Logger logger = LoggerFactory.getLogger(NtsbApplication.class);

    @Value("${access.zip:aDefaultUrl}")
    private String filePathProperty;

    @Value("${access.url:aDefaultUrl}")
    private String accessUrlProperty;

    private static String accessUrl;
    private static String filePath;

    @PostConstruct
    private void init(){
        accessUrl = accessUrlProperty;
        filePath = filePathProperty;
    }

    public void downloadAvall() {

        logger.info("Started downloading avall.zip");

        try (InputStream in = new URL(accessUrl).openStream()) {
            Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Downloading avall.zip successful");

        } catch (IOException e) {
            logger.info("Downloading avall.zip failed");
            e.printStackTrace();
        }
    }

    public static void downloadWithJavaNIO() throws IOException {

        URL url = new URL(accessUrl);
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             FileChannel fileChannel = fileOutputStream.getChannel()) {

            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
        }
    }

    public static long downloadFile() throws IOException, URISyntaxException {

        File outputFile = new File(filePath);
        URLConnection downloadFileConnection = new URI(accessUrl).toURL()
                .openConnection();
        return transferDataAndGetBytesDownloaded(downloadFileConnection, outputFile);
    }

    private static long transferDataAndGetBytesDownloaded(URLConnection downloadFileConnection, File outputFile) throws IOException {

        long bytesDownloaded = 0;
        try (InputStream is = downloadFileConnection.getInputStream(); OutputStream os = new FileOutputStream(outputFile, true)) {

            byte[] buffer = new byte[1024];

            int bytesCount;
            while ((bytesCount = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesCount);
                bytesDownloaded += bytesCount;
            }
        }
        return bytesDownloaded;
    }

    public static long downloadFileWithResume() throws IOException, URISyntaxException {
        File outputFile = new File(filePath);

        URLConnection downloadFileConnection = addFileResumeFunctionality(accessUrl, outputFile);
        return transferDataAndGetBytesDownloaded(downloadFileConnection, outputFile);
    }

    private static URLConnection addFileResumeFunctionality(String downloadUrl, File outputFile) throws IOException, URISyntaxException, ProtocolException, ProtocolException {
        long existingFileSize = 0L;
        URLConnection downloadFileConnection = new URI(downloadUrl).toURL()
                .openConnection();

        if (outputFile.exists() && downloadFileConnection instanceof HttpURLConnection) {
            HttpURLConnection httpFileConnection = (HttpURLConnection) downloadFileConnection;

            HttpURLConnection tmpFileConn = (HttpURLConnection) new URI(downloadUrl).toURL()
                    .openConnection();
            tmpFileConn.setRequestMethod("HEAD");
            long fileLength = tmpFileConn.getContentLengthLong();
            existingFileSize = outputFile.length();

            if (existingFileSize < fileLength) {
                httpFileConnection.setRequestProperty("Range", "bytes=" + existingFileSize + "-" + fileLength);
            } else {
                throw new IOException("File Download already completed.");
            }
        }
        return downloadFileConnection;
    }
}
