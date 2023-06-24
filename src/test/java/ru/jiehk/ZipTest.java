package ru.jiehk;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipTest {

    ClassLoader cl = ZipTest.class.getClassLoader();

    @Test
    void filesFromZipTest() throws Exception {
        try (ZipFile zipFile = new ZipFile(new File("src/test/resources/TestFiles.zip"));
             ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("TestFiles.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                List<String> entryNameArray = List.of(entry.getName().split("\\."));
                String lastElement = entryNameArray.get(entryNameArray.size() - 1);
                switch (lastElement) {
                    case ("xls"):
                        try (InputStream xlsIs = zipFile.getInputStream(entry)) {
                            XLS xls = new XLS(xlsIs);
                            assertThat(
                                    xls.excel.getSheetAt(0)
                                            .getRow(1)
                                            .getCell(4)
                                            .getStringCellValue()
                            ).isEqualTo("United States");
                        }
                        break;
                    case ("pdf"):
                        try (InputStream pdfIs = zipFile.getInputStream(entry)) {
                            PDF pdf = new PDF(pdfIs);
                            assertThat(pdf.text).contains("A Simple PDF File");
                        }
                        break;
                    case ("csv"):
                        try (InputStream csvIs = zipFile.getInputStream(entry);
                             CSVReader reader = new CSVReader(new InputStreamReader(csvIs))) {
                            List<String[]> content = reader.readAll();
                            String[] row = content.get(1);
                            assertThat(row[0]).isEqualTo("booker12");
                            assertThat(row[1]).isEqualTo("rachel@example.com");
                        }
                        break;
                    default:
                        Assertions.fail("Неизвестный формат файла");
                }
            }
        }
    }
}

