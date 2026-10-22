package eu.virac.vea_izi_vsrc.service.impl.fileConvert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.fileConvert.ExcelRow;
import eu.virac.vea_izi_vsrc.service.fileConvert.IFileService;

@Service
public class FileServiceImpl implements IFileService {


	
    // EXPORT EXCEL 
    @Override
    public byte[] exportExcel(List<ExcelRow> rows) throws Exception {

        if(rows == null || rows.isEmpty()) {
            throw new Exception("Nav datu eksportam");
        }

        Workbook wb = new XSSFWorkbook();	//create a new workbook
        Sheet sheet = wb.createSheet("data");	//create a sheet

        //data to write
        Map<Long, List<ExcelRow>> grouped = groupByEntity(rows);
        List<String> headers = collectHeaders(rows);

        int r = 0;

        // Header row
        Row headerRow = sheet.createRow(r++);
        int col = 0;
        headerRow.createCell(col++).setCellValue("ID");
        for(String h : headers) {
            if(!h.equals("ID")) {
                headerRow.createCell(col++).setCellValue(h);
            }
        }

        // Data rows
        for(var entry : grouped.entrySet()) {

            Long entityId = entry.getKey();
            List<ExcelRow> entityRows = entry.getValue();

            Row row = sheet.createRow(r++);
            int c = 0;

         // ID must be written as String, otherwise POI throws error
            row.createCell(c++).setCellValue(entityId == null ? "" : String.valueOf(entityId));

            for(String h : headers) {
                if(h.equals("ID")) continue;
                row.createCell(c++).setCellValue(findValue(entityRows, h));
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        wb.close();

        return bos.toByteArray();
    }



    // IMPORT EXCEL 
    @Override
    public List<ExcelRow> importExcel(byte[] fileBytes) throws Exception {

        if(fileBytes == null || fileBytes.length == 0) {
            throw new Exception("Faila saturs ir tukšs");
        }

        List<ExcelRow> rows = new ArrayList<>();

        Workbook wb = new XSSFWorkbook(new ByteArrayInputStream(fileBytes));
        Sheet sheet = wb.getSheetAt(0);

        if(sheet == null) {
            throw new Exception("Excel failā nav nevienas lapas");
        }

        var it = sheet.iterator();
        if(!it.hasNext()) {
            throw new Exception("Excel failā nav datu");
        }

        Row headerRow = it.next();
        List<String> headers = new ArrayList<>();
        for(int i = 0; i < headerRow.getLastCellNum(); i++) {
            headers.add(getCellValue(headerRow.getCell(i)));
        }

        int idIndex = headers.indexOf("ID");
        if(idIndex == -1) {
            throw new Exception("Excel failā nav ID kolonnas");
        }

        while(it.hasNext()) {
            Row row = it.next();

            Long entityId = parseLong(getCellValue(row.getCell(idIndex)));

            for(int i = 0; i < headers.size(); i++) {
                if(i == idIndex) continue;

                String columnName = headers.get(i);
                String columnValue = getCellValue(row.getCell(i));

                rows.add(new ExcelRow(null, entityId, columnName, columnValue));
            }
        }

        wb.close();
        return rows;
    }



    // EXPORT WORD 
    @Override
    public byte[] exportWord(List<ExcelRow> rows) throws Exception {

        if(rows == null || rows.isEmpty()) {
            throw new Exception("Nav datu eksportam");
        }

        Map<Long, List<ExcelRow>> grouped = groupByEntity(rows);
        List<String> headers = collectHeaders(rows);

        XWPFDocument doc = new XWPFDocument();
        XWPFTable table = doc.createTable();

        // Header row
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("ID");
        for(String h : headers) {
            if(!h.equals("ID")) {
                headerRow.addNewTableCell().setText(h);
            }
        }

        // Data rows
        for(var entry : grouped.entrySet()) {

            Long entityId = entry.getKey();
            List<ExcelRow> entityRows = entry.getValue();

            XWPFTableRow row = table.createRow();
            int c = 0;

            row.getCell(c++).setText(entityId == null ? "" : String.valueOf(entityId));

            for(String h : headers) {
                if(h.equals("ID")) continue;
                row.getCell(c++).setText(findValue(entityRows, h));
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        doc.write(bos);
        doc.close();

        return bos.toByteArray();
    }



    // IMPORT WORD 
    @Override
    public List<ExcelRow> importWord(byte[] fileBytes) throws Exception {

        if(fileBytes == null || fileBytes.length == 0) {
            throw new Exception("Faila saturs ir tukšs");
        }

        List<ExcelRow> rows = new ArrayList<>();

        XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(fileBytes));

        for(XWPFTable table : doc.getTables()) {

            List<XWPFTableRow> tableRows = table.getRows();
            if(tableRows.isEmpty()) {
                throw new Exception("Word tabula ir tukša");
            }

            XWPFTableRow headerRow = tableRows.get(0);
            List<String> headers = new ArrayList<>();
            for(int i = 0; i < headerRow.getTableCells().size(); i++) {
                headers.add(headerRow.getCell(i).getText());
            }

            int idIndex = headers.indexOf("ID");
            if(idIndex == -1) {
                throw new Exception("Word failā nav ID kolonnas");
            }

            for(int r = 1; r < tableRows.size(); r++) {

                XWPFTableRow tr = tableRows.get(r);
                Long entityId = parseLong(tr.getCell(idIndex).getText());

                for(int i = 0; i < headers.size(); i++) {
                    if(i == idIndex) continue;

                    String columnName = headers.get(i);
                    String columnValue = tr.getCell(i).getText();

                    rows.add(new ExcelRow(null, entityId, columnName, columnValue));
                }
            }
        }

        doc.close();
        return rows;
    }


    // -----------------------------------------------------
    // HELPERS 
    // -----------------------------------------------------
    private Map<Long, List<ExcelRow>> groupByEntity(List<ExcelRow> rows) {
        Map<Long, List<ExcelRow>> map = new LinkedHashMap<>();
        for(ExcelRow r : rows) {
            map.computeIfAbsent(r.getEntityId(), k -> new ArrayList<>()).add(r);
        }
        return map;
    }

    
    private List<String> collectHeaders(List<ExcelRow> rows) {
        List<String> headers = new ArrayList<>();
        for(ExcelRow r : rows) {
            if(!headers.contains(r.getColumnName())) {
                headers.add(r.getColumnName());
            }
        }
        return headers;
    }

    
    private String findValue(List<ExcelRow> rows, String columnName) {
        for(ExcelRow r : rows) {
            if(columnName.equals(r.getColumnName())) {
                return r.getColumnValue();
            }
        }
        return "";
    }

    
    private String getCellValue(Cell cell) {
        if(cell == null) {
        	return "";
        }
        
        switch(cell.getCellType()) {
            case STRING : 
            	return cell.getStringCellValue();
            case NUMERIC :
            	return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN :
            	return String.valueOf(cell.getBooleanCellValue());
            default :
            	return "";
        }
    }

    
    
    private Long parseLong(String s) {
        try {
            return Long.parseLong(s.trim());
        }
        catch(Exception e) {
            return null;
        }
    }


}
