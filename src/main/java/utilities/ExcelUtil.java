package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	private static XSSFSheet excelWSheet;

	private static XSSFWorkbook excelWBook;

	private static XSSFCell Cell;
	private static FileInputStream excelFile;

	private static String filePath;

	private static String sheetName;

	public static void setExcelFile(String sheet) {

		try {

			// Open the Excel file
			
			sheetName = sheet;
			excelFile = new FileInputStream(PropertyManager.getInstance().getExcelFilePath());

			// Access the required test data sheet

			excelWBook = new XSSFWorkbook(excelFile);

			excelWSheet = excelWBook.getSheet(sheetName);

		} catch (Exception e) {

			System.out.println("Error opening the Excel file");

		}

	}

	public static Object[][] getData(String testCaseName, String sheetName)

	{

		setExcelFile(sheetName);
		int startRow = getRowContains(testCaseName);
		int lastCol = excelWSheet.getRow(startRow).getLastCellNum()-1;

		// System.out.println(headerRow);
		
		int lastRow = getLastRowContains(testCaseName, startRow);
		//System.out.println(startRow+":"+lastRow+":"+lastCol);
		String[][] data = new String[lastRow - startRow + 1][lastCol];
		//int iRow = startRow, iCol = 1;
		int dataRowIndex = 0, dataColIndex = 0;
		for (dataRowIndex = 0; dataRowIndex < lastRow - startRow + 1;  dataRowIndex++) {
			for (dataColIndex = 0; dataColIndex < lastCol;  dataColIndex++) {
				//System.out.println(dataRowIndex+":"+dataColIndex);
				data[dataRowIndex][dataColIndex] = getCellData(startRow+dataRowIndex, dataColIndex+1);
			}
		}
		closeFile();
		return data;
	}

	private static int getLastRowContains(String testCaseName, int startRow) {
		// TODO Auto-generated method stub
		int index = startRow;

		do {
			index++;
		} while (getCellData(index, 0).equals(testCaseName));

		return index - 1;
	}

	private static int getRowContains(String testCaseName) {
		// TODO Auto-generated method stub
		int start;

		try {

			int rowCount = getRowUsed();

			for (start = 0; start < rowCount; start++) {

				if (getCellData(start, 0).equalsIgnoreCase(testCaseName)) {

					break;

				}

			}

			return start;

		} catch (Exception e) {

			throw (e);

		}

	}


	private static String getCellData(int RowNum, int ColNum) {

		try {

			Cell = excelWSheet.getRow(RowNum).getCell(ColNum);

			String CellData = Cell.getStringCellValue();
			//System.out.println(CellData);
			return CellData;

		} catch (Exception e) {

			return "";

		}

	}

	private static int getRowUsed() {

		try {

			int RowCount = excelWSheet.getLastRowNum();

			return RowCount;

		} catch (Exception e) {

			throw (e);

		}

	}

	public static void setData(String key, String value) {
		// TODO Auto-generated method stub
		setExcelFile( sheetName);
		int row = getRowForKey(key);

		if (row == 0) {
			row = getRowUsed() + 1;
			excelWSheet.createRow(row);
			excelWSheet.getRow(row).createCell(0).setCellValue(key);
			excelWSheet.getRow(row).createCell(1).setCellValue(value);
		} else {
			excelWSheet.getRow(row).getCell(1).setCellValue(value);
		}
		closeFile();
		try {
			FileOutputStream outFile = new FileOutputStream(new File(filePath));
			excelWBook.write(outFile);
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static int getRowForKey(String key) {
		// TODO Auto-generated method stub
		int lastRow = getRowUsed();
		int index = 1;

		do {
			if (getCellData(index, 0).equalsIgnoreCase(key)) {
				return index;
			}
			index++;
		} while (index <= lastRow);
		return 0;
	}

	public static void closeFile() {
		try {
			excelFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error closing the file");
		}
	}

}
