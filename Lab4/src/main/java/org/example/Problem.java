package org.example;

import org.graph4j.Graph;

import java.util.*;

public class Problem {
    private List<Destination> listOfDestinations;
    private List<Destination> listOfDestinationsPassed;
    private Map<Destination, List<String>> destinationMap;
    private List<Person> listOfPassengers;
    private List<Person> listOfDrivers;

    private Graph graph;

    public Problem(List<Person> listOfPersons) {
        listOfDestinations = new ArrayList<>();

        for(Person person : listOfPersons) {
            listOfDestinations.add(person.getDestination());
        }

        destinationMap = new HashMap<>();
        listOfPassengers = new ArrayList<>();
        listOfDrivers = new ArrayList<>();

        createDriversSet(listOfPersons);
        createPassengersList(listOfPersons);
        createDestinationsList(listOfPersons);

    }

    public Problem(List<Person> listOfPersons, Graph graph) {
        this(listOfPersons);
        this.graph = graph;
    }

    private void createPassengersList(List<Person> listOfPersons) {
        listOfPersons.stream()
                .filter(p -> p instanceof Passenger)
                .forEach(listOfPassengers::add);
    }

    private void createDriversSet(List<Person> listOfPersons) {
        listOfPersons.stream()
                .filter(p -> p instanceof Driver)
                .forEach(listOfDrivers::add);
    }

    private void createDestinationsList(List<Person> listOfPersons) {
        // collecting all the destinations
        listOfDestinationsPassed = listOfPersons.stream()
                .filter(p -> p instanceof Driver)
                .flatMap(list ->  ((Driver) list).getPathToDestination().stream())
                //TODO: only drivers
                //TODO: use flatMap
                .toList();
    }

    public List<Destination> GetDestinationsList() {
        return listOfDestinationsPassed;
    }

    public void createMapDestinationToPeople() {
        for(Destination destination : listOfDestinations) {
            destinationMap.put(destination, new ArrayList<>());

            listOfDrivers.stream()
                    .filter(p -> p.getDestination().equals(destination))
                    .forEach(p -> destinationMap.get(destination).add(p.getName()));

            listOfPassengers.stream()
                    .filter(p -> p.getDestination().equals(destination))
                    .forEach(p -> destinationMap.get(destination).add(p.getName()));
        }
    }

    public Map<Destination, List<String>> getDestinationMap() {
        return destinationMap;
    }

    public List<Person> getListOfPassengers() {
        return listOfPassengers;
    }

    public List<Person> getListOfDrivers() {
        return listOfDrivers;
    }

    public Graph getGraph() {
        return graph;
    }

    public void addDriver(Driver driver) {
        listOfDrivers.add(driver);
    }
    public void addPassenger(Passenger passenger) {
        listOfDrivers.add(passenger);
    }

}
