package com.interview.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.interview.model.Campaign;
import com.interview.model.Invoice;
import com.interview.model.LineItem;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InvoiceExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Invoice invoice;
    private int rowCount = 0;

    public InvoiceExcelExporter(Invoice invoice) {
        this.invoice = invoice;
        workbook = new XSSFWorkbook();
    }

    private void writeInvoiceHeader() {
        sheet = workbook.createSheet("Invoice");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
         
        createCell(row, 0, invoice.getInvoiceNumber(), style);      
    }

    private void writeCampaignHeader(int rowCount, Campaign campaign) {
        rowCount++;

        Row row = sheet.createRow(rowCount);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 4));

        createCell(row, 0, campaign.getCampaignName(), style); 

        this.rowCount = rowCount;
    }

    private void writeLineItemHeader(int rowCount) {
        rowCount++;

        Row row = sheet.createRow(rowCount);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        createCell(row, 0, "Name", style); 
        createCell(row, 1, "Booked Amount", style);       
        createCell(row, 2, "Actual Amount", style);    
        createCell(row, 3, "Adjusyments", style);
        createCell(row, 4, "Billable Amount", style);

        this.rowCount = rowCount;
    }

    private void writeLineItemData(int rowCount, List<LineItem> lineItems) {
        rowCount++;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
          
        for (LineItem lineItem : lineItems) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, lineItem.getName(), style);
            createCell(row, columnCount++, lineItem.getBookedAmount().doubleValue(), style);
            createCell(row, columnCount++, lineItem.getActualAmount().doubleValue(), style);
            createCell(row, columnCount++, lineItem.getAdjustments().doubleValue(), style);
            createCell(row, columnCount++, lineItem.getBillableAmount().doubleValue(), style);
        }

        this.rowCount = rowCount;
    }

    private void writeGrandTotal(int rowCount, List<Campaign> campaigns) {
        rowCount++;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
      
        BigDecimal grandTotal = new BigDecimal(0);
        for (Campaign campaign : campaigns) {
            grandTotal = grandTotal.add(campaign.getSubTotals());
        }

        Row row = sheet.createRow(rowCount);

        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 2, 4));

        createCell(row, 0, "Grand Total", style);
        createCell(row, 2, grandTotal.doubleValue(), style);

        this.rowCount = rowCount;
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeInvoiceHeader();
        for(int i = 0;i < invoice.getCampaigns().size();i++) {
            writeCampaignHeader(rowCount, invoice.getCampaigns().get(i));
            writeLineItemHeader(rowCount);
            writeLineItemData(rowCount, invoice.getCampaigns().get(i).getLineItems());
        }
        writeGrandTotal(rowCount, invoice.getCampaigns());
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
    }
    
}
