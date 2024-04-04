package org.example;

import freemarker.template.TemplateException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, TemplateException {
        Main app = new Main();

        List<String> allAbilities = Arrays.asList("SoftDev",
                "ProjectManagement",
                "DatabaseAdmin",
                "NetworkAdmin",
                "SysAdmin",
                "WebDev",
                "MobileDev",
                "UI/UX");

        ExcelGenerator.generateRandomExcel("random_dataset.xlsx", 15, 4, allAbilities);
        ExcelReader.getExcelMaxCliques("random_dataset.xlsx", allAbilities);
        app.simulateShell();
    }
    private void simulateShell() throws IOException, TemplateException {
        var service = new RepositoryService();
        System.out.println("\n\tHomework");

        System.out.println("List of commands:\n\t1.view <file_path>\n\t2.export <repo_path> <output.json>\n\t3.report <repo_path> <output.html>\n\t4.exit");

        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));


        while (true) {
            System.out.print("\nEnter command: ");
            commandLine = console.readLine();

//            if (commandLine.isEmpty())
//                continue;

            String[] split = commandLine.split(" ",2);
            String command = split[0];

            if (command.equals("view")) {
                service.view(split[1]);
            }

            if (command.equals("export")) {
                String[] arguments = split[1].split(" ");
                service.export(new Repository(arguments[0]), arguments[1]);
            }

            if (command.equals("report")) {
                String[] arguments = split[1].split(" ");
                service.report(new Repository(arguments[0]), arguments[1]);
            }

            if (commandLine.equals("exit")) {
                System.exit(0);
            }
        }
    }
}
