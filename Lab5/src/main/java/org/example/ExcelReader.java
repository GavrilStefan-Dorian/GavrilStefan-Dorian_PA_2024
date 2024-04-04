package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.graph4j.GraphBuilder;
import org.graph4j.alg.clique.BronKerboschCliqueIterator;
import org.graph4j.util.Clique;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public ExcelReader(String filename, List<String> allAbilities) {
        getExcelMaxCliques(filename, allAbilities);
    }

    public static void getExcelMaxCliques(String filename, List<String> allAbilities) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filename))) {
            Sheet sheet = workbook.getSheetAt(0);

            Map<Integer, Set<Integer>> personAbilitiesMap = new HashMap<>();
            Map<String, Integer> abilitiesMap = new HashMap<>();

            int abilityIdCounter = 0;

            for(var i : allAbilities)
                abilitiesMap.put(i, abilityIdCounter++);

            for (Row row : sheet) {
                int personId = (int) row.getCell(0).getNumericCellValue();
                String[] abilitiesArray = row.getCell(1).getStringCellValue().split(", ");
                Set<Integer> abilityIds = new HashSet<>();

                for (String ability : abilitiesArray) {
                    int abilityId = abilitiesMap.get(ability);
                    abilityIds.add(abilityId);
                }

                personAbilitiesMap.put(personId, abilityIds);
            }

            var graph = GraphBuilder.empty().buildGraph();

            for (int personId : personAbilitiesMap.keySet())
                graph.addVertex(personId);

            for (Map.Entry<Integer, Set<Integer>> entry1 : personAbilitiesMap.entrySet()) {
                for (Map.Entry<Integer, Set<Integer>> entry2 : personAbilitiesMap.entrySet()) {
                    if (!entry1.getKey().equals(entry2.getKey())) {
                        Set<Integer> commonAbilities = new HashSet<>(entry1.getValue());
                        commonAbilities.retainAll(entry2.getValue());
                        if (!commonAbilities.isEmpty()) {
                            graph.addEdge(entry1.getKey().intValue(), entry2.getKey().intValue());
                        }
                    }
                }
            }

            BronKerboschCliqueIterator cliqueIterator = new BronKerboschCliqueIterator(graph);

            System.out.println("\tBonus\nEmployee IDs Maximal Cliques:\n");
            for (Clique clique : cliqueIterator.getAll()) {
                Set<String> commonAbilities = new HashSet<>(allAbilities);

                for(int personID : clique) {
                    Set<String> personAbilities = new HashSet<>();

                    for(int abilityIndex : personAbilitiesMap.get(personID))
                        personAbilities.add(allAbilities.get(abilityIndex));

                    commonAbilities.retainAll(personAbilities);
                }

                if(!commonAbilities.isEmpty())
                    System.out.println(commonAbilities + " -> " + clique);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
