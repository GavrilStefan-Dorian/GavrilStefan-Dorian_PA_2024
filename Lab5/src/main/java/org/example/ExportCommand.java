package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public record ExportCommand(Repository repo, String path) implements Command {
    @Override
    public void execute() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(
                    new File(path),
                    repo);
        }
}
