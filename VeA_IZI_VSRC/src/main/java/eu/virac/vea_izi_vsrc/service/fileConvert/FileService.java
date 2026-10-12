package eu.virac.vea_izi_vsrc.service.fileConvert;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import eu.virac.vea_izi_vsrc.model.fileConvert.ExcelRow;

public interface FileService {

	// Export a list of ExcelRow objects into an Excel (.xlsx) file. -> The file is streamed directly to the browser.
	void exportExcel(List<ExcelRow> rows, OutputStream os) throws Exception;

	// Import an Excel (.xlsx) file and convert its rows into ExcelRow objects.
    // These ExcelRow objects are later converted into NEW entities (model cl).
	List<ExcelRow> importExcel(InputStream is) throws Exception;

	// Export a list of ExcelRow objects into a Word (.docx) file.
    // The file is streamed directly to the browser.
	void exportWord(List<ExcelRow> rows, OutputStream os) throws Exception;

	// Import a Word (.docx) file and convert its paragraphs into ExcelRow objects.
    // These ExcelRow objects are later converted into NEW entities (model cl).
	List<ExcelRow> importWord(InputStream is) throws Exception;

}
