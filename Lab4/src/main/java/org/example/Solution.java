package org.example;

import org.graph4j.alg.matching.HopcroftKarpMaximumMatching;

import java.util.*;

public class Solution {
    Problem problem;

    public Solution(Problem problem) {
        this.problem = problem;
    }
    public Map<String, String> greedyMatch() {
        Map<String, String> matches = new HashMap<>();

        List<Person> passengers = new ArrayList<>(problem.getListOfPassengers());

        for (Person driver : problem.getListOfDrivers()) {
            for (Person passenger : passengers) {

                    if (Objects.equals(driver.getDestination().getName(), passenger.getDestination().getName()) || ((Driver) driver).getPathToDestination().contains(passenger.getDestination())) {
                        matches.put("Driver: " + driver.getName(), "Passenger: " + passenger.getName());
                        passengers.remove(passenger);
                        break;
                    }
                }
        }
        return matches;
    }

    public Map<String, String> hopcroftKarpMatch() {
        var graph = problem.getGraph();
        var matches = new HopcroftKarpMaximumMatching(graph);
        var matching = matches.getMatching();

        Map<String, String> driverPassengerMap = new HashMap<>();

        int driversCount = problem.getListOfDrivers().size();

        //Iterate edges to create driver-passenger mapping
        for (int driverIndex = 0; driverIndex < driversCount; driverIndex++) {
            int passengerIndex = matching.mate(driverIndex);

            if(passengerIndex != -1) {
                String driverName = problem.getListOfDrivers().get(driverIndex).getName();
                String passengerName = problem.getListOfPassengers().get(passengerIndex - driversCount).getName();
                driverPassengerMap.put("Driver: " + driverName, "Passenger: " + passengerName);
            }
        }

        return driverPassengerMap;
    }

}
