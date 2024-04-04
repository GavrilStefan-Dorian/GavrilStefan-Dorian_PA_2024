package org.example;

public record Document (String title, String fileType) {
    public Document {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title should not be empty.");
        }

        if (fileType == null || fileType.trim().isEmpty()) {
            throw new IllegalArgumentException("File type should not be empty.");
        }
    }
}

