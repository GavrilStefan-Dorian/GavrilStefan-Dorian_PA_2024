package org.example;

import freemarker.template.TemplateException;

import java.io.IOException;

public interface Command {
    public void execute() throws IOException, TemplateException;
}
