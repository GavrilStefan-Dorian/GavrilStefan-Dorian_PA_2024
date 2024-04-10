package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelGenerator {

    public ExcelGenerator() {
    }

    public static void generateRandomExcel(String filename, int rows, int maxAbilitiesPerEmployee, List<String> allAbilities) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employee Abilities");

            Random random = new Random();

            for (int i = 0; i < rows; i++) {
                Row row = sheet.createRow(i);
                int employeeId = i + 1;
                row.createCell(0).setCellValue(employeeId);

                int numAbilities = random.nextInt(maxAbilitiesPerEmployee) + 1;
                Set<String> abilities = generateRandomAbilities(numAbilities, allAbilities);
                StringBuilder abilitiesString = new StringBuilder();
                for (String ability : abilities) {
                    abilitiesString.append(ability).append(", ");
                }
                abilitiesString.delete(abilitiesString.length() - 2, abilitiesString.length());
                row.createCell(1).setCellValue(abilitiesString.toString());
            }

            FileOutputStream fileOut = new FileOutputStream(new File(filename));
            workbook.write(fileOut);
            fileOut.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Set<String> generateRandomAbilities(int numAbilities, List<String> allAbilities) {
        Set<String> abilities = new HashSet<>();

        Random random = new Random();
        while (abilities.size() < numAbilities) {
            int index = random.nextInt(allAbilities.size());
            abilities.add(allAbilities.get(index));
        }

        return abilities;
    }
}
