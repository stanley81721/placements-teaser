package com.interview.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class InvoiceHelper {
    
    public String generateInvoiceNumber() {
        Timestamp time= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String invoiceNumber = df.format(time); 
        System.out.println(invoiceNumber);

        return invoiceNumber;
    }

}
