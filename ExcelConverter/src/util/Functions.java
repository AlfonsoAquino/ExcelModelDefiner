package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Functions {

	private static List<List<HSSFCell>> sheetData;
	private static String filePath="\\ModelliEbay\\";

	public static List<List<HSSFCell>> fillTheSheet(InputStream streamStarter, int intestazione) throws IOException {
		// Create an ArrayList to store the data read from excel sheet.
		sheetData = new ArrayList<>();
		int i = 1;
		// Create an excel workbook from the file system.
		Workbook workbook = WorkbookFactory.create(streamStarter);
		// Get the first sheet on the workbook.
		Sheet sheet = workbook.getSheetAt(0);

		// When we have a sheet object in hand we can iterator on
		// each sheet's rows and on each row's cells. We store the
		// data read on an ArrayList so that we can printed the
		// content of the excel to the console.
		Iterator<Row> rows = sheet.rowIterator();

		while (rows.hasNext()) {
			HSSFRow row = (HSSFRow) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			List<HSSFCell> data = new ArrayList<>();

			while (cells.hasNext()) {
				HSSFCell cell = (HSSFCell) cells.next();
				if (i == intestazione) {
					data.add(cell);
				}
			}
			if (i == intestazione) {
				sheetData.add(data);
				break;
			}
			workbook.close();
			i++;
		}

		System.out.println("---------------->" + intestazione);
		return sheetData;
	}

	public static String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
			}
		}
		return "";
	}

	public static void mergeTheSheet(List<Collegamento> collega, List<List<HSSFCell>>sheetNaming, String fileName) throws IOException {

		Workbook workbook = new HSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
//		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Modello");

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
			
			for (List<HSSFCell> item : sheetNaming) {
				for (int j = 0; j < item.size(); j++) {
					Cell cell = headerRow.createCell(j);
					cell.setCellValue(item.get(j).toString());
				}
		}

		// Create Other rows and cells with employees data
		int rowNum = 1;
		Row rowCollega = sheet.createRow(rowNum++);
		Row rowOrdine = sheet.createRow(rowNum++);
		Row rowLimite = sheet.createRow(rowNum++);
		int io=0;
		for (Collegamento employee : collega) {
			
			System.out.println(collega.size());
			//aggiungere altri campi di collegamento dall'arraylist
			rowCollega.createCell(io).setCellValue(employee.getDestinazione());
			rowOrdine.createCell(io).setCellValue(employee.getOrdine());//employee.getOrdine
			rowLimite.createCell(io).setCellValue(employee.getLimite());
			io++;
			
		}

//		// Resize all columns to fit the content size
//		for (int i = 0; i < colonne.length; i++) {
//			sheet.autoSizeColumn(i);
//		}

		//Write the output to a file
		Path path = Paths.get(filePath);
		if (!Files.exists(path)) {
            
            Files.createDirectory(path);
            System.out.println("Directory created");
        } else {
            
            System.out.println("Directory already exists");
        }
		FileOutputStream fileOut = new FileOutputStream(filePath+fileName+".xls");
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
	}

	public static void showExcelData(List<List<HSSFCell>> sheetData) {
		// Iterates the data and print it out to the console.
		for (List<HSSFCell> data : sheetData) {
			for (int j = 0; j < data.size(); j++) {
				HSSFCell cell = data.get(j);
				System.out.print(cell);
			}
			System.out.println("");
		}
	}
	
//	public static void readCSV(InputStream input) {
//		
//		try {
//
//            while ((line = input.read) != null) {
//
//                // use comma as separator
//                String[] country = line.split(cvsSplitBy);
//
//                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
//
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//	}
}
