package eu.virac.vea_izi_vsrc.service.impl.fileConvert;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.model.fileConvert.ExcelRow;
import eu.virac.vea_izi_vsrc.service.fileConvert.FileService;

@Service
public class FileServiceImpl implements FileService {

	
	// EXPORT EXCEL
    @Override
    public void exportExcel(List<ExcelRow> rows, OutputStream os) throws Exception {

        if (rows == null || rows.isEmpty()) {
            throw new Exception("No data available for export.");
        }
        
        // Create Excel workbook in memory
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("data");

        int rowIdx = 0;

        // Header row
        Row header = sheet.createRow(rowIdx++);
        header.createCell(0).setCellValue("Entity ID");
        header.createCell(1).setCellValue("COL1");
        header.createCell(2).setCellValue("COL2");
        header.createCell(3).setCellValue("COL3");
        header.createCell(4).setCellValue("COL4");
        header.createCell(5).setCellValue("COL5");

        
        // Data rows
        for (ExcelRow r : rows) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(r.getEntityId() == null ? 0 : r.getEntityId());
            row.createCell(1).setCellValue(safe(r.getCol1()));
            row.createCell(2).setCellValue(safe(r.getCol2()));
            row.createCell(3).setCellValue(safe(r.getCol3()));
            row.createCell(4).setCellValue(safe(r.getCol4()));
            row.createCell(5).setCellValue(safe(r.getCol5()));
        }

        
        // Stream file to browser (NOT saved on server)
        wb.write(os);
        wb.close();
    }

    
    // IMPORT EXCEL
    @Override
    public List<ExcelRow> importExcel(InputStream is) throws Exception {

        List<ExcelRow> rows = new ArrayList<>();

        Workbook wb;
        try {
            wb = new XSSFWorkbook(is);		// Read file from memory
        }
        catch (Exception e) {
            throw new Exception("Invalid Excel file format.");
        }

        Sheet sheet = wb.getSheetAt(0);

        boolean header = true;
        for (Row row : sheet) {
            if (header) {
                header = false;		// Skip header row
                continue;
            }

            ExcelRow r = new ExcelRow();
            r.setEntityId(getLong(row, 0)); 	// informational only
            r.setCol1(getString(row, 1));
            r.setCol2(getString(row, 2));
            r.setCol3(getString(row, 3));
            r.setCol4(getString(row, 4));
            r.setCol5(getString(row, 5));

            rows.add(r);
        }

        wb.close();
        return rows;		// Controller converts these into NEW entities
    }

    
    // Excel stores numbers as double, even if they look like integers.
    // Reads NUMBERS from excel -> 5 is 5.0
    // for entityId
    private Long getLong(Row row, int index) {
        try {
            return (long) row.getCell(index).getNumericCellValue();
        }
        catch (Exception e) {
            return null;
        }
    }

    // Reads a TEXT cell from Excel
    // for col1, col2 ... col5
    private String getString(Row row, int index) {
        try {
            return row.getCell(index).getStringCellValue();
        }
        catch (Exception e) {
            return "";	// if cell is numeric or empty
        }
    }

    
    //EXPORT WORD
    @Override
    public void exportWord(List<ExcelRow> rows, OutputStream os) throws Exception {

        if (rows == null || rows.isEmpty()) {
            throw new Exception("No data available for export.");
        }

        XWPFDocument doc = new XWPFDocument();

        for (ExcelRow r : rows) {
        	
        	// Header paragraph
            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();
            run.setBold(true);
            run.setText("Entity ID: " + (r.getEntityId() == null ? "" : r.getEntityId()));

            
            // Data paragraph
            XWPFParagraph p2 = doc.createParagraph();
            XWPFRun r2 = p2.createRun();
            r2.setText("COL1: " + safe(r.getCol1())); r2.addBreak();
            r2.setText("COL2: " + safe(r.getCol2())); r2.addBreak();
            r2.setText("COL3: " + safe(r.getCol3())); r2.addBreak();
            r2.setText("COL4: " + safe(r.getCol4())); r2.addBreak();
            r2.setText("COL5: " + safe(r.getCol5())); r2.addBreak();
        }

        
        // Stream file to browser
        doc.write(os);
        doc.close();
    }

    
    // IMPORT WORD
    @Override
    public List<ExcelRow> importWord(InputStream is) throws Exception {

        List<ExcelRow> rows = new ArrayList<>();

        XWPFDocument doc;
        try {
            doc = new XWPFDocument(is);			// Read file from memory
        }
        catch (Exception e) {
            throw new Exception("Invalid Word file format.");
        }

        
        ExcelRow current = null;

        for (XWPFParagraph p : doc.getParagraphs()) {
            String text = p.getText().trim();

            
            // Detect start of a new row
            if (text.startsWith("Entity ID:")) {
                if (current != null) rows.add(current);
                current = new ExcelRow();
                String idStr = text.replace("Entity ID:", "").trim();
                if (!idStr.isEmpty()) {
                    try {
                        current.setEntityId(Long.parseLong(idStr));
                    } catch (NumberFormatException ignored) {}
                }
            }
            
            // Map columns
            else if (text.startsWith("COL1:")) current.setCol1(text.replace("COL1:", "").trim());
            else if (text.startsWith("COL2:")) current.setCol2(text.replace("COL2:", "").trim());
            else if (text.startsWith("COL3:")) current.setCol3(text.replace("COL3:", "").trim());
            else if (text.startsWith("COL4:")) current.setCol4(text.replace("COL4:", "").trim());
            else if (text.startsWith("COL5:")) current.setCol5(text.replace("COL5:", "").trim());
        }

        if (current != null) rows.add(current);

        doc.close();
        return rows;	// Controller converts these into NEW entities
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
