package org.example;

import freemarker.template.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand implements Command {
    private freemarker.template.Configuration cfg;
    Repository repo;
    String path;
    public ReportCommand(Repository repository, String path) throws IOException {
        repo = repository;
        this.path = path;
        cfg = new Configuration(freemarker.template.Configuration.VERSION_2_3_31);

        cfg.setDirectoryForTemplateLoading(new File("templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
    @Override
    public void execute() throws IOException, TemplateException {
        Template template = cfg.getTemplate("html_template.ftl");

        Map<String, Object> data = new HashMap<>();
        data.put("repository", repo);

        File output = new File(path);
        Writer fileWriter = new FileWriter(output);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        template.process(data, bufferedWriter);

        bufferedWriter.flush();
        bufferedWriter.close();

    }
}
