package org.example;

public class InvalidCommand extends Exception {
    public InvalidCommand() {
        super("Invalid command.");
    }
}