package org.example;

public record Person (int id, String name) {
    public Person {
        if (id <= 0) {
            throw new IllegalArgumentException("Id should be a positive number: " + id);
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name should not be empty.");
        }
    }
}