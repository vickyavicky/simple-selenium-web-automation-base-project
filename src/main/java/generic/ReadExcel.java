package generic;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadExcel {

    public static void main (String [] args) throws IOException, Exception, Throwable {

        //Path of the excel file
        FileInputStream fileInputStream = new FileInputStream("/replace/with/path/to/excel.xlsx/file");
        //Creating a workbook
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet ("replace_with_sheet_name");

        //Scenario 1 --> replace with relevant explanation of what data is being fetched
        //replace with specific variable names and appropriate cell locations
        String item1 = sheet.getRow (2).getCell (7).getStringCellValue ( );
        String item2 = sheet.getRow (4).getCell (8).getStringCellValue ( );


    }
}
