package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Repository {
    private String directory;
    private Map<Person, List<Document>> documents = new HashMap<>();

    public Repository (String directory) {
        this.directory = directory;
        loadDocuments();
    }

    private void loadDocuments() {
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths.forEach(file -> {
                try {
                    if(Files.isDirectory(file)) {
                        String dirName = file.getFileName().toString();
                        if(dirName.compareTo(directory.substring(directory.lastIndexOf('\\') + 1)) != 0) {
                            String[] split = dirName.split("_");
                            String personName = split[0];

                            int personId = Integer.parseInt(split[1]);
                            Person person = new Person(personId, personName);
                            documents.putIfAbsent(person, new ArrayList<>());
                        }
                    } else if(Files.isRegularFile(file)) {
                        String parentDirName = file.getParent().getFileName().toString();
                        String personName = parentDirName.substring(0, parentDirName.lastIndexOf('_'));
                        int personId = Integer.parseInt(parentDirName.substring(parentDirName.lastIndexOf('_') + 1));

                        Person person = new Person(personId, personName);

                        String docName = file.getFileName().toString();
                        String fileName = docName.substring(0, docName.lastIndexOf('.'));
                        String fileType = docName.substring(docName.lastIndexOf('.') +1);
                        Document document = new Document(fileName, fileType);
                        documents.get(person).add(document);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error processing directory name: " + file + " - " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void displayContents() {
        for (Map.Entry<Person, List<Document>> entry : documents.entrySet()) {
            Person person = entry.getKey();
            List<Document> docs = entry.getValue();
            System.out.println("Person: " + person.name() + " (ID: " + person.id() + ")");
            for (Document doc : docs) {
                System.out.println("\tDocument: " + doc.title() + " (Type: " + doc.fileType() + ")");
            }
        }
    }

    @JsonProperty("directory")
    public String getDirectory() {
        return directory;
    }
    @JsonProperty("documents")
    public Map<Person, List<Document>> getDocuments() {
        return documents;
    }
}