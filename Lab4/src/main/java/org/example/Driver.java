package org.example;

import java.util.List;

public class Driver extends Person {
    private List<Destination> pathToDestination;

    public Driver(String name, int age, Destination destination, List<Destination> pathToDestination) {
        super(name, age, destination);
        this.pathToDestination = pathToDestination;
    }
    public List<Destination> getPathToDestination(){
        return pathToDestination;
    }
}
