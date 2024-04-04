package org.example;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ViewCommand implements Command {
    private final String path;

    public ViewCommand(String path) {
        this.path = path;
    }

    @Override
    public void execute() throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File(path));
    }
}
