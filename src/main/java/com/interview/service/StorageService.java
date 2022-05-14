package com.interview.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageService {
    
    private String bucketName = "placementteaserbucket";

    @Autowired
    private AmazonS3 s3Client;

    public void uploadFile(ByteArrayOutputStream byteArrayOutputStream) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        ByteArrayInputStream bi = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        Long contentLength = Long.valueOf(byteArrayOutputStream.toByteArray().length);

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType("application/octet-stream");
        objectMetaData.setContentLength(contentLength);
        s3Client.putObject(new PutObjectRequest(bucketName, "invoice_" + currentDateTime + ".xlsx", bi, objectMetaData));

        try {
            bi.close();
        } catch (IOException e) {
            log.error("Error accure when closing byteArrayInputStream!", e);
        }
    }

}
