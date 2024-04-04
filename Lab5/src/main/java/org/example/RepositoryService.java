package org.example;
import freemarker.template.*;
import java.io.*;


public class RepositoryService {
    public void export(Repository repository, String outputPath)
            throws IOException {
        ExportCommand expCmd = new ExportCommand(repository, outputPath);
        expCmd.execute();
    }

    public void report(Repository repository, String outputPath) throws IOException, TemplateException {
        ReportCommand repCmd = new ReportCommand(repository, outputPath);
        repCmd.execute();
        view(outputPath);
    }
    public void view(String path) throws IOException {
        ViewCommand viewCmd = new ViewCommand(path);
        viewCmd.execute();
    }
    public void print(Repository repo) {
    }
}